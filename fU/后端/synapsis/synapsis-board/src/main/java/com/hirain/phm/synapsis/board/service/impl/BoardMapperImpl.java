/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.domain.CardStatus;
import com.hirain.phm.synapsis.board.service.BoardMapper;
import com.hirain.phm.synapsis.board.service.RecordMapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 2:15:01 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class BoardMapperImpl implements BoardMapper {

	private List<Board> boards = new CopyOnWriteArrayList<>();

	@Autowired
	private RecordMapper recordMapper;

	/**
	 * @see com.hirain.phm.synapsis.board.service.BoardMapper#init(java.util.List)
	 */
	@Override
	public void init(List<Board> boards) {
		this.boards.clear();
		this.boards.addAll(boards);
	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.BoardMapper#update(java.util.List)
	 */
	@Override
	public void update(List<Board> newBoards) {
		Map<Integer, Board> newBoardMap = newBoards.stream().collect(Collectors.toMap(Board::getSlotID, board -> board));
		Map<Integer, Board> boardMap = list().stream().collect(Collectors.toMap(Board::getSlotID, board -> board));
		for (Board board : boards) {
			Board newBoard = newBoardMap.get(board.getSlotID());
			if (newBoard == null) {
				recordMapper.addRecord(board, board.getCardStatus(), CardStatus.OFFLINE);
			} else if (board.getCardStatus() != null && !board.getCardStatus().equals(newBoard.getCardStatus())) {
				recordMapper.addRecord(board, board.getCardStatus(), newBoard.getCardStatus());
			}
		}
		for (Integer slotId : newBoardMap.keySet()) {
			Board board = boardMap.get(slotId);
			Board newBoard = newBoardMap.get(slotId);
			if (board == null && newBoard.getCardStatus() != null) {
				recordMapper.addRecord(newBoard, null, newBoard.getCardStatus());
			}
		}
		boards.clear();
		boards.addAll(newBoards);
	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.BoardMapper#list()
	 */
	@Override
	public List<Board> list() {
		return new ArrayList<>(boards);
	}

}
