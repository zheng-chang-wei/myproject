package com.hirain.phm.bd.message.train;

import lombok.Getter;

public enum DoorState {
	Opened("开门"), Closed("关门"),

	Opening("开门中"), Closing("关门中");

	@Getter
	private String message;

	private DoorState(String message) {
		this.message = message;
	}
}
