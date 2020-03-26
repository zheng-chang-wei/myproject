/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.data.bean.DataRecord;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 11, 2020 10:42:23 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 11, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class FileManagerImpl implements FileManager {

	@Autowired
	private FileWriterFactory factory;

	private Map<String, FileWriter> writers = new HashMap<>();

	/**
	 * @see com.hirain.phm.bd.data.hive.file.FileManager#write(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void write(String tableName, List<DataRecord> records) {
		FileWriter writer = getWriter(tableName);
		System.out.println("getWriter");
		writer.write(records);
	}

	/**
	 * @param tableName
	 */
	private FileWriter getWriter(String tableName) {
		FileWriter writer = writers.get(tableName);
		if (writer == null) {
			writer = factory.create(tableName);
			writer.init(tableName);
			writers.put(tableName, writer);
		}
		return writer;
	}

}
