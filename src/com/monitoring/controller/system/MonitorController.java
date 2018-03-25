package com.monitoring.controller.system;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hyperic.sigar.Sigar;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitoring.annotation.SystemLog;
import com.monitoring.controller.index.BaseController;
import com.monitoring.entity.ParamFormMap;
import com.monitoring.entity.ServerInfoExcelBean;
import com.monitoring.entity.ServerInfoFormMap;
import com.monitoring.plugin.PageView;
import com.monitoring.util.Common;
import com.monitoring.util.JavaMail;
import com.monitoring.util.SystemInfo;

import mtime.kits.excel.ExportExcel;
import mtime.kits.excel.bean.XBeanExport;

/**
 * 
 * @author lanyuan 2015-11-19
 * @Email: mmm333zzz520@163.com
 * @version 4.1v
 */
@Controller
@RequestMapping("/system/monitor")
public class MonitorController extends BaseController {
	
	private static volatile ConcurrentHashMap<Integer,Object> cache=new ConcurrentHashMap<Integer,Object>();
	
	private static ExecutorService pool=Executors.newFixedThreadPool(8);
	
	@RequestMapping("/info")
	public String info(Model model) throws Exception {
		ParamFormMap paramFormMap=findHasHMap(ParamFormMap.class);
		List<ParamFormMap> list=paramFormMap.findByAll();
		if(cache.isEmpty()){
			for(ParamFormMap map:list){
				cache.put(map.getInt("id"), map.get("value"));
			}
		}
		model.addAttribute("cpu", cache.get(1));
		model.addAttribute("jvm", cache.get(3));
		model.addAttribute("ram", cache.get(2));
		model.addAttribute("toEmail", cache.get(4));
		return Common.BACKGROUND_PATH + "/system/monitor/info";
	}
	
	@RequestMapping("/monitor")
	public String monitor() throws Exception {
		return Common.BACKGROUND_PATH + "/system/monitor/monitor";
	}
	
	@RequestMapping("/systemInfo")
	public String systemInfo(Model model) throws Exception {
		model.addAttribute("systemInfo", SystemInfo.SystemProperty());
		return Common.BACKGROUND_PATH + "/system/monitor/systemInfo";
	}
	
	@ResponseBody
	@RequestMapping("/usage")
	public ServerInfoFormMap usage(Model model) throws Exception {
		ServerInfoFormMap map=SystemInfo.usage(new Sigar());
		try{
			long cpu=map.getLong("cpuUsage");
			long jvm=map.getLong("jvmUsage");
			long ram=map.getLong("ramUsage");
			pool.submit(new EmailAction(cpu, jvm, ram));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	static class EmailAction implements Runnable{

		private long cpu;
		private long jvm;
		private long ram;
		
		public EmailAction(long cpu,long jvm,long ram){
			this.cpu=cpu;
			this.jvm=jvm;
			this.ram=ram;
		}
		@Override
		public void run() {
			JavaMail mailServer=new JavaMail();
			if(cpu>Long.valueOf(cache.get(1).toString())){
				String text="Current CPU usage:"+cpu+"%, over preset value:"+cache.get(1).toString()+"%, Please deal with it in time. ";
				saveMonitorInfo("cpu",text,String.valueOf(cpu),cache.get(1).toString(),1);
				mailServer.doSendHtmlEmail("CPU monitoring warning",text, cache.get(4).toString());
			}
			if(jvm>Long.valueOf(cache.get(3).toString())){
				String text= "Current JVM RAM usage:"+jvm+"%, over preset value:"+cache.get(3).toString()+"%, please deal with it in time。";
				saveMonitorInfo("jvm",text,String.valueOf(jvm),cache.get(3).toString(),3);
				mailServer.doSendHtmlEmail("JVM monitoring warning",text, cache.get(4).toString());
			}
			if(ram>Long.valueOf(cache.get(2).toString())){
				String text="Current server RAM usage:"+ram+"%, over preset value:"+cache.get(2).toString()+"%,Please deal with it in time。";
				saveMonitorInfo("Server RAM",text,String.valueOf(ram),cache.get(2).toString(),2);
				mailServer.doSendHtmlEmail("Server RAM monitoring warning",text, cache.get(4).toString());
			}
		}
		
	}
	
	public static void saveMonitorInfo(String type,String text,String currentValue,String threshold,int index){
		try{
			ServerInfoFormMap log=new ServerInfoFormMap();
			log.put("type", type);
			log.put("threshold", cache.get(index).toString()+"%");
			log.put("currentValue", currentValue+"%");
			log.put("emailText", text);
			log.put("email", cache.get(4).toString());
			log.save();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改配置　
	 * @param request
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
    @ResponseBody
	@RequestMapping("/modifySer")
    @SystemLog(module="Monitoring System",methods="Real-time monitoring-Warning parameter setting")
    public Object modifySer(Integer id,String value) throws Exception{
    	Map<String, Object> dataMap = new HashMap<String,Object>();
    	try {
    		ParamFormMap paramFormMap=getFormMap(ParamFormMap.class);
    		paramFormMap.update();
    		cache.put(id, value);
    		dataMap.put("flag", true);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", false);
		}
		return dataMap;
    }
	
	@RequestMapping("/findMonitorLogPages")
	@ResponseBody
	public PageView findMonitorLogPages(){
		ServerInfoFormMap logMap=findHasHMap(ServerInfoFormMap.class);
		if(!StringUtils.isEmpty(logMap.getStr("operTimeStart"))){
			logMap.put("operTimeStart", logMap.getStr("operTimeStart")+" 00:00:00");
		}
		if(!StringUtils.isEmpty(logMap.getStr("operTimeEnd"))){
			logMap.put("operTimeEnd", logMap.getStr("operTimeEnd")+" 23:59:59");
		}
		PageView pageView = getPageView(logMap);
		logMap.put("mapper_id", "SelfMapper.findSelfMonitorPage");
		List<ServerInfoFormMap> list=logMap.findByPage();
		pageView.setRecords(list);
		return pageView;
	}
	
	@RequestMapping("/exportMonitorDataByPage")
	public void exportMonitorDataByPage(HttpServletResponse response) throws Exception{
		ServerInfoFormMap logMap=findHasHMap(ServerInfoFormMap.class);
		if(!StringUtils.isEmpty(logMap.getStr("operTimeStart"))){
			logMap.put("operTimeStart", logMap.getStr("operTimeStart")+" 00:00:00");
		}
		if(!StringUtils.isEmpty(logMap.getStr("operTimeEnd"))){
			logMap.put("operTimeEnd", logMap.getStr("operTimeEnd")+" 23:59:59");
		}
		PageView pageView = getPageView(logMap);
		logMap.put("mapper_id", "SelfMapper.findSelfMonitorPage");
		List<ServerInfoFormMap> list=logMap.findByPage();
		PageView newPageView=new PageView();
		newPageView.setPageSize((int)pageView.getRowCount());
		logMap.put("pageSize",pageView.getRowCount());
		logMap.put("paging", newPageView);
		List<ServerInfoFormMap> dataList=logMap.findByPage();
		if(CollectionUtils.isEmpty(dataList)){
			dataList=new ArrayList<ServerInfoFormMap>();
		}
		List<ServerInfoExcelBean> exportList=new ArrayList<ServerInfoExcelBean>();
		for(int i=0;i<dataList.size();i++){
			ServerInfoExcelBean bean=new ServerInfoExcelBean();
		    bean.setType(dataList.get(i).getStr("type"));
		    bean.setThreshold(dataList.get(i).getStr("threshold"));
		    bean.setCurrentValue(dataList.get(i).getStr("currentValue"));
		    bean.setEmailText(dataList.get(i).getStr("emailText"));
			bean.setEmail(dataList.get(i).getStr("email"));
			bean.setOperTime(dataList.get(i).getStr("operTime"));
			exportList.add(bean);
		}
		XBeanExport xbean = ExportExcel.BeanExport(ServerInfoExcelBean.class);
		xbean.createBeanSheet("Warning monitrong data", null,ServerInfoExcelBean.class).addData(exportList);
		xbean.write("Warning monitoring file-"+System.currentTimeMillis(), response);
	}
}