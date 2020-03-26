/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.setting.dao.BoardSettingMapper;
import com.hirain.phm.synapsis.setting.db.BoardSettingQuery;
import com.hirain.phm.synapsis.setting.service.BoardSettingService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 5:45:24 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class BoardTest extends BaseTest {

	@Autowired
	private BoardSettingQuery query;

	@Autowired
	private BoardSettingService service;

	@Autowired
	private BoardSettingMapper mapper;

	@Test
	public void test() {
		BoardSetting boardSetting = query.getBoardSetting(1);
		assertNotNull(boardSetting);

		assertEquals(1, boardSetting.getSlotId().intValue());

		assertEquals(BoardType.MVB.name(), boardSetting.getType());
	}

	@Test
	public void testVariables() {
		List<VariableGroup> variables = service.getVariables(1);
		assertNotNull(variables);
	}

	@Test
	public void testInsert() {
		BoardSetting setting = new BoardSetting();
		setting.setEnable(true);
		setting.setFilename("xxx");
		setting.setIp("11111");
		setting.setSettingId(1);
		setting.setSlotId(2);
		setting.setType(BoardType.AD1.name());
		// mapper.insert(setting);
		mapper.insertGenerateKey(setting);
		BoardSetting result = mapper.selectByPrimaryKey(setting.getId());

		assertNotNull(result.getType());
	}

}
