package com.hirain.qsy.shaft.service;

import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.hirain.qsy.shaft.model.AxleExceptionData;
import com.hirain.qsy.shaft.model.AxleExceptionStateData;
import com.hirain.qsy.shaft.model.ExceptionData;

@CacheConfig(cacheNames = "ExceptionDataService")
public interface ExceptionDataService extends IService<ExceptionData> {

	@CacheEvict(allEntries = true)
	void saveList(List<ExceptionData> list, Integer trainId);

	/**
	 * 查询轴的异常状态
	 * 
	 * @param trainType
	 * @param trainNum
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@Cacheable(key = "'analyseExceptionData'+#p0 + (#p1 != null ? #p1 : '')+(#p2 != null ? #p2 : '')+(#p3 != null ? #p3 : '')")
	Set<AxleExceptionStateData> analyseExceptionData(String trainType, String trainNum, String startDate, String endDate) throws Exception;

	/**
	 * 通过轴名称获取轴的信息
	 * 
	 * @param trainType
	 * @param trainNum
	 * @param startDate
	 * @param endDate
	 * @param axleName
	 * @return
	 * @throws Exception
	 */
	@Cacheable(key = "#p0 + (#p1 != null ? #p1 : '')+(#p2 != null ? #p2 : '')+(#p3 != null ? #p3 : '')+(#p4 != null ? #p4: '')")
	List<AxleExceptionData> findAxleExceptionData(String trainType, String trainNum, String startDate, String endDate, String axleName)
			throws Exception;

	@CacheEvict(allEntries = true)
	void deleteByTrainNumAndTime(Integer trainId, String deadline);

	@CacheEvict(allEntries = true)
	void dropTable(Integer trainId);

	@Cacheable(key = "'listExceptionData'+#p0 + (#p1 != null ? #p1 : '')+(#p2 != null ? #p2 : '')+(#p3 != null ? #p3 : '')")
	List<ExceptionData> listExceptionData(String trainType, String trainNum, String startDate, String endDate);

	@Cacheable(key = "'listExceptionDataGroupByAcquisitionTime'+#p0 + (#p1 != null ? #p1 : '')+(#p2 != null ? #p2 : '')+(#p3 != null ? #p3 : '')")
	List<ExceptionData> listExceptionDataGroupByAcquisitionTime(String trainType, String trainNum, String startDate, String endDate);

	boolean isException(String exception);
}
