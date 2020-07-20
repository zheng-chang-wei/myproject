/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.board.BoardQuery;
import com.hirain.phm.synapsis.board.IBoard;
import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.service.BoardService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 18, 2020 11:14:28 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 18, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class BoardQueryImpl implements BoardQuery {

	@Autowired
	private BoardService service;

	/**
	 * @see com.hirain.phm.synapsis.board.BoardQuery#getBoards(java.lang.String)
	 */
	@Override
	public List<IBoard> getBoards(String type) {
		List<Board> boards = service.getBoards();
		List<IBoard> list = boards.stream().filter(b -> b.getBoardType().getType().equals(type)).map(t -> (IBoard) t).collect(Collectors.toList());
		return list;
	}

}
