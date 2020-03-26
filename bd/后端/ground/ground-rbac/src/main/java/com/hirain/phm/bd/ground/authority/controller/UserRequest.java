/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.authority.controller;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年3月28日 下午3:08:15
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年3月28日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
public class UserRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5531163658939828616L;
	
	private String roleName;
	
	private String userName;
	
	private String name;
	
	private String email;
	
	private String mobile;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date starttime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endtime;
	
	private String deptName;

	@Override
	public String toString() {
		return "UserRequest [roleName=" + roleName + ", starttime=" + starttime + ", endtime=" + endtime + "]" + super.toString();
	}

}
