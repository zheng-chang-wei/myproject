/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.domain.CardStatus;
import com.hirain.phm.synapsis.board.message.BoardControlResponseMessage;
import com.hirain.phm.synapsis.board.message.BoardLaunchMessage;
import com.hirain.phm.synapsis.board.message.BoardStartupConfirmMessage;
import com.hirain.phm.synapsis.board.message.BoardStartupNoticeMessage;
import com.hirain.phm.synapsis.board.message.BoardStatusInquireMessage;
import com.hirain.phm.synapsis.board.message.BoardStatusResponseMessage;
import com.hirain.phm.synapsis.board.message.BoardStoppingMessage;
import com.hirain.phm.synapsis.board.param.BoardStatisticsResponse;
import com.hirain.phm.synapsis.board.param.BoardTypeCount;
import com.hirain.phm.synapsis.board.param.Record;
import com.hirain.phm.synapsis.board.service.BoardMapper;
import com.hirain.phm.synapsis.board.service.BoardService;
import com.hirain.phm.synapsis.board.service.RecordMapper;
import com.hirain.phm.synapsis.communication.Communication;
import com.hirain.phm.synapsis.communication.Message;
import com.hirain.phm.synapsis.communication.impl.TransportMessage;
import com.hirain.phm.synapsis.constant.Program;
import com.hirain.phm.synapsis.constant.SidConstant;
import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.db.BoardSettingQuery;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 4, 2019 3:48:57 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 zepei.tao@hirain.com 1.0 create file
 */
/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 4:08:21 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

	@Autowired
	public Communication communication;

	@Autowired
	private BoardConfig config;

	@Autowired
	private BoardMapper mapper;

	@Autowired
	private RecordMapper recordMapper;

	@Autowired
	private BoardSettingQuery query;

	/**
	 * @see com.hirain.phm.synapsis.board.service.BoardService#initBoards()
	 */
	@Override
	public void initBoards(List<Board> boards) {
		mapper.init(boards);
	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.BoardService#getBoards()
	 */
	@Override
	public List<Board> getBoards() {
		return mapper.list();
	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.BoardService#start()
	 */
	@Override
	public BoardControlResponseMessage start() throws Exception {
		BoardLaunchMessage runningMessage = new BoardLaunchMessage();
		TransportMessage<BoardLaunchMessage> transportMessage = new TransportMessage<>();
		transportMessage.setSid(SidConstant.CONTROL_COMMAND);
		transportMessage.setSource(Program.CPU_SERVICE.getCode());
		transportMessage.setTarget(Program.CPU_CONTROL.getCode());
		transportMessage.setData(runningMessage);

		Message<?> response = getResponse(transportMessage);
		BoardControlResponseMessage message = (BoardControlResponseMessage) response.getData();
		return message;
	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.BoardService#stop()
	 */
	@Override
	public BoardControlResponseMessage stop() throws Exception {
		BoardStoppingMessage stoppingMessage = new BoardStoppingMessage();
		TransportMessage<BoardStoppingMessage> transportMessage = new TransportMessage<>();
		transportMessage.setSid(SidConstant.CONTROL_COMMAND);
		transportMessage.setSource(Program.CPU_SERVICE.getCode());
		transportMessage.setTarget(Program.CPU_CONTROL.getCode());
		transportMessage.setData(stoppingMessage);

		Message<?> response = getResponse(transportMessage);
		BoardControlResponseMessage message = (BoardControlResponseMessage) response.getData();
		return message;
	}

	private Message<?> getResponse(TransportMessage<?> transportMessage) throws Exception {
		Message<?> response = null;
		for (int i = 0; i < 3; i++) {
			response = communication.send(transportMessage, config.getTimeout());
			if (response != null) {
				break;
			}
		}
		if (response == null) {
			throw new SynapsisException("超时：没有收到回复");//
		}
		return response;
	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.BoardService#boardStatusInquire()
	 */
	@Override
	public void boardStatusInquire() {
		BoardStatusInquireMessage inquireMessage = new BoardStatusInquireMessage();
		TransportMessage<BoardStatusInquireMessage> transportMessage = new TransportMessage<>();
		transportMessage.setSid(SidConstant.STATUS_COMMAND);
		transportMessage.setSource(Program.CPU_SERVICE.getCode());
		transportMessage.setTarget(Program.CPU_CONTROL.getCode());
		transportMessage.setData(inquireMessage);

		try {
			Message<?> response = getResponse(transportMessage);
			BoardStatusResponseMessage data = (BoardStatusResponseMessage) response.getData();
			mapper.update(data.getBoards());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.BoardService#replySelfcheck(com.hirain.phm.synapsis.board.message.BoardStartupNoticeMessage)
	 */
	@Override
	public void replySelfcheck(BoardStartupNoticeMessage message) {
		BoardStartupConfirmMessage confirmMessage = new BoardStartupConfirmMessage();
		TransportMessage<BoardStartupConfirmMessage> transportMessage = new TransportMessage<>();
		transportMessage.setSid(SidConstant.SELFCHECK_CONFIRM);
		transportMessage.setSource(Program.CPU_SERVICE.getCode());
		transportMessage.setTarget(Program.CPU_CONTROL.getCode());
		transportMessage.setData(confirmMessage);
		communication.sendAsync(transportMessage);
	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.BoardService#count()
	 */
	@Override
	public BoardStatisticsResponse count() {
		BoardStatisticsResponse response = new BoardStatisticsResponse();
		List<Board> boards = mapper.list();
		response.setTotal(boards.size());
		int run = 0;
		int error = 0;
		int stopped = 0;
		Map<String, BoardTypeCount> map = new HashMap<>();
		for (Board board : boards) {
			if (board.getCardStatus() == null) {
				continue;
			}
			BoardTypeCount count = map.get(board.getBoardType().getType());
			if (count == null) {
				count = new BoardTypeCount();
				count.setType(board.getBoardType().getType());
				map.put(count.getType(), count);
			}
			count.increTotal();
			if (board.getCardStatus().equals(CardStatus.WORKING)) {
				run++;
				count.increRun();
			} else if (board.getCardStatus().equals(CardStatus.ERROR)) {
				error++;
			} else if (board.getCardStatus().equals(CardStatus.STOPPED)) {
				stopped++;
			}
		}
		response.setRunning(run);
		response.setError(error);
		response.setStopped(stopped);
		response.setTypes(new ArrayList<>(map.values()));
		return response;
	}

	@Override
	public BoardSetting getSetting(int slotId) {
		return query.getBoardSetting(slotId);
	}

	@Override
	public List<Record> getRecords() {
		return recordMapper.getRecords();
	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.BoardService#updateStatus(CardStatus)
	 */
	@Override
	public void updateStatus(CardStatus status) {
		List<Board> boards = getBoards();
		for (Board board : boards) {
			board.setCardStatus(status);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.BoardService#scanBoards()
	 */
	@Override
	public List<Board> scanBoards() {
		boardStatusInquire();
		return getBoards();
	}
}
