package com.hirain.phm.bd.ground.statistics.domain;

import java.util.List;

import lombok.Data;

@Data
public class TypeStatisticsResult {

	private String topType;

	private List<TypeResult> types;
}
