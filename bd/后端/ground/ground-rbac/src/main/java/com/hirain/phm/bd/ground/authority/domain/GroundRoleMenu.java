package com.hirain.phm.bd.ground.authority.domain;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.Data;

@Table(name = "t_ground_role_menu")
@Data
public class GroundRoleMenu implements Serializable {

	private static final long serialVersionUID = -7573904024872252113L;

	private Long groundRoleId;

	private Long menuId;
}