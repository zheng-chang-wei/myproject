package com.hirain.phm.bd.ground.bigdata.param;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class GroundData {

	private Long id;

	private String project;

	private String train;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date startDay;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date endDay;
}
