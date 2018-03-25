package com.monitoring.plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.PropertyException;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.log4j.Logger;

import com.monitoring.annotation.TableSeg;
import com.monitoring.entity.base.FormMap;
import com.monitoring.exception.SystemException;
import com.monitoring.util.Common;
import com.monitoring.util.EhcacheUtils;
import com.monitoring.util.ParseSql;

/**
 * Mybatis的分页查询插件，通过拦截StatementHandler的prepare方法来实现。 
 * 只有在参数列表中包括Page类型的参数时才进行分页查询。 
 * 在多参数的情况下，只对第一个Page类型的参数生效。 
 * 另外，在参数列表中，Page类型的参数无需用@Param来标注 
 * @author lanyuan
 * 2015-03-20
 * @Email: mmm333zzz520@163.com
 * @version 4.1v
 */
@SuppressWarnings("unchecked")
@Intercepts( { @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PagePlugin implements Interceptor {
	public final static Logger logger = Logger.getLogger(PagePlugin.class);
	private static String dialect = null;//数据库类型
	private static String pageSqlId = ""; // mybaits的数据库xml映射文件中需要拦截的ID(正则匹配)
	@SuppressWarnings("rawtypes")
	public Object intercept(Invocation ivk) throws Throwable {
		if (ivk.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk
					.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper
					.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper
					.getValueByFieldName(delegate, "mappedStatement");
			delegate.getBoundSql();
			/**
			 * 方法1：通过ＩＤ来区分是否需要分页．.*query.*
			 * 方法2：传入的参数是否有page参数，如果有，则分页，
			 */
		//	if (mappedStatement.getId().matches(pageSqlId)) { // 拦截需要分页的SQL
				BoundSql boundSql = delegate.getBoundSql();
				Object parameterObject = boundSql.getParameterObject();// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				if (parameterObject instanceof Map) {
					Map mp = (Map) parameterObject;
			        mp.remove("mapper_id");
	                mp.remove("ly_base");
				}
				if (parameterObject == null) {
					//throw new NullPointerException("boundSql.getParameterObject() is null!");
					return ivk.proceed();
				} else {
					
					if(mappedStatement.getId().endsWith(".dynamic_sql")||mappedStatement.getId().endsWith(".find_feild")){
						Connection connection = (Connection) ivk.getArgs()[0];
						Map map = (Map) parameterObject;
						dynamic_sql(connection, mappedStatement, boundSql, map);
						return ivk.proceed();
					}else if (mappedStatement.getId().indexOf("baseMapper.") > -1&&!mappedStatement.getId().endsWith(".initTableField")) {
						Connection connection = (Connection) ivk.getArgs()[0];
						// parameterObject = toHashMap(model, pageView);
						// 公共方法被调用
							if (parameterObject instanceof Map) {
								Map map = (Map) parameterObject;
								if (map.containsKey("list")&&map.size()==1) {
									List<Object> lists = (List<Object>) map.get("list");
									joinSql(connection, mappedStatement, boundSql, null, lists);
								}else{
									joinSql(connection, mappedStatement, boundSql, map, null);
								}
							} else {
								throw new SystemException("Call public method with wrong input parameters! Please review parameter instruction for details!");
							}
							return ivk.proceed();
					}
					PageView pageView = null;
					if (parameterObject instanceof PageView) { // 参数就是Pages实体
						pageView = (PageView) parameterObject;
					} else if (parameterObject instanceof Map) {
						for (Entry entry : (Set<Entry>) ((Map) parameterObject)
								.entrySet()) {
							if (entry.getValue() instanceof PageView) {
								pageView = (PageView) entry.getValue();
								break;
							}
						}
					} else { // 参数为某个实体，该实体拥有Pages属性
						pageView = ReflectHelper.getValueByFieldType(
								parameterObject, PageView.class);
						if (pageView == null) {
							return ivk.proceed();
						}
					}
					if (pageView == null) {
						return ivk.proceed();
					}
					String sql = boundSql.getSql();
					Connection connection = (Connection) ivk.getArgs()[0];
					setCount(sql, mappedStatement, connection, boundSql, pageView);
					String pageSql = generatePagesSql(sql, pageView);
					ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql); // 将分页sql语句反射回BoundSql.
				}
			//}
		}
		return ivk.proceed();
	}
    /**
	 * 根据数据库方言，生成特定的分页sql
	 * 
	 * @param sql
	 * @param page
	 * @return
	 */
	private String generatePagesSql(String sql, PageView page) {
		if (page != null) {
			if("mysql".equals(dialect)){
				return buildPageSqlForMysql(sql, page).toString();
			}else if("oracle".equals(dialect)){
				return buildPageSqlForOracle(sql, page).toString();
			}
		}
		return sql;
	}
	  /**
     * mysql的分页语句
     * 
     * @param sql
     * @param page
     * @return String
     */
    public StringBuilder buildPageSqlForMysql(String sql, PageView page) {
    	 StringBuilder pageSql = new StringBuilder(100);
         String beginrow = String.valueOf((page.getPageNow() - 1) * page.getPageSize());
         pageSql.append(sql);
         pageSql.append(" limit " + beginrow + "," + page.getPageSize());
         return pageSql;
    }

    /**
     * 参考hibernate的实现完成oracle的分页
     * 
     * @param sql
     * @param page
     * @return String
     */
    public StringBuilder buildPageSqlForOracle(String sql, PageView page) {
        StringBuilder pageSql = new StringBuilder(100);
        String beginrow = String.valueOf((page.getPageNow()) * page.getPageSize());
        String endrow = String.valueOf(page.getPageNow()+1 * page.getPageSize());

        pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
        pageSql.append(sql);
        pageSql.append(" ) temp where rownum <= ").append(endrow);
        pageSql.append(") where row_id > ").append(beginrow);
        return pageSql;
    }
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (isEmpty(dialect)) {
			try {
				throw new PropertyException("dialectName or dialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		} 
		pageSqlId = p.getProperty("pageSqlId");//根据id来区分是否需要分页
		if (isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void dynamic_sql( Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Map<String, Object> formMap)throws Exception{
		List<ParameterMapping> bpm =new ArrayList<ParameterMapping>();
		Configuration configuration = mappedStatement.getConfiguration();
		Map map = (Map) boundSql.getParameterObject();
		String sql = new ParseSql().parseSql(formMap);
		Pattern p = Pattern.compile("#(.*?)#");
        Matcher m = p.matcher(sql);
        int i = 0;
        while (m.find()) {
           String v = m.group(1);  
           Builder mapping = new ParameterMapping.Builder(configuration, v+"_"+i, String.class);
			ParameterMapping parameter = mapping.build();
			bpm.add(parameter);
			map.put(v+"_"+i, formMap.get(v));
			i++;
        } 
        if(bpm.size()>0){
			ReflectHelper.setValueByFieldName(boundSql, "parameterMappings", bpm);
		}
        sql=sql.replaceAll("#(.*?)#", "?");
        if(mappedStatement.getId().endsWith(".find_feild")){
        	sql+= " "+map.get("$orderby");
        }else{
        	Object paging = formMap.get("paging");
       		if (null == paging) {
       			throw new SystemException("findByPage interface must input with PageView object! formMap.set(\"paging\", pageView);");
       		} else if (isEmpty(paging.toString())) {
       			throw new SystemException("findByPage interface must input with PageView object! formMap.set(\"paging\", pageView);");
       		}
               PageView pageView = (PageView) paging;
       		setCount(sql, mappedStatement,connection, boundSql, pageView);
       		sql = generatePagesSql(sql, pageView);
        }
		ReflectHelper.setValueByFieldName(boundSql, "sql", sql);
	}
	
	
	public void joinSql( Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Map<String, Object> formMap, List<Object> formMaps)throws Exception{
		Object table = null;
		String sql = "";
		Map<String, Object> mapfield=null;
		String field = null;
		List<ParameterMapping> bpm =new ArrayList<ParameterMapping>();
		Configuration configuration = mappedStatement.getConfiguration();
		if (null != formMap) {
			table = formMap.get("ly_table");
			mapfield=(Map<String, Object>) EhcacheUtils.get(table);
			field = mapfield.get("field").toString();
			sql = " select "+field+" from " + String.valueOf(table);
		}
		String sqlId = mappedStatement.getId();
		sqlId = sqlId.substring(sqlId.lastIndexOf(".") + 1);
		if ("findByWhere".equals(sqlId)) {// 根据条件查询
			if (null != formMap.get("where") && !isEmpty(formMap.get("where").toString())) {
				sql += " where " + formMap.get("where").toString();
			}
		} else if ("findByPage".equals(sqlId)) {// 分页时..必须 有PageView对象
			if (null != formMap.get("where") && !isEmpty(formMap.get("where").toString())) {
				
			}else{

				String[] fe = field.split(",");
				String param = "";
				for (String string : fe) {
					if (formMap.containsKey(string)) {
						Object v = formMap.get(string);
						if (v.toString().indexOf("%") > -1)// 处理模糊查询
						{
							if(v.toString().indexOf("%")==v.toString().lastIndexOf("%")&&v.toString().indexOf("%")==0)
							{	
								v = v.toString().substring(1);
								param += " and " + string + " like CONCAT('%', ?)";
							}else if(v.toString().indexOf("%")==v.toString().lastIndexOf("%")&&v.toString().indexOf("%")==v.toString().length())
							{	v = v.toString().substring(0,v.toString().length()-1);
								param += " and " + string + " like CONCAT(?, '%')";	
							}else
							{ 
								v = v.toString().substring(1,v.toString().length()-1);
								param += " and " + string + " like CONCAT(CONCAT('%', ?), '%')";	
							}
						} else {
							param += " and " + string + " = ?";
						}
						Builder mapping = new ParameterMapping.Builder(configuration, string, v.getClass());
						ParameterMapping parameter = mapping.build();
						bpm.add(parameter);
						ReflectHelper.setValueByFieldName(boundSql, "parameterMappings", bpm);
					}
				}
				if (StringUtils.isNotBlank(param)) {
					param = param.substring(param.indexOf("and") + 4);
					sql += " where " + param;
				}
				Object by = formMap.get("$orderby");
				Object sort = formMap.get("column");
				if (null != by) {
					sql += " " + by;
				}else if(null != sort){
					sql += " order by " + sort +" "+formMap.get("sort");
				}
			}
			Object paging = formMap.get("paging");
			if (null == paging) {
				throw new SystemException("findByPage interface must input with PageView object! formMap.set(\"paging\", pageView);");
			} else if (isEmpty(paging.toString())) {
				throw new SystemException("findByPage interface must input with PageView object! formMap.set(\"paging\", pageView);");
			}
			PageView pageView = (PageView) paging;
			setCount(sql, mappedStatement,connection, boundSql, pageView);
			sql = generatePagesSql(sql, pageView);
		}else if("deleteByIds".equals(sqlId)){
			String tableid = formMap.get("ly_table_id").toString();
			sql = "delete from "+table.toString()+" where "+tableid+" in ("+formMap.get(tableid)+")";
		}else if("findById".equals(sqlId)){
			String tableid = formMap.get("ly_table_id").toString();
			sql = "select * from "+table.toString()+" where "+tableid+" in ("+formMap.get(tableid)+")";
		}else if ("deleteByNames".equals(sqlId)) {
			sql = "delete from " + table.toString() + " where ";
			String[] fe = field.split(",");
			String param = "";
			for (String string : fe) {
				if (formMap.containsKey(string)) {
					String[] v = String.valueOf(formMap.get(string)).split(",");
					String vs = "";
					Map map = (Map) boundSql.getParameterObject();
					for (int i = 0; i < v.length; i++) {
						vs+="?,";
						Builder mapping = new ParameterMapping.Builder(configuration, string+"_value_"+i, v[i].getClass());
						ParameterMapping parameter = mapping.build();
						bpm.add(parameter);
						map.put(string+"_value_"+i, v[i]);
					}
					vs=Common.trimComma(vs);
					param += " and "+string+" in ("+vs+")";
				}
			}	
			
			if (StringUtils.isNotBlank(param)) {
				param = param.substring(param.indexOf("and") + 4);
				sql += param;
			}
		} else if ("deleteByAttribute".equals(sqlId)) {
			sql = "delete from " + table.toString() + " where " + formMap.get("key");
			if(null!=formMap.get("value"))
			{
				String[] v = String.valueOf(formMap.get("value")).split(",");
				String vs = "";
				Map map = (Map) boundSql.getParameterObject();
				for (int i = 0; i < v.length; i++) {
					vs+="?,";
					Builder mapping = new ParameterMapping.Builder(configuration, "value_"+i, v[i].getClass());
					ParameterMapping parameter = mapping.build();
					bpm.add(parameter);
					map.put("value_"+i, v[i]);
				}
				vs=Common.trimComma(vs);
				sql += " in ("+vs+")";
			}
		} else if ("findByNames".equals(sqlId)) {
			String[] fe = field.split(",");
			String param = "";
			Map map = (Map) boundSql.getParameterObject();
			for (String string : fe) {
				if (formMap.containsKey(string)) {
					Object v = formMap.get(string);
					if (v.toString().indexOf("%") > -1)// 处理模糊查询
					{
						if(v.toString().indexOf("%")==v.toString().lastIndexOf("%")&&v.toString().indexOf("%")==0)
						{	
							v = v.toString().substring(1);
							param += " and " + string + " like CONCAT('%', ?)";
						}else if(v.toString().indexOf("%")==v.toString().lastIndexOf("%")&&v.toString().indexOf("%")==v.toString().length())
						{	v = v.toString().substring(0,v.toString().length()-1);
							param += " and " + string + " like CONCAT(?, '%')";	
						}else
						{ 
							v = v.toString().substring(1,v.toString().length()-1);
							param += " and " + string + " like CONCAT(CONCAT('%', ?), '%')";	
						}
						Builder mapping = new ParameterMapping.Builder(configuration, string, v.getClass());
						ParameterMapping parameter = mapping.build();
						bpm.add(parameter);
					} else {
						String[] vv = String.valueOf(v).split(",");
						if(vv != null && vv.length>1){
							String vs = "";
							for (int i = 0; i < vv.length; i++) {
								vs+="?,";
								Builder mapping = new ParameterMapping.Builder(configuration, string+"_key_"+i, vv[i].getClass());
								ParameterMapping parameter = mapping.build();
								bpm.add(parameter);
								map.put(string+"_key_"+i, vv[i]);
							}
							vs=Common.trimComma(vs);
							param += " and "+string+" in ("+vs+")";
						}else{
							param += " and " + string + " = ?";
							Builder mapping = new ParameterMapping.Builder(configuration, string, v.getClass());
							ParameterMapping parameter = mapping.build();
							bpm.add(parameter);
						}
					}
					
				}
			}
			if (StringUtils.isNotBlank(param)) {
				param = param.substring(param.indexOf("and") + 4);
				sql += " where " + param;
			}
			Object by = formMap.get("$orderby");
			Object sort = formMap.get("column");
			if (null != by) {
				sql += " " + by;
			}else if(null != sort){
				sql += " order by " + sort +" "+formMap.get("sort");
			}
		}  else if ("findbyFrist".equals(sqlId)) {
			String key = formMap.get("key")+"";
			sql = "select "+field+" from " + table.toString() + " where " + key;
			Object v = formMap.get("value");
			Map map = (Map) boundSql.getParameterObject();
			if (null != v && !"".equals(v))// 处理模糊查询
			{
				String[] vv = String.valueOf(v).split(",");
				String vs = "";
				for (int j = 0; j < vv.length; j++) {
					vs+="?,";
					Builder mapping = new ParameterMapping.Builder(configuration, "key_"+j, vv[j].getClass());
					ParameterMapping parameter = mapping.build();
					bpm.add(parameter);
					map.put("key_"+j, vv[j]);
				}
				vs=Common.trimComma(vs);
				sql += " in ("+vs+")";
			} else {
				throw new SystemException(sqlId + " Call public method with wrong input parameters!");
			}

		} else if ("findByAttribute".equals(sqlId)) {
			String key = formMap.get("key")+"";
			sql = "select "+field+" from " + table.toString() + " where " + key;
			Object v = formMap.get("value");
			Map map = (Map) boundSql.getParameterObject();
			if (null != v && v.toString().indexOf("%") > -1)// 处理模糊查询
			{
				if(v.toString().indexOf("%")==v.toString().lastIndexOf("%")&&v.toString().indexOf("%")==0)
				{	
					v = v.toString().substring(1);
					sql +=" like CONCAT('%', ?)";
				}else if(v.toString().indexOf("%")==v.toString().lastIndexOf("%")&&v.toString().indexOf("%")==v.toString().length())
				{	v = v.toString().substring(0,v.toString().length()-1);
					sql +=" like CONCAT(?, '%')";	
				}else
				{ 
					v = v.toString().substring(1,v.toString().length()-1);
					sql +=" like CONCAT(CONCAT('%', ?), '%')";	
				}
				Builder mapping = new ParameterMapping.Builder(configuration, "key_", v.getClass());
				ParameterMapping parameter = mapping.build();
				bpm.add(parameter);
				map.put("key_", v);
			} else {
				String[] vv = String.valueOf(v).split(",");
				String vs = "";
				for (int i = 0; i < vv.length; i++) {
					vs+="?,";
					Builder mapping = new ParameterMapping.Builder(configuration, "key_"+i, vv[i].getClass());
					ParameterMapping parameter = mapping.build();
					bpm.add(parameter);
					map.put("key_"+i, vv[i]);
				}
				vs=Common.trimComma(vs);
				sql += " in ("+vs+")";

			}
		} else if ("addEntity".equals(sqlId)) {
			String[] fe = field.split(",");
			String fieldString = "";
			String fieldValues = "";
			for (String string : fe) {
				Object v = formMap.get(string);
				if (null != v && !isEmpty(v.toString())) {
					fieldString += string + ",";
					fieldValues += "?,";
					Builder mapping = new ParameterMapping.Builder(configuration, string, v.getClass());
					ParameterMapping parameter = mapping.build();
					bpm.add(parameter);
				}
				
			}
			sql = "insert into " + table.toString() + " (" + Common.trimComma(fieldString) + ")  values (" + Common.trimComma(fieldValues) + ")";
		} else if ("editEntity".equals(sqlId)) {
			String[] fe = field.split(",");
			String fieldString = "";
			String where = " where " + formMap.get("ly_table_id") + " = ?";
			String column_key = formMap.get("ly_table_id").toString();
			for (String string : fe) {
				Object v = formMap.get(string);
				if (null != v && !isEmpty(v.toString()) && !column_key.equals(string)) {
						fieldString += string + "= ?,";
						Builder mapping = new ParameterMapping.Builder(configuration, string, v.getClass());
						ParameterMapping parameter = mapping.build();
						bpm.add(parameter);
				}
			}
			if(Common.isNotEmpty(column_key)){
				Builder mapping = new ParameterMapping.Builder(configuration, column_key,String.class);
				ParameterMapping parameter = mapping.build();
				bpm.add(parameter);
			}
			sql = "update " + table.toString() + " set " + Common.trimComma(fieldString) + " " + where;
		} else if ("batchUpdate".equals(sqlId)) {
			if (null != formMaps && formMaps.size() > 0) {
				table = toHashMap(formMaps.get(0)).get("ly_table");
				String column_key = toHashMap(formMaps.get(0)).get("ly_table_id").toString();
				Map map = (Map) boundSql.getParameterObject();
				mapfield=(Map<String, Object>) EhcacheUtils.get(table);
				field = mapfield.get("field").toString();
				String[] fe = field.split(",");
				for (int i = 0; i < formMaps.size(); i++) {
					Object object = formMaps.get(i);
					FormMap<String, Object> froMmap = (FormMap<String, Object>) object;
					String where = " where " + column_key + " = ? ;\n ";
					String fieldString = "";
					for (String string : fe) {
						Object v = froMmap.get(string);
						if (null != v && !isEmpty(v.toString()) && !column_key.equals(string)) {
							fieldString += string + "= ?,";
							Builder mapping = new ParameterMapping.Builder(configuration, string+"_"+i, v.getClass());
							ParameterMapping parameter = mapping.build();
							bpm.add(parameter);
							map.put(string+"_"+i, v);
						}
					}
					if(Common.isNotEmpty(column_key)){
						Builder mapping = new ParameterMapping.Builder(configuration, column_key+"_"+i,String.class);
						ParameterMapping parameter = mapping.build();
						bpm.add(parameter);
						map.put(column_key+"_"+i, froMmap.get(column_key).toString());
					}
					sql+= "update " + table.toString() + " set " + Common.trimComma(fieldString) + " " + where;
				}
				sql=sql.substring(0, sql.length()-4);
			}
		}else if ("batchSave".equals(sqlId)) {
			if (null != formMaps && formMaps.size() > 0) {
				table = toHashMap(formMaps.get(0)).get("ly_table");
				mapfield=(Map<String, Object>) EhcacheUtils.get(table);
				field = mapfield.get("field").toString();
				
				sql = "insert into " + table.toString();
				String fieldString = "";
				String fs = "";
				String fd = "";
				String fieldValues = "";
				String fvs = "";
				for (int i = 0; i < formMaps.size(); i++) {
					Object object = formMaps.get(i);
					FormMap<String, Object> froMmap = (FormMap<String, Object>) object;
					String[] fe = field.split(",");
					for (String string : fe) {
						Object v = froMmap.get(string);
						if (null != v && !isEmpty(v.toString())) {
							fieldString += string + ",";
							fieldValues += "'" + Common.mysqltoString(v.toString()) + "',";
						}
					}
					if (i == 0) {
						fd = fieldString;
					}
					fvs += "(" + Common.trimComma(fieldValues) + "),";
					fs += " insert into " + table.toString() + " (" + Common.trimComma(fieldString) + ")  values (" + Common.trimComma(fieldValues) + ");";
					fieldValues = "";
					fieldString = "";
				}
				String v = Common.trimComma(fvs);
				sql = "insert into " + table.toString() + " (" + Common.trimComma(fd) + ")  values " + Common.trimComma(fvs) + "";
				String[] vs = v.split("\\),");
				boolean b = false;
				for (String string : vs) {
					if (string.split(",").length != fd.split(",").length) {
						b = true;
					}
				}
				if (b) {
					sql = fs.substring(0, fs.length() - 1);
				}
			}else{
				throw new SystemException("Call public method exception! error: this list->obj is null");
			}
		} else {
			throw new SystemException("Call public method exception!");
		}
		if(bpm.size()>0){
			ReflectHelper.setValueByFieldName(boundSql, "parameterMappings", bpm);
		}
		ReflectHelper.setValueByFieldName(boundSql, "sql", sql);
	}
	public static void setCount(String sql,MappedStatement mappedStatement, Connection connection, BoundSql boundSql,
			PageView pageView) throws SQLException {
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {
			String countSql = "";
			try {
				if(Common.getStringNoBlank(sql).toUpperCase().indexOf(" GROUP ")>-1){
					countSql = "select count(1) from (" + sql+ ") tmp_count"; 
				}else{
					countSql = "select count(1) " + suffixStr(sql);
				}
				countStmt = connection.prepareStatement(countSql);
	            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),countSql.toString(),boundSql.getParameterMappings(),boundSql.getParameterObject());    
	            setParameters(countStmt,mappedStatement,countBS,boundSql.getParameterObject());    
				rs = countStmt.executeQuery();
			} catch (Exception e) {
				logger.error(countSql+" Count SQL occurs error. Switch to normal count SQL automatically!");
				countSql = "select count(1) from (" + sql+ ") tmp_count"; 
				countStmt = connection.prepareStatement(countSql);
	            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),countSql.toString(),boundSql.getParameterMappings(),boundSql.getParameterObject());    
	            setParameters(countStmt,mappedStatement,countBS,boundSql.getParameterObject());    
				rs = countStmt.executeQuery();
			}
			int count = 0;
			if (rs.next()) {
				count = ((Number) rs.getObject(1)).intValue();
			}
			pageView.setRowCount(count);
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				countStmt.close();
			} catch (Exception e) {
			}
		}

	}
	/**
	 * select id, articleNofrom, sum(ddd) ss, articleName, ( SELECT
	 * loginName,sum(ddd) from oss_userinfo u where u.id=userId order by id)
	 * loginName, (SELECT userName from oss_userinfo u where u.id=userId and
	 * fromaa = (SELECT userName from oss_userinfo u where u.id=userId)
	 * fromuserName, sum(ddd) ss from article,(select xxx) where = (SELECT
	 * userName from oss_userinfo u where u.id=userId order by id desc) order by
	 * id desc 兼容以上子查询 //去除sql ..from 前面的字符。考虑 aafrom fromdd 等等情况
	 */
	static String source_sql = "";

	public static String suffixStr(String toSql) {
		toSql = Common.getStringNoBlank(toSql);
		if (StringUtils.isBlank(source_sql))
			source_sql = toSql;
		toSql = toSql.toLowerCase();
		int sun = toSql.indexOf(" from ");
		String s1 = toSql.substring(0, sun);
		if (s1.indexOf("( select ") > -1 || s1.indexOf("(select ") > -1) {
			return suffixStr(toSql.substring(sun + 5));
		} else {
			toSql = toSql.substring(sun);
			source_sql = source_sql.substring(source_sql.length()
					- toSql.length());
			int s = 0;
			int e = 0;
			String sql = source_sql.toLowerCase();
			sun = sql.lastIndexOf(" order ");
			if (sun > -1) {
				String f = sql.substring(sun);
				if (f.lastIndexOf(")") == -1) {
					int a = sql.lastIndexOf(" asc");
					if (a > -1) {
						String as = sql.substring(a + 4, a + 5);
						if (as.trim().isEmpty()) {
							s = sql.lastIndexOf(" order ");
							e = a + 5;
						}
					}
					int d = sql.lastIndexOf(" desc");
					if (d > -1) {
						String ds = sql.substring(d + 5, d + 6);
						if (ds.trim().isEmpty()) {
							s = sql.lastIndexOf(" order ");
							e = d + 6;
						}
					}
					String ss = source_sql.substring(0, s + 1);
					String ee = source_sql.substring(e);
					source_sql = ss + ee;
				}

			}
		}
		toSql = source_sql;
		source_sql = "";
		return toSql;
	}

	public static void main(String[] args) throws Exception {  
		 String sql = ""
		 		+ " select id, articleNoorder by id desc, sum(ddd) ss, articleName, ( SELECT loginName,sum(ddd) from"
		 		+ "  oss_userinfo u where u.id=userId) loginName, (SELECT userName from"
	 + "  oss_userinfo u where u.id=userId and order by id descaa =  (SELECT userName from"
	 + " oss_userinfo u where u.id=userId  order by id asc) userNameorder by id desc, sum(ddd) ss from article,(select xxx)  where = (SELECT userName from"
	 + " oss_userinfo u where u.id=userId order By id desc) order by   f1   group by xx";
		 System.err.println((suffixStr(sql)));
		
  
    }  
	/**
	 * 判断变量是否为空
	 * 
	 * @param s
	 * @return
	 */
	public boolean isEmpty(String s) {
		if (null == s || "".equals(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s)) {
			return true;
		} else {
			return false;
		}
	}
	public Map<String, Object> toHashMap(Object parameterObject) {
		Map<String, Object> froMmap = (FormMap<String, Object>) parameterObject;
		try {
			String name = parameterObject.getClass().getName();
			Class<?> clazz = Class.forName(name);  
			boolean flag = clazz.isAnnotationPresent(TableSeg.class);  //某个类是不是存在TableSeg注解
			if (flag) {  
				TableSeg table = (TableSeg) clazz.getAnnotation(TableSeg.class);
				logger.info(" Public method is called. Input parameters ==>> " + froMmap);
				froMmap.put("ly_table", table.tableName());
				froMmap.put("ly_table_id", table.id());
			}else{
				throw new NullPointerException("In"+name+" can not find correspondent annotation in database!");
			}
			return froMmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return froMmap;
	}
	/**  
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler  
     * @param ps  
     * @param mappedStatement  
     * @param boundSql  
     * @param parameterObject  
     * @throws SQLException  
     */    
    @SuppressWarnings("rawtypes")
    public static void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {    
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());    
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();    
        if (parameterMappings != null) {    
            Configuration configuration = mappedStatement.getConfiguration();    
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();    
            MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);    
            for (int i = 0; i < parameterMappings.size(); i++) {    
                ParameterMapping parameterMapping = parameterMappings.get(i);    
                if (parameterMapping.getMode() != ParameterMode.OUT) {    
                    Object value;    
                    String propertyName = parameterMapping.getProperty();    
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);    
                    if (parameterObject == null) {    
                        value = null;    
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {    
                        value = parameterObject;    
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {    
                        value = boundSql.getAdditionalParameter(propertyName);    
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {    
                        value = boundSql.getAdditionalParameter(prop.getName());    
                        if (value != null) {    
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));    
                        }    
                    } else {    
                        value = metaObject == null ? null : metaObject.getValue(propertyName);    
                    }    
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();    
                    if (typeHandler == null) {    
                        throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());    
                    }    
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());    
                }    
            }    
        }    
    } 
}
