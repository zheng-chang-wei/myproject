package com.hirain.qsy.shaft.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class InitialAxleData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8976173399476413088L;

	private Integer axle1;

	private Integer axle2;

	private Integer axle3;

	private Integer axle4;

	private Integer axle5;

	private Integer axle6;

	private Integer gpsSpeed;

	private Integer ambientTemperature1;

	private Integer ambientTemperature2;

	private Date acquisitionTime;

}
