package com.hirain.phm.bd.ground.life.domain;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "t_life_item")
public class LifeItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4874811840274633909L;

	private Integer id;

	private String itemName;

}