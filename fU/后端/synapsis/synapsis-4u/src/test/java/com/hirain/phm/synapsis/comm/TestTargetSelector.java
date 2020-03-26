/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.comm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;

import com.hirain.phm.synapsis.SynapsisCApplication;
import com.hirain.phm.synapsis.connection.Connection;
import com.hirain.phm.synapsis.connection.TargetConnSelector;
import com.hirain.phm.synapsis.constant.Program;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 5:39:35 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = SynapsisCApplication.class)
@ContextConfiguration
@RunWith(Parameterized.class)
public class TestTargetSelector {

	@Autowired
	private TargetConnSelector selector;

	@Autowired
	private Map<String, Connection> connectionMap;

	private int target;

	private String programId;

	@Before
	public void setUp() throws Exception {
		TestContextManager testContextManager = new TestContextManager(getClass());
		testContextManager.prepareTestInstance(this);
	}

	/**
	 * @param target
	 * @param programId
	 */
	public TestTargetSelector(int target, String programId) {
		super();
		this.target = target;
		this.programId = programId;
	}

	@Test
	public void test() {
		assertNotNull(connectionMap);
		Connection connection = selector.select(target, connectionMap);
		assertEquals(connectionMap.get(programId), connection);
	}

	@Parameters
	public static Collection<Object[]> getConnection() {
		Program[] values = Program.values();
		Object[][] parameter = new Object[values.length - 1][];
		for (int i = 1; i < values.length; i++) {
			Object[] objs = new Object[] { i, values[i].name() };
			parameter[i - 1] = objs;
		}
		return Arrays.asList(parameter);
	}

}
