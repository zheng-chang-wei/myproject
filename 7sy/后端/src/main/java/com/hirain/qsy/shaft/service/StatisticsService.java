package com.hirain.qsy.shaft.service;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.DetailDataRequest;
import com.hirain.qsy.shaft.common.model.StatisticsRequest;
import com.hirain.qsy.shaft.model.StatisticsChartDataRow;
import com.hirain.qsy.shaft.model.StatisticsExceptionTableDatas;

@CacheConfig(cacheNames = "StatisticsService")
public interface StatisticsService {

	/**
	 * 获取统计结果按车辆层级
	 * 
	 * @param statisticsRequest
	 * @return
	 */
	@Cacheable(key = "#p0")
	List<StatisticsChartDataRow> findStatisticsResultByCar(StatisticsRequest statisticsRequest) throws Exception;

	/**
	 * 获取统计结果按测点层级
	 * 
	 * @param dataRequest
	 * @return
	 */
	@Cacheable(key = "#p0")
	List<StatisticsChartDataRow> findStatisticsResultByPoint(DataRequest dataRequest) throws Exception;

	@Cacheable(key = "#p0 + (#p1 != null ? #p1 : '')")
	List<StatisticsExceptionTableDatas> findTableData(DataRequest dataRequest, String axleNum) throws Exception;

	@Cacheable(key = "#p0")
	List<Map<String, Object>> findDetailData(DetailDataRequest detailDataRequest) throws Exception;

}
