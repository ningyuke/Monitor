package com.monitoring.entity.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.monitoring.annotation.TableSeg;
import com.monitoring.exception.ParameterException;
import com.monitoring.exception.SystemException;
import com.lanyuan.mapper.base.BaseMapper;
import com.monitoring.util.Common;
import com.monitoring.util.EhcacheUtils;
import com.monitoring.util.SpringIocUtils;

/**
 * SpringMvc 把请求的所有参数封装到Map中,提供最常用的方法
 * 
 * @author lanyuan
 * @Email：mmm333zzz520@163.com 
 * @date：2015-10-21
 * @version 4.0v
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class FormMap<K, V> extends ConcurrentHashMap<K, V> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BaseMapper baseMapper;

	public BaseMapper getRepository() {
		BaseMapper bs=SpringIocUtils.getBean(BaseMapper.class);
		if(null==bs){
			return baseMapper;
		}else{
			return bs;
		}
	}
	
	public void getRepository(BaseMapper mapper) {
		 baseMapper=mapper;
	}
	
	/**
	 *  #################  说明   ################# <br/>
	 * <b>公共方法默认调用 不需要传入 mapper_id</b><br/>
	 * 如果自定义复杂的Sql分页数据 必须传入 mapper_id=xxx.xxxx, <br/>
	 *　　　xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" , <br/>
	 *　　　其中的你查询sql是对应的 &lt;select id="findUserPage"...>....</select> 其中 id = findUserPage 这个就是sqlid <br/>
	 * 那么参数必须是  mapper_id=userMapper.findUserPage<br/><br/>
	 * 1：调用公共findByPage接口,必须传入PageView对象!<br/>
	 * 2：根据多字段分页查询 <br/>
	 * 3：如果是多个id,用","组成字符串. <br/>
	 * 4：formMap.put("id","xxx") 或 formMap.put("id","xxx,xxx,xxx")<br/>
	 * 5：formMap.put("name","xxx") 或 formMap.put("name","xxx,xxx,xxx")<br/>
	 * 6：如果值是多个“,”逗号分隔，则不支持模糊查询
	 * 7：兼容模糊查询。 formMap.put("name","用户%") 或 formMap.put("name","%用户%") <br/>
	 * 8：兼容排序查询 : formMap.put("$orderby","order by id desc");  <br/>
	 * <b>author：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/>
	 * <b>date：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-10-26</b><br/>
	 * <b>version：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.0v</b>
	 * 
	 * @return List<T>
	 */
	public <T> List<T> findByPage() {
		vali_param(this, "baseMapper.findByPage");
		List fs = getRepository().findByList(this);
		if(null != fs&&fs.size()>0){
			return fs;
		}else{
			return new ArrayList<T>();
		}
	}

	/**
	 *  #################  说明   ################# <br/>
	 * <b>公共方法默认调用 不需要传入 mapper_id</b><br/>
	 * 如果自定义复杂的Sql查询数据 必须传入 mapper_id=xxx.xxxx, <br/>
	 *　　　xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" , <br/>
	 *　　　其中的你查询sql是对应的  &lt;select id="findByAll"...>....其中 id = findByAll 这个就是 sqlid<br/>
	 * 那么参数必须是  mapper_id=userMapper.findByAll<br/><br/>
	 * 1：返回所有数据<br/>
	 * 
	 * <b>author：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/>
	 * <b>date：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-10-26</b><br/>
	 * <b>version：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.0v</b>
	 * @return List<T>
	 * @throws Exception
	 */
	public <T> List<T> findByAll() {
		vali_param(this, "baseMapper.findByNames");
		List fs = getRepository().findByList(this);
		if(null != fs&&fs.size()>0){
			return fs;
		}else{
			return new ArrayList<T>();
		}
	}
	
	
	/**
	 * #################  说明   ################# <br/>
	 * <b>公共方法默认调用 不需要传入 mapper_id</b><br/>
	 * 如果自定义Sql的删除语句 必须传入 mapper_id=xxx.xxxx, <br/>
	 *　　　xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" , <br/>
	 *　　　其中的你sql是对应的  &lt;delete id="deleteByIds"...>....其中 id = deleteByIds 这个就是 sqlid<br/>
	 * 那么参数必须是  mapper_id=userMapper.deleteByIds<br/><br/>
	 * 1：根据ID删除数据<br/>
	 * 2：如是多个Id,则 id="xxx,xxx,xxx"<br/>
	 * 3：formMap.put("id","xxx") 或 formMap.put("id","xxx,xxx,xxx")<br/>
	 * <b>author：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/>
	 * <b>date：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-10-26</b><br/>
	 * <b>version：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.0v</b>
	 * 
	 * @throws Exception
	 */
	public void deleteByIds(String id) throws Exception{
		vali_param(this, "baseMapper.deleteByIds");
		if (Common.isNotEmpty(id)) {
			try {
				FormMap<K, V> f =  this.getClass().newInstance();
				f.put((K)"ly_table", this.get("ly_table"));
				f.put((K)"ly_table_id", this.get("ly_table_id"));
				f.put((K)"ly_base",  this.get("ly_base"));
				f.put((K)"mapper_id", this.get("mapper_id"));
				f.put((K) this.get("ly_table_id"), (V) id);
				getRepository().delete(f);
			} catch (Exception e) {
				String results = "{\"results\":\"error\",\"message\":\""+e+"\"}";
				throw new SystemException(results);
			}
		} else {
			String results = "{\"results\":\"error\",\"message\":\"parameter error, id can not be empty!\"}";
			throw new ParameterException(results);
		}
	}

	/**
	 * #################  说明   ################# <br/>
	 * <b>公共方法默认调用 不需要传入 mapper_id</b><br/>
	 * 如果自定义Sql的插入语句 必须传入 mapper_id=xxx.xxxx, <br/>
	 *　　　xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" , <br/>
	 *　　　其中的你sql是对应的  &lt;insert id="add"...>....其中 id = add 这个就是 sqlid<br/>
	 * 那么参数必须是  mapper_id=userMapper.add<br/><br/>
	 * 1：保存数据,保存数据后返回对象的所有数据包括id. <br/>
	 * <b>author：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/>
	 * <b>date：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-10-26</b><br/>
	 * <b>version：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.0v</b>
	 * @throws Exception
	 */
	public <T> T save() throws Exception {
		vali_param(this, "baseMapper.addEntity");
		getRepository().addEntity(this);
		TableSeg table = (TableSeg) this.getClass().getAnnotation(TableSeg.class);
		if(null==table){
			if(null!=this.get("ly_table_id")){
				this.put((K)this.get("ly_table_id"), this.get("id"));
				this.remove("id");
			}
		}else{
			String table_id=table.id();
			if(table_id.split(",").length==1){
				if(this.get(table_id)==null){
					this.put((K)table_id, this.get("id"));
					this.remove("id");
				}
			}
		}
		return (T) this;
	}

	/**
	 * #################  说明   ################# <br/>
	 * <b>公共方法默认调用 不需要传入 mapper_id</b><br/>
	 * 如果自定义Sql的更新语句 必须传入 mapper_id=xxx.xxxx, <br/>
	 *　　　xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" , <br/>
	 *　　　其中的你sql是对应的  &lt;insert id="update"...>....其中 id = update 这个就是 sqlid<br/>
	 * 那么参数必须是  mapper_id=userMapper.update<br/><br/>
	 * 1：更新数据<br/>
	 * <b>author：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/>
	 * <b>date：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-10-26</b><br/>
	 * <b>version：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.0v</b>
	 * 
	 * @throws Exception
	 */
	public <T> T update() throws Exception {
		vali_param(this, "baseMapper.editEntity");
		getRepository().editEntity(this);
		return (T) this;
	}
	
	/**
	 * #################  说明   ################# <br/>
	 * <b>公共方法默认调用 不需要传入 mapper_id</b><br/>
	 * 如果自定义Sql的查询语句 必须传入 mapper_id=xxx.xxxx, <br/>
	 *　　　xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" , <br/>
	 *　　　其中的你sql是对应的  &lt;insert id="findById"...>....其中 id = findById 这个就是 sqlid<br/>
	 * 那么参数必须是  mapper_id=userMapper.findById<br/><br/>
	 * 1：根据ID获取数据 <br/>
	 * 2：如是多个Id,则 id="xxx,xxx,xxx"<br/>
	 * <b>author：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/>
	 * <b>date：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-10-26</b><br/>
	 * <b>version：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.0v</b>
	 * @return T
	 * @throws Exception
	 */
	public <T> T findById(String id){
		vali_param(this, "baseMapper.findById");
		if (Common.isNotEmpty(id)) {
			try {
				T t = (T) this.getClass().newInstance();
				FormMap f = (FormMap) t;
				f.put("ly_table", this.get("ly_table"));
				f.put("ly_table_id", this.get("ly_table_id"));
				f.put("ly_base",  this.get("ly_base"));
				f.put("mapper_id", this.get("mapper_id"));
				f.put((K) this.get("ly_table_id"), (V) id);
				List<T> lists = (List<T>) getRepository().findByList(t);
				if (null != lists && lists.size() > 0) {
					return (T) lists.get(0);
				} else {
					return (T) this.getClass().newInstance();
				}
			} catch (Exception e) {
				String results = "{\"results\":\"error\",\"message\":\""+e+"\"}";
				throw new SystemException(results);
			}
		} else {
			String results = "{\"results\":\"error\",\"message\":\"parameter error, id can not be empty!\"}";
			throw new ParameterException(results);
		}
	}
	
	/**
	 * 1：批量保存数据,如果是mysql,在驱动连接加上&allowMultiQueries=true这个参数 <br/>
	 * <b>author：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/>
	 * <b>date：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-10-26</b><br/>
	 * <b>version：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.0v</b>
	 * 
	 * @throws Exception
	 */
	public <T> void batchSave(List<T> formMap) throws Exception{
		 getRepository().batchSave(formMap);
	}

	/**
	 * 1：自定义where查询条件,返回是一个List<T>集合 <br/>
	 * <b>2：注意：这个方法有SQL注入风险,请谨慎使用</b><br/>
	 * 3：返回查询条件数据,如不传入.则返回所有数据..如果附加条件.如下 <br/>
	 * 4：formMap.put('where","id=XX and name= XX order by XX") <br/>
	 * <b>author：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/>
	 * <b>date：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-10-26</b><br/>
	 * <b>version：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.0v</b>
	 * 
	 * @return List<T>
	 */
	public <T> List<T> findByWhere() {
		vali_param(this, "baseMapper.findByWhere");
		List fs = getRepository().findByList(this);
		if(null != fs&&fs.size()>0){
			return fs;
		}else{
			return new ArrayList<T>();
		}
	}
	
	/**
	 * #################  说明   ################# <br/>
	 * <b>公共方法默认调用 不需要传入 mapper_id</b><br/>
	 * 如果自定义Sql的查询语句 必须传入 mapper_id=xxx.xxxx, <br/>
	 *　　　xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" , <br/>
	 *　　　其中的你sql是对应的  &lt;insert id="findByNames"...>....其中 id = findByNames 这个就是 sqlid<br/>
	 * 那么参数必须是  mapper_id=userMapper.findByNames<br/><br/>
	 * 1：根据属性获取数据<br/>
	 * 2：如果是多个属性值,用","组成字符串. <br/>
	 * 3：formMap.put("id","xxx") 或 formMap.put("id","xxx,xxx,xxx")<br/>
	 * 4：formMap.put("name","xxx") 或 formMap.put("name","xxx,xxx,xxx") <br/>
	 * 5：如果值是多个“,”逗号分隔，则不支持模糊查询
	 * 6：兼容模糊查询。 formMap.put("name","用户%") 或 formMap.put("name","%用户%") <br/>
	 * 7：兼容排序查询 : formMap.put("$orderby","order by id desc");  <br/>
	 * <b>author：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/>
	 * <b>date：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-10-26</b><br/>
	 * <b>version：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.0v</b>
	 * @return List<T>
	 * @throws Exception
	 */
	public <T> List<T> findByNames() {
		vali_param(this, "baseMapper.findByNames");
		List fs = getRepository().findByList(this);
		if(null != fs&&fs.size()>0){
			return fs;
		}else{
			return new ArrayList<T>();
		}
	}
	
	/**
	 * #################  说明   ################# <br/>
	 * 返回特定的类型对象或集合
	 * 如果自定义Sql的查询语句 必须传入 mapper_id=xxx.xxxx, <br/>
	 *　　　xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" , <br/>
	 *　　　其中的你sql是对应的  &lt;insert id="findByNames"...>....其中 id = findByNames 这个就是 sqlid<br/>
	 * 那么参数必须是  mapper_id=userMapper.findByNames<br/><br/>
	 * <b>author：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/>
	 * <b>date：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-10-26</b><br/>
	 * <b>version：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.0v</b>
	 * @return List<T>
	 * @throws Exception
	 */

	public <T> List<T> findObject(Class<T> t) {
		if (null == Common.FormMap(this).get("mapper_id")) {
			throw new SystemException("Can not find mapper_id parameter!");
		}
		List<?> fs = getRepository().findByList(this);
		if(null != fs&&fs.size()>0){
			return (List<T>)fs;
		}else{
			return new ArrayList<T>();
		}
	}
	
	/**
	 * 1：根据属性查询数据，返回第一个对象 <br/>
	 * 2：如果是多个属性值,用","组成字符串. <br/>
	 * 3：findbyFrist("id","xxx") 或 findbyFrist("id","xxx,xxx,xxx")<br/>
	 * 4：findbyFrist("name","xxx") 或 findbyFrist("name","xxx,xxx,xxx") <br/>
	 * 5：如果值是“,”逗号分隔，则不支持模糊查询
	 * 6：兼容模糊查询。 findbyFrist("name","用户%") 或 findbyFrist("name","%用户%") <br/>
	 * <b>author：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/>
	 * <b>date：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-10-26</b><br/>
	 * <b>version：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.0v</b>
	 * @return T
	 */
	public <T> T findbyFrist(String key, String value){
		try {
			this.put((K)"key", (V)key);
			this.put((K)"value", (V)value);
			vali_param(this, "baseMapper.findbyFrist");
			List<T> ms = (List<T>) getRepository().findByList(this);
			if(null != ms && ms.size()>=1)
				return (T) ms.get(0);
			else
				return (T) this.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 1：根据属性查询数据，返回第一个对象 <br/>
	 * 2：如果是多个属性值,用","组成字符串. <br/>
	 * <b>公共方法默认调用 不需要传入 mapper_id</b><br/>
	 * 如果自定义Sql的查询语句 必须传入 mapper_id=xxx.xxxx, <br/>
	 *　　　xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" , <br/>
	 *　　　其中的你sql是对应的  &lt;insert id="findbyFrist"...>....其中 id = findbyFrist 这个就是 sqlid<br/>
	 * 那么参数必须是  mapper_id=userMapper.findbyFrist<br/><br/>
	 * 1：根据属性获取数据<br/>
	 * 2：如果是多个属性值,用","组成字符串. <br/>
	 * 3：formMap.put("id","xxx") 或 formMap.put("id","xxx,xxx,xxx")<br/>
	 * 4：formMap.put("name","xxx") 或 formMap.put("name","xxx,xxx,xxx") <br/>
	 * 5：如果值是多个“,”逗号分隔，则不支持模糊查询
	 * 6：兼容模糊查询。 formMap.put("name","用户%") 或 formMap.put("name","%用户%") <br/>
	 * 7：兼容排序查询 : formMap.put("$orderby","order by id desc");  <br/>
	 * <b>author：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/>
	 * <b>date：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-10-26</b><br/>
	 * <b>version：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.0v</b>
	 * @return List<T>
	 * @throws Exception
	 */
	public <T> T findbyFrist(){
		vali_param(this, "baseMapper.findByNames");
		try {
			List fs = getRepository().findByList(this);
			if(null != fs&&fs.size()>0){
				return (T) fs.get(0);
			}else{
				return (T) this.getClass().newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * #################  说明   ################# <br/>
	 * <b>公共方法默认调用 不需要传入 mapper_id</b><br/>
	 * 如果自定义Sql的删除语句 必须传入 mapper_id=xxx.xxxx, <br/>
	 *　　　xxx.xxxx表示某个xxx-mapper.xml 的 namespace和sqlId <br/>
	 * 如 user-mapper.xml中。 namespace="userMapper" , <br/>
	 *　　　其中的你sql是对应的  &lt;insert id="deleteByNames"...>....其中 id = deleteByNames 这个就是 sqlid<br/>
	 * 那么参数必须是  mapper_id=userMapper.deleteByNames<br/><br/>
	 * 2：如果是多个属性,用","组成字符串. <br/>
	 * 3：formMap.put("id","xxx") 或 formMap.put("id","xxx,xxx,xxx")<br/>
	 * 4：formMap.put("name","xxx") 或 formMap.put("name","xxx,xxx,xxx") <br/>
	 * <b>author：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/>
	 * <b>date：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-10-26</b><br/>
	 * <b>version：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.0v</b>
	 * 
	 * @throws Exception
	 */
	public void deleteByNames()throws Exception{
		vali_param(this, "baseMapper.deleteByNames");
		 getRepository().delete(this);
	}
	
	/**
	 * 1：根据某个字段删除数据 <br/>
	 * 2：如果是多个属性值,用","组成字符串,value=xxx,xxx,xxx <br/>
	 * <b>author：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/>
	 * <b>date：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-10-26</b><br/>
	 * <b>version：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.0v</b>
	 * 
	 * @throws Exception
	 */
	public void deleteByAttribute(String key,Object value) throws Exception{
		this.put((K)"key", (V)key);
		this.put((K)"value", (V)value);
		vali_param(this, "baseMapper.deleteByAttribute");
		getRepository().delete(this);
	}
	
	
	/**
	 * 1：根据某个字段查询数据 <br/>
	 * 2：如果是多个属性值,用","组成字符串. <br/>
	 * 3：findByAttribute("id","xxx") 或 findByAttribute("id","xxx,xxx,xxx")<br/>
	 * 4：findByAttribute("name","xxx") 或 findByAttribute("name","xxx,xxx,xxx") <br/>
	 * 5：如果值是“,”逗号分隔，则不支持模糊查询<br/>
	 * 6：兼容模糊查询。 findbyFrist("name","用户%") 或 findbyFrist("name","%用户%") <br/>
	 * <b>author：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplanyuan</b><br/>
	 * <b>date：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2015-10-26</b><br/>
	 * <b>version：</b><br/>
	 * <b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp4.0v</b>
	 * 
	 * @return List<T>
	 */
	public <T> List<T> findByAttribute(String key,Object value){
			this.put((K)"key", (V)key);
			this.put((K)"value", (V)value);
			vali_param(this, "baseMapper.findByAttribute");
			List fs = getRepository().findByList(this);
			if(null != fs&&fs.size()>0){
				return fs;
			}else{
				return new ArrayList<T>();
			}
	}
	
	/**
	 * Get attribute of mysql type: varchar, char, enum, set, text, tinytext,
	 * mediumtext, longtext
	 */
	public String getStr(String attr) {
		return this.get(attr) == null ? "":this.get(attr).toString();
	}

	/**
	 * Get attribute of mysql type: int, integer, tinyint(n) n > 1, smallint,
	 * mediumint
	 */
	public Integer getInt(String attr) {
		return Integer.parseInt(this.get(attr).toString());
	}

	/**
	 * Get attribute of mysql type: bigint, unsign int
	 */
	public Long getLong(String attr) {
		return Long.parseLong(this.get(attr).toString());
	}

	/**
	 * Get attribute of mysql type: unsigned bigint
	 */
	public java.math.BigInteger getBigInteger(String attr) {
		return (java.math.BigInteger) this.get(attr);
	}

	/**
	 * Get attribute of mysql type: date, year
	 */
	public java.util.Date getDate(String attr) {
		return (java.util.Date) this.get(attr);
	}

	/**
	 * Get attribute of mysql type: time
	 */
	public java.sql.Time getTime(String attr) {
		return (java.sql.Time) this.get(attr);
	}

	/**
	 * Get attribute of mysql type: timestamp, datetime
	 */
	public java.sql.Timestamp getTimestamp(String attr) {
		return (java.sql.Timestamp) this.get(attr);
	}

	/**
	 * Get attribute of mysql type: real, double
	 */
	public Double getDouble(String attr) {
		return Double.parseDouble(this.get(attr).toString());
	}

	/**
	 * Get attribute of mysql type: float
	 */
	public Float getFloat(String attr) {
		return Float.parseFloat(this.get(attr).toString());
	}

	/**
	 * Get attribute of mysql type: bit, tinyint(1)
	 */
	public Boolean getBoolean(String attr) {
		return (Boolean) this.get(attr);
	}

	/**
	 * Get attribute of mysql type: decimal, numeric
	 */
	public java.math.BigDecimal getBigDecimal(String attr) {
		return (java.math.BigDecimal) this.get(attr);
	}

	/**
	 * Get attribute of mysql type: binary, varbinary, tinyblob, blob,
	 * mediumblob, longblob
	 */
	public byte[] getBytes(String attr) {
		return (byte[]) this.get(attr);
	}

	/**
	 * Get attribute of any type that extends from Number
	 */
	public Number getNumber(String attr) {
		return (Number) this.get(attr);
	}
	
	/**
	 * Return attribute names of this model.
	 */
	public String[] getAttrNames() {
		Set<K> attrNameSet =this.keySet();
		return attrNameSet.toArray(new String[attrNameSet.size()]);
	}

	/**
	 * Return attribute values of this model.
	 */
	public Object[] getAttrValues() {
		Collection<V> attrValueCollection = values();
		return attrValueCollection.toArray(new Object[attrValueCollection
				.size()]);
	}
	
	public <T> void vali_param(T formMap, String mapper_id) {
		if (null == Common.FormMap(formMap).get("mapper_id")) {
			Object o = Common.FormMap(formMap).get("FormMap");
			Object ly_table = null;
			if (null == o) {
				ly_table=EhcacheUtils.get("formMap_" + formMap.getClass().getSimpleName());
				if(null == ly_table)
				throw new SystemException(
						"Can not find correspondent name in formMap");
			}else{
				ly_table=EhcacheUtils.get("formMap_" + o.toString());
			}
			Common.FormMap(formMap).put("ly_table", ly_table.toString().split(",")[0]);
			Common.FormMap(formMap).put("ly_table_id", ly_table.toString().split(",")[1]);
			Common.FormMap(formMap).put("ly_base", "ly_base");
			Common.FormMap(formMap).put("mapper_id", mapper_id);
		}
	}
}
