package com.hirain.phm.bd.ground.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsEvent {

	private boolean statistics;

	private int topCode;

	private long faultId;
}
