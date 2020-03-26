/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.common;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.hirain.phm.synapsis.setting.ADVariable;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.MVBVariable;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 20, 2020 3:41:02 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 20, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class XmlTest {

	@Test
	public void test() throws JAXBException {
		BoardSetting boardSetting = new BoardSetting();
		VariableGroup group1 = new VariableGroup();
		ADVariable variable = new ADVariable();
		variable.setChnId(1);
		variable.setSignalName("ad");
		group1.setVariables(Arrays.asList(variable));

		VariableGroup group2 = new VariableGroup();
		MVBVariable mvb = new MVBVariable();
		mvb.setPort(1);
		mvb.setSignalName("mvb");
		group2.setVariables(Arrays.asList(mvb));
		boardSetting.setVariableGroups(Arrays.asList(group1, group2));

		JAXBContext jaxbContext = JAXBContext.newInstance(boardSetting.getClass(), ADVariable.class, MVBVariable.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		StringWriter writer = new StringWriter();
		marshaller.marshal(boardSetting, writer);

		String xml = writer.toString();
		System.out.println(xml);
		assertTrue(xml.contains("ADVariable"));
		assertTrue(xml.contains("MVBVariable"));

		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		StringReader reader = new StringReader(xml);
		BoardSetting board = (BoardSetting) unmarshaller.unmarshal(reader);
		System.out.println(board);
		List<VariableGroup> groups = board.getVariableGroups();
		assertTrue(groups.get(0).getVariables().get(0) instanceof ADVariable);
		assertTrue(groups.get(1).getVariables().get(0) instanceof MVBVariable);
	}
}
