package com.hirain.phm.bd.ground.life.param;

import lombok.Data;

@Data
public class LifeMonitorRequest {

	private String project;

	private String trainNo;

	private Integer carNo;

	private Integer doorAddr;
}
