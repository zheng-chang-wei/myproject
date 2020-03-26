/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.algorithm.domain.Algorithm;
import com.hirain.phm.synapsis.algorithm.domain.RunStatus;
import com.hirain.phm.synapsis.algorithm.param.Record;
import com.hirain.phm.synapsis.algorithm.service.RecordMapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 23, 2019 3:00:54 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 23, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class AlgorithmRecordMapperImpl implements RecordMapper {

	private Deque<Record> records = new LinkedList<>();

	private int recordLimit = 20;

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.RecordMapper#getRecords()
	 */
	@Override
	public List<Record> getRecords() {
		return new ArrayList<>(records);
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.RecordMapper#addRecord(com.hirain.phm.synapsis.algorithm.domain.Algorithm,
	 *      com.hirain.phm.synapsis.algorithm.domain.RunStatus, com.hirain.phm.synapsis.algorithm.domain.RunStatus)
	 */
	@Override
	public void addRecord(Algorithm algorithm, RunStatus oldStatus, RunStatus newStatus) {
		Record record = new Record();
		record.setTimestamp(new Date());
		String message = algorithm.getName() + "【" + algorithm.getSlotId() + "】";
		message += newStatus == null ? RunStatus.Idle : newStatus.getEvent();
		record.setMessage(message);
		add(record);
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.RecordMapper#addRecord(java.lang.String)
	 */
	@Override
	public void addRecord(String message) {
		Record record = new Record();
		record.setTimestamp(new Date());
		record.setMessage(message);
		add(record);
	}

	/**
	 * @param record
	 */
	private void add(Record record) {
		if (records.size() >= recordLimit) {
			records.removeLast();
		}
		records.addFirst(record);
	}

}
