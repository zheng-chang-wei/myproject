package com.hirain.qsy.shaft.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "t_user_role")
@Data
public class UserRole implements Serializable {

	private static final long serialVersionUID = -3166012934498268403L;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "ROLE_ID")
	private Long roleId;

}