package com.hirain.phm.bd.ground.authority.domain;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.Data;

@Table(name = "t_project_user")
@Data
public class ProjectUser implements Serializable {

	private static final long serialVersionUID = 4049968611486077732L;

	private Long userId;

	private Long projectId;

}
