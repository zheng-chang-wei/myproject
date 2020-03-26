package com.hirain.phm.bd.ground.authority.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private Long menuId;

	private Long parentId;

	private String menuName;

	private String url;

	private String perms;

	private String icon;

	private String type;

	private Long orderNum;

	private Date createTime;

	private Date modifyTime;
	
	@Transient
	private List<Menu> children = new ArrayList<>();
}