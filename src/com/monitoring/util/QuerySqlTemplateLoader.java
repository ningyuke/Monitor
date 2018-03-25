package com.monitoring.util;
/*
 * Copyright (c) nesoft Technologies(China),Inc.
 *
 * FileName: ConfigSqlTemplateLoader.java
 *
 * Created Jimmy 2014-4-1
 * 
 */


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;
import java.util.Map;

import freemarker.cache.TemplateLoader;
/**
 * @author lanyuan
 * @date 2016-05-19
 * @Email mmm333zzz520@163.com
 * @version 4.1v
 */
@SuppressWarnings("rawtypes")
public class QuerySqlTemplateLoader implements TemplateLoader
{
	// ----------------------------------------------------- Properties
	
	private Map config;
	private String i18n;

	// ----------------------------------------------------- Constructors

	protected QuerySqlTemplateLoader( Map config )
	{
		this.config = config;

		Locale locale = Locale.getDefault();
		i18n = "_"+locale.getLanguage();
	}

	// ----------------------------------------------------- Methods

	public Object findTemplateSource( String name ) throws IOException
	{
		String result = name.substring( 0, name.indexOf( i18n ) );

		return result;
	}

	public long getLastModified( Object templateSource )
	{
		return Long.parseLong(config.get("last_time").toString());
	}

	public Reader getReader( Object templateSource, String encoding ) throws IOException
	{
		ByteArrayInputStream in = new ByteArrayInputStream( config.get("query_sql").toString().getBytes( encoding ) );

		return new InputStreamReader( in, encoding );
	}

	public void closeTemplateSource( Object templateSource ) throws IOException
	{
	}
}
