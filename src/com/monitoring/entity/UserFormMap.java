package com.monitoring.entity;

import com.monitoring.annotation.TableSeg;
import com.monitoring.entity.base.FormMap;
import com.monitoring.exception.ParameterException;
import com.monitoring.util.Common;

/**
 * user实体表
 */
@TableSeg(tableName = "ly_user", id = "id")
public class UserFormMap extends FormMap<String, Object> {

	/**
	 * @descript
	 * @author lanyuan
	 * @date 2015年3月29日
	 * @version 1.0
	 */
	private static final long serialVersionUID = 1L;
	
	public UserFormMap validate(){
		if(Common.isEmpty(getStr("name"))){
			throw new ParameterException(" User name can not be empty!");
		}
		if(Common.isEmpty(getStr("password"))){
			throw new ParameterException(" Password can not be empty!");
		}
		return this;
	}
}
