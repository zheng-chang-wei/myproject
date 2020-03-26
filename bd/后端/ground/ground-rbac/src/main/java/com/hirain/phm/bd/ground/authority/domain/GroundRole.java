package com.hirain.phm.bd.ground.authority.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Table(name = "t_ground_role")
@Data
public class GroundRole implements Serializable {

	private static final long serialVersionUID = -1714476694755654924L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Long groundRoleId;

	private String roleName;

	private String remark;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	private Date modifyTime;

	private List<Menu> menus;

	@Transient
	private List<Long> menuIds;

}