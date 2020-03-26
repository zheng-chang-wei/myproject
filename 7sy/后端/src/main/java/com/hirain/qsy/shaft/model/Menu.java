package com.hirain.qsy.shaft.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Table(name = "t_menu")
@Data
public class Menu implements Serializable {

	private static final long serialVersionUID = 7187628714679791771L;

	public static final String TYPE_MENU = "0";

	public static final String TYPE_BUTTON = "1";

	@Id
	@GeneratedValue(generator = "JDBC")
	@Column(name = "MENU_ID")
	private Long id;

	@Column(name = "PARENT_ID")
	private Long parentId;

	@Column(name = "MENU_NAME")
	private String name;

	@Column(name = "URL")
	private String url;

	@Column(name = "PERMS")
	private String perms;

	@Column(name = "ICON")
	private String icon;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "ORDER_NUM")
	private Long orderNum;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "MODIFY_TIME")
	private Date modifyTime;

	@Transient
	private List<Menu> children = new ArrayList<>();

}