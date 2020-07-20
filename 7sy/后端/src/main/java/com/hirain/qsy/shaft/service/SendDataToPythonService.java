package com.hirain.qsy.shaft.service;

import java.util.Date;
import java.util.List;

import com.hirain.qsy.shaft.model.ExceptionData;

public interface SendDataToPythonService {

	List<ExceptionData> postData(String params, List<Date> acquisitionTimes, List<String> primaryKey);
}
