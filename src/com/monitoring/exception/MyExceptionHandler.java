package com.monitoring.exception;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.monitoring.entity.LogFormMap;

import com.monitoring.util.Common;
import com.monitoring.util.JsonUtils;

/**
 * 抛出自定义异常时..如果是返回json数据就如下.<br/>
 * String results = "{\"results\":\"error\",\"message\":\""+e+"\"}";<br/>
 * 如果返回指定页面,还需要添加一个topage属性指定页面,,如下<br/>
 * String results = "{\"results\":\"error\",\"message\":\""+e+"\",\"topage"\:\"/user/add\"}";<br/>
 * 统一异常处理，有效地针对异步和非异步请求 不同异常会到不同页面 <br/>
 * throw new ParameterException(results) 参数异常<br/>
 * throw new SystemException(results) 系统业务异常<br/>
 * throw new Exception(results) 其他异常返回<br/>
 * 1001 业务异常返回 <br/>
 * 1002 参数异常返回 <br/>
 * 1003 其他异常返回<br/>
 * @author lanyuan
 * @date 2016-04-10
 * @Email mmm333zzz520@163.com
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		LogFormMap logForm =  (LogFormMap) request.getSession().getAttribute("logForm");
		if(logForm != null&&logForm.size()>0){
			try {
				logForm.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String ms = ex.getMessage();
		if(Common.isEmpty(ms))
			ms=ex.toString();
		ms=ms.replaceAll("com.monitoring.exception.ParameterException:", "");
		ms=ms.replaceAll("com.monitoring.exception.SystemException:", "");
		ms=ms.replaceAll("\r|\n","");//去除字符串中的空格,回车,换行符,制表符
		// 是否异步请求
		if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			map=JsonUtils.parseJSONMap(ms);
			// 根据不同错误转向不同页面
			if (ex instanceof SystemException) {
				map.put("result", "error");
				map.put("code", 1001);
				map.put("messages", "Business exception");
				//response.setStatus(1001);// 业务异常返回 1001
				//抛出异常时,返回默认页面,否则返回指定页面.
				if(null!=map.get("topage")&&!"".equals(map.get("topage").toString())){
					return new ModelAndView("WEB-INF/jsp"+map.get("topage"), map);
				}else{
					return new ModelAndView("WEB-INF/jsp/exception/error-system", map);
				}
			} else if (ex instanceof ParameterException) {
				map.put("result", "error");
				map.put("code", 1002);
				map.put("messages", "Parameter exception");
				//response.setStatus(1002);// 参数异常返回 1002
				//抛出异常时,返回默认页面,否则返回指定页面.
				if(null!=map.get("topage")&&!"".equals(map.get("topage").toString())){
					return new ModelAndView("WEB-INF/jsp"+map.get("topage"), map);
				}else{
					return new ModelAndView("WEB-INF/jsp/exception/error-parameter", map);
				}
			} else {
				map.put("result", "error");
				map.put("code", 1003);
				map.put("messages", "Other exception");
				//response.setStatus(1003);// 其他异常返回 1003
				//抛出异常时,返回默认页面,否则返回指定页面.
				if(null!=map.get("topage")&&!"".equals(map.get("topage").toString())){
					return new ModelAndView("WEB-INF/jsp"+map.get("topage"), map);
				}else{
					return new ModelAndView("/error", map);
				}
			}
		} else {
			try {
				if (ex instanceof SystemException) {
					map.put("result", "error");
					map.put("code", 1001);
					map.put("messages", "Business exception");
					response.setStatus(1001);//业务异常返回 1001
				} else if (ex instanceof ParameterException) {
					map.put("result", "error");
					map.put("code", 1002);
					map.put("messages", "Parameter exception");
					response.setStatus(1002);//参数异常返回 1002
				} else {
					map.put("result", "error");
					map.put("code", 1003);
					map.put("messages", "Other exception");
					response.setStatus(1003);//其他异常返回 1003
				}
				
				ms =  ms.substring(2);
				ms ="{\"result\":\""+map.get("result")+"\",\"code\":"+map.get("code")+",\"messages\":\""+map.get("messages")+"\","+ms;
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=UTF-8");  
				PrintWriter writer = response.getWriter();
				writer.write(ms);
				writer.flush();
			} catch (Exception e) {
				return null;
			}
			return null;
		}
	}
}
