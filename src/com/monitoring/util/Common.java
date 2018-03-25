package com.monitoring.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class Common {
	// 后台访问
	public static final String BACKGROUND_PATH = "WEB-INF/jsp";
	// 前台访问
	public static final String WEB_PATH = "/WEB-INF/jsp";
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	public static final String ACCESS_ID = "bDgWSX8JYIXpwBEV";
	public static final String ACCESS_KEY = "FIGkS5NeTb22IUY8jYkhBI5HBlSnfG";
	public static final String OSS_ENDPOINT = "http://oss.aliyuncs.com/";
    @SuppressWarnings("unchecked")
	public static Map<String, Object> FormMap(Object formMap){
		return (Map<String, Object>)formMap;
	}
	/**
	 * String转换double
	 * @param string
	 * @return double
	 */
	public static double convertSourData(String dataStr) throws Exception{
		if(dataStr!=null&&!"".equals(dataStr)){
			return Double.valueOf(dataStr);
		}
		throw new NumberFormatException("convert error!");
	}
	
	/**
	 * 判断变量是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (null == s || "".equals(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 使用率计算
	 * 
	 * @return
	 */
	public static String fromUsage(long free, long total) {
		Double d = new BigDecimal(free * 100 / total).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return String.valueOf(d);
	}
	/**
	 * 返回当前时间　格式：yyyy-MM-dd hh:mm:ss
	 * @return String
	 */
	public static String fromDateH(){
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format1.format(new Date());
	}
	/**
	 * 保留两个小数
	 * 
	 * @return
	 */
	public static String formatDouble(Double b) {
		BigDecimal bg = new BigDecimal(b);
		return bg.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
	/**
	 * 返回当前时间　格式：yyyy-MM-dd
	 * @return String
	 */
	public static String fromDateY(){
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		return format1.format(new Date());
	}
	/**
	 * 用来去掉List中空值和相同项的。
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> removeSameItem(List<String> list) {
		List<String> difList = new ArrayList<String>();
		for (String t : list) {
			if (t != null && !difList.contains(t)) {
				difList.add(t);
			}
		}
		return difList;
	}

	/**
	 * 返回用户的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String toIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 传入原图名称，，获得一个以时间格式的新名称
	 * 
	 * @param fileName
	 *            　原图名称
	 * @return
	 */
	public static String generateFileName(String fileName) {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String formatDate = format.format(new Date());
		int random = new Random().nextInt(10000);
		int position = fileName.lastIndexOf(".");
		String extension = fileName.substring(position);
		return formatDate + random + extension;
	}

	/**
	 * 取得html网页内容 UTF8编码
	 * 
	 * @param urlStr
	 *            网络地址
	 * @return String
	 */
	public static String getInputHtmlUTF8(String urlStr) {
		URL url = null;
		try {
			url = new URL(urlStr);
			HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();

			httpsURLConnection.setRequestMethod("GET");
			httpsURLConnection.setConnectTimeout(5 * 1000);
			httpsURLConnection.connect();
			if (httpsURLConnection.getResponseCode() == 200) {
				// 通过输入流获取网络图片
				InputStream inputStream = httpsURLConnection.getInputStream();
				String data = readHtml(inputStream, "UTF-8");
				inputStream.close();
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}
	/**
	 * 获取远程内容  UTF8编码
	 * 
	 * @param urlStr
	 *            网络地址
	 * @return requestParamsMap 参数
	 */
	public static String getInputHtmlUTF8(String urlStr, Map<String, Object> requestParamsMap) {
		URL url = null;
		try {
			 //之前所有的参数只是写入写出流的缓存中并没有发送到服务端，执行下面这句话后表示将参数信息发送到服务端
		     // 组织请求参数  
	        StringBuffer params = new StringBuffer();  
	        if(null !=requestParamsMap){
		        Iterator<Entry<String, Object>> it = requestParamsMap.entrySet().iterator();  
		        while (it.hasNext()) {  
		            Map.Entry<String, Object> element = (Map.Entry<String, Object>) it.next();  
		            params.append(element.getKey());  
		            params.append("=");  
		            params.append(element.getValue());  
		            params.append("&");  
		        }  
		        if (params.length() > 0) {  
		            params.deleteCharAt(params.length() - 1);  
		        }  
	        }
			url = new URL(urlStr);
			HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
			//2、设置属性
			//post请求必须设置的两个
			httpsURLConnection.setDoInput(true);
			httpsURLConnection.setDoOutput(true);
		     //设置属性
			httpsURLConnection.setUseCaches(false);
			httpsURLConnection.setRequestMethod("POST");
			 // 设置通用的请求属性  
			for (Map.Entry<String, Object> entry : requestParamsMap.entrySet()) {
				if(entry.getKey().indexOf("p_")>-1){
					String key = entry.getKey();
					key=key.substring(key.indexOf("p_")+2, key.length());
					httpsURLConnection.setRequestProperty(key, entry.getValue()+"");  
				}
			}
			httpsURLConnection.setRequestProperty("accept", "*/*");  
            httpsURLConnection.setRequestProperty("connection", "Keep-Alive");  
            httpsURLConnection.setRequestProperty("Content-Length", String.valueOf(params.length()));  
			httpsURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
			httpsURLConnection.setRequestProperty("contentType", "UTF-8");
			httpsURLConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			httpsURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			httpsURLConnection.setConnectTimeout(5 * 1000);
			//如果使用URLconnection既要读取输入流 又要传参数 那么一定要先使用输出流 在使用输入流
	       //getOutputStream 中包含了connect 也就是说使用了getoutputStream的时候connect可以不写
	        OutputStream os = httpsURLConnection.getOutputStream();
	        //设置编码 防止到服务端出现中文乱码
	        OutputStreamWriter ow = new OutputStreamWriter(os, "UTF-8");
	        PrintWriter printWriter = new PrintWriter(ow,true);
            // 发送请求参数  
            printWriter.write(params.toString());  
            // flush输出流的缓冲  
            printWriter.flush();  
			if (httpsURLConnection.getResponseCode() == 200) {
				// 通过输入流获取网络图片
				InputStream inputStream = httpsURLConnection.getInputStream();
				String data = readHtml(inputStream, "UTF-8");
				inputStream.close();
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "异常："+e.getMessage();
		}
		return null;
	}

	public static void main(String[] args) {
		
		for (int i = 0; i <10000; i+=100) {
			String urlStr="https://www.douban.com/group/topic/87140921/?start="+i;
			String data = getInputHtmlUTF8(urlStr);
			if(data.indexOf("清欢")>-1){
				System.err.println(urlStr);
				//data=data.substring(data.indexOf("<strong>标题：</strong>"),data.indexOf("<strong>标题：</strong>")+50);
				//System.err.println(data);
			}
			
		}
		
		
	}
	/**
	 * 取得html网页内容 GBK编码
	 * 
	 * @param urlStr
	 *            网络地址
	 * @return String
	 */
	public static String getInputHtmlGBK(String urlStr) {
		URL url = null;
		try {
			url = new URL(urlStr);
			HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();

			httpsURLConnection.setRequestMethod("GET");
			httpsURLConnection.setConnectTimeout(5 * 1000);
			httpsURLConnection.connect();
			if (httpsURLConnection.getResponseCode() == 200) {
				// 通过输入流获取网络图片
				InputStream inputStream = httpsURLConnection.getInputStream();
				String data = readHtml(inputStream, "GBK");
				inputStream.close();
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}

	/**
	 * @param inputStream
	 * @param uncode
	 *            编码 GBK 或 UTF-8
	 * @return
	 * @throws Exception
	 */
	public static String readHtml(InputStream inputStream, String uncode) throws Exception {
		InputStreamReader input = new InputStreamReader(inputStream, uncode);
		BufferedReader bufReader = new BufferedReader(input);
		String line = "";
		StringBuilder contentBuf = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			contentBuf.append(line);
		}
		return contentBuf.toString();
	}

	/**
	 * 
	 * @return 返回资源的二进制数据 @
	 */
	public static byte[] readInputStream(InputStream inputStream) {

		// 定义一个输出流向内存输出数据
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// 定义一个缓冲区
		byte[] buffer = new byte[1024];
		// 读取数据长度
		int len = 0;
		// 当取得完数据后会返回一个-1
		try {
			while ((len = inputStream.read(buffer)) != -1) {
				// 把缓冲区的数据 写到输出流里面
				byteArrayOutputStream.write(buffer, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				byteArrayOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		// 得到数据后返回
		return byteArrayOutputStream.toByteArray();

	}
	/**
	 * 修改配置　
	 * 
	 * @param request
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/modifySer")
	public static Map<String, Object> modifySer(String key, String value) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			PropertiesUtils.modifyProperties(key, value);
		} catch (Exception e) {
			dataMap.put("flag", false);
		}
		dataMap.put("flag", true);
		return dataMap;
	}

	
	/**
	 * 获取登录账号的ID
	 * 
	 * @author lanyuan 
	 * @Email：mmm333zzz520@163.com 
	 * @date：2015-10-27
	 * @param request
	 * @return
	 */
	public static String findUserSessionId() {
		Object obj = SecurityUtils.getSubject().getSession().getAttribute("userSessionId");
		if (obj != null) {
			return obj.toString();
		}
		return null;
	}

	/**
	 * 获取登录账号的的对象
	 * 
	 * @author lanyuan 
	 * @Email：mmm333zzz520@163.com 
	 * @date：2015-10-27
	 * @param request
	 * @return Object 返回是Object..需要转型为(Account)Object
	 */
	public static Object findUserSession() {
		return (Object) SecurityUtils.getSubject().getSession().getAttribute("userSession");
	}
	/**
	 * 获取 前端登录账号的ID
	 * 
	 * @author lanyuan Email：mmm333zzz520@163.com date：2014-11-27
	 * @param request
	 * @return
	 */
	public static String findWebUserSessionId() {
		Object obj = SecurityUtils.getSubject().getSession().getAttribute("userWebSessionId");
		if (obj != null) {
			return obj.toString();
		}
		return null;
	}

	/**
	 * 获取前端登录账号的的对象
	 * 
	 * @author lanyuan Email：mmm333zzz520@163.com date：2014-2-27
	 * @param request
	 * @return Object 返回是Object..需要转型为(Account)Object
	 */
	public static Object findWebUserSession() {
		return (Object) SecurityUtils.getSubject().getSession().getAttribute("userWebSession");
	}

	public static Object findSession(String key) {
		return (Object) SecurityUtils.getSubject().getSession().getAttribute(key);
	}

	public static void putSession(String key, Object value) {
		SecurityUtils.getSubject().getSession().setAttribute(key, value);
	}
	
	public static void removeSession(String key) {
		SecurityUtils.getSubject().getSession().removeAttribute(key);
	}

	/**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
	public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    } 
	 /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
 
    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * 去除字符串最后一个逗号,若传入为空则返回空字符串
     *@descript
     *@param para
     *@return
     *@author LJN
     *@date 2015年3月29日
     *@version 1.0
     */
	public static String trimComma(String para) {
		if (StringUtils.isNotBlank(para)) {
			if (para.endsWith(",")) {
				para= para.substring(0, para.length() - 1);
			}
			if(para.startsWith(",")){
				para= para.substring(1);
			}
			return para;
		} else {
			return "";
		}
	}
    /**
	 * 判断变量是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(String s) {
		if (null == s || "".equals(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s)) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 将Map形式的键值对中的值转换为泛型形参给出的类中的属性值 t一般代表pojo类
	 * 
	 * @descript
	 * @param t
	 * @param params
	 * @author lanyuan
	 * @date 2015年3月29日
	 * @version 1.0
	 */
	public static <T extends Object> T flushObject(T t, Map<String, Object> params) {
		if (params == null || t == null)
			return t;

		Class<?> clazz = t.getClass();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				Field[] fields = clazz.getDeclaredFields();

				for (int i = 0; i < fields.length; i++) {
					String name = fields[i].getName(); // 获取属性的名字
					Object value = params.get(name);
					if (value != null && !"".equals(value)) {
						// 注意下面这句，不设置true的话，不能修改private类型变量的值
						fields[i].setAccessible(true);
						fields[i].set(t, value);
					}
				}
			} catch (Exception e) {
			}

		}
		return t;
	}
	/**
	 * html转议
	 * 
	 * @descript
	 * @param content
	 * @return
	 * @author lanyuan
	 * @date 2015年4月27日
	 * @version 1.0
	 */
	public static String htmltoString(String content) {
		if (content == null)
			return "";
		String html = content;
		html = html.replaceAll("&", "&amp;");
		html = html.replace("\"", "&quot;"); // "
		html = html.replace("\t", "&nbsp;&nbsp;");// 替换跳格
		html = html.replace("<", "&lt;");
		html = html.replaceAll(">", "&gt;");

		return html;
	}

	/**
	 * html转议
	 * 
	 * @descript
	 * @param content
	 * @return
	 * @author lanyuan
	 * @date 2015年4月27日
	 * @version 1.0
	 */
	public static String stringtohtml(String content) {
		if (content == null)
			return "";
		String html = content;
		html = html.replaceAll("&amp;", "&");
		html = html.replace("&quot;", "\""); // "
		html = html.replace("&nbsp;&nbsp;", "\t");// 替换跳格
		html = html.replace("&lt;", "<");
		html = html.replaceAll("&gt;", ">");

		return html;
	}
	/**
	 * 插入数据库时.对字符串进行转议
	 * 
	 * @descript
	 * @param content
	 * @return
	 * @author lanyuan
	 * @date 2015年6月15日
	 * @version 1.0v
	 */
	public static String mysqltoString(String content) {
		if (content == null)
			return "";
		String html = content;
		html = html.replace("'", "\\\'");
		//html = html.replace("%", "\\%");
		html = html.replace("\"", "\\\""); //
		return html;
	}   
	public static String emilStyle() {
		String s = "<style>div{width:80%;margin:0 auto;padding:10px 20px;background:#fff;position:relative;border-radius:3px}div:after{content:'';background:-webkit-repeating-linear-gradient(45deg,#114abc,#114abc 10px,#fff 10px,#fff 20px,#bf2010 20px,#bf2010 30px,#fff 30px,#fff 40px);background:repeating-linear-gradient(45deg,#114abc,#114abc 10px,#fff 10px,#fff 20px,#bf2010 20px,#bf2010 30px,#fff 30px,#fff 40px);padding:20px;border-radius:6px;box-shadow:1px 2px 6px 1px rgba(76,66,47,.75);width:calc(100% - 24px);height:calc(100% - 24px);position:absolute;top:-8px;left:-8px;z-index:-1}h1{display:inline-block;margin:15px 0 10px -28px;padding:10px 15px 5px 30px;background:#bf2010;border-width:1px 1px 1px 0;border-style:dashed;color:#fff;box-shadow:2px 0 0 2px #bf2010,2px 2px 0 2px #bf2010,2px -2px 0 2px #bf2010,inset 0 0 5px 2px rgba(0,0,0,.2)}body,html{height:100%}body{background:#d3ccc1;display:-webkit-box;display:-webkit-flex;display:-ms-flexbox;display:flex;-webkit-box-pack:center;-webkit-justify-content:center;-ms-flex-pack:center;justify-content:center;-webkit-box-align:center;-webkit-align-items:center;-ms-flex-align:center;align-items:center;font-family:'IM Fell English',serif;background-image:-webkit-radial-gradient(rgba(191,32,16,.075) 15%,rgba(0,0,0,0) 16%),-webkit-radial-gradient(rgba(17,74,188,.05) 15%,rgba(0,0,0,0) 16%);background-image:radial-gradient(rgba(191,32,16,.075) 15%,rgba(0,0,0,0) 16%),radial-gradient(rgba(17,74,188,.05) 15%,rgba(0,0,0,0) 16%);background-size:120px 120px;background-position:0 0,60px 60px;background-color:#d3ccc1}h1{font-size:2.5rem}";
		s += "</style>";
		return s;
	}

	/**
	 * 是否为整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric1(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 两个map对应转换
	 * @param map 传入
	 * @param clazz 返回
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T mapToObj(Object map,Class<T> clazz) {
		try {
			T t = clazz.newInstance();
			Map<String, Object> formmap = (Map<String, Object>) t;
			Map<String, Object> m = (Map<String, Object>) map;
			for (String key : m.keySet()) {
				formmap.put(key, m.get(key));
			}
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 正则表达式替换空格和换行符 为一个空格
	 * @param str
	 * @return
	 */
	public static String getStringNoBlank(String str) {      
        if(str!=null && !"".equals(str)) {      
            Pattern p = Pattern.compile("\t|\r|\n");      
            Matcher m = p.matcher(str);      
            String strNoBlank = m.replaceAll(" ");
            p = Pattern.compile("\\s+");      
            m = p.matcher(str);      
            strNoBlank = m.replaceAll(" ");
            return strNoBlank;      
        }else {      
            return str;      
        }           
    }
}
