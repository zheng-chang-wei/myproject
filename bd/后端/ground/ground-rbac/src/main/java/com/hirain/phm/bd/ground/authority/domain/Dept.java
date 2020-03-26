package com.hirain.phm.bd.ground.authority.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "t_dept")
@Data
public class Dept implements Serializable {

	private static final long serialVersionUID = -7790334862410409053L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Long deptId;

	private Long parentId;

	private String deptName;

	private Date createTime;
}