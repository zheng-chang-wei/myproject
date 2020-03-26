/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.domain.CardStatus;
import com.hirain.phm.synapsis.board.param.Record;
import com.hirain.phm.synapsis.board.service.RecordMapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 21, 2019 4:12:14 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 21, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class RecordMapperImpl implements RecordMapper {

	private Deque<Record> records = new LinkedList<>();

	private int recordLimit = 20;

	/**
	 * @see com.hirain.phm.synapsis.board.service.RecordMapper#getRecords()
	 */
	@Override
	public List<Record> getRecords() {
		return new ArrayList<>(records);
	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.RecordMapper#addRecord(com.hirain.phm.synapsis.board.domain.Board,
	 *      com.hirain.phm.synapsis.board.domain.CardStatus, com.hirain.phm.synapsis.board.domain.CardStatus)
	 */
	@Override
	public void addRecord(Board board, CardStatus oldStatus, CardStatus newStatus) {
		Record record = new Record();
		record.setTimestamp(new Date());
		String boardMsg = board.getBoardType().getType() + "【" + board.getSlotID() + "】";
		String event = newStatus == null ? CardStatus.OFFLINE.getEvent() : newStatus.getEvent();
		record.setMessage(boardMsg + event);
		add(record);
	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.RecordMapper#addRecord(java.lang.String)
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
