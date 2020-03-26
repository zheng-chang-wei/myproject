/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;
import java.util.List;

import org.junit.Test;

import com.hirain.phm.synapsis.board.domain.ADBoard;
import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.domain.CPUBoard;
import com.hirain.phm.synapsis.board.domain.CardStatus;
import com.hirain.phm.synapsis.board.domain.DataStatus;
import com.hirain.phm.synapsis.board.domain.ECNBoard;
import com.hirain.phm.synapsis.board.domain.LedStatus;
import com.hirain.phm.synapsis.board.domain.MVBBoard;
import com.hirain.phm.synapsis.board.domain.PHMBoard;
import com.hirain.phm.synapsis.board.domain.SSDBoard;
import com.hirain.phm.synapsis.board.util.BoardDecodeHelper;
import com.hirain.phm.synapsis.message.MessageConstant;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 27, 2019 5:29:18 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 27, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class BoardDecodeHelperTest {

	@Test
	public void testBasicInfos() {
		ByteBuffer buffer = ByteBuffer.allocate(1 + 2 * 128);
		buffer.put((byte) 2);
		buffer.put(getBoardBytes());
		buffer.put(getBoardBytes());

		List<Board> boards = BoardDecodeHelper.parseBoardInfos(buffer.array());
		assertEquals(2, boards.size());
		System.out.println(boards);
	}

	@Test
	public void testADBoard() {
		ByteBuffer buffer = ByteBuffer.allocate(128).order(MessageConstant.MESSAGE_ORDER);
		fillCommon(buffer);
		buffer.put(new byte[] { 1, 0, 0, 0 });
		buffer.put(new byte[] { 1, 0, 0, 0 });
		buffer.put((byte) 1);
		buffer.put((byte) 20);
		buffer.put(new byte[] { 1, 2, 3, (byte) 0xff });
		ADBoard adBoard = BoardDecodeHelper.getADBoard(buffer.array());
		assertEquals(CardStatus.WORKING, adBoard.getCardStatus());
		System.out.println(adBoard);
		List<LedStatus> statusList = adBoard.getOutside().getLedStatus();
		assertEquals(LedStatus.values()[1], statusList.get(0));
		assertEquals(LedStatus.NULL, statusList.get(3));
	}

	@Test
	public void testECNBoard() {
		ByteBuffer buffer = ByteBuffer.allocate(128).order(MessageConstant.MESSAGE_ORDER);
		fillCommon(buffer);
		buffer.put(new byte[] { 1, 0, 0, 0 });
		buffer.put(new byte[] { 1, 0, 0, 0 });
		buffer.put((byte) 2);
		buffer.put((byte) 0);
		buffer.put((byte) 20);
		buffer.put(new byte[] { 1, 2, 3, (byte) 0xff });
		ECNBoard ecnBoard = BoardDecodeHelper.getECNBoard(buffer.array());
		assertEquals(CardStatus.STOPPED, ecnBoard.getCardStatus());
		assertEquals(DataStatus.NO_DATA, ecnBoard.getDataStatus());
		System.out.println(ecnBoard);
	}

	@Test
	public void testCPUBoard() {
		ByteBuffer buffer = ByteBuffer.allocate(128).order(MessageConstant.MESSAGE_ORDER);
		fillCommon(buffer);
		buffer.put(new byte[] { 1, 0, 0, 0 });
		buffer.putInt(125);
		buffer.put((byte) 50);
		buffer.put((byte) 20);
		buffer.put(new byte[] { 1, 2, 3, (byte) 0xff });
		CPUBoard cpuBoard = BoardDecodeHelper.getCPUBoard(buffer.array());
		assertEquals(125, cpuBoard.getRamSize());
		assertEquals(20, cpuBoard.getCoreTemp());
		System.out.println(cpuBoard);
	}

	@Test
	public void testPHMBoard() {
		ByteBuffer buffer = ByteBuffer.allocate(128).order(MessageConstant.MESSAGE_ORDER);
		fillCommon(buffer);
		buffer.put(new byte[] { 1, 0, 0, 0 });
		buffer.put(new byte[] { 1, 0, 0, 0 });
		buffer.put((byte) 2);
		buffer.putInt(125);
		buffer.put((byte) 50);
		buffer.putInt(125);
		buffer.put((byte) 50);
		buffer.put((byte) 20);
		buffer.put((byte) 40);
		buffer.put((byte) 20);
		buffer.put(new byte[] { 1, 2, 3, (byte) 0xff });
		PHMBoard phmBoard = BoardDecodeHelper.getPHMBoard(buffer.array());
		assertEquals(20, phmBoard.getCoreTemp());
		System.out.println(phmBoard);
	}

	@Test
	public void testMVBBoard() {
		ByteBuffer buffer = ByteBuffer.allocate(128).order(MessageConstant.MESSAGE_ORDER);
		fillCommon(buffer);
		buffer.put(new byte[] { 1, 0, 0, 0 });
		buffer.put((byte) 2);
		buffer.put((byte) 1);
		buffer.put((byte) 1);
		buffer.put((byte) 20);
		buffer.put(new byte[] { 1, 2, 3, (byte) 0xff });
		MVBBoard mvbBoard = BoardDecodeHelper.getMVBBoard(buffer.array());
		assertEquals(20, mvbBoard.getCoreTemp());
		System.out.println(mvbBoard);
	}

	@Test
	public void testSSDBoard() {
		ByteBuffer buffer = ByteBuffer.allocate(128).order(MessageConstant.MESSAGE_ORDER);
		buffer.put((byte) 4);
		buffer.put((byte) 12);
		buffer.putInt(120);
		buffer.put((byte) 20);
		SSDBoard ssdBoard = BoardDecodeHelper.getSSDBoard(buffer.array());
		assertEquals(20, ssdBoard.getSsdUsed());
		System.out.println(ssdBoard);
	}

	/**
	 * @return
	 */
	private byte[] getBoardBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(128).order(MessageConstant.MESSAGE_ORDER);
		fillCommon(buffer);
		byte[] boardBytes = buffer.array();
		return boardBytes;
	}

	/**
	 * @param buffer
	 */
	private void fillCommon(ByteBuffer buffer) {
		buffer.put((byte) 1);
		buffer.put(getBytes("synapsis.cpu_1", 50));
		buffer.put(getBytes("12345", 20));
		buffer.put(getBytes("1234", 8));
		buffer.put((byte) 127);
		buffer.put((byte) 0);
		buffer.put((byte) 0);
		buffer.put((byte) 1);
		buffer.put((byte) 1);
	}

	/**
	 * @param name
	 * @return
	 */
	private byte[] getBytes(String name, int length) {
		byte[] bytes = name.getBytes();
		byte[] nameBytes = new byte[length];
		System.arraycopy(bytes, 0, nameBytes, 0, bytes.length);
		return nameBytes;
	}
}
