package com.hirain.qsy.shaft.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hirain.qsy.shaft.model.ExceptionData;

public class JsonUtils {

	public static List<ExceptionData> jsonToObject(String jsonString, List<Date> dates, List<String> primaryKey) {
		if (jsonString != "") {
			List<ExceptionData> dataExceptions = new ArrayList<ExceptionData>();
			String[] resultAxles = new String[36];
			JSONObject jsonObject = JSON.parseObject(jsonString);
			Integer trainId = jsonObject.getInteger("train_No");
			List<String[]> jsonStrings = new ArrayList<>();
			for (int i = 1; i <= 6; i++) {
				for (int j = 1; j <= 6; j++) {
					JSONObject pointJsonObject = jsonObject.getJSONObject("bearing" + i).getJSONObject("point" + j);
					String[] sampleValue = pointJsonObject.getString("sampleValue").replace("[", "").replace("]", "").split(",");
					String[] predictedValue = pointJsonObject.getString("predictedValue").replace("[", "").replace("]", "").split(",");
					String[] residuals = pointJsonObject.getString("residuals").replace("[", "").replace("]", "").split(",");
					String[] shhaftTemerature = pointJsonObject.getString("shhaftTemperature" + "Abnormal").replace("[", "").replace("]", "")
							.split(",");
					jsonStrings.add(sampleValue);
					jsonStrings.add(predictedValue);
					jsonStrings.add(residuals);
					jsonStrings.add(shhaftTemerature);
				}
			}
			for (int i = 0; i < dates.size(); i++) {
				for (int j = 0; j < jsonStrings.size() / 4; j++) {
					// String tmpString=jsonStrings.get(j)[i]
					resultAxles[j] = jsonStrings.get(4 * j)[i] + "," + jsonStrings.get(4 * j + 1)[i] + "," + jsonStrings.get(4 * j + 2)[i] + ","
							+ jsonStrings.get(4 * j + 3)[i];

				}
				ExceptionData exceptionData = new ExceptionData(trainId, dates.get(i), primaryKey.get(i), resultAxles);
				dataExceptions.add(exceptionData);
			}
			return dataExceptions;
		} else {
			return null;
		}

	}
}
