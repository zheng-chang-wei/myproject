/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.service;

import java.util.List;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.domain.CardStatus;
import com.hirain.phm.synapsis.board.message.BoardControlResponseMessage;
import com.hirain.phm.synapsis.board.message.BoardStartupNoticeMessage;
import com.hirain.phm.synapsis.board.param.BoardStatisticsResponse;
import com.hirain.phm.synapsis.board.param.Record;
import com.hirain.phm.synapsis.setting.BoardSetting;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 4, 2019 10:39:26 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 zepei.tao@hirain.com 1.0 create file
 */
public interface BoardService {

	/**
	 * 管理查询到的板卡信息
	 */
	void initBoards(List<Board> boards);

	/**
	 * 得到内存中的板卡列表
	 */
	List<Board> getBoards();

	/**
	 * 向控制管理程序发送板卡启动指令,并收到回复
	 */
	BoardControlResponseMessage start() throws Exception;

	/**
	 * 向控制管理程序发送板卡停止指令,并收到回复
	 */
	BoardControlResponseMessage stop() throws Exception;

	/**
	 * 向控制管理程序发送板卡查询指令
	 */
	void boardStatusInquire();

	/**
	 * @param message
	 */
	void replySelfcheck(BoardStartupNoticeMessage message);

	/**
	 * 获得板卡状态汇总信息
	 * 
	 * @return
	 */
	BoardStatisticsResponse count();

	/**
	 * @param slotId
	 * @return
	 */
	BoardSetting getSetting(int slotId);

	/**
	 * @return
	 */
	List<Record> getRecords();

	/**
	 * @param status
	 */
	void updateStatus(CardStatus status);
}
