package com.hirain.qsy.shaft.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;

import com.hirain.qsy.shaft.common.config.MyMapper;
import com.hirain.qsy.shaft.handler.BatchDataHandler;
import com.hirain.qsy.shaft.model.ExceptionData;

public interface ExceptionDataMapper extends MyMapper<ExceptionData> {

	List<ExceptionData> listExceptionData(Integer trainId, String startTime, String endTime);

	List<ExceptionData> listExceptionDataGroupByAcquisitionTime(Integer trainId, String startTime, String endTime);

	/**
	 * 批量插入同时处理重复数据
	 * 
	 * @param initialDatas
	 * @return
	 */
	@InsertProvider(type = BatchDataHandler.class, method = "batExceptionDataAdd")
	Integer batExceptionlAdd(List<ExceptionData> list, Integer trainId);

	@Delete("delete from t_exception_data_#{trainId} where acquisition_time<#{acquisitionTime}")
	Integer deleteByTrianIdAndAcquisitionTime(Integer trainId, String acquisitionTime);

}