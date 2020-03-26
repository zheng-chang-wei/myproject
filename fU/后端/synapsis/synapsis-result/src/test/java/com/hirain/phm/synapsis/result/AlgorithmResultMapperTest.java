/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.synapsis.result.dao.AlgorithmHeaderMapper;
import com.hirain.phm.synapsis.result.dao.AlgorithmResultMapper;
import com.hirain.phm.synapsis.result.dao.CommonHeaderMapper;
import com.hirain.phm.synapsis.result.domain.AlgorithmResult;
import com.hirain.phm.synapsis.result.param.ResultQueryParam;
import com.hirain.phm.synapsis.result.service.AlgorithmResultService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 1:47:20 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class AlgorithmResultMapperTest {

	@Autowired
	private AlgorithmResultMapper mapper;

	@Autowired
	private AlgorithmResultService service;

	private AlgorithmResult expected;

	@Autowired
	private CommonHeaderMapper headerMapper;

	@Autowired
	private AlgorithmHeaderMapper algorithmMapper;

	@Before
	public void setUp() {
		expected = TestApplication.create();
		service.save(expected);
	}

	@Test
	public void testSelect() {
		AlgorithmResult result = mapper.selectResult(expected.getId());
		assertEquals(expected.getName(), result.getName());
		assertEquals(expected.getTimestamp(), result.getTimestamp());
		assertNotNull(result.getHeader());
		assertNotNull(result.getHeaderId());
		assertEquals(expected.getHeader().getLongiDegree(), result.getHeader().getLongiDegree());
		assertNotNull(result.getAlgorithmHeader());
		assertNotNull(result.getAlgorithmHeaderId());
		assertEquals(expected.getAlgorithmHeader().getHeaderType(), result.getAlgorithmHeader().getHeaderType());

		assertEquals(expected.getCode(), result.getCode());
		assertEquals(expected.getFileType(), result.getFileType());
	}

	@Test
	public void testSelectList() {
		List<AlgorithmResult> results = mapper.selectResults();
		assertNotNull(results);
		assertFalse(results.isEmpty());
		results.forEach(System.out::println);
	}

	@Test
	public void testSelectListParam() {
		ResultQueryParam param = new ResultQueryParam();
		long current = System.currentTimeMillis();
		param.setEnd(new Date(current));
		param.setStart(new Date(current - 1000 * 60));

		List<AlgorithmResult> results = mapper.selectResultsWithParam(param);
		assertFalse(results.isEmpty());
		assertNotNull(results.get(0).getHeader());
		assertNotNull(results.get(0).getAlgorithmHeader());
		results.forEach(System.err::println);
	}

	@After
	public void tearDown() {
		mapper.deleteByPrimaryKey(expected.getId());
		headerMapper.deleteByPrimaryKey(expected.getHeaderId());
		algorithmMapper.deleteByPrimaryKey(expected.getAlgorithmHeaderId());
	}
}
