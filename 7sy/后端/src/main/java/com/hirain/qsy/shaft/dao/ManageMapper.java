package com.hirain.qsy.shaft.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ManageMapper {

	int createInitialDataTable(@Param("trainId") int trainId, @Param("list") List<String> list);

	int createExceptionDataTable(@Param("trainId") int trainId, @Param("list") List<String> list);

	int existInitialDataTable(@Param("trainId") int trainId);

	String lastPartition(@Param("tableName") String tableName);

	int addPartitions(String tableName, List<String> list);

	Integer dropTable(@Param("tableName") String tableName);

}
