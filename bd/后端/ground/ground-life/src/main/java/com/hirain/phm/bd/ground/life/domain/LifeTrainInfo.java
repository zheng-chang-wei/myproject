package com.hirain.phm.bd.ground.life.domain;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "t_life_traininfo")
public class LifeTrainInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6889178053457630063L;

	private Integer id;

	private Integer trainId;

	private Integer lifeitemId;

	/**
	 * 参考值
	 */
	private Integer referenceValue;

	/**
	 * 项点发生故障的次数
	 */
	private Integer failuresNums;

	/**
	 * 每次故障时该项点运行值的总和
	 */
	private Integer allValue;

}