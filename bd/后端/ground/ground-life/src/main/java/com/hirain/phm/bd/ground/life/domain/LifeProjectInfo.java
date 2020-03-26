package com.hirain.phm.bd.ground.life.domain;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "t_life_project_info")
public class LifeProjectInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2898500463797529934L;

	private Integer id;

	private Integer projectId;

	private Integer lifeitemId;

}