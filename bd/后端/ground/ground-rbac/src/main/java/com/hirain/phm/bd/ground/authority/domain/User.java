package com.hirain.phm.bd.ground.authority.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hirain.phm.bd.ground.train.domain.Project;

import lombok.Data;

@Table(name = "t_user")
@Data
public class User implements Serializable {

	private static final long serialVersionUID = -4852732617765810959L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Long userId;

	private String userName;

	private String password;

	private String name;

	private Long deptId;

	private String email;

	private String mobile;

	private String wechat;

	private Date createTime;

	private Date modifyTime;

	@Transient
	private String deptName;

	private List<GroundRole> groundRoles;

	private List<Project> projects;

	private List<String> approvalRoleNames;

	public Long getId() {
		return userId;
	}

}