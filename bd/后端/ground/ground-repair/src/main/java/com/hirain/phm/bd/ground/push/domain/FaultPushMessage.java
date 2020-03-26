package com.hirain.phm.bd.ground.push.domain;

import lombok.Data;

@Data
public class FaultPushMessage {

	private String messageIndex;

	private String messageVersion;

	private String messageCode;

	private String remarks;

	private int lifeForecastKm = 1;

	private int lifeForecastTime = 1;

	private String beginTime;

	private String endTime;

	private String messageURL;
}
