/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.service.test;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.domain.CardStatus;
import com.hirain.phm.synapsis.board.param.Record;
import com.hirain.phm.synapsis.board.service.RecordMapper;
import com.hirain.phm.synapsis.board.service.impl.RecordMapperImpl;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 21, 2019 5:00:59 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 21, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Profile("test")
@Primary
public class TestRecordMapperImpl implements RecordMapper {

	private RecordMapper mapper = new RecordMapperImpl();

	/**
	 * @see com.hirain.phm.synapsis.board.service.RecordMapper#getRecords()
	 */
	@Override
	public List<Record> getRecords() {
		List<Record> records = mapper.getRecords();
		if (records.isEmpty()) {
			mapper.addRecord("系统启动");
			mapper.addRecord("MVB【1】停止运行");
			mapper.addRecord("MVB【1】正常运行");
			mapper.addRecord("PHM【5】异常运行");
		}
		return mapper.getRecords();
	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.RecordMapper#addRecord(com.hirain.phm.synapsis.board.domain.Board,
	 *      com.hirain.phm.synapsis.board.domain.CardStatus, com.hirain.phm.synapsis.board.domain.CardStatus)
	 */
	@Override
	public void addRecord(Board board, CardStatus oldStatus, CardStatus newStatus) {
		mapper.addRecord(board, oldStatus, newStatus);
	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.RecordMapper#addRecord(java.lang.String)
	 */
	@Override
	public void addRecord(String message) {
		mapper.addRecord(message);
	}

}
