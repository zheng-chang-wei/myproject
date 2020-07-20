/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.message.BoardStartupNoticeMessage;
import com.hirain.phm.synapsis.board.service.BoardService;
import com.hirain.phm.synapsis.board.service.RecordMapper;
import com.hirain.phm.synapsis.constant.RunState;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 4, 2019 11:02:46 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Configuration
@Slf4j
public class BoardStartUpEventHandler {

	@Autowired
	private BoardService boardService;

	@Autowired
	private RecordMapper recordMapper;

	/**
	 * 监听板卡启动报文，初始化板卡
	 * 
	 * @param message
	 */
	@EventListener
	@Async
	public void handleStartupNotice(BoardStartupNoticeMessage message) {
		log.info("board init");
		List<Board> boards = message.getBoards();
		boardService.replySelfcheck(message);
		boardService.initBoards(boards);
	}

	/**
	 * 根据系统状态，启动或停止状态查询
	 * 
	 * @param state
	 */
	@EventListener
	@Async
	public void listen(RunState state) {
		if (state.equals(RunState.RUNNING)) {
			recordMapper.addRecord("系统启动");
		} else if (state.equals(RunState.IDLE)) {
			boardService.updateStatus(null);
			recordMapper.addRecord("系统停止");
		} else if (state.equals(RunState.WAITING)) {
			recordMapper.addRecord("系统启动出错");
		}
	}

}
