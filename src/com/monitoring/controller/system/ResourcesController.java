package com.monitoring.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitoring.annotation.SystemLog;
import com.monitoring.controller.index.BaseController;
import com.monitoring.entity.ResFormMap;
import com.monitoring.entity.ResRoleFormMap;
import com.monitoring.exception.SystemException;
import com.monitoring.util.Common;
import com.monitoring.util.JsonUtils;
import com.monitoring.util.TreeObject;
import com.monitoring.util.TreeUtil;

/**
 * 
 * @author lanyuan 
 * @date  2015-11-19
 * @Email: mmm333zzz520@163.com
 * @version 4.1v
 */
@Controller
@RequestMapping("/system/resources/")
public class ResourcesController extends BaseController {
	@RequestMapping("addEntity")
	@ResponseBody
	@Transactional
	@SystemLog(module="System Management",methods="Resource management-Add resource")//凡需要处理业务逻辑的.都需要记录操作日志
	public String addEntity() throws Exception {
		try {
			ResFormMap resFormMap = getFormMap(ResFormMap.class);
			Object o = resFormMap.get("ishide");
			if (null == o) {
				resFormMap.put("ishide", "0");
			}
			if("2".equals(resFormMap.getStr("type"))){
				String btn = resFormMap.getStr("buttom");
				if(Common.isNotEmpty(btn)){
					List<Map<String, Object>> libtn=JsonUtils.parseJSONList(btn);
					for (Map<String, Object> map : libtn) {
						resFormMap.put("btn",map.get("btnId")+","+map.get("btnName")+","+map.get("btnClass")+","+map.get("btnValue"));
					}
					resFormMap.save();
				}
			}else if("1".equals(resFormMap.getStr("type"))){
				resFormMap.save();
				String btn = resFormMap.getStr("buttom");
				if(Common.isNotEmpty(btn)){
					List<Map<String, Object>> libtn=JsonUtils.parseJSONList(btn);
					List<ResFormMap> res = new ArrayList<ResFormMap>();
					ResFormMap rMap = null;
					int level=resFormMap.getInt("level");
					for (Map<String, Object> map : libtn) {
						if(null!=map.get("btnName")&&!"".equals(map.get("btnName")+"")){
							rMap = new ResFormMap();
							rMap.put("parentId", resFormMap.get("id"));
							rMap.put("name", map.get("btnValue"));
							rMap.put("type", 2);
							rMap.put("resKey", resFormMap.get("resKey")+"_"+map.get("btnId"));
							rMap.put("resUrl", map.get("btnUrl"));
							rMap.put("btn",map.get("btnId")+","+map.get("btnName")+","+map.get("btnClass")+","+map.get("btnValue"));
							rMap.put("level", level);
							level++;
							res.add(rMap);
						}
					}
					if(res.size()>0)
					rMap.batchSave(res);
				}
			}else{
				resFormMap.save();
			}
		} catch (Exception e) {
			//try catch 必须抛出自定义异常,否则事务失效!   #### lanyuan ####
			String results = "{\"results\":\"error\",\"message\":\""+e+"\"}";
			throw new SystemException(results);
		}
		
		return "success";
	}
	 /**
		 * 更新菜单
		 * 
		 * @param model
		 * @param Map
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
		@RequestMapping("editEntity")
		@SystemLog(module="System Management",methods="Resource management-Modify resource")//凡需要处理业务逻辑的.都需要记录操作日志
		public String editEntity(Model model) throws Exception {
			try {
				ResFormMap resFormMap = getFormMap(ResFormMap.class);
				Object o = resFormMap.get("ishide");
				if (null == o) {
					resFormMap.put("ishide", "0");
				}
				String btnId = getPara("btnId");
				String btnName = getPara("btnName");
				String btnClass = getPara("btnClass");
				String btnValue = getPara("btnValue");
				if(Common.isNotEmpty(btnId))
				resFormMap.put("btn", btnId+","+btnName+","+btnClass+","+btnValue);
				resFormMap.update();
			} catch (Exception e) {
				//try catch 必须抛出自定义异常,否则事务失效!   #### lanyuan ####
				String results = "{\"results\":\"error\",\"message\":\""+e+"\"}";
				throw new SystemException(results);
			}
			return "success";
		}
	
	@ResponseBody
	@RequestMapping("reslists")
	public List<TreeObject> reslists(Model model) throws Exception {
		ResFormMap resFormMap = getFormMap(ResFormMap.class);
		List<ResFormMap> mps = resFormMap.findByNames();
		List<TreeObject> list = new ArrayList<TreeObject>();
		for (ResFormMap map : mps) {
			TreeObject ts = new TreeObject();
			Common.flushObject(ts, map);
			list.add(ts);
		}
		TreeUtil treeUtil = new TreeUtil();
		List<TreeObject> ns = treeUtil.getChildTreeObjects(list, 0, "　");
		return ns;
	}
	
	@ResponseBody
	@RequestMapping("addRoleRes")
	@SystemLog(module="System Management",methods="Role management-Modify permission")//凡需要处理业务逻辑的.都需要记录操作日志
	public String addUserRes() throws Exception {
		try {
			String g = getPara("roleId");
			String roleId[] = g.split(",");
			String resId = getPara("resId");
			new ResRoleFormMap().deleteByAttribute("roleId", g);
			for (String rid : roleId) {
				if(Common.isNotEmpty(rid)){
					String[] s = Common.trimComma(resId).split(",");
					List<ResRoleFormMap> resRoleFormMaps = new ArrayList<ResRoleFormMap>();
					ResRoleFormMap formMap = null;
					for (String re : s) {
						formMap = new ResRoleFormMap();
						formMap.put("resId", re);
						formMap.put("roleId", rid);
						resRoleFormMaps.add(formMap);
					}
					formMap.batchSave(resRoleFormMaps);
				}
			}
		} catch (Exception e) {
			//try catch 必须抛出自定义异常,否则事务失效!   #### lanyuan ####
			String results = "{\"results\":\"error\",\"message\":\""+e+"\"}";
			throw new SystemException(results);
		}
		return "success";
	}
	@RequestMapping("permissions" )
	public String edit(Model model) throws Exception {
		return Common.BACKGROUND_PATH +"/system/resources/permissions";
	}
}