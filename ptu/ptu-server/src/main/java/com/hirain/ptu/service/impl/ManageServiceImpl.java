package com.hirain.ptu.service.impl;

import com.hirain.ptu.dao.ManageMapper;
import com.hirain.ptu.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  public int isExistTable(String tableName) {
    return manageMapper.isExistTable(tableName);
  }

  @Override
  public String lastPartition(String tableName) {
    return manageMapper.lastPartition(tableName);
  }

  @Override
  public int addPartitions(String tableName, List<String> list) {
    return manageMapper.addPartitions(tableName, list);
  }

  @Override
  public Integer dropTable(String tableName) {
    return manageMapper.dropTable(tableName);
  }
}
