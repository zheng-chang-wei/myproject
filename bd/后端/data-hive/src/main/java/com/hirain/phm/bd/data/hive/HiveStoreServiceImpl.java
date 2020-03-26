/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.data.DataStoreService;
import com.hirain.phm.bd.data.bean.DataRecord;
import com.hirain.phm.bd.data.hive.file.FileManager;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 5, 2020 3:18:16 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 5, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class HiveStoreServiceImpl implements DataStoreService {

	@Autowired
	private FileManager manager;

	/**
	 * @see com.hirain.phm.bd.data.DataStoreService#insert(com.hirain.phm.bd.data.bean.DataRecord)
	 */
	@Override
	public void insert(DataRecord record) {
		manager.write(getTableName(record), Arrays.asList(record));
	}

	/**
	 * @param record
	 * @return
	 */
	private String getTableName(DataRecord record) {
		// String project = PinyinUtil.getFullSpell(record.getProject());
		// String train = PinyinUtil.getFullSpell(record.getTrain());
		return "shenzhen_717";
	}

	/**
	 * @see com.hirain.phm.bd.data.DataStoreService#insertList(java.util.List)
	 */
	@Override
	public void insertList(List<DataRecord> records) {
		System.out.println("insert");
		manager.write(getTableName(records.get(0)), records);
	}

}
