package com.hirain.qsy.shaft.common.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobVariableConfig {

	/**
	 * DataToPythonUtils.class 所需字段
	 */
	// 转化为python所需的字段名
	public static String[] title = { "1轴测试点1温度-℃", "1轴测试点2温度-℃", "1轴测试点3温度-℃", "1轴测试点4温度-℃", "1轴测试点5温度-℃", "1轴测试点6温度-℃", "2轴测试点1温度-℃", "2轴测试点2温度-℃",
			"2轴测试点3温度-℃", "2轴测试点4温度-℃", "2轴测试点5温度-℃", "2轴测试点6温度-℃", "3轴测试点1温度-℃", "3轴测试点2温度-℃", "3轴测试点3温度-℃", "3轴测试点4温度-℃", "3轴测试点5温度-℃",
			"3轴测试点6温度-℃", "4轴测试点1温度-℃", "4轴测试点2温度-℃", "4轴测试点3温度-℃", "4轴测试点4温度-℃", "4轴测试点5温度-℃", "4轴测试点6温度-℃", "5轴测试点1温度-℃", "5轴测试点2温度-℃",
			"5轴测试点3温度-℃", "5轴测试点4温度-℃", "5轴测试点5温度-℃", "5轴测试点6温度-℃", "6轴测试点1温度-℃", "6轴测试点2温度-℃", "6轴测试点3温度-℃", "6轴测试点4温度-℃", "6轴测试点5温度-℃",
			"6轴测试点6温度-℃", "GPS速度" };

	/**
	 * Excel 第一行要包含的标题
	 */
	public static List<String> excelFirstRowTitleList = new ArrayList<>();

	static {
		excelFirstRowTitleList.add("原始表主键");
		excelFirstRowTitleList.add("机车车型");
		excelFirstRowTitleList.add("机车车号");
		excelFirstRowTitleList.add("采集时间");
		excelFirstRowTitleList.add("环温1-℃");
		excelFirstRowTitleList.add("环温2-℃");
		excelFirstRowTitleList.addAll(Arrays.asList(title));
	}

	/**
	 * 历史数据展示的列名
	 */
	public static String[] axleName = { "axle11", "axle12", "axle13", "axle14", "axle15", "axle16", "ambientTemperature1", "ambientTemperature2",
			"gpsSpeed", "acquisitionTime", "axle21", "axle22", "axle23", "axle24", "axle25", "axle26", "ambientTemperature1", "ambientTemperature2",
			"gpsSpeed", "acquisitionTime", "axle31", "axle32", "axle33", "axle34", "axle35", "axle36", "ambientTemperature1", "ambientTemperature2",
			"gpsSpeed", "acquisitionTime", "axle41", "axle42", "axle43", "axle44", "axle45", "axle46", "ambientTemperature1", "ambientTemperature2",
			"gpsSpeed", "acquisitionTime", "axle51", "axle52", "axle53", "axle54", "axle55", "axle56", "ambientTemperature1", "ambientTemperature2",
			"gpsSpeed", "acquisitionTime", "axle61", "axle62", "axle63", "axle64", "axle65", "axle66", "ambientTemperature1", "ambientTemperature2",
			"gpsSpeed", "acquisitionTime" };

	// excel时间字段的转化字段OperateExcelUtils.class
	public static String acquisitionTime = "采集时间";

}
