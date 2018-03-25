package com.monitoring.controller.system;

import java.util.ArrayList;
import java.util.List;



import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitoring.annotation.SystemLog;
import com.monitoring.controller.index.BaseController;
import com.monitoring.entity.UserFormMap;
import com.monitoring.entity.UserRoleFormMap;
import com.monitoring.exception.SystemException;
import com.monitoring.util.Common;
import com.monitoring.util.PasswordHelper;

/**
 * 
 * @author lanyuan 2015-11-19
 * @Email: mmm333zzz520@163.com
 * @version 4.1v
 */
@Controller
@RequestMapping("/system/user/")
public class UserController extends BaseController {
	
	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(module="System Management",methods="User management-Add user")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional
	public String addEntity(String txtSelect){
		try {
			UserFormMap userFormMap = getFormMap(UserFormMap.class).validate();
			userFormMap.put("txtSelect", txtSelect);
			PasswordHelper passwordHelper = new PasswordHelper();
			userFormMap.put("password",userFormMap.getStr("password"));
			passwordHelper.encryptPassword(userFormMap);
			userFormMap.save();
			if (!Common.isEmpty(txtSelect)) {
				String[] txt = txtSelect.split(",");
				List<UserRoleFormMap> ulis = new ArrayList<UserRoleFormMap>();
				UserRoleFormMap userRoleFormMap = null;
				for (String roleId : txt) {
					userRoleFormMap = new UserRoleFormMap();
					userRoleFormMap.put("userId", userFormMap.get("id"));
					userRoleFormMap.put("roleId", roleId);
					ulis.add(userRoleFormMap);
				}
				userRoleFormMap.batchSave(ulis);
			}
		} catch (Exception e) {
			//try catch 必须抛出自定义异常,否则事务失效!   #### lanyuan ####
			String results = "{\"results\":\"error\",\"message\":\""+e+"\"}";
			throw new SystemException(results);
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("editEntity")
	@SystemLog(module="System Management",methods="User management-Modify user")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional
	public String editEntity(String txtSelect) throws Exception {
		UserFormMap userFormMap = getFormMap(UserFormMap.class);
		userFormMap.put("txtRoleSelect", txtSelect);
		userFormMap.update();
		if(!Common.isEmpty(txtSelect)){
			UserRoleFormMap roleFormMap=new UserRoleFormMap();
			roleFormMap.deleteByAttribute("userId", userFormMap.getStr("id"));
			String[] txt = txtSelect.split(",");
			UserRoleFormMap userRoleFormMap = null;
			for (String roleId : txt) {
				userRoleFormMap = new UserRoleFormMap();
				userRoleFormMap.put("userId", userFormMap.get("id"));
				userRoleFormMap.put("roleId", roleId);
				userRoleFormMap.save();
			}
		}
		return "success";
	}
	
	@RequestMapping("seletUser")
	public String seletRole(Model model) throws Exception {
		UserFormMap userFormMap = getFormMap(UserFormMap.class);
		userFormMap.put("mapper_id", "UserMapper.seletUser");
		List<UserFormMap> list = userFormMap.findByNames();
		String ugid = "";
		for (UserFormMap ml : list) {
			ugid += ml.get("id")+",";
		}
		ugid = Common.trimComma(ugid);
		model.addAttribute("txtSelect", ugid);
		model.addAttribute("useSelect", list);
		if(StringUtils.isNotBlank(ugid)){
			String[] g = ugid.split(",");
			String v = "";
			for (String s : g) {
				v+="'"+s+"',";
			}
			userFormMap.put("where", " id not in ("+Common.trimComma(v)+")");
		}
		userFormMap.remove("mapper_id");
		List<UserFormMap> users = userFormMap.findByWhere();
		model.addAttribute("unSelect", users);
		model.addAttribute("lableName", userFormMap.get("lableName"));
		return "/common/select_plugin";
	}
	
	
}