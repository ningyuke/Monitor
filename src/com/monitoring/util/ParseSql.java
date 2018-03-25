package com.monitoring.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.Template;

@SuppressWarnings({"rawtypes","deprecation"}) 
public class ParseSql {
	public String parseSql(Map map){
			try {
				String packpath = getClass().getPackage().getName().replaceAll( "[.]", "/" );
				Configuration cfg = new Configuration();
				cfg.setClassForTemplateLoading( getClass(), "/" + packpath );
				cfg.setDefaultEncoding( "UTF-8" );
				cfg.setOutputEncoding( "UTF-8" );
				cfg.setTemplateLoader(  new QuerySqlTemplateLoader( map ) );
				Template template = cfg.getTemplate(getClass().getPackage().getName());
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				template.process(map, new OutputStreamWriter(out, "UTF-8"));
				return new String(out.toByteArray(), "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
}

