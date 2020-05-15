package com.hirain.ptu.service;

import java.text.ParseException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface ManageService {

  public int createComIdDataTable(String tableName, List<String> list);

  public int createCsPortDataTable(String tableName, List<String> list);

  public boolean isExistTable(String tableName);

  public String lastPartition(String tableName);

  public List<String> allPartition(String tableName);

  public int addPartitions(String tableName, List<String> list);

  public Integer dropTable(String tableName);

  public Integer dropPartition(String tableName, String partitionName);

  public int createTable(String tableName, List<String> partitions);

  public void deletePartitions(String tableName, String deadlineTime) throws ParseException;

  public void createDatabase(String name);

}
