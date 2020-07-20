/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.setting.MVBVariable;
import com.hirain.phm.synapsis.setting.Variable.VariableType;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.support.param.MVBGroupVO;
import com.hirain.phm.synapsis.setting.support.param.MVBVariableVO;
import com.hirain.phm.synapsis.setting.support.variable.impl.MVBGroupConverter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 12, 2019 10:47:22 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 12, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class SettingToParamTest extends BaseTest {

	@Autowired
	SettingViewSupporter parser;

	@Test
	public void test() {
		// Setting setting = TestObjectUtils.getSetting();
		// SettingVO frontSetting = parser.settingToVO(setting);
		// assertNotNull(frontSetting);
		// assertEquals(setting.getName(), frontSetting.getName());
		//
		// if (setting.getTimeOn()) {
		// assertNotNull(frontSetting.getTimeVariables());
		// }
		// assertTime(setting.getTimeVariables(), frontSetting.getTimeVariables());
		// assertStore(setting.getStoreVariables(), frontSetting.getStoreVariables());
		// assertBoard(setting.getBoardSettings(), frontSetting.getBoardSettings());
		// List<AlgorithmSetting> algorithmSettings = setting.getAlgorithmSettings();
		// List<AlgorithmSettingVO> frontAlgorithms = frontSetting.getAlgorithmSettings();
		// assertEquals(algorithmSettings.size(), frontAlgorithms.size());
		// for (int i = 0; i < algorithmSettings.size(); i++) {
		// AlgorithmSetting algorithmSetting = algorithmSettings.get(i);
		// AlgorithmSettingVO frontAlgorithmSetting = frontAlgorithms.get(i);
		// Set<Integer> slotSet = new HashSet<>();
		// for (VariableGroup group : algorithmSetting.getVariableGroups()) {
		// slotSet.add(group.getSlotId());
		// }
		// int size = 0;
		// if (frontAlgorithmSetting.getAdGroups() != null) {
		// size += frontAlgorithmSetting.getAdGroups().size();
		// }
		// if (frontAlgorithmSetting.getMvbGroups() != null) {
		// size += frontAlgorithmSetting.getMvbGroups().size();
		// }
		// if (frontAlgorithmSetting.getEcnGroups() != null) {
		// size += frontAlgorithmSetting.getEcnGroups().size();
		// }
		// assertEquals(slotSet.size(), size);
		// }
	}

	// /**
	// * @param boardSettings
	// * @param frontBoardSettings
	// */
	// private void assertBoard(List<BoardSetting> boardSettings, List<BoardSettingVO> frontBoardSettings) {
	// assertEquals(boardSettings.size(), frontBoardSettings.size());
	// for (BoardSettingVO boardSetting : frontBoardSettings) {
	// if (boardSetting.getType().startsWith("CARD_TYPE_AD")) {
	// assertNotNull(boardSetting.getVariables());
	// }
	// }
	// }
	//
	// /**
	// * @param timeVariables
	// * @param frontTime
	// */
	// private void assertTime(VariableGroup timeVariables, TimeVariablesVO frontTime) {
	// if (timeVariables.getType().equals(VariableType.ECN.name())) {
	// assertNotNull(frontTime.getEcnGroup());
	// } else {
	// assertNotNull(frontTime.getMvbGroup());
	// }
	// }
	//
	// /**
	// * @param groups
	// * @param storeVariables
	// */
	// private void assertStore(List<VariableGroup> groups, StoreVariablesVO storeVariables) {
	// Set<Integer> slotSet = new HashSet<>();
	// for (VariableGroup group : groups) {
	// slotSet.add(group.getSlotId());
	// }
	// int sum = 0;
	// if (storeVariables.getAdGroups() != null) {
	// sum += storeVariables.getAdGroups().size();
	// }
	// if (storeVariables.getMvbGroups() != null) {
	// sum += storeVariables.getMvbGroups().size();
	// }
	// if (storeVariables.getEcnGroups() != null) {
	// sum += storeVariables.getEcnGroups().size();
	// }
	// assertEquals(slotSet.size(), sum);
	// }

	@Test
	public void testMVBVariableGroup() {
		VariableGroup group = getMVBVariableGroup();
		MVBGroupConverter converter = new MVBGroupConverter();
		MVBGroupVO mvbGroup = converter.encodeGroup(group);
		assertNotNull(mvbGroup);
		assertEquals(group.getSlotId(), mvbGroup.getSlotId());
		assertEquals(group.getVariables().size(), mvbGroup.getVariables().size());
		MVBVariableVO mvbVariable = mvbGroup.getVariables().get(0);
		MVBVariable variable = (MVBVariable) group.getVariables().get(0);
		assertEquals(variable.getName(), mvbVariable.getName());
	}

	/**
	 * @return
	 */
	private VariableGroup getMVBVariableGroup() {
		VariableGroup group = TestObjectUtils.createVariableGroup(1, VariableType.MVB.name());
		MVBVariable mvbVariable = TestObjectUtils.getMVBVariable("mvb-v");
		group.setVariables(Arrays.asList(mvbVariable));
		return group;
	}

}
