/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import java.util.Arrays;
import java.util.Date;

import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.setting.Variable.VariableType;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 5, 2019 5:28:52 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 5, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class TestObjectUtils {

	/**
	 * @param slotId
	 * @param type
	 * @return
	 */
	public static BoardSetting createBoardSetting(int slotId, BoardType type) {
		BoardSetting boardSetting = new BoardSetting();
		boardSetting.setEnable(true);
		boardSetting.setIp("192.168.40.31");
		boardSetting.setSlotId(slotId);
		boardSetting.setType(type.name());
		boardSetting.setFilename("xxx.xls");
		boardSetting.setFileOriginalName("xxxx.xls");
		return boardSetting;
	}

	/**
	 * @param slotId
	 * @param type
	 */
	public static VariableGroup createVariableGroup(Integer slotId, String type) {
		VariableGroup group = new VariableGroup();
		group.setType(type);
		group.setConsumptionId(1l);
		group.setMulticastIp("127.0.0.1");
		group.setMulticastPort(0);
		group.setSlotId(slotId);
		return group;
	}

	/**
	 * @return
	 */
	public static Setting getSetting() {
		Setting setting = new Setting();
		setting.setLine("北京1号线");
		setting.setTrain("2");
		setting.setName("配置2");
		setting.setLastModify(new Date());
		setting.setRawStrategy(1);
		setting.setRawSpace(20);
		setting.setResultStrategy(1);
		setting.setResultSpace(80);
		setting.setTimeOn(true);
		setting.setSelected(false);

		AlgorithmSetting algorithmSetting = new AlgorithmSetting();
		algorithmSetting.setName("算法1");
		algorithmSetting.setCode(1);
		algorithmSetting.setSlotId(1);
		algorithmSetting.setType(1);
		algorithmSetting.setFilename("xxx.py");
		algorithmSetting.setEnable(true);
		algorithmSetting.setSubsystemId(1);

		BoardSetting mvbSetting = createBoardSetting(1, BoardType.MVB);
		VariableGroup mvbGroup = createVariableGroup(mvbSetting.getSlotId(), VariableType.MVB.name());
		MVBVariable mvbVariable = new MVBVariable();
		mvbVariable.setDevice(1);
		mvbGroup.setVariables(Arrays.asList(mvbVariable));

		BoardSetting adSetting = createBoardSetting(2, BoardType.AD1);
		VariableGroup adGroup = createVariableGroup(adSetting.getSlotId(), VariableType.AD.name());
		ADVariable adVariable = new ADVariable();
		adVariable.setChnId(1);
		adGroup.setVariables(Arrays.asList(adVariable));
		adSetting.setVariableGroups(Arrays.asList(adGroup));

		BoardSetting ecnSetting = createBoardSetting(3, BoardType.ECN);
		VariableGroup ecnGroup = createVariableGroup(ecnSetting.getSlotId(), VariableType.ECN.name());
		ECNVariable ecnVariable = new ECNVariable();
		ecnVariable.setComId(1l);
		ecnVariable.setBitLen(1);
		ecnVariable.setBitOffset(1);
		ecnVariable.setByteOffset(1);
		ecnVariable.setDataType(1);
		ecnVariable.setName("ecn变量");
		ecnVariable.setUnit("V");
		ecnVariable.setSourceIp("127.0.0.1");
		ecnVariable.setSignalName("xxx");
		ecnGroup.setVariables(Arrays.asList(ecnVariable));

		algorithmSetting.setVariableGroups(Arrays.asList(mvbGroup, ecnGroup, adGroup));

		setting.setTimeVariables(mvbGroup);

		setting.setStoreVariables(Arrays.asList(mvbGroup, ecnGroup, adGroup));

		setting.setAlgorithmSettings(Arrays.asList(algorithmSetting));
		setting.setBoardSettings(Arrays.asList(mvbSetting, ecnSetting, adSetting));
		return setting;
	}

	/**
	 * @param string
	 * @param i
	 */
	public static MVBVariable createMVBVariable(String name, int port) {
		MVBVariable variable = new MVBVariable();
		variable.setName(name);
		variable.setPort(port);
		variable.setDataType(BusDataType.CHAR8.ordinal());
		variable.setBitLen(1);
		variable.setBitOffset(1);
		variable.setByteOffset(1);
		variable.setCarriage("1");
		variable.setDevice(1);
		variable.setDeviceName("1");
		variable.setFcode(1);
		variable.setSignalName(name);
		variable.setSystem("1");
		variable.setUnit("V");
		return variable;
	}

	/**
	 * @param string
	 */
	public static ECNVariable createECNVariable(String string) {
		ECNVariable variable = new ECNVariable();
		variable.setName(string);
		variable.setComId(12096L);
		return variable;
	}

}
