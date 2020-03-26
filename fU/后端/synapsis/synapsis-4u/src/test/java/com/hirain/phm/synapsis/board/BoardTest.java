/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.hirain.phm.synapsis.BaseTest;
import com.hirain.phm.synapsis.board.service.BoardService;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 5, 2019 6:34:10 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 5, 2019 zepei.tao@hirain.com 1.0 create file
 */
@ActiveProfiles("test")
public class BoardTest extends BaseTest {

	@Autowired
	private BoardService boardService;

	@Test
	public void testBoardLaunch() {
		try {
			boardService.start();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testBoardStop() {
		try {
			boardService.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testBoardStatusInquire() {
		boardService.boardStatusInquire();
	}
}
