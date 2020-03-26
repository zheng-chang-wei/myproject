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

@Table(name = "t_user")
@Data
public class User implements Serializable {

	private static final long serialVersionUID = -4852732617765810959L;

	@Id
	@GeneratedValue(generator = "JDBC")
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	private String name;

	private String idNum;

	private String deptName;

	private Long parentId;

	private String parentName;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Transient
	private List<Role> roleList = new ArrayList<>();

}