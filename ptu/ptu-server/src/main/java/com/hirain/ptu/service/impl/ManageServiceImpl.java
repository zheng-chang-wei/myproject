package com.hirain.ptu.service.impl;

import com.hirain.ptu.common.model.TableNameConstant;
import com.hirain.ptu.common.utils.DateUtil;
import com.hirain.ptu.dao.ManageMapper;
import com.hirain.ptu.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author changwei.zheng
 * @date 2020/3/27 18:46
 * @describe
 */
@Service
public class ManageServiceImpl implements ManageService {

  @Autowired private ManageMapper manageMapper;

  @Override
  public int createComIdDataTable(String tableName, List<String> list) {
    return manageMapper.createComIdDataTable(tableName, list);
  }

  @Override
  public int createCsPortDataTable(String tableName, List<String> list) {
    return manageMapper.createCsPortDataTable(tableName, list);
  }

  @Override
  public boolean isExistTable(String tableName) {
    return manageMapper.isExistTable(tableName) == 1;
  }

  @Override
  public String lastPartition(String tableName) {
    return manageMapper.lastPartition(tableName);
  }

  @Override
  public List<String> allPartition(String tableName) {
    return manageMapper.allPartition(tableName);
  }

  @Override
  public int addPartitions(String tableName, List<String> list) {
    return manageMapper.addPartitions(tableName, list);
  }

  @Override
  public Integer dropTable(String tableName) {
    return manageMapper.dropTable(tableName);
  }

  @Override
  public Integer dropPartition(String tableName, String partitionName) {
    return manageMapper.dropPartition(tableName, partitionName);
  }

  @Override
  public int createTable(String tableName, List<String> partitions) {
    switch (tableName) {
      case TableNameConstant.COMID_DATA_TABLE_NAME:
        return manageMapper.createComIdDataTable(tableName, partitions);
      case TableNameConstant.CSPROT_DATA_TABLE_NAME:
        return manageMapper.createCsPortDataTable(tableName, partitions);
    }
    return 0;
  }

  @Override
  public void deletePartitions(String tableName, String deadlineTime) throws ParseException {
    List<String> partitions = allPartition(tableName);
    for (String partition : partitions) {
      Date partitionDate = DateUtil.parse(partition.substring(1, partition.length()), "yyyyMMdd");
      Date deadlineDate = DateUtil.parse(deadlineTime, "yyyy-MM-dd");
      if (partitionDate.before(deadlineDate)) {
        dropPartition(tableName, partition);
      }
    }
  }
}
