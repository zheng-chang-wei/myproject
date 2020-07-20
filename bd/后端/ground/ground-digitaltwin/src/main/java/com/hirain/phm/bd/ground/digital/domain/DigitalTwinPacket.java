/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.digital.domain;

import java.util.Date;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Sep 25, 2019 10:41:04 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Sep 25, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class DigitalTwinPacket {

	@SerializedName("Project")
	private String project;

	@SerializedName("TrainID")
	private String trainID;

	@SerializedName("CarID")
	private int carID;

	@SerializedName("DoorID")
	private int doorID;

	@SerializedName("Debug")
	private Boolean debug;

	@SerializedName("Date")
	private Date date;

	@SerializedName("MotorPara")
	private Map<String, Double> motorPara;

	@SerializedName("MechPara")
	private Map<String, Double> mechPara;
}
