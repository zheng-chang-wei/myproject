package com.hirain.ptu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface ManageMapper {

  int createComIdDataTable(@Param("tableName") String tableName, @Param("list") List<String> list);

  int createCsPortDataTable(@Param("tableName") String tableName, @Param("list") List<String> list);

  int isExistTable(@Param("tableName") String tableName);

  String lastPartition(@Param("tableName") String tableName);

  List<String> allPartition(@Param("tableName") String tableName);

  int addPartitions(String tableName, List<String> list);

  Integer dropTable(@Param("tableName") String tableName);

  Integer dropPartition(
      @Param("tableName") String tableName, @Param("partitionName") String partitionName);

  @Update("create database #{name}")
  void createDatabase(String name);

  int isExistDatabase(@Param("databaseName") String databaseName);

  int createComIdObjectTable();

  int createConditionTable();

  int createCsPortObjectTable();

  int createDownloadedFileTable();

  int createTargerConfigTable();
}
