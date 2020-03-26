package com.hirain.phm.bd.ground.life.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "t_life_warning")
public class LifeWarning {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	private Integer trainId;

	private Integer carNo;

	private Integer doorAddr;

	private Integer lifeitemId;

	private Date warningTime;

	private Double remainderLife;

	private Byte debugMode;

	private Boolean statistics;

}