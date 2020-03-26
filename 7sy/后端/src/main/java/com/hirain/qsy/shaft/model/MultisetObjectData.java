package com.hirain.qsy.shaft.model;

import java.util.List;

import lombok.Data;

@Data
public class MultisetObjectData {

	private List<InitialData> initialdatas;

	private List<List<String>> excelDataList;

	private TrainInfo trainInfor;
}
