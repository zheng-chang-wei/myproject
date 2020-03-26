package com.hirain.qsy.shaft.service;

import java.util.Map;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.hirain.qsy.shaft.common.model.QueryRequest;

@CacheConfig(cacheNames = "DataService")
public interface DataService {

	@Cacheable(key = "#p0 + (#p1 != null ? #p1 : '')+(#p2 != null ? #p2 : '')")
	Map<String, Object> getList(QueryRequest request, String trainType, String trainNum);

	@CacheEvict(allEntries = true)
	void delete(Integer trainId, String deadline);

	/**
	 * 获取服务器存储空间
	 * 
	 * @return
	 */
	Map<String, Object> getStorage();

	@CacheEvict(allEntries = true)
	void dropTable(Integer trainId);
}
