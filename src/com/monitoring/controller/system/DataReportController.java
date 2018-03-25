package com.monitoring.controller.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitoring.controller.index.BaseController;
import com.monitoring.entity.DataFormMap;
import com.monitoring.entity.LogFormMap;
import com.monitoring.entity.LoginLogExcelBean;
import com.monitoring.entity.OptLogExeclBean;
import com.monitoring.entity.UserLoginFormMap;
import com.monitoring.plugin.PageView;

import mtime.kits.excel.ExportExcel;
import mtime.kits.excel.bean.XBeanExport;

@Controller
@RequestMapping("/report/data")
public class DataReportController extends BaseController{
	
	private static final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   
	@RequestMapping("/findByPages")
	@ResponseBody
	public PageView findByPages() throws Exception {
		DataFormMap formMap=findHasHMap(DataFormMap.class);
		PageView pageView = getPageView(formMap);
		List<DataFormMap> list=this.getFormatData(formMap,pageView);
		pageView.setRecords(list);
		return pageView;
	}
	
	@RequestMapping("/loginLogs/findByPages")
	@ResponseBody
	public PageView loginLogsFindByPages(){
		UserLoginFormMap logMap=findHasHMap(UserLoginFormMap.class);
		if(!StringUtils.isEmpty(logMap.getStr("operTimeStart"))){
			logMap.put("operTimeStart", logMap.getStr("operTimeStart")+" 00:00:00");
		}
		if(!StringUtils.isEmpty(logMap.getStr("operTimeEnd"))){
			logMap.put("operTimeEnd", logMap.getStr("operTimeEnd")+" 23:59:59");
		}
		PageView pageView = getPageView(logMap);
		logMap.put("mapper_id", "SelfMapper.findSelfLoginLogPage");
		List<UserLoginFormMap> list=logMap.findByPage();
		pageView.setRecords(list);
		return pageView;
	}
	
	@RequestMapping("/loginLogs/exportLogDataByPage")
	public void exportLoginLogData(HttpServletResponse response) throws Exception{
		UserLoginFormMap logMap=findHasHMap(UserLoginFormMap.class);
		if(!StringUtils.isEmpty(logMap.getStr("operTimeStart"))){
			logMap.put("operTimeStart", logMap.getStr("operTimeStart")+" 00:00:00");
		}
		if(!StringUtils.isEmpty(logMap.getStr("operTimeEnd"))){
			logMap.put("operTimeEnd", logMap.getStr("operTimeEnd")+" 23:59:59");
		}
		PageView pageView = getPageView(logMap);
		logMap.put("mapper_id", "SelfMapper.findSelfLoginLogPage");
		List<UserLoginFormMap> list=logMap.findByPage();
		PageView newPageView=new PageView();
		newPageView.setPageSize((int)pageView.getRowCount());
		logMap.put("pageSize",pageView.getRowCount());
		logMap.put("paging", newPageView);
		List<UserLoginFormMap> dataList=logMap.findByPage();
		if(CollectionUtils.isEmpty(dataList)){
			dataList=new ArrayList<UserLoginFormMap>();
		}
		List<LoginLogExcelBean> exportList=new ArrayList<LoginLogExcelBean>();
		for(int i=0;i<dataList.size();i++){
			LoginLogExcelBean bean=new LoginLogExcelBean();
			bean.setAccountName(dataList.get(i).getStr("accountName"));
			bean.setLoginTime(dataList.get(i).getStr("loginTime"));
			bean.setLoginIP(dataList.get(i).getStr("loginIP"));
			exportList.add(bean);
		}
		XBeanExport xbean = ExportExcel.BeanExport(LoginLogExcelBean.class);
		xbean.createBeanSheet("Login log data", null,LoginLogExcelBean.class).addData(exportList);
		xbean.write("Login log file-"+System.currentTimeMillis(), response);
		
	}
	
	@RequestMapping("/optLogs/findOptLogsByPage")
	@ResponseBody
	public PageView findOptLogsByPage(){
		LogFormMap logMap=findHasHMap(LogFormMap.class);
		if(!StringUtils.isEmpty(logMap.getStr("operTimeStart"))){
			logMap.put("operTimeStart", logMap.getStr("operTimeStart")+" 00:00:00");
		}
		if(!StringUtils.isEmpty(logMap.getStr("operTimeEnd"))){
			logMap.put("operTimeEnd", logMap.getStr("operTimeEnd")+" 23:59:59");
		}
		PageView pageView = getPageView(logMap);
		logMap.put("mapper_id", "SelfMapper.findSelfOptLogPage");
		List<LogFormMap> list=logMap.findByPage();
		pageView.setRecords(list);
		return pageView;
	}
	
	@RequestMapping("/optLogs/exportLogDataByPage")
	public void exportOptLogData(HttpServletResponse response) throws Exception{
		LogFormMap logMap=findHasHMap(LogFormMap.class);
		if(!StringUtils.isEmpty(logMap.getStr("operTimeStart"))){
			logMap.put("operTimeStart", logMap.getStr("operTimeStart")+" 00:00:00");
		}
		if(!StringUtils.isEmpty(logMap.getStr("operTimeEnd"))){
			logMap.put("operTimeEnd", logMap.getStr("operTimeEnd")+" 23:59:59");
		}
		PageView pageView = getPageView(logMap);
		logMap.put("mapper_id", "SelfMapper.findSelfOptLogPage");
		List<LogFormMap> list=logMap.findByPage();
		PageView newPageView=new PageView();
		newPageView.setPageSize((int)pageView.getRowCount());
		logMap.put("pageSize",pageView.getRowCount());
		logMap.put("paging", newPageView);
		List<LogFormMap> dataList=logMap.findByPage();
		if(CollectionUtils.isEmpty(dataList)){
			dataList=new ArrayList<LogFormMap>();
		}
		List<OptLogExeclBean> exportList=new ArrayList<OptLogExeclBean>();
		for(int i=0;i<dataList.size();i++){
			OptLogExeclBean bean=new OptLogExeclBean();
			bean.setAccountName(dataList.get(i).getStr("accountName"));
			bean.setActionTime(dataList.get(i).getStr("actionTime"));
			bean.setDescription(dataList.get(i).getStr("description"));
			bean.setMethods(dataList.get(i).getStr("methods"));
			bean.setModule(dataList.get(i).getStr("module"));
			bean.setOperTime(dataList.get(i).getStr("operTime"));
			bean.setUserIP(dataList.get(i).getStr("userIP"));
			exportList.add(bean);
		}
		XBeanExport xbean = ExportExcel.BeanExport(OptLogExeclBean.class);
		xbean.createBeanSheet("Operation log data", null,OptLogExeclBean.class).addData(exportList);
		xbean.write("Operation log file-"+System.currentTimeMillis(), response);
	}
	
	private List<DataFormMap> getFormatData(DataFormMap formMap,PageView pageView){
		
		if(null!=formMap.get("type")&&formMap.getInt("type")!=0){
			formMap.put("type", formMap.getInt("type"));
		}else{
			formMap.remove("type");
		}
		if(null!=formMap.get("startDate")){
			formMap.put("startTime", formMap.getStr("startDate"));
		}
		if(null!=formMap.get("endDate")){
			formMap.put("endTime", formMap.getStr("endDate"));
		}
		formMap.put("pageIndex",pageView.getStartPage());
		formMap.put("pageSize", pageView.getPageSize());
		formMap.put("mapper_id", "logDataMapper.findLogDataByParam");
		
		List<DataFormMap> list=formMap.findByNames();
		if(null!=list&&!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				DataFormMap data=list.get(i);
				data.put("createTime", format.format(data.getDate("createTime")));
			}
		}else{
			list=new ArrayList<DataFormMap>();
		}
		return list;
	}
}
