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

import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.file.FileGenerator;
import com.hirain.phm.synapsis.file.XmlFileGenerator;
import com.hirain.phm.synapsis.setting.ADVariable;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.MVBVariable;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.StoreSetting;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.property.BusBoardProperty;
import com.hirain.phm.synapsis.setting.property.CPUBoardProperty;
import com.hirain.phm.synapsis.setting.property.ECNBoardProperty;
import com.hirain.phm.synapsis.setting.property.MVBBoardProperty;
import com.hirain.phm.synapsis.setting.property.PHMBoardProperty;

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

	private FileGenerator generator = new XmlFileGenerator();

	@Test
	public void test() throws JAXBException {
		BusBoardProperty boardSetting = new BusBoardProperty();
		VariableGroup group1 = new VariableGroup();
		ADVariable variable = new ADVariable();
		variable.setChnId(1);
		variable.setName("ad");
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
		BusBoardProperty board = (BusBoardProperty) unmarshaller.unmarshal(reader);
		System.out.println(board);
		List<VariableGroup> groups = board.getVariableGroups();
		assertTrue(groups.get(0).getVariables().get(0) instanceof ADVariable);
		assertTrue(groups.get(1).getVariables().get(0) instanceof MVBVariable);
	}

	@Test
	public void testBoard() throws Exception {
		BoardSetting board1 = new BoardSetting();
		CPUBoardProperty cpuBoard = new CPUBoardProperty();
		cpuBoard.setIp("local");
		board1.setEnable(true);
		board1.setSlotId(1);
		board1.setType(BoardType.CPU.name());
		board1.setProperty(cpuBoard);

		BoardSetting board2 = new BoardSetting();
		PHMBoardProperty phmBoard = new PHMBoardProperty();
		board2.setEnable(true);
		phmBoard.setIp1("local");
		phmBoard.setIp2("local");
		phmBoard.setTargetIp1("local");
		phmBoard.setTargetIp2("local");
		board2.setSlotId(2);
		board2.setType(BoardType.PHM_AGX.name());
		board2.setProperty(phmBoard);

		BoardSetting board3 = new BoardSetting();
		ECNBoardProperty ecnBoardProperty = new ECNBoardProperty();
		board3.setEnable(true);
		ecnBoardProperty.setFilename("file");
		ecnBoardProperty.setIp1("local");
		ecnBoardProperty.setIp2("local");
		ecnBoardProperty.setOriginalName("oringinal");
		board3.setSlotId(3);
		board3.setType(BoardType.ECN.name());
		board3.setProperty(ecnBoardProperty);

		BoardSetting board4 = new BoardSetting();
		MVBBoardProperty mvbBoardProperty = new MVBBoardProperty();
		board4.setEnable(true);
		mvbBoardProperty.setFilename("file");
		mvbBoardProperty.setOriginalName("oringinal");
		mvbBoardProperty.setTrustLine(1);
		mvbBoardProperty.setMode("EMD");
		board4.setSlotId(4);
		board4.setType(BoardType.MVB.name());
		board4.setProperty(mvbBoardProperty);

		Setting setting = new Setting();
		setting.setBoardSettings(Arrays.asList(board1, board2, board3));

		generator.generate("D:\\test\\test", setting);
	}

	@Test
	public void testStore() throws Exception {
		StoreSetting storeSetting = new StoreSetting();
		storeSetting.setRawSpace(1);
		storeSetting.setResultSpace(1);
		storeSetting.setResultStrategy(1);
		storeSetting.setRawStrategy(1);
		VariableGroup group = new VariableGroup();
		group.setConsumptionId(1L);
		group.setMulticastIp("local");
		group.setMulticastPort(1);
		group.setSlotId(1);
		group.setType("MVB");
		MVBVariable variable = new MVBVariable();
		variable.setName("test");
		group.setVariables(Arrays.asList(variable));
		storeSetting.setStoreVariables(Arrays.asList(group));
		Setting setting = new Setting();
		setting.setStoreSetting(storeSetting);
		generator.generate("D:\\test\\test", setting);
	}
}
