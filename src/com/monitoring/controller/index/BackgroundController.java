package com.monitoring.controller.index;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitoring.util.Const;
import com.monitoring.util.Tools;
import com.monitoring.entity.ResFormMap;
import com.monitoring.entity.UserFormMap;
import com.monitoring.entity.UserLoginFormMap;
import com.monitoring.plugin.SocketPageData;
import com.monitoring.util.Common;
import com.monitoring.util.TreeObject;
import com.monitoring.util.TreeUtil;
import com.mysql.jdbc.Connection;

/**
 * 进行管理后台框架界面的类
 * 
 * @author lanyuan 2015-04-05
 * @Email: mmm333zzz520@163.com
 * @version 4.1v
 */
@Controller
@RequestMapping("/")
public class BackgroundController extends BaseController {
	
	/**
	 * 获取头部信息
	 */
	@RequestMapping(value="/head/getUname")
	@ResponseBody
	public Object getList() {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<UserFormMap> pdList = new ArrayList<UserFormMap>();
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			UserFormMap userMap=(UserFormMap) Common.findUserSession();
			if(null == userMap){
				userMap=userMap.findById(Common.findUserSessionId());
				String USERNAME = userMap.getStr("name");	//获取当前登录者loginname
				map.put("USERNAME", USERNAME);
				session.setAttribute(Const.SESSION_userpds, userMap);
			}
			
			pdList.add(userMap);
			map.put("list", pdList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public String login(HttpServletRequest request) {
		request.removeAttribute("error");
		return "/login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public String login(String username, String password, HttpServletRequest request) {
		try {
			if (!request.getMethod().equals("POST")) {
				request.setAttribute("error", "Support POST method submit!");
			}
			if (Common.isEmpty(username) || Common.isEmpty(password)) {
				request.setAttribute("error", "User name or password can not be empty!");
				return "/login";
			}
			// 想要得到 SecurityUtils.getSubject()　的对象．．访问地址必须跟ｓｈｉｒｏ的拦截地址内．不然后会报空指针
			Subject user = SecurityUtils.getSubject();
			// 用户输入的账号和密码,,存到UsernamePasswordToken对象中..然后由shiro内部认证对比,
			// 认证执行者交由ShiroDbRealm中doGetAuthenticationInfo处理
			// 当以上认证成功后会向下执行,认证失败会抛出异常
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			try {
				user.login(token);
			} catch (LockedAccountException lae) {
				token.clear();
				request.setAttribute("error", "User has been locked! Please contact with administrator!");
				return "/login";
			} catch (ExcessiveAttemptsException e) {
				token.clear();
				request.setAttribute("error", "Account:" + username + " Multiple login failed. Locked 10 minutes!");
				return "/login";
			} catch (AuthenticationException e) {
				token.clear();
				request.setAttribute("error", "User or password is incorrect!");
				return "/login";
			}
			UserLoginFormMap userLogin = new UserLoginFormMap();
			Session session = SecurityUtils.getSubject().getSession();
			userLogin.put("userId", session.getAttribute("userSessionId"));
			userLogin.put("accountName", username);
			String ip=session.getHost();
			if(StringUtils.isEmpty(ip)||"0:0:0:0:0:0:0:1".equals(ip)){
				ip="127.0.0.1";
			}
			userLogin.put("loginIP",ip);
			userLogin.save();
			request.removeAttribute("error");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Login exception. Please contact with administrator!");
			return "/login";
		}
		return "redirect:index.shtml";
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("index")
	public String index(Model model, HttpServletRequest request)
			throws Exception {
		ResFormMap resFormMap = new ResFormMap();
		resFormMap.put("userId", Common.findUserSessionId());
		resFormMap.put("mapper_id", "ResourcesMapper.findRes");
		List<ResFormMap> mps = resFormMap.findByNames();
		List<TreeObject> list = new ArrayList<TreeObject>();
		for (ResFormMap map : mps) {
			TreeObject ts = new TreeObject();
			Common.flushObject(ts, map);
			list.add(ts);
		}
		TreeUtil treeUtil = new TreeUtil();
		List<TreeObject> ns = treeUtil.getChildTreeObjects(list, 0);
		//聊天室信息-------------------
		 SocketPageData pd = new SocketPageData();
		 pd = this.getPageData();
		 	//读取websocket配置
		 	String strWEBSOCKET = Tools.readTxtFile(Const.WEBSOCKET);//读取WEBSOCKET配置
		 	if(null != strWEBSOCKET && !"".equals(strWEBSOCKET)){
				String strIW[] = strWEBSOCKET.split(",fh,");
				if(strIW.length == 4){
					pd.put("WIMIP", strIW[0]);
					pd.put("WIMPORT", strIW[1]);
					pd.put("OLIP", strIW[2]);
					pd.put("OLPORT", strIW[3]);
				}
			}
		 	pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		 	//读取websocket配置
		 	model.addAttribute("pd",pd);
		//聊天室信息-------------------
		
		model.addAttribute("list", ns);
		// 登陆的信息回传页面
		//model.addAttribute("userFormMap",(UserFormMap) Common.findUserSession());
		return "/index";
	}

	@RequestMapping("menu")
	public String menu(Model model) {
		return "/framework/menu";
	}

	/**
	 * 获取某个用户的权限资源
	 * 
	 * @author lanyuan Email：mmm333zzz520@163.com date：2015-3-4
	 * @param request
	 * @return
	 */
	@RequestMapping("findAuthority")
	@ResponseBody
	public List<String> findAuthority(HttpServletRequest request) {
		return null;
	}
	
	@RequestMapping("download")
	public void download(String fileName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;

		String ctxPath = request.getSession().getServletContext().getRealPath("/") + "\\"
				+ "filezip\\";
		String downLoadPath = ctxPath + fileName;
		System.out.println(downLoadPath);
		try {
			long fileLength = new File(downLoadPath).length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		// 使用权限管理工具进行用户的退出，注销登录
		SecurityUtils.getSubject().logout(); // session
												// 会销毁，在SessionListener监听session销毁，清理权限缓存
		return "redirect:login.shtml";
	}

	@RequestMapping("install")
	public String install() throws Exception {

		try {
			Properties props = Resources.getResourceAsProperties("jdbc.properties");
			String url = props.getProperty("jdbc.url");
			String driver = props.getProperty("jdbc.driverClass");
			String username = props.getProperty("jdbc.username");
			String password = props.getProperty("jdbc.password");
			Class.forName(driver).newInstance();
			Connection conn = (Connection) DriverManager.getConnection(url, username, password);
			ScriptRunner runner = new ScriptRunner(conn);
			runner.setErrorLogWriter(null);
			runner.setLogWriter(null);
			runner.runScript((new InputStreamReader(getClass().getResourceAsStream("/junit/test/bata.sql"),"UTF-8")));

		} catch (Exception e) {
			e.printStackTrace();
			return "Initialization failed! Please contact with administrator" + e;
		}

		return "/install";
	}
	
	public SocketPageData getPageData(){
		return new SocketPageData(this.getRequest());
	}

}
