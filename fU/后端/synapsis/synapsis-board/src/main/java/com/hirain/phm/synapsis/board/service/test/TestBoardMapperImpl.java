/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.service.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.board.domain.ADBoard;
import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.domain.BoardOutside;
import com.hirain.phm.synapsis.board.domain.CPUBoard;
import com.hirain.phm.synapsis.board.domain.CardStatus;
import com.hirain.phm.synapsis.board.domain.DataStatus;
import com.hirain.phm.synapsis.board.domain.ECNBoard;
import com.hirain.phm.synapsis.board.domain.LedStatus;
import com.hirain.phm.synapsis.board.domain.MVBBoard;
import com.hirain.phm.synapsis.board.domain.PHMBoard;
import com.hirain.phm.synapsis.board.domain.SSDBoard;
import com.hirain.phm.synapsis.board.domain.WirelessBoard;
import com.hirain.phm.synapsis.board.service.BoardMapper;
import com.hirain.phm.synapsis.constant.BoardType;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 21, 2019 2:30:30 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 21, 2019 jianwen.xin@hirain.com 1.0 create file
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 25, 2020 zhaobin.xin@hirain.com 调整板卡槽位号，贴近实际
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 26, 2020 zhaobin.xin@hirain.com 添加led灯状态
 */
@Service
@Profile("test")
@Primary
public class TestBoardMapperImpl implements BoardMapper {

	private List<Board> boards;

	/**
	 * @see com.hirain.phm.synapsis.board.service.BoardMapper#init(java.util.List)
	 */
	@Override
	public void init(List<Board> boards) {
		this.boards = boards;
	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.BoardMapper#update(java.util.List)
	 */
	@Override
	public void update(List<Board> boards) {
		this.boards = boards;
	}

	/**
	 * @see com.hirain.phm.synapsis.board.service.BoardMapper#list()
	 */
	@Override
	public synchronized List<Board> list() {
		if (boards == null) {
			boards = new ArrayList<>();
			boards.add(createSsdBoard());
			boards.add(createCPUBoard());
			boards.add(createAdBoard(BoardType.AD1, 3));
			boards.add(createAdBoard(BoardType.AD2, 4));
			boards.add(createPhmBoard(BoardType.PHM_AGX, 5, CardStatus.WORKING));
			boards.add(createPhmBoard(BoardType.PHM_TX2, 7, CardStatus.WORKING));
			boards.add(createPhmBoard(BoardType.PHM_IMX8, 9, CardStatus.WORKING));
			boards.add(createPhmBoard(BoardType.PHM_IMX8, 10, CardStatus.WORKING));
			boards.add(createPhmBoard(BoardType.PHM_IMX8, 11, CardStatus.STOPPED));
			boards.add(createPhmBoard(BoardType.PHM_IMX8, 12, CardStatus.STOPPED));
			boards.add(createWirelessBoard(BoardType.WIRELESS, 13));
			boards.add(createECNBoard());
			boards.add(createMVBBoard());
			for (Board board : boards) {
				board.setControlVer("V1.0");
				board.setCoreTemp(50);
			}
		}
		return boards;
	}

	/**
	 * @return
	 */
	private Board createSsdBoard() {
		SSDBoard board = new SSDBoard();
		board.setBoardType(BoardType.SSD);
		board.setType(board.getBoardType().getType());
		board.setSlotID(1);
		board.setSsdSize(200000);
		board.setSsdUsed(30);
		return board;
	}

	/**
	 * @return
	 */
	private Board createCPUBoard() {
		CPUBoard board = new CPUBoard();
		board.setBoardType(BoardType.CPU);
		board.setType(board.getBoardType().getType());
		board.setSlotID(2);
		board.setRamSize(20000);
		board.setRamUsed(50);
		board.setCardStatus(CardStatus.WORKING);

		List<LedStatus> ledStatus = new ArrayList<>();
		ledStatus.add(LedStatus.GREEN_ON);
		ledStatus.add(LedStatus.GREEN_ON);
		ledStatus.add(LedStatus.ORANGE_FLASHING);
		ledStatus.add(LedStatus.RED_FLASHING);
		BoardOutside outside = new BoardOutside();
		outside.setLedStatus(ledStatus);
		board.setOutside(outside);

		return board;
	}

	/**
	 */
	private Board createPhmBoard(BoardType boardType, int slotId, CardStatus status) {
		PHMBoard board = new PHMBoard();
		board.setBoardType(boardType);
		board.setType(boardType.getType());
		board.setCardStatus(status);
		board.setSlotID(slotId);
		board.setCpuUsed(50);
		board.setGpuUsed(40);
		board.setRamSize(800000);
		board.setRamUsed(20);
		board.setRomSize(100000);
		board.setRomUsed(40);
		board.setServerVer("V1.0");

		List<LedStatus> ledStatus = new ArrayList<>();
		ledStatus.add(LedStatus.GREEN_ON);
		ledStatus.add(LedStatus.GREEN_ON);
		ledStatus.add(LedStatus.OFF);
		ledStatus.add(LedStatus.OFF);
		BoardOutside outside = new BoardOutside();
		outside.setLedStatus(ledStatus);
		board.setOutside(outside);

		return board;
	}

	/**
	 * @param boardType
	 * @param slotId
	 * @return
	 */
	private Board createAdBoard(BoardType boardType, int slotId) {
		ADBoard board = new ADBoard();
		board.setBoardType(boardType);
		board.setType(board.getBoardType().getType());
		board.setCardStatus(CardStatus.ERROR);
		board.setFpgaVer("V1.0");
		board.setSlotID(slotId);
		return board;
	}

	/**
	 * @param boardType
	 * @param slotId
	 * @return
	 */
	private Board createWirelessBoard(BoardType boardType, int slotId) {
		WirelessBoard board = new WirelessBoard();
		board.setBoardType(boardType);
		board.setType(board.getBoardType().getType());
		board.setCardStatus(CardStatus.ERROR);
		board.setSlotID(slotId);

		List<LedStatus> ledStatus = new ArrayList<>();
		ledStatus.add(LedStatus.RED_FLASHING);
		ledStatus.add(LedStatus.ORANGE_FLASHING);
		ledStatus.add(LedStatus.RED_ON);
		ledStatus.add(LedStatus.GREEN_ON);
		BoardOutside outside = new BoardOutside();
		outside.setLedStatus(ledStatus);
		board.setOutside(outside);

		return board;
	}

	/**
	 * @return
	 */
	private MVBBoard createMVBBoard() {
		MVBBoard board = new MVBBoard();
		board.setBoardType(BoardType.MVB);
		board.setType(board.getBoardType().getType());
		board.setCardStatus(CardStatus.WORKING);
		board.setSlotID(15);
		board.setDataStatusA(DataStatus.BE_DATA);
		board.setDataStatusB(DataStatus.NO_DATA);

		List<LedStatus> ledStatus = new ArrayList<>();
		ledStatus.add(LedStatus.OFF);
		ledStatus.add(LedStatus.ORANGE_FLASHING);
		ledStatus.add(LedStatus.RED_ON);
		ledStatus.add(LedStatus.RED_FLASHING);
		BoardOutside outside = new BoardOutside();
		outside.setLedStatus(ledStatus);
		board.setOutside(outside);

		return board;
	}

	/**
	 * @return
	 */
	private Board createECNBoard() {
		ECNBoard board = new ECNBoard();
		board.setBoardType(BoardType.ECN);
		board.setType(board.getBoardType().getType());
		board.setCardStatus(CardStatus.WORKING);
		board.setDataStatus(DataStatus.BE_DATA);
		board.setSlotID(14);
		board.setFpgaVer("V1.0");

		List<LedStatus> ledStatus = new ArrayList<>();
		ledStatus.add(LedStatus.GREEN_ON);
		ledStatus.add(LedStatus.OFF);
		ledStatus.add(LedStatus.OFF);
		ledStatus.add(LedStatus.RED_FLASHING);
		BoardOutside outside = new BoardOutside();
		outside.setLedStatus(ledStatus);
		board.setOutside(outside);

		return board;
	}

}
