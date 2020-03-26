/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.BaseTest;
import com.hirain.phm.synapsis.board.controller.BoardController;
import com.hirain.phm.synapsis.board.param.BoardStatisticsResponse;
import com.hirain.phm.synapsis.response.ResultBean;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 21, 2019 3:36:42 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 21, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class BoardControllerTest extends BaseTest {

	@Autowired
	private BoardController controller;

	@Test
	public void test() {
		ResultBean<BoardStatisticsResponse> resultBean = controller.count();
		assertEquals(9, resultBean.getData().getTotal());
	}
}
