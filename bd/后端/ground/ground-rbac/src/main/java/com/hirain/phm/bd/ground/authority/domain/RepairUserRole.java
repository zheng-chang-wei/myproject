package com.hirain.phm.bd.ground.authority.domain;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.Data;

@Table(name = "t_repair_user_role")
@Data
public class RepairUserRole implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Long userId;

	private Integer repairRoleId;
}
