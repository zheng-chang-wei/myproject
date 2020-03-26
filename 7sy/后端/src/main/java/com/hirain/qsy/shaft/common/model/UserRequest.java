/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.qsy.shaft.common.model;

import com.hirain.qsy.shaft.model.User;

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
public class UserRequest extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5531163658939828616L;

	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "UserRequest [roleName=" + roleName + "]" + super.toString();
	}

}
