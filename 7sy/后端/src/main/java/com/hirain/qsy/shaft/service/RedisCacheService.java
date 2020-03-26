package com.hirain.qsy.shaft.service;

import java.util.List;
import java.util.Set;

import com.hirain.qsy.shaft.model.ExceptionData;
import com.hirain.qsy.shaft.model.StatisticsChartDataRow;

public interface RedisCacheService {

	void cache(List<ExceptionData> exceptionDataList, Integer trainId) throws Exception;

	String getLastestTime(Integer trainId);

	String getLastestExceptionTime(Integer trainId);

	void deleteBypPttern(String pattern);

	Integer getLastMonthExceptionCount(Integer trainId, String lastestTime);

	Integer getExceptionCountByDate(Integer trainId, String startTime, String endTime);

	void deleteCache(Integer trainId);

	void deleteCache(Integer trainId, String deadline);

	Set<String> getKeysPyPttern(String pattern);

	Integer getValueByKey(String key);

	int getCurrentWeekExeptionCount(List<Integer> trainId, String monday);

	List<StatisticsChartDataRow> getAxlesExceptionCount(Integer trainId, String startTime, String endTime);

}
