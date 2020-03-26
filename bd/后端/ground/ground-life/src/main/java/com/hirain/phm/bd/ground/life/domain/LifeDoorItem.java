package com.hirain.phm.bd.ground.life.domain;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "t_life_dooritem")
public class LifeDoorItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3429547303919830221L;

	private Integer id;

	private Integer trainId;

	private Integer carNo;

	private Integer doorAddr;

	private Integer lifeitemId;

	private Integer value;

}