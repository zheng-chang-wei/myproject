package com.hirain.qsy.shaft.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Table(name = "t_initial_data")
public class InitialData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "train_id")
	private Integer trainId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "acquisition_time")
	private Date acquisitionTime;

	@Column(name = "ambient_temperature_1")
	private Integer ambientTemperature1;

	@Column(name = "ambient_temperature_2")
	private Integer ambientTemperature2;

	@Column(name = "axle1_1")
	private Integer axle11;

	@Column(name = "axle1_2")
	private Integer axle12;

	@Column(name = "axle1_3")
	private Integer axle13;

	@Column(name = "axle1_4")
	private Integer axle14;

	@Column(name = "axle1_5")
	private Integer axle15;

	@Column(name = "axle1_6")
	private Integer axle16;

	@Column(name = "axle2_1")
	private Integer axle21;

	@Column(name = "axle2_2")
	private Integer axle22;

	@Column(name = "axle2_3")
	private Integer axle23;

	@Column(name = "axle2_4")
	private Integer axle24;

	@Column(name = "axle2_5")
	private Integer axle25;

	@Column(name = "axle2_6")
	private Integer axle26;

	@Column(name = "axle3_1")
	private Integer axle31;

	@Column(name = "axle3_2")
	private Integer axle32;

	@Column(name = "axle3_3")
	private Integer axle33;

	@Column(name = "axle3_4")
	private Integer axle34;

	@Column(name = "axle3_5")
	private Integer axle35;

	@Column(name = "axle3_6")
	private Integer axle36;

	@Column(name = "axle4_1")
	private Integer axle41;

	@Column(name = "axle4_2")
	private Integer axle42;

	@Column(name = "axle4_3")
	private Integer axle43;

	@Column(name = "axle4_4")
	private Integer axle44;

	@Column(name = "axle4_5")
	private Integer axle45;

	@Column(name = "axle4_6")
	private Integer axle46;

	@Column(name = "axle5_1")
	private Integer axle51;

	@Column(name = "axle5_2")
	private Integer axle52;

	@Column(name = "axle5_3")
	private Integer axle53;

	@Column(name = "axle5_4")
	private Integer axle54;

	@Column(name = "axle5_5")
	private Integer axle55;

	@Column(name = "axle5_6")
	private Integer axle56;

	@Column(name = "axle6_1")
	private Integer axle61;

	@Column(name = "axle6_2")
	private Integer axle62;

	@Column(name = "axle6_3")
	private Integer axle63;

	@Column(name = "axle6_4")
	private Integer axle64;

	@Column(name = "axle6_5")
	private Integer axle65;

	@Column(name = "axle6_6")
	private Integer axle66;

	@Column(name = "gps_speed")
	private Integer gpsSpeed;

	@Column(name = "original_primary_key")
	private String originalprimarykey;

}