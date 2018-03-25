package com.monitoring.entity;

import com.monitoring.annotation.TableSeg;
import com.monitoring.entity.base.FormMap;

@TableSeg(tableName = "ly_server_info", id="id")
public class ServerInfoFormMap extends FormMap<String, Object> {
	private static final long serialVersionUID = 1L;
}