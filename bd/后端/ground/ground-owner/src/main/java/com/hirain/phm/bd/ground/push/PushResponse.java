package com.hirain.phm.bd.ground.push;

import lombok.Data;

@Data
public class PushResponse {

	private int code;

	private String message;

	private String data;
}
