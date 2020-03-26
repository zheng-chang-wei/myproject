package com.hirain.phm.bd.ground.common.event;

import lombok.Data;

@Data
public class TrainOnlineEvent {

	private String project;

	private String city;

	private String line;

	private String train;
}
