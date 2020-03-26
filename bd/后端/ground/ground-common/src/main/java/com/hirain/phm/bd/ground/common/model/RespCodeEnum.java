package com.hirain.phm.bd.ground.common.model;

/**
 * 返回代码 常量
 * 
 * @author
 */
public enum RespCodeEnum {

	// 该用户名已存在！
	RepeatName("0001", "该用户名已存在！"),
	// 不能删除超级管理员
	CanNotDeleteAdministrator("0002", "不能删除超级管理员"),
	// 删除用户失败，不能删除当前登录用户！
	CanNotDeleteCurrentLoginUser("0003", "删除用户失败，不能删除当前登录用户！"),

	FORCE_LOGIN("500", "NotLogin");

	private RespCodeEnum(String code, String name) {

		this.code = code;
		this.name = name;
	}

	public String code;

	public String name;

	public String getCode() {

		return code;
	}

	public String getName() {

		return name;
	}

}
