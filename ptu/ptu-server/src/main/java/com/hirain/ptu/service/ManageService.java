package com.hirain.ptu.service;

import java.text.ParseException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface ManageService {

  int createComIdDataTable(String tableName, List<String> list);

  int createCsPortDataTable(String tableName, List<String> list);

  int isExistTable(String tableName);

  String lastPartition(String tableName);

  List<String> allPartition(String tableName);

  int addPartitions(String tableName, List<String> list);

  Integer dropTable(String tableName);

  Integer dropPartition(String tableName, String partitionName);

  int createTable(String tableName, List<String> partitions);

  void deletePartitions(String tableName, String deadlineTime) throws ParseException;
}
