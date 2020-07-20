/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.data.DataStoreService;
import com.hirain.phm.bd.data.TableNameFormatter;
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

	@Autowired
	private TableNameFormatter tnf;

	/**
	 * @see com.hirain.phm.bd.data.DataStoreService#insert(com.hirain.phm.bd.data.bean.DataRecord)
	 */
	@Override
	public void insert(DataRecord record) {
		manager.write(tnf.getTableName(record.getProject(), record.getTrain()), Arrays.asList(record));
	}

	/**
	 * @see com.hirain.phm.bd.data.DataStoreService#insertList(java.util.List)
	 */
	@Override
	public void insertList(List<DataRecord> records) {
		System.out.println("insert");
		DataRecord record = records.get(0);
		manager.write(tnf.getTableName(record.getProject(), record.getTrain()), records);
	}

}
