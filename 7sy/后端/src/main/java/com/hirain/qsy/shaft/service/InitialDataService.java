package com.hirain.qsy.shaft.service;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.model.InitialAxleData;
import com.hirain.qsy.shaft.model.InitialData;

@CacheConfig(cacheNames = "InitialDataService")
public interface InitialDataService extends IService<InitialData> {

	@CacheEvict(allEntries = true)
	void importFile(String realPath, String originalFilename, String userName, String ip) throws Exception;

	@Cacheable(key = "#p0 + (#p1 != null ? #p1 : '')+(#p2 != null ? #p2 : '')+(#p3 != null ? #p3 : '')+(#p4 != null ? #p4: '')")
	List<InitialAxleData> findByTrainInfoAndTime(String trainType, String trainNum, String startDate, String endDate, int axleNum) throws Exception;

	/**
	 * 按trianId查询最早时间和最晚时间
	 * 
	 * @param trainId
	 * @return
	 */
	@Cacheable(key = "'findMaxAndMinTime'+#p0 ")
	Map<String, Object> findMaxAndMinTime(Integer trainId);

	@CacheEvict(allEntries = true)
	void deleteByTrainNumAndTime(Integer trainId, String deadline);

	@CacheEvict(allEntries = true)
	void dropTable(Integer trainId);

	@Cacheable(key = "#p0 + (#p1 != null ? #p1 : '')+(#p2 != null ? #p2 : '')")
	Map<String, Object> listLastMonthExceptionData(QueryRequest request, String trainType, String trainNum) throws Exception;

	/**
	 * 根据车辆信息查询最大时间
	 * 
	 * @param trainId
	 * @return
	 */
	@Cacheable(key = "'findMaxTime'+#p0 ")
	String findMaxTime(Integer trainId);
}
