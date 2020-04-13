package com.hirain.ptu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ManageMapper {

	int createComIdDataTable(@Param("tableName") String tableName, @Param("list") List<String> list);

	int createCsPortDataTable(@Param("tableName") String tableName, @Param("list") List<String> list);

	int isExistTable(@Param("tableName") String tableName);

	String lastPartition(@Param("tableName") String tableName);

	int addPartitions(String tableName, List<String> list);

	Integer dropTable(@Param("tableName") String tableName);

}
