/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.protocol.ecn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.ecn.impl.ECNGroupConverter;
import com.hirain.phm.synapsis.setting.ECNVariable;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.support.param.ECNGroupVO;
import com.hirain.phm.synapsis.setting.support.param.ECNVariableVO;
import com.hirain.phm.synapsis.setting.support.variable.ParamVariableConverter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 21, 2020 10:24:22 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class ECNGroupConverterTest {

	private ParamVariableConverter converter = new ECNGroupConverter();

	@Test
	public void test() {
		ECNGroupVO ecnGroup = new ECNGroupVO();
		ecnGroup.setSlotId(1);
		ECNVariableVO frontECNVariable = new ECNVariableVO();
		frontECNVariable.setName("ecn_v");
		ecnGroup.setVariables(Arrays.asList(frontECNVariable));

		VariableGroup group = converter.parseGroupVO(ecnGroup);
		assertNotNull(group);
		assertEquals(ecnGroup.getSlotId(), group.getSlotId());
		assertEquals(ecnGroup.getVariables().size(), group.getVariables().size());
		Variable variable = group.getVariables().get(0);
		assertTrue(variable instanceof ECNVariable);

		ECNVariable ecnVariable = (ECNVariable) variable;
		assertEquals(frontECNVariable.getName(), ecnVariable.getName());

		assertEquals(BoardType.ECN.getType(), group.getType());
	}
}
