package com.hirain.qsy.shaft.handler;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.hirain.qsy.shaft.model.ExceptionData;
import com.hirain.qsy.shaft.model.InitialData;

public class BatchDataHandler {

	@SuppressWarnings("unchecked")
	public String batDataAdd(Map<?, ?> map) {
		List<InitialData> initialDatas = (List<InitialData>) map.get("list");
		Integer trainId = (Integer) map.get("trainId");
		StringBuilder sb = new StringBuilder();
		sb.append("insert ignore  into t_initial_data_").append(trainId)
				.append("(acquisition_time, ambient_temperature_1, ambient_temperature_2," + "axle1_1, axle1_2, axle1_3, axle1_4, axle1_5, axle1_6, "
						+ "axle2_1, axle2_2, axle2_3, axle2_4, axle2_5,axle2_6," + "axle3_1, axle3_2, axle3_3, axle3_4, axle3_5, axle3_6, "
						+ "axle4_1, axle4_2, axle4_3, axle4_4, axle4_5,axle4_6, " + "axle5_1, axle5_2, axle5_3, axle5_4, axle5_5, axle5_6, "
						+ "axle6_1, axle6_2, axle6_3, axle6_4, axle6_5,axle6_6, " + "gps_speed, original_primary_key) values ");
		MessageFormat mf = new MessageFormat("(#'{'list[{0}].acquisitionTime},#'{'list[{0}].ambientTemperature1},#'{'list[{0}].ambientTemperature2},"
				+ "#'{'list[{0}].axle11},#'{'list[{0}].axle12},#'{'list[{0}].axle13},#'{'list[{0}].axle14},#'{'list[{0}].axle15},#'{'list[{0}].axle16},"
				+ "#'{'list[{0}].axle21},#'{'list[{0}].axle22},#'{'list[{0}].axle23},#'{'list[{0}].axle24},#'{'list[{0}].axle25},#'{'list[{0}].axle26},"
				+ "#'{'list[{0}].axle31},#'{'list[{0}].axle32},#'{'list[{0}].axle33},#'{'list[{0}].axle34},#'{'list[{0}].axle35},#'{'list[{0}].axle36},"
				+ "#'{'list[{0}].axle41},#'{'list[{0}].axle42},#'{'list[{0}].axle43},#'{'list[{0}].axle44},#'{'list[{0}].axle45},#'{'list[{0}].axle46},"
				+ "#'{'list[{0}].axle51},#'{'list[{0}].axle52},#'{'list[{0}].axle53},#'{'list[{0}].axle54},#'{'list[{0}].axle55},#'{'list[{0}].axle56},"
				+ "#'{'list[{0}].axle61},#'{'list[{0}].axle62},#'{'list[{0}].axle63},#'{'list[{0}].axle64},#'{'list[{0}].axle65},#'{'list[{0}].axle66},"
				+ "#'{'list[{0}].gpsSpeed},#'{'list[{0}].originalprimarykey})");
		for (int i = 0; i < initialDatas.size(); i++) {
			sb.append(mf.format(new Object[] { i }));
			if (i < initialDatas.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public String batExceptionDataAdd(Map<?, ?> map) {
		List<ExceptionData> exceptionDatas = (List<ExceptionData>) map.get("list");
		Integer trainId = (Integer) map.get("trainId");
		StringBuilder sb = new StringBuilder();
		sb.append("insert ignore  into t_exception_data_").append(trainId).append("(acquisition_time, result_axle1_1, "
				+ "result_axle1_2, result_axle1_3, result_axle1_4, result_axle1_5, result_axle1_6, result_axle2_1,"
				+ "result_axle2_2, result_axle2_3, result_axle2_4, result_axle2_5, result_axle2_6, "
				+ "result_axle3_1,result_axle3_2, result_axle3_3, result_axle3_4, result_axle3_5, result_axle3_6,"
				+ "result_axle4_1,result_axle4_2, result_axle4_3, result_axle4_4, result_axle4_5, result_axle4_6,"
				+ "result_axle5_1,result_axle5_2, result_axle5_3, result_axle5_4, result_axle5_5, result_axle5_6,"
				+ "result_axle6_1,result_axle6_2, result_axle6_3, result_axle6_4, result_axle6_5, result_axle6_6," + "original_primary_key) values ");
		MessageFormat mf = new MessageFormat("(#'{'list[{0}].acquisitionTime},"
				+ "#'{'list[{0}].resultAxle11},#'{'list[{0}].resultAxle12},#'{'list[{0}].resultAxle13},#'{'list[{0}].resultAxle14},#'{'list[{0}].resultAxle15},#'{'list[{0}].resultAxle16},"
				+ "#'{'list[{0}].resultAxle21},#'{'list[{0}].resultAxle22},#'{'list[{0}].resultAxle23},#'{'list[{0}].resultAxle24},#'{'list[{0}].resultAxle25},#'{'list[{0}].resultAxle26},"
				+ "#'{'list[{0}].resultAxle31},#'{'list[{0}].resultAxle32},#'{'list[{0}].resultAxle33},#'{'list[{0}].resultAxle34},#'{'list[{0}].resultAxle35},#'{'list[{0}].resultAxle36},"
				+ "#'{'list[{0}].resultAxle41},#'{'list[{0}].resultAxle42},#'{'list[{0}].resultAxle43},#'{'list[{0}].resultAxle44},#'{'list[{0}].resultAxle45},#'{'list[{0}].resultAxle46},"
				+ "#'{'list[{0}].resultAxle51},#'{'list[{0}].resultAxle52},#'{'list[{0}].resultAxle53},#'{'list[{0}].resultAxle54},#'{'list[{0}].resultAxle55},#'{'list[{0}].resultAxle56},"
				+ "#'{'list[{0}].resultAxle61},#'{'list[{0}].resultAxle62},#'{'list[{0}].resultAxle63},#'{'list[{0}].resultAxle64},#'{'list[{0}].resultAxle65},#'{'list[{0}].resultAxle66},"
				+ "#'{'list[{0}].originalPrimaryKey})");
		for (int i = 0; i < exceptionDatas.size(); i++) {
			sb.append(mf.format(new Object[] { i }));
			if (i < exceptionDatas.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	public String findInitialData(Integer trainId, String beginTime, String endTime, int axleNum) {
		String sql = "select acquisition_time,Avg(ambient_temperature_1) AS ambient_temperature_1,Avg(ambient_temperature_2) AS ambient_temperature_2,";
		StringBuilder sb = new StringBuilder();
		sb.append(sql);
		switch (axleNum) {
		case 1:
			sb.append(
					"Avg(axle1_1) AS axle1_1,Avg(axle1_2) AS axle1_2,Avg(axle1_3) AS axle1_3,Avg(axle1_4) AS axle1_4,Avg(axle1_5) AS axle1_5,Avg(axle1_6) AS axle1_6,");
			break;
		case 2:
			sb.append(
					"Avg(axle2_1) AS axle2_1,Avg(axle2_2) AS axle2_2,Avg(axle2_3) AS axle2_3,Avg(axle2_4) AS axle2_4,Avg(axle2_5) AS axle2_5,Avg(axle2_6) AS axle2_6,");
			break;
		case 3:
			sb.append(
					"Avg(axle3_1) AS axle3_1,Avg(axle3_2) AS axle3_2,Avg(axle3_3) AS axle3_3,Avg(axle3_4) AS axle3_4,Avg(axle3_5) AS axle3_5,Avg(axle3_6) AS axle3_6,");
			break;
		case 4:
			sb.append(
					"Avg(axle4_1) AS axle4_1,Avg(axle4_2) AS axle4_2,Avg(axle4_3) AS axle4_3,Avg(axle4_4) AS axle4_4,Avg(axle4_5) AS axle4_5,Avg(axle4_6) AS axle4_6,");
			break;
		case 5:
			sb.append(
					"Avg(axle5_1) AS axle5_1,Avg(axle5_2) AS axle5_2,Avg(axle5_3) AS axle5_3,Avg(axle5_4) AS axle5_4,Avg(axle5_5) AS axle5_5,Avg(axle5_6) AS axle5_6,");
			break;
		case 6:
			sb.append(
					"Avg(axle6_1) AS axle6_1,Avg(axle6_2) AS axle6_2,Avg(axle6_3) AS axle6_3,Avg(axle6_4) AS axle6_4,Avg(axle6_5) AS axle6_5,Avg(axle6_6) AS axle6_6,");
			break;
		default:
			break;
		}
		sb.append("Avg(gps_speed) AS gps_speed FROM ").append("t_initial_data_" + trainId);
		sb.append(" where acquisition_time BETWEEN '").append(beginTime).append("' and '").append(endTime);
		sb.append("' GROUP BY acquisition_time ORDER BY acquisition_time");

		return sb.toString();
	}

}
