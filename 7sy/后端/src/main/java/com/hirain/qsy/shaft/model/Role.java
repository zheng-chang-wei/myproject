package com.hirain.qsy.shaft.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "t_role")
@Data
public class Role implements Serializable {

	private static final long serialVersionUID = -1714476694755654924L;

	@Id
	@GeneratedValue(generator = "JDBC")
	@Column(name = "ROLE_ID")
	private Long roleId;

	@Column(name = "ROLE_NAME")
	private String roleName;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "MODIFY_TIME")
	private Date modifyTime;

}