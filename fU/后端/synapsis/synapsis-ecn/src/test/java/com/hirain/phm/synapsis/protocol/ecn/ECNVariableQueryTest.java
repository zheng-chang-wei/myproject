/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.protocol.ecn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import com.hirain.phm.synapsis.ecn.ECNVariableQuery;
import com.hirain.phm.synapsis.ecn.impl.ECNProcotolStream;
import com.hirain.phm.synapsis.ecn.impl.ECNVariableQueryImpl;
import com.hirain.phm.synapsis.ecn.model.Device;
import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.protocol.ProtocolStream;
import com.hirain.phm.synapsis.setting.ECNVariable;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 1:40:31 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class ECNVariableQueryTest {

	private ECNVariableQuery query;

	private Device device;

	private ProtocolStream parser = new ECNProcotolStream();

	@Before
	public void setUp() throws Exception {
		query = new ECNVariableQueryImpl();
		File file = ResourceUtils.getFile("classpath:trdp.xls");
		ParseResult result = parser.read(file);
		device = (Device) result.getData();
	}

	@Test
	public void testGetHeader() throws Exception {
		List<ECNVariable> variables = query.getVariables(12096L, 2, device);
		assertNotNull(variables);
		assertEquals(2, variables.size());

		ECNVariable v1 = variables.get(0);
		assertEquals(12096L, v1.getComId().longValue());
		assertEquals("10.0.1.215", v1.getSourceIp());
		assertEquals(0, v1.getDataType().byteValue());
		assertEquals("data", v1.getName());
	}

	@Test
	public void testGetHeader1() {
		List<ECNVariable> variables = query.getVariables(12096L, 1, device);
		assertEquals(1, variables.size());
		System.out.println(variables.get(0));
	}

	@Test
	public void testByteLen() {
		int length = query.getDataSetLength(12096L, device);
		assertEquals(2, length);
	}
}
