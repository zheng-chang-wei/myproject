package com.hirain.qsy.shaft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.qsy.shaft.dao.ManageMapper;
import com.hirain.qsy.shaft.service.ManageService;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年10月18日 下午7:25:04
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年10月18日 changwei.zheng@hirain.com 1.0 create file
 */
@Service
public class ManageServiceImpl implements ManageService {

	@Autowired
	private ManageMapper manageMapper;

	@Override
	public int createInitialDataTable(int trainId, List<String> list) {
		return manageMapper.createInitialDataTable(trainId, list);
	}

	@Override
	public int createExceptionDataTable(int trainId, List<String> list) {
		return manageMapper.createExceptionDataTable(trainId, list);
	}

	@Override
	public int existInitialDataTable(int trainId) {
		return manageMapper.existInitialDataTable(trainId);
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
