package com.hirain.qsy.shaft.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.hirain.qsy.shaft.handler.BatchDataHandler;
import com.hirain.qsy.shaft.model.InitialData;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

@org.apache.ibatis.annotations.Mapper
public interface InitialDataMapper extends Mapper<InitialData>, InsertListMapper<InitialData> {

	/**
	 * 查询历史数据
	 * 
	 * @param train_id
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@SelectProvider(type = BatchDataHandler.class, method = "findInitialData")
	List<InitialData> avgOfAxleData(Integer trainId, String beginTime, String endTime, int axleNum);

	/**
	 * 批量插入同时处理重复数据
	 * 
	 * @param initialDatas
	 * @return
	 */
	@InsertProvider(type = BatchDataHandler.class, method = "batDataAdd")
	Integer batInitialAdd(List<InitialData> list, Integer trainId);

	@Select("select max(acquisition_time) max,min(acquisition_time) min  from t_initial_data_#{trainId} ")
	Map<String, Object> findMaxAndMinTime(Integer trainId);

	/**
	 * 根据train_id查询最大时间
	 * 
	 * @param trainId
	 * @return
	 */
	@Select("select max(acquisition_time) max  from t_initial_data_#{trainId} ")
	String findMaxTime(Integer trainId);

	/**
	 * 根据train_id查询最大时间
	 * 
	 * @param trainId
	 * @return
	 */
	@Select("select id from t_initial_data_#{trainId}")
	Integer findIdByTrainId(Integer trainId);

	@Delete("delete from t_initial_data_#{trainId} where acquisition_time<#{acquisitionTime}")
	Integer deleteByTrianIdAndAcquisitionTime(Integer trainId, String acquisitionTime);

}