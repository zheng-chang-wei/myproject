package com.hirain.qsy.shaft.model;

import java.util.HashMap;
import java.util.Map;

public class AttributeMappingConfigurationData {

	public static Map<String, String> mapData = new HashMap<>();

	public static Map<String, String> exceptionData = new HashMap<>();
	static {
		exceptionData.put("resultAxle11", "11");
		exceptionData.put("resultAxle12", "12");
		exceptionData.put("resultAxle13", "13");
		exceptionData.put("resultAxle14", "14");
		exceptionData.put("resultAxle15", "15");
		exceptionData.put("resultAxle16", "16");

		exceptionData.put("resultAxle21", "21");
		exceptionData.put("resultAxle22", "22");
		exceptionData.put("resultAxle23", "23");
		exceptionData.put("resultAxle24", "24");
		exceptionData.put("resultAxle25", "25");
		exceptionData.put("resultAxle26", "26");

		exceptionData.put("resultAxle31", "31");
		exceptionData.put("resultAxle32", "32");
		exceptionData.put("resultAxle33", "33");
		exceptionData.put("resultAxle34", "34");
		exceptionData.put("resultAxle35", "35");
		exceptionData.put("resultAxle36", "36");

		exceptionData.put("resultAxle41", "41");
		exceptionData.put("resultAxle42", "42");
		exceptionData.put("resultAxle43", "43");
		exceptionData.put("resultAxle44", "44");
		exceptionData.put("resultAxle45", "45");
		exceptionData.put("resultAxle46", "46");

		exceptionData.put("resultAxle51", "51");
		exceptionData.put("resultAxle52", "52");
		exceptionData.put("resultAxle53", "53");
		exceptionData.put("resultAxle54", "54");
		exceptionData.put("resultAxle55", "55");
		exceptionData.put("resultAxle56", "56");

		exceptionData.put("resultAxle61", "61");
		exceptionData.put("resultAxle62", "62");
		exceptionData.put("resultAxle63", "63");
		exceptionData.put("resultAxle64", "64");
		exceptionData.put("resultAxle65", "65");
		exceptionData.put("resultAxle66", "66");

		mapData.put("机车车号", "trainId");
		mapData.put("采集时间", "acquisitionTime");
		mapData.put("环温1-℃", "ambientTemperature1");
		mapData.put("环温2-℃", "ambientTemperature2");
		mapData.put("主发电机温度-℃", "generatorTemperature");
		mapData.put("风机1温度-℃", "fan1Temperature");
		mapData.put("风机2温度-℃", "fan2Temperature");

		mapData.put("1轴测试点1温度-℃", "axle11");
		mapData.put("1轴测试点2温度-℃", "axle12");
		mapData.put("1轴测试点3温度-℃", "axle13");
		mapData.put("1轴测试点4温度-℃", "axle14");
		mapData.put("1轴测试点5温度-℃", "axle15");
		mapData.put("1轴测试点6温度-℃", "axle16");

		mapData.put("2轴测试点1温度-℃", "axle21");
		mapData.put("2轴测试点2温度-℃", "axle22");
		mapData.put("2轴测试点3温度-℃", "axle23");
		mapData.put("2轴测试点4温度-℃", "axle24");
		mapData.put("2轴测试点5温度-℃", "axle25");
		mapData.put("2轴测试点6温度-℃", "axle26");

		mapData.put("3轴测试点1温度-℃", "axle31");
		mapData.put("3轴测试点2温度-℃", "axle32");
		mapData.put("3轴测试点3温度-℃", "axle33");
		mapData.put("3轴测试点4温度-℃", "axle34");
		mapData.put("3轴测试点5温度-℃", "axle35");
		mapData.put("3轴测试点6温度-℃", "axle36");

		mapData.put("4轴测试点1温度-℃", "axle41");
		mapData.put("4轴测试点2温度-℃", "axle42");
		mapData.put("4轴测试点3温度-℃", "axle43");
		mapData.put("4轴测试点4温度-℃", "axle44");
		mapData.put("4轴测试点5温度-℃", "axle45");
		mapData.put("4轴测试点6温度-℃", "axle46");

		mapData.put("5轴测试点1温度-℃", "axle51");
		mapData.put("5轴测试点2温度-℃", "axle52");
		mapData.put("5轴测试点3温度-℃", "axle53");
		mapData.put("5轴测试点4温度-℃", "axle54");
		mapData.put("5轴测试点5温度-℃", "axle55");
		mapData.put("5轴测试点6温度-℃", "axle56");

		mapData.put("6轴测试点1温度-℃", "axle61");
		mapData.put("6轴测试点2温度-℃", "axle62");
		mapData.put("6轴测试点3温度-℃", "axle63");
		mapData.put("6轴测试点4温度-℃", "axle64");
		mapData.put("6轴测试点5温度-℃", "axle65");
		mapData.put("6轴测试点6温度-℃", "axle66");

		mapData.put("最大温度-℃", "maxTemp");
		mapData.put("最大温度测点号", "maxTempPoint");
		mapData.put("当前报警测点号", "alarmPoint");
		mapData.put("报警代码", "alarmCode");
		mapData.put("当前报警时间", "alarmTime");
		mapData.put("报警时测点温度-℃", "alarmPointTemp");
		mapData.put("GPS经度", "gpsLongitude");
		mapData.put("GPS纬度", "gpsLatitude");
		mapData.put("GPS速度", "gpsSpeed");
		mapData.put("原始表主键", "originalprimarykey");
	}
}
