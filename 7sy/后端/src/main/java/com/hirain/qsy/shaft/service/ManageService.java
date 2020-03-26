package com.hirain.qsy.shaft.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "ManageService")
public interface ManageService {

	int createInitialDataTable(@Param("trainId") int trainId, @Param("list") List<String> list);

	int createExceptionDataTable(@Param("trainId") int trainId, @Param("list") List<String> list);

	@Cacheable(key = "#p0")
	int existInitialDataTable(@Param("trainId") int trainId);

	String lastPartition(@Param("tableName") String tableName);

	int addPartitions(String tableName, List<String> list);

	@CacheEvict(allEntries = true)
	Integer dropTable(@Param("tableName") String tableName);
}
