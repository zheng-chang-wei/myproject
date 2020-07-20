/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.param.BoardStatisticsResponse;
import com.hirain.phm.synapsis.board.param.Record;
import com.hirain.phm.synapsis.board.service.BoardService;
import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.setting.BoardSetting;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 5, 2019 11:12:25 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 5, 2019 zepei.tao@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@GetMapping("/list")
	public ResultBean<List<Board>> listAll() {
		return new ResultBean<>(boardService.getBoards());
	}

	@GetMapping("/list/force")
	public ResultBean<List<Board>> listForce() {
		return new ResultBean<>(boardService.scanBoards());
	}

	@GetMapping("/count")
	public ResultBean<BoardStatisticsResponse> count() {
		return new ResultBean<>(boardService.count());
	}

	@GetMapping("/setting")
	public ResultBean<BoardSetting> getSetting(int slotId) {
		return new ResultBean<>(boardService.getSetting(slotId));
	}

	@GetMapping("/history")
	public ResultBean<List<Record>> getRecords() {
		return new ResultBean<>(boardService.getRecords());
	}
}
