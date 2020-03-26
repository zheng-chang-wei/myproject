/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.domain.CardStatus;
import com.hirain.phm.synapsis.board.domain.MVBBoard;
import com.hirain.phm.synapsis.board.param.Record;
import com.hirain.phm.synapsis.board.service.RecordMapper;
import com.hirain.phm.synapsis.constant.BoardType;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 21, 2019 4:11:01 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 21, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class BoardRecordTest {

	@Autowired
	private RecordMapper mapper;

	@Test
	public void test() {
		List<Record> records = mapper.getRecords();
		assertNotNull(records);
	}

	@Test
	public void testAdd() {
		int size = mapper.getRecords().size();
		Board board = new MVBBoard();
		board.setBoardType(BoardType.MVB);
		board.setSlotID(1);
		mapper.addRecord(board, CardStatus.WORKING, CardStatus.STOPPED);
		List<Record> records = mapper.getRecords();
		assertEquals(size + 1, records.size());

		assertNotNull(records.get(0).getTimestamp());
		assertEquals("MVB【" + board.getSlotID() + "】停止运行", records.get(0).getMessage());
	}

	@Test
	public void testAddMessage() {
		String message = "系统启动";
		mapper.addRecord(message);
		List<Record> records = mapper.getRecords();
		assertEquals(message, records.get(records.size() - 1).getMessage());
	}

	@Test
	public void testSizeLimit() {
		for (int i = 0; i < 20; i++) {
			mapper.addRecord(i + "");
		}
		assertEquals(20, mapper.getRecords().size());
		mapper.addRecord("21");
		assertEquals(20, mapper.getRecords().size());
	}
}
