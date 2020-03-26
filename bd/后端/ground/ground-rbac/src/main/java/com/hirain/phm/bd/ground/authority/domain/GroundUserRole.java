package com.hirain.phm.bd.ground.authority.domain;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.Data;

@Table(name = "t_ground_user_role")
@Data
public class GroundUserRole implements Serializable {

	private static final long serialVersionUID = -3166012934498268403L;

	private Long userId;

	private Long groundRoleId;

}