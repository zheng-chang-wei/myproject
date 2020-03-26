/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

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
import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.message.MessageConstant;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 27, 2019 5:16:03 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 27, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class BoardDecodeHelper {

	public static List<Board> parseBoardInfos(byte[] bytes) {
		List<Board> boards = new ArrayList<>();
		final ByteBuffer buffer = ByteBuffer.wrap(bytes).order(MessageConstant.MESSAGE_ORDER);
		int boardNum = buffer.get() & 0XFF;
		for (int i = 0; i < boardNum; i++) {
			byte[] datas = new byte[128];
			buffer.get(datas);
			ByteBuffer buf = ByteBuffer.wrap(datas).order(MessageConstant.MESSAGE_ORDER);
			Board board = new Board();
			parseBoardBasicProperty(board, buf);
			boards.add(board);
		}
		return boards;
	}

	public static ADBoard getADBoard(byte[] bytes) {
		ADBoard adBoard = new ADBoard();
		ByteBuffer buffer = ByteBuffer.wrap(bytes).order(MessageConstant.MESSAGE_ORDER);
		byte[] commonBytes = new byte[88];
		buffer.get(commonBytes);
		parseBoardCommonProperty(commonBytes, adBoard);
		adBoard.setFpgaVer(getFpgaVer(buffer));
		int cardStatus = buffer.get() & 0xFF;
		adBoard.setCardStatus(getCardStatus(cardStatus));
		adBoard.setCoreTemp(buffer.get() & 0xFF);
		parseLedProperty(adBoard, buffer);
		return adBoard;
	}

	public static ECNBoard getECNBoard(byte[] bytes) {
		ECNBoard ecnBoard = new ECNBoard();
		ByteBuffer buffer = ByteBuffer.wrap(bytes).order(MessageConstant.MESSAGE_ORDER);
		byte[] commonBytes = new byte[88];
		buffer.get(commonBytes);
		parseBoardCommonProperty(commonBytes, ecnBoard);
		ecnBoard.setFpgaVer(getFpgaVer(buffer));

		int cardStatus = buffer.get() & 0xFF;
		ecnBoard.setCardStatus(getCardStatus(cardStatus));

		int dataStatus = buffer.get() & 0xFF;
		ecnBoard.setDataStatus(getDataStatus(dataStatus));
		ecnBoard.setCoreTemp(buffer.get() & 0xFF);

		parseLedProperty(ecnBoard, buffer);
		return ecnBoard;
	}

	public static CPUBoard getCPUBoard(byte[] bytes) {
		CPUBoard cpuBoard = new CPUBoard();
		ByteBuffer buffer = ByteBuffer.wrap(bytes).order(MessageConstant.MESSAGE_ORDER);
		byte[] commonBytes = new byte[88];
		buffer.get(commonBytes);
		parseBoardCommonProperty(commonBytes, cpuBoard);

		cpuBoard.setRamSize(buffer.getInt() & 0xFF);
		cpuBoard.setRamUsed(buffer.get() & 0xFF);
		cpuBoard.setCoreTemp(buffer.get() & 0xFF);
		parseLedProperty(cpuBoard, buffer);
		return cpuBoard;
	}

	public static PHMBoard getPHMBoard(byte[] bytes) {
		PHMBoard phmBoard = new PHMBoard();
		ByteBuffer buffer = ByteBuffer.wrap(bytes).order(MessageConstant.MESSAGE_ORDER);
		byte[] commonBytes = new byte[88];
		buffer.get(commonBytes);
		parseBoardCommonProperty(commonBytes, phmBoard);

		phmBoard.setServerVer(getFpgaVer(buffer));

		int cardStatus = buffer.get() & 0xFF;
		phmBoard.setCardStatus(getCardStatus(cardStatus));

		phmBoard.setRamSize(buffer.getInt() & 0xFF);
		phmBoard.setRamUsed(buffer.get() & 0xFF);
		phmBoard.setRomSize(buffer.getInt() & 0xFF);
		phmBoard.setRomUsed(buffer.get() & 0xFF);
		phmBoard.setCpuUsed(buffer.get() & 0xFF);
		phmBoard.setGpuUsed(buffer.get() & 0xFF);
		phmBoard.setCoreTemp(buffer.get() & 0xFF);

		parseLedProperty(phmBoard, buffer);
		return phmBoard;
	}

	public static MVBBoard getMVBBoard(byte[] bytes) {
		MVBBoard mvbBoard = new MVBBoard();
		ByteBuffer buffer = ByteBuffer.wrap(bytes).order(MessageConstant.MESSAGE_ORDER);
		byte[] commonBytes = new byte[88];
		buffer.get(commonBytes);
		parseBoardCommonProperty(commonBytes, mvbBoard);

		int cardStatus = buffer.get() & 0xFF;
		mvbBoard.setCardStatus(getCardStatus(cardStatus));

		int dataStatus_A = buffer.get() & 0xFF;
		mvbBoard.setDataStatusA(getDataStatus(dataStatus_A));
		int dataStatus_B = buffer.get() & 0xFF;
		mvbBoard.setDataStatusB(getDataStatus(dataStatus_B));
		mvbBoard.setCoreTemp(buffer.get() & 0xFF);

		parseLedProperty(mvbBoard, buffer);
		return mvbBoard;
	}

	public static SSDBoard getSSDBoard(byte[] bytes) {
		SSDBoard ssdBoard = new SSDBoard();
		final ByteBuffer buffer = ByteBuffer.wrap(bytes).order(MessageConstant.MESSAGE_ORDER);
		ssdBoard.setBoardType(BoardType.values()[buffer.get() & 0xFF]);
		ssdBoard.setSlotID(buffer.get() & 0xFF);
		ssdBoard.setSsdSize(buffer.getInt() & 0xFF);
		ssdBoard.setSsdUsed(buffer.get() & 0xFF);
		return ssdBoard;
	}

	private static DataStatus getDataStatus(int dataStatus) {
		switch (dataStatus) {
		case 0:
			return DataStatus.NO_DATA;
		case 1:
			return DataStatus.BE_DATA;
		}
		return null;
	}

	private static void parseBoardCommonProperty(byte[] bytes, Board board) {
		final ByteBuffer buffer = ByteBuffer.wrap(bytes).order(MessageConstant.MESSAGE_ORDER);
		parseBoardBasicProperty(board, buffer);
		board.setControlVer(getControlVer(buffer));
	}

	/**
	 * @param board
	 * @param buffer
	 */
	private static void parseBoardBasicProperty(Board board, final ByteBuffer buffer) {
		board.setBoardType(BoardType.values()[buffer.get()]);
		board.setType(board.getBoardType().getType());
		board.setCardName(getCardName(buffer).trim());
		board.setCardSN(getCardSN(buffer).trim());
		board.setDeviceID(getDeviceID(buffer).trim());
		board.setCardIP(getCardIP(buffer));
		board.setSlotID(buffer.get() & 0xFF);
	}

	private static void parseLedProperty(Board board, ByteBuffer buffer) {
		List<LedStatus> statusList = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			int index = Byte.toUnsignedInt(buffer.get());
			LedStatus status = LedStatus.value(index);
			statusList.add(status);
		}
		BoardOutside outside = new BoardOutside();
		outside.setLedStatus(statusList);
		board.setOutside(outside);
	}

	/**
	 * @param buffer
	 * @return
	 */
	private static String getCardName(final ByteBuffer buffer) {
		byte[] nameBytes = new byte[50];
		buffer.get(nameBytes);
		String cardName = new String(nameBytes);
		return cardName;
	}

	/**
	 * @param buffer
	 * @return
	 */
	private static String getCardSN(final ByteBuffer buffer) {
		byte[] cardSNBytes = new byte[20];
		buffer.get(cardSNBytes);
		String cardSN = new String(cardSNBytes);
		return cardSN;
	}

	/**
	 * @param buffer
	 * @return
	 */
	private static String getDeviceID(final ByteBuffer buffer) {
		byte[] deviceIDBytes = new byte[8];
		buffer.get(deviceIDBytes);
		String deviceID = new String(deviceIDBytes);
		return deviceID;
	}

	/**
	 * @param buffer
	 * @return
	 */
	private static String getCardIP(final ByteBuffer buffer) {
		byte[] cardIPBytes = new byte[4];
		buffer.get(cardIPBytes);
		StringBuilder cardIP = new StringBuilder();
		cardIP.append(String.valueOf(cardIPBytes[0] & 0XFF));
		cardIP.append(".");
		cardIP.append(String.valueOf(cardIPBytes[1] & 0XFF));
		cardIP.append(".");
		cardIP.append(String.valueOf(cardIPBytes[2] & 0XFF));
		cardIP.append(".");
		cardIP.append(String.valueOf(cardIPBytes[3] & 0XFF));
		String ip = cardIP.toString();
		return ip;
	}

	/**
	 * @param buffer
	 * @return
	 */
	private static String getControlVer(final ByteBuffer buffer) {
		byte[] controlVerBytes = new byte[4];
		buffer.get(controlVerBytes);
		StringBuilder controlVer = new StringBuilder();
		controlVer.append(String.valueOf(controlVerBytes[0] & 0xFF));
		controlVer.append(".");
		controlVer.append(String.valueOf(controlVerBytes[1] & 0xFF));
		controlVer.append(".");
		controlVer.append(String.valueOf(controlVerBytes[2] & 0xFF));
		controlVer.append(".");
		controlVer.append(String.valueOf(controlVerBytes[3] & 0xFF));
		String controlVersion = controlVer.toString();
		return controlVersion;
	}

	/**
	 * @param buffer
	 * @return
	 */
	private static String getFpgaVer(final ByteBuffer buffer) {
		byte[] fpgaVerBytes = new byte[4];
		buffer.get(fpgaVerBytes);
		StringBuilder fpgaVer = new StringBuilder();
		fpgaVer.append(String.valueOf(fpgaVerBytes[0] & 0xFF));
		fpgaVer.append(".");
		fpgaVer.append(String.valueOf(fpgaVerBytes[1] & 0xFF));
		fpgaVer.append(".");
		fpgaVer.append(String.valueOf(fpgaVerBytes[2] & 0xFF));
		fpgaVer.append(".");
		fpgaVer.append(String.valueOf(fpgaVerBytes[3] & 0xFF));
		return fpgaVer.toString();
	}

	/**
	 * @param cardStatus
	 * @return
	 */
	private static CardStatus getCardStatus(int cardStatus) {
		return CardStatus.values()[cardStatus];
	}
}
