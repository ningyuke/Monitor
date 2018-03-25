package com.monitoring.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.monitoring.annotation.TableSeg;
import com.monitoring.entity.base.FormMap;
import com.lanyuan.mapper.base.BaseMapper;


public class ConfigUtils {
	private final Logger logger = Logger.getLogger(ConfigUtils.class);
	/**
	 * 初始化数据库表字段到缓存
	 * @param baseMapper 
	 * @param baseMapper 
	 * @param entityPackage entity的包的路径 例如：com.sysbase.server.entity
	 * @param databaseName 主数据库的名称 例如tpmsaas
	 */
	public void initTableField(BaseMapper baseMapper, String entityPackage) {
		try {
			Properties pro = PropertiesUtils.getjdbcProperties();
			String url = pro.getProperty("jdbc.url");
			String db = url.substring(url.lastIndexOf("/")+1);
			if(db.indexOf("?")>-1){
				db=db.substring(0, db.indexOf("?"));
			}
			String tabs = "";
			List<String> classNames = ClassUtil.getClassName(entityPackage, true);
			if (classNames != null) {
				for (String className : classNames) {
					Class<?> clazz = Class.forName(className);
					boolean flag = clazz.isAnnotationPresent(TableSeg.class); // 某个类是不是存在TableSeg注解
					if (flag) {
						TableSeg table = (TableSeg) clazz.getAnnotation(TableSeg.class);
						tabs+="'"+table.tableName()+"',";
						EhcacheUtils.put("formMap_"+clazz.getSimpleName(), table.tableName()+","+table.id());
					} 
				}
			}
			//获取数据库名
			tabs=Common.trimComma(tabs);
			//尽量减少对数据库/IO流操作,一次查询所有表的的字段
			FormMap<String, Object> tm = new FormMap<String, Object>();
			tm.put("table_name", tabs);
			tm.put("database_name","'"+db+"'");
			tm.put("mapper_id", "initTableField");
			 List<FormMap<String, Object>> lh = baseMapper.findByList(tm);
			 for (Map<String, Object> hashMap : lh) {
				 Map<String, Object> m = new HashMap<String, Object>();
				 String field = hashMap.get("COLUMN_NAME").toString();
				 field= Common.trimComma(field);
				 m.put("field", field);
				 String ble =hashMap.get("TABLE_NAME").toString();//表名
				 //m.put("column_key", map.get(ble));//获取表的主键
				 logger.info("加载表字段到缓存 -->> 表："+ble+" 字段："+m);
				 EhcacheUtils.put(ble, m);//某表对应的主键和字段放到缓存
			}
		} catch (Exception e) {
			logger.error(" 初始化数据失败,没法加载表字段到缓存 -->> "+e.fillInStackTrace());
			e.printStackTrace();
		}
	}
}
