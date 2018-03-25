package com.monitoring.controller.index;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.monitoring.util.Common;
import com.monitoring.entity.base.FormMap;
import com.monitoring.exception.SystemException;
import com.monitoring.plugin.PageView;
/**
 * Controller基类，提供常用的方法，对于单表的增，删，改，查不需要再重复写。
 * 如果对于单表的操作，有其他业务，则需要自己写方法处理
 * @author lanyuan
 * @Email：mmm333zzz520@163.com
 * @date：2016-03-11
 * @version：4.1v
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BaseController {
	
	public PageView getPageView(FormMap formMap) {
		PageView pageView = new PageView();
		if (!Common.isEmpty(formMap.getStr("pageNow"))) {
			pageView = new PageView(formMap.getInt("pageNow"));
		}
		if (!Common.isEmpty(formMap.getStr("pageSize"))) {
			pageView.setPageSize(formMap.getInt("pageSize"));
		} 
		formMap.put("paging", pageView);
		return pageView;
	}
	
	/**
	 * 1：默认返回的界面是
	 *     当前调用的Controller中RequestMapping注解地址路径  + list.jsp。
	 *     如：@RequestMapping("/user")。 地址是:/user/list.shtnl 则返回的界面地址是 /user/list.jsp
	 * 2：返回自定义界面
	 *     必须转递一个参数   xxx.shtml?page_path=/user/list。则返回的界面地址是 /user/list.jsp
	 * 3：接收的所有参数都会回传给界面。爱怎么获取就怎么获取！    
	 * @author lanyuan
	 * @Email：mmm333zzz520@163.com
	 * @date：2016-03-11
	 * @version：4.1v
	 */
	@RequestMapping("/list" )
	public String list(Model model,HttpServletRequest request) throws Exception {
		FormMap formMap=findHasHMap(FormMap.class);
		model.addAllAttributes(formMap);
		model.addAttribute("res", findByRes(request));
		if(Common.isEmpty(formMap.getStr("page_path"))){
			return Common.BACKGROUND_PATH + findUrl()[0]+"/list";
		}else{
			return Common.BACKGROUND_PATH +formMap.getStr("page_path");
		}
	}
	
	/**
	 * 1：默认返回的界面是
	 *     当前调用的Controller中RequestMapping注解地址路径  + add.jsp。
	 *     如：@RequestMapping("/user")。 地址是:/user/add.shtnl 则返回的界面地址是 /user/add.jsp
	 * 2：返回自定义界面
	 *     必须转递一个参数   xxx.shtml?page_path=/user/add。则返回的界面地址是 /user/add.jsp
	 * 3：接收的所有参数都会回传给界面。爱怎么获取就怎么获取！    
	 * @author lanyuan
	 * @Email：mmm333zzz520@163.com
	 * @date：2016-03-11
	 * @version：4.1v
	 */
	@RequestMapping("/add" )
	public String add(Model model) throws Exception {
		FormMap formMap=findHasHMap(FormMap.class);
		model.addAllAttributes(formMap);
		if(Common.isEmpty(formMap.getStr("page_path"))){
			return Common.BACKGROUND_PATH + findUrl()[0]+"/add";
		}else{
			return Common.BACKGROUND_PATH +formMap.getStr("page_path");
		}
	}
	
	/**
	 * 获取返回某一页面的按扭组,传入某url资源的ID, resId=XXX
	 * <br/>
	 *<b>author：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/> 
	 *<b>date：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-04-01</b><br/> 
	 *<b>mod by：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspEkko</b><br/> 
	 *<b>date：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-09-07</b><br/> 
	 *<b>version：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.1</b>
	 * @return Class<T>
	 * @throws Exception
	 */
	@RequestMapping("/findByRes" )
	@ResponseBody
	public List<FormMap> findByRes(HttpServletRequest request){
		// 资源ID
		String id = request.getParameter("resId");
		String resKey = request.getParameter("resKey");
		// 获取request
		FormMap f = new FormMap();
		if(Common.isNotEmpty(id)){
			f.put("parentId", id);
		}else if(Common.isNotEmpty(resKey)){
			f.put("resKey", resKey);
		}else{
			return null;
		}
		//f.put("userId", Common.findUserSessionId());
		f.put("mapper_id", "ResourcesMapper.findRes");
		List<FormMap> fs = f.findByNames();
		return fs;
	}


	/**
	 * 返回列表 
	 * 1：如果返回单表的分页数据 必须传入 FormMap=XXX, XXX表示某表的实体类的名称，
	 *    如FormMap=UserFormMap (名称大小写必须一致)
	 *    
	 * 2：如果自定义复杂的Sql分页数据 必须传入 mapper_id=xxx.xxxx, 
	 *    xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" ,
	 *    其中的你查询sql是对应的 <select id="findUserPage"...>....</select> 其中 id = findUserPage 这个就是sqlid <br/>
	 * 那么前端参数必须是  mapper_id=userMapper.findUserPage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPage")
	@ResponseBody
	public PageView findByPage() throws Exception {
		FormMap formMap=findHasHMap(FormMap.class);
		PageView pageView = getPageView(formMap);
		formMap.put("paging", pageView);
		pageView.setRecords(formMap.findByPage());
		return pageView;
	}
	
	/**
	 * 返回表的所有数据，json格式 
	 * 1：如果返回单表的所有数据 必须传入 FormMap=XXX, XXX表示某表的实体类的名称，
	 *    如FormMap=UserFormMap (名称大小写必须一致)
	 *    
	 * 2：如果自定义复杂的Sql所有数据 必须传入 mapper_id=xxx.xxxx, 
	 *    xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" ,
	 *    其中的你查询sql是对应的 <select id="findUserAll"...>....</select> 其中 id = findUserAll 这个就是sqlid <br/>
	 * 那么前端参数必须是  mapper_id=userMapper.findUserAll
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/jsonlist")
	@ResponseBody
	public List<FormMap> jsonlist() throws Exception {
		FormMap formMap=findHasHMap(FormMap.class);
		return formMap.findByNames();
	}
	
	/**
	 * 
	 * 1：根据ID删除数据
	 *   1：根据ID返回单表的数据 必须传入 FormMap=XXX, XXX表示某表的实体类的名称，
	 *     如FormMap=UserFormMap (名称大小写必须一致)
	 *   2：如果自定义复杂delete的sql语句 必须传入 mapper_id=xxx.xxxx, 
	 *     xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" ,
	 *     其中的你查询sql是对应的 <delete id="deleteByIds"...>....</delete> 其中 id = deleteByIds 这个就是sqlid <br/>
	 * 那么前端参数必须是  mapper_id=userMapper.deleteByIds
	 * @author lanyuan
	 * @Email：mmm333zzz520@163.com
	 * @date：2016-03-11
	 * @version：4.1v
	 */
	@RequestMapping("/deleteByIds")
	@ResponseBody
	public String deleteByIds(String id) {
		FormMap formMap=findHasHMap(FormMap.class);
		try {
			formMap.deleteByIds(id);
			return "success";
		} catch (Exception e) {
			return "error "+e.getMessage();
		}
	}
	
	/**
	 * 
	 * 1：默认返回的界面是
	 *     当前调用的Controller中RequestMapping注解地址路径  + edit.jsp。
	 *     如：@RequestMapping("/user")。 地址是:/user/edit.shtnl 则返回的界面地址是 /user/edit.jsp
	 * 2：返回自定义界面
	 *     必须转递一个参数   xxx.shtml?page_path=/user/edit。则返回的界面地址是 /user/edit.jsp
	 * 3：接收的所有参数都会回传给界面。爱怎么获取就怎么获取！    
	 * 
	 *   1：根据ID返回单表的数据 必须传入 FormMap=XXX, XXX表示某表的实体类的名称，
	 *     如FormMap=UserFormMap (名称大小写必须一致)
	 *   2：如果自定义复杂的Sql数据 必须传入 mapper_id=xxx.xxxx, 
	 *     xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" ,
	 *     其中的你查询sql是对应的 <select id="findById"...>....</select> 其中 id = findById 这个就是sqlid <br/>
	 * 那么前端参数必须是  mapper_id=userMapper.findById
	 * @author lanyuan
	 * @Email：mmm333zzz520@163.com
	 * @date：2016-03-11
	 * @version：4.1v
	 */
	@RequestMapping("/edit" )
	public String edit(Model model,String id) throws Exception {
		FormMap formMap=findHasHMap(FormMap.class);
		model.addAllAttributes(formMap);
		FormMap map=(FormMap) formMap.findById(id);
		model.addAllAttributes(map);
		if(Common.isEmpty(formMap.getStr("page_path"))){
			return Common.BACKGROUND_PATH + findUrl()[0]+"/edit";
		}else{
			return Common.BACKGROUND_PATH +formMap.getStr("page_path");
		}
	}
	
	/**
	 * 
	 * 1：根据ID返回json数据
	 *   1：根据ID返回单表的数据 必须传入 FormMap=XXX, XXX表示某表的实体类的名称，
	 *     如FormMap=UserFormMap (名称大小写必须一致)
	 *   2：如果自定义复杂的Sql数据 必须传入 mapper_id=xxx.xxxx, 
	 *     xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" ,
	 *     其中的你查询sql是对应的 <select id="findById"...>....</select> 其中 id = findById 这个就是sqlid <br/>
	 * 那么前端参数必须是  mapper_id=userMapper.findById
	 * @author lanyuan
	 * @Email：mmm333zzz520@163.com
	 * @date：2016-03-11
	 * @version：4.1v
	 */
	@RequestMapping("/findByIdJson" )
	@ResponseBody
	public Map findByIdJson(Model model,String id) throws Exception {
		FormMap formMap=findHasHMap(FormMap.class);
		model.addAllAttributes(formMap);
		return (Map) formMap.findById(id);
	}
	
	/**
	 * 
	 * 1：根据某字段判断表是否存在数据
	 *   1：查询单表的数据 必须传入 FormMap=XXX, XXX表示某表的实体类的名称，
	 *     如FormMap=UserFormMap (名称大小写必须一致)
	 *   2：如果自定义的Sql查询数据 必须传入 mapper_id=xxx.xxxx, 
	 *     xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" ,
	 *     其中的你查询sql是对应的 <select id="isExist"...>....</select> 其中 id = isExist 这个就是sqlid <br/>
	 * 那么前端参数必须是  mapper_id=userMapper.isExist
	 * 
	 */
	@RequestMapping("isExist")
	@ResponseBody
	public boolean isExist() {
		FormMap formMap=findHasHMap(FormMap.class);
		List<FormMap> fs = formMap.findByNames();
		if (null == fs || fs.size()==0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 *   1：修改单表的数据 必须传入 FormMap=XXX, XXX表示某表的实体类的名称，
	 *     如FormMap=UserFormMap (名称大小写必须一致)
	 *   2：如果自定义的update Sql 必须传入 mapper_id=xxx.xxxx, 
	 *     xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" ,
	 *     其中的你查询sql是对应的 <select id="editEntity"...>....</select> 其中 id = editEntity 这个就是sqlid <br/>
	 * 那么前端参数必须是  mapper_id=userMapper.editEntity
	 * 
	 */
	@ResponseBody
	@RequestMapping("update")
	public String update() throws Exception {
		try {
			FormMap formMap=findHasHMap(FormMap.class);
			formMap.update();
			return "success";
		} catch (Exception e) {
			//try catch 必须抛出自定义异常,否则事务失效!   #### lanyuan ####
			String results = "{\"results\":\"error\",\"message\":\""+e+"\"}";
			throw new SystemException(results);
		}
	}
	
	/**
	 * 
	 *   1：插入单表的数据 必须传入 FormMap=XXX, XXX表示某表的实体类的名称，
	 *     如FormMap=UserFormMap (名称大小写必须一致)
	 *   2：如果自定义的insert Sql 必须传入 mapper_id=xxx.xxxx, 
	 *     xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="addEntity" ,
	 *     其中的你查询sql是对应的 <select id="addEntity"...>....</select> 其中 id = addEntity 这个就是sqlid <br/>
	 * 那么前端参数必须是  mapper_id=userMapper.addEntity
	 * 
	 */
	@ResponseBody
	@RequestMapping("insert")
	public String insert() throws Exception {
		try {
			FormMap formMap=findHasHMap(FormMap.class);
			formMap.save();
			return "success";
		} catch (Exception e) {
			//try catch 必须抛出自定义异常,否则事务失效!   #### lanyuan ####
			String results = "{\"results\":\"error\",\"message\":\""+e+"\"}";
			throw new SystemException(results);
		}
	}
	
	/**
	 * 获取传递的所有参数,
	 * 反射实例化对象，再设置属性值
	 * 通过泛型回传对象.
	 * <br/>
	 *<b>author：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/> 
	 *<b>date：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-04-01</b><br/> 
	 *<b>version：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp1.0v</b>
	 * @return Class<T>
	 * @throws Exception
	 */
	public <T> T getFormMap(Class<T> clazz){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		Enumeration<String> en = request.getParameterNames();
		T t = null;
		try {
			t = clazz.newInstance();
			FormMap<String, Object> map = (FormMap<String, Object>) t;
			while (en.hasMoreElements()) {
				String nms = en.nextElement().toString();
				if(nms.endsWith("[]")){
					String[] as = request.getParameterValues(nms);
					if(as!=null&&as.length!=0&&as.toString()!="[]"){
						String mname = t.getClass().getSimpleName().toUpperCase();
						if(nms.toUpperCase().startsWith(mname)){
							nms=nms.substring(nms.toUpperCase().indexOf(mname)+1);
						}
						map.put( nms,as);
					}
				}else{
					String as = request.getParameter(nms);
					if(!Common.isEmpty(as)){
						String mname = t.getClass().getSimpleName().toUpperCase();
						if(nms.toUpperCase().startsWith(mname)){
							nms=nms.substring(mname.length()+1);
						}
						map.put( nms, as);
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return  t;
	}
	
	/**
	 * 获取传递的所有参数,
	 * 再设置属性值
	 * 通过回传Map对象.
	 * <br/>
	 *<b>author：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/> 
	 *<b>date：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-04-01</b><br/> 
	 *<b>version：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp1.0v</b>
	 * @return Class<T>
	 * @throws Exception
	 */
	public <T> T findHasHMap(Class<T> clazz){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		Enumeration<String> en = request.getParameterNames();
		T t = null;
		try {
			t = clazz.newInstance();
			FormMap<String, Object> map = (FormMap<String, Object>) t;
			while (en.hasMoreElements()) {
				String nms = en.nextElement().toString();
				if(!"_t".equals(nms)){
					if(nms.endsWith("[]")){
						String[] as = request.getParameterValues(nms);
						if(as!=null&&as.length!=0&&as.toString()!="[]"){
							map.put( nms,as);
						}
					}else{
						String as = request.getParameter(nms);
						if(!Common.isEmpty(as)){
							map.put( nms, as);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * @ModelAttribute
	 * 这个注解作用.每执行controllor前都会先执行这个方法
	 * @author lanyuan
	 * Email：mmm333zzz520@163.com
	 * date：2014-5-25
	 * @param request
	 */
	/*@ModelAttribute
	public void init(HttpServletRequest request){
		String path = Common.BACKGROUND_PATH;
		Object ep = request.getSession().getAttribute("basePath");
		if(ep!=null){
			if(!path.endsWith(ep.toString())){
				Common.BACKGROUND_PATH = "/WEB-INF/jsp"+ep;
			}
		}
		
	}*/
	public String[] findUrl(){
		RequestMapping annotation = this.getClass().getAnnotation(RequestMapping.class);
		return annotation.value();
	}
	
	/**
	 * 获取页面传递的某一个参数值,
	 * <br/>
	 *<b>author：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/> 
	 *<b>date：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-04-01</b><br/> 
	 *<b>version：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.1</b>
	 */
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	/**
	 * 获取页面传递的某一个数组值,
	 * <br/>
	 *<b>author：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/> 
	 *<b>date：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-04-01</b><br/> 
	 *<b>version：</b><br/> 
	 *<b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.1</b>
	 * @return Class<T>
	 * @throws Exception
	 */
	public String[] getParaValues(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameterValues(key);
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
}