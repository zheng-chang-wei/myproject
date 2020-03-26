/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.service;

import java.util.List;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.domain.CardStatus;
import com.hirain.phm.synapsis.board.param.Record;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 21, 2019 4:50:59 PM
 * @Description
 *              <p>
 *              板卡运行历史记录
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 21, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface RecordMapper {

	/**
	 * 
	 */
	List<Record> getRecords();

	/**
	 * @param board
	 * @param working
	 * @param stopped
	 */
	void addRecord(Board board, CardStatus oldStatus, CardStatus newStatus);

	/**
	 * @param message
	 */
	void addRecord(String message);

}