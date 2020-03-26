package com.hirain.phm.bd.ground.authority.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "t_repair_role")
@Data
public class RepairRole implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer repairRoleId;

	private String roleName;

	private String remark;
}
