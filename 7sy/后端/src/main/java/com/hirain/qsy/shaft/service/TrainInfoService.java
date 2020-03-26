package com.hirain.qsy.shaft.service;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.hirain.qsy.shaft.model.TrainInfo;

@CacheConfig(cacheNames = "TrainInfoService")
public interface TrainInfoService extends IService<TrainInfo> {

	/**
	 * 根据车号查询trianId
	 * 
	 * @param trianId
	 * @return
	 */
	@Cacheable(key = "'findIdByTrainNum'+#p0 ")
	List<Integer> findIdByTrainNum(String trainNum);

	/**
	 * 根据车型和车号查询车辆id
	 * 
	 * @param trainNum
	 * @param trainType
	 * @return
	 */
	@Cacheable(key = "#p0 + (#p1 != null ? #p1.toString() : '')")
	Integer findTrainInfoByTypeAndNum(String trainNum, String trainType);

	@Cacheable(key = "#p0 + (#p1 != null ? #p1.toString() : '')")
	Integer findTrainIdByTypeAndNum(String trainNum, String trainType);

	@Cacheable(key = "'getAllTrainType'")
	List<String> getAllTrainType();

	@Cacheable(key = "'queryTrainNumByType'+#p0 ")
	List<String> queryTrainNumByType(String trainType);

	@Cacheable(key = "'findList'+#p0 + (#p1 != null ? #p1 : '')")
	List<TrainInfo> findList(String trainType, String trainNum);

	@Cacheable(key = "'findTrainNums'")
	List<String> findTrainNums();

	@CacheEvict(allEntries = true)
	void deleteByTrainId(Integer trainId);
}
