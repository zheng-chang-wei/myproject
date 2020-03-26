/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;
import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.support.impl.SettingProperties;
import com.hirain.phm.synapsis.setting.support.param.ADGroupVO;
import com.hirain.phm.synapsis.setting.support.param.ADVariableVO;
import com.hirain.phm.synapsis.setting.support.param.AlgorithmSettingVO;
import com.hirain.phm.synapsis.setting.support.param.BoardSettingVO;
import com.hirain.phm.synapsis.setting.support.param.ECNGroupVO;
import com.hirain.phm.synapsis.setting.support.param.ECNVariableVO;
import com.hirain.phm.synapsis.setting.support.param.MVBGroupVO;
import com.hirain.phm.synapsis.setting.support.param.MVBVariableVO;
import com.hirain.phm.synapsis.setting.support.param.SettingVO;
import com.hirain.phm.synapsis.setting.support.param.StoreVariablesVO;
import com.hirain.phm.synapsis.setting.support.param.TimeVariablesVO;
import com.hirain.phm.synapsis.util.JsonUtil;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 5, 2019 6:34:10 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 5, 2019 zepei.tao@hirain.com 1.0 create file
 */
public class SettingFileGenetorTest extends BaseTest {

	@Autowired
	private SettingActivateSupporter genetor;

	@Autowired
	private SettingProperties properties;

	@Autowired
	private SettingUpdateSupporter updateSupporter;

	/**
	 * 测试配置文件的生成
	 */
	@Test
	public void test() {
		deleteRootFolder();
		File root = new File(properties.getRootDirectory());
		File[] listFiles = root.listFiles();
		assertTrue(listFiles == null || listFiles.length == 0);
		try {
			File file = ResourceUtils.getFile("classpath:request.json");
			String jsonResult = JsonUtil.readJSONStringFromFile(file);
			SettingVO frontSetting = JSON.parseObject(jsonResult, SettingVO.class);
			Setting setting = updateSupporter.voToSetting(frontSetting);
			updateSupporter.assignMulticastIP(setting);
			setting.setId(1);
			genetor.acivate(setting);
			BoardSetting boardSetting = setting.getBoardSettings().get(0);
			File boardFile = new File(properties.getRootDirectory() + File.separator + String.valueOf(boardSetting.getSlotId()) + "_"
					+ boardSetting.getType() + File.separator + properties.getBoardSettingFileName() + ".xml");
			System.err.println(boardFile.getAbsolutePath());
			boolean exists = boardFile.exists();
			assertTrue(exists);

			for (BoardSetting bSetting : setting.getBoardSettings()) {
				BoardType type = BoardType.valueOf(bSetting.getType());
				if (type.getType().equals("PHM")) {
					File bFile = new File(properties.getRootDirectory() + File.separator + String.valueOf(bSetting.getSlotId()) + "_"
							+ bSetting.getType() + File.separator + properties.getAlgorithmSettingFileName() + ".xml");
					assertTrue(bFile.exists());
				}
			}

			File allFile = new File(properties.getRootDirectory(), properties.getAllSettingFileName() + ".xml");
			assertTrue(allFile.exists());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * 
	 */
	private void deleteRootFolder() {
		File folder = new File(properties.getRootDirectory());
		File[] files = folder.listFiles();
		for (File file : files) {
			FileUtils.deleteQuietly(file);
		}
	}

	/**
	 * 测试由前端配置对象转换为后端配置对象
	 */
	@Test
	public void testParseFrontSetting() {
		SettingVO frontSetting = createFrontSetting();
		Setting setting = updateSupporter.voToSetting(frontSetting);
		assertNotNull(setting);
		assertEquals(frontSetting.getName(), setting.getName());
		assertEquals(frontSetting.getBoardSettings().size(), setting.getBoardSettings().size());
		assertEquals(frontSetting.getAlgorithmSettings().size(), setting.getAlgorithmSettings().size());
		assertNotNull(setting.getStoreVariables());
	}

	/**
	 * 测试消费者ID、组播地址的分配
	 */
	@Test
	public void testAssignMulticastIP() {
		Setting setting = TestObjectUtils.getSetting();
		updateSupporter.assignMulticastIP(setting);
		assertNotNull(setting.getRetryIp());

		List<VariableGroup> storeVariables = setting.getStoreVariables();
		for (VariableGroup group : storeVariables) {
			assertGroup(group);
		}
		VariableGroup timeGroup = setting.getTimeVariables();
		if (timeGroup != null) {
			assertGroup(timeGroup);
		}

		List<AlgorithmSetting> algorithmSettings = setting.getAlgorithmSettings();
		for (AlgorithmSetting algorithmSetting : algorithmSettings) {
			for (VariableGroup group : algorithmSetting.getVariableGroups()) {
				assertGroup(group);
			}
		}
	}

	/**
	 * @param group
	 */
	private void assertGroup(VariableGroup group) {
		assertNotNull(group.getConsumptionId());
		assertNotNull(group.getMulticastIp());
		assertNotNull(group.getMulticastPort());
	}

	/**
	 * 创建一个FrontSetting，写成json文件
	 */
	@Test
	@Ignore
	public void genetorFrontSettingFile() {
		SettingVO frontSetting = createFrontSetting();
		frontSetting.setName("testFrontSetting");
		frontSetting.setLine("上海1号线");
		frontSetting.setTrain("333555");
		frontSetting.setTimeOn(true);
		frontSetting.setRawSpace(20);
		frontSetting.setRawStrategy(1);
		frontSetting.setResultSpace(80);
		frontSetting.setResultStrategy(0);
		frontSetting.setAlgorithmSettings(produceFrontAlgorithmSettings());
		frontSetting.setBoardSettings(produceFrontBoardSettings());
		frontSetting.setTimeVariables(produceTimeVariable());
		frontSetting.setStoreVariables(produceStoreVariable());

		String jsonString = JsonUtil.formatJson(JSON.toJSONString(frontSetting));
		try {
			File file = ResourceUtils.getFile("classpath:genetorFront.json");
			assertTrue(writeStringToFile(file.getAbsolutePath(), jsonString));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		}
	}

	private SettingVO createFrontSetting() {
		SettingVO setting = new SettingVO();
		setting.setName("配置1");
		setting.setLine("1");
		setting.setTrain("1");
		BoardSettingVO boardSetting = new BoardSettingVO();
		boardSetting.setSlotId(1);
		boardSetting.setType(BoardType.MVB.name());
		BoardSettingVO boardSetting2 = new BoardSettingVO();
		boardSetting2.setSlotId(2);
		boardSetting2.setType(BoardType.AD1.name());
		boardSetting2.setVariables(Arrays.asList(new ADVariableVO(), new ADVariableVO()));
		setting.setBoardSettings(Arrays.asList(boardSetting, boardSetting2));

		AlgorithmSettingVO algorithmSetting = new AlgorithmSettingVO();
		algorithmSetting.setName("haha");
		ADGroupVO adGroup = new ADGroupVO();
		adGroup.setSlotId(2);
		adGroup.setVariables(Arrays.asList(new ADVariableVO()));
		MVBGroupVO mvbGroup = new MVBGroupVO();
		mvbGroup.setSlotId(1);
		mvbGroup.setVariables(Arrays.asList(new MVBVariableVO(), new MVBVariableVO()));
		algorithmSetting.setAdGroups(Arrays.asList(adGroup));
		algorithmSetting.setMvbGroups(Arrays.asList(mvbGroup));

		TimeVariablesVO timeVariables = new TimeVariablesVO();
		timeVariables.setMvbGroup(mvbGroup);

		StoreVariablesVO storeVariables = new StoreVariablesVO();
		storeVariables.setAdGroups(Arrays.asList(adGroup));
		storeVariables.setMvbGroups(Arrays.asList(mvbGroup));
		storeVariables.setEcnGroups(new ArrayList<>());
		setting.setAlgorithmSettings(Arrays.asList(algorithmSetting));
		setting.setTimeOn(true);
		setting.setTimeVariables(timeVariables);
		setting.setStoreVariables(storeVariables);
		return setting;
	}

	private List<AlgorithmSettingVO> produceFrontAlgorithmSettings() {
		AlgorithmSettingVO frontAlgorithmSetting = new AlgorithmSettingVO();
		frontAlgorithmSetting.setSlotId(1);
		frontAlgorithmSetting.setType(2);
		frontAlgorithmSetting.setSubsystemId(3);
		frontAlgorithmSetting.setFilename("fileName");
		frontAlgorithmSetting.setEnable(true);
		frontAlgorithmSetting.setCode(1);
		frontAlgorithmSetting.setName("bbb");
		frontAlgorithmSetting.setAdGroups(Stream.of(produceFrontADGroup()).collect(Collectors.toList()));
		frontAlgorithmSetting.setEcnGroups(Stream.of(produceFrontECNGroup()).collect(Collectors.toList()));
		frontAlgorithmSetting.setMvbGroups(Stream.of(produceFrontMVBGroup()).collect(Collectors.toList()));
		return Stream.of(frontAlgorithmSetting).collect(Collectors.toList());
	}

	private List<BoardSettingVO> produceFrontBoardSettings() {
		BoardSettingVO frontBoardSetting = new BoardSettingVO();
		frontBoardSetting.setSlotId(1);
		frontBoardSetting.setType("MVB");
		frontBoardSetting.setFilename("filename");
		frontBoardSetting.setIp("123.4.5.6");
		frontBoardSetting.setEnable(true);

		ADVariableVO variable = new ADVariableVO();
		variable.setChnId(3);
		variable.setSampleRate(0.8);
		variable.setName("adName");

		frontBoardSetting.setVariables(Stream.of(variable).collect(Collectors.toList()));
		return Stream.of(frontBoardSetting).collect(Collectors.toList());
	}

	private TimeVariablesVO produceTimeVariable() {
		TimeVariablesVO timeVariable = new TimeVariablesVO();
		timeVariable.setEcnGroup(produceFrontECNGroup());
		return timeVariable;
	}

	private ECNGroupVO produceFrontECNGroup() {
		ECNGroupVO frontECNGroup = new ECNGroupVO();
		frontECNGroup.setSlotId(1);
		frontECNGroup.setVariables(new ArrayList<>());
		ECNVariableVO variable = new ECNVariableVO();
		variable.setBitLen(1);
		variable.setBitOffset(0);
		variable.setByteOffset(0);
		variable.setComId(1000L);
		variable.setDataType(2);
		variable.setName("aaa");
		variable.setSignalName("变量名称");
		variable.setSourceIp("122.0.0.3");
		variable.setUnit("单位");
		frontECNGroup.getVariables().add(variable);
		return frontECNGroup;
	}

	private MVBGroupVO produceFrontMVBGroup() {
		MVBGroupVO frontMVBGroup = new MVBGroupVO();
		frontMVBGroup.setSlotId(1);
		frontMVBGroup.setVariables(new ArrayList<>());
		MVBVariableVO variable = new MVBVariableVO();
		variable.setBitLen(1);
		variable.setBitOffset(0);
		variable.setByteOffset(0);
		variable.setCarriage("carriage");
		variable.setDevice(2);
		variable.setDeviceName("deviceName");
		variable.setFcode(2);
		variable.setName("name");
		variable.setPort(3);
		variable.setSignalName("signalName");
		variable.setSystem("system");
		variable.setUnit("unit");
		variable.setDataType(3);
		frontMVBGroup.getVariables().add(variable);
		return frontMVBGroup;
	}

	private ADGroupVO produceFrontADGroup() {
		ADGroupVO frontADGroup = new ADGroupVO();
		frontADGroup.setSlotId(2);
		frontADGroup.setVariables(new ArrayList<>());
		ADVariableVO variable = new ADVariableVO();
		variable.setChnId(3);
		variable.setSampleRate(0.8);
		variable.setName("adName");
		frontADGroup.getVariables().add(variable);
		return frontADGroup;
	}

	private StoreVariablesVO produceStoreVariable() {
		StoreVariablesVO storeVariable = new StoreVariablesVO();
		storeVariable.setAdGroups(new ArrayList<>());
		storeVariable.getAdGroups().add(produceFrontADGroup());
		storeVariable.setEcnGroups(new ArrayList<>());
		storeVariable.getEcnGroups().add(produceFrontECNGroup());
		storeVariable.setMvbGroups(new ArrayList<>());
		storeVariable.getMvbGroups().add(produceFrontMVBGroup());
		return storeVariable;
	}

	private boolean writeStringToFile(String path, String string) {
		File file = new File(path);
		FileOutputStream out = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new FileOutputStream(file);
			out.write(string.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

}
