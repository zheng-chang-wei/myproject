/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.hirain.phm.synapsis.file.XmlFileGenerator;
import com.hirain.phm.synapsis.result.ecn.domain.AlgorithmIndex;
import com.hirain.phm.synapsis.result.ecn.domain.AlgorithmIndexSetting;
import com.hirain.phm.synapsis.result.ecn.domain.CommonSegment;
import com.hirain.phm.synapsis.result.ecn.domain.CommonSegmentSetting;
import com.hirain.phm.synapsis.result.ecn.service.ResultSettingService;
import com.hirain.phm.synapsis.result.ecn.service.impl.InMemoryAlgorithmIndexSettingService;
import com.hirain.phm.synapsis.result.ecn.service.impl.InMemoryCommonHeaderService;
import com.hirain.phm.synapsis.result.ecn.service.impl.ResultSettingServiceImpl;
import com.hirain.phm.synapsis.result.ecn.vo.AlgorithmIndexVO;
import com.hirain.phm.synapsis.result.ecn.vo.AlgorithmOutputVO;
import com.hirain.phm.synapsis.result.ecn.vo.CommonSegmentVO;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.db.AlgorithmSettingQuery;
import com.hirain.phm.synapsis.setting.db.SettingDbService;
import com.hirain.phm.synapsis.setting.db.VariableDbService;
import com.hirain.phm.synapsis.setting.support.MulticastAssigner;
import com.hirain.phm.synapsis.setting.support.impl.SettingProperties;
import com.hirain.phm.synapsis.setting.support.param.ECNVariableVO;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 2:58:41 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class ResultServiceTest {

	private ResultSettingService service;

	private SettingDbService dbService;

	private AlgorithmOutputVO output;

	private Setting setting;

	@Before
	public void setUp() {
		service = new ResultSettingServiceImpl();
		dbService = new TestSettingDbService();
		inject();
		output = createOutput();
		setting = dbService.selectWithDetail(1);
	}

	@Test
	public void testCommonHeader() {
		CommonSegmentSetting headerSetting = service.generateCommonSegmentSetting(setting, output);
		assertNotNull(headerSetting);
		assertEquals(output.getCommonSegments().size(), headerSetting.getSegments().size());
		assertNotNull(headerSetting.getSubscribeGroup());
		assertEquals(1, headerSetting.getSubscribeGroup().getVariables().size());
		System.out.println(headerSetting);
	}

	@Test
	public void testAlgorithmIndex() {
		List<AlgorithmIndexSetting> indexSettings = service.genearteAlgorithmIndexSetting(setting, output);
		assertEquals(output.getAlgorithms().size(), indexSettings.size());
		AlgorithmIndexSetting indexSetting = indexSettings.get(0);
		AlgorithmIndex index = indexSetting.getAlgorithms().get(0);
		assertEquals(output.getAlgorithms().get(0).getComId(), indexSetting.getComId());
		assertEquals(1, index.getCode().intValue());
		System.out.println(indexSetting);
	}

	@Test
	public void testGetResultSetting() {
		service.save(1, output);
		AlgorithmOutputVO output = service.getResultSetting(setting.getId());
		System.out.println(output);
	}

	@Test
	public void testGenerateFile() throws Exception {
		service.save(1, output);
		((ResultSettingServiceImpl) service).setFileGenerator(new XmlFileGenerator());
		SettingProperties properties = new SettingProperties();
		properties.setRootDirectory("D:\\test\\settings");
		((ResultSettingServiceImpl) service).setRootDirectory(properties.getRootDirectory());
		File file = new File(properties.getRootDirectory(), "resultsetting.xml");
		if (file.exists()) {
			file.delete();
		}
		service.genearteSettingFile(setting.getId());

		assertTrue(file.exists());
	}

	/**
	 * @return
	 */
	private AlgorithmOutputVO createOutput() {
		AlgorithmOutputVO output = new AlgorithmOutputVO();
		output.setCommonLength(1);
		output.setDataLength(1);
		CommonSegmentVO source1 = new CommonSegmentVO();
		source1.setType(CommonSegment.SOURCE_SYSTEM);
		source1.setProperty("millisecond");
		source1.setByteLen(1);
		source1.setName("segment");
		source1.setDataType("UINT8");
		source1.setIndex(0);
		CommonSegmentVO source2 = new CommonSegmentVO();
		source2.setType(CommonSegment.SOURCE_BUS);
		ECNVariableVO variable = new ECNVariableVO();
		variable.setSignalName("test");
		variable.setDataType(1);
		variable.setComId(12096L);
		variable.setSourceIp("127.0.0.1");
		source2.setSource(variable);
		source2.setByteLen(1);
		source2.setName("segment1");
		source2.setDataType("UINT8");
		source2.setIndex(1);
		output.setCommonSegments(Arrays.asList(source1, source2));
		AlgorithmIndexVO algorithmIndex = new AlgorithmIndexVO();
		algorithmIndex.setComId(12096L);
		algorithmIndex.setAlgorithms(Arrays.asList("算法1"));
		output.setAlgorithms(Arrays.asList(algorithmIndex));
		return output;
	}

	/**
	 * 
	 */
	private void inject() {
		((ResultSettingServiceImpl) service).setIndexService(new InMemoryAlgorithmIndexSettingService());
		((ResultSettingServiceImpl) service).setHeaderService(new InMemoryCommonHeaderService());
		((ResultSettingServiceImpl) service).setDbService(dbService);
		((ResultSettingServiceImpl) service).setGroupService(new VariableDbService() {

			@Override
			public void saveGroups(int boardId, List<VariableGroup> groups) {
				groups.get(0).setId(1);
			}

			@Override
			public void insertVariable(String type, Variable variable) {
			}
		});
		((ResultSettingServiceImpl) service).setAlgorithmSettingQuery(new AlgorithmSettingQuery() {

			@Override
			public AlgorithmSetting getAlgorithmSetting(int settingId, int code) {
				AlgorithmSetting algorithmSetting = new AlgorithmSetting();
				algorithmSetting.setId(1);
				algorithmSetting.setName("算法1");
				algorithmSetting.setCode(1);
				algorithmSetting.setSlotId(1);
				algorithmSetting.setType(1);
				algorithmSetting.setFilename("xxx.py");
				algorithmSetting.setEnable(true);
				algorithmSetting.setSubsystemId(1);
				return algorithmSetting;
			}

			@Override
			public AlgorithmSetting getAlgorithmSetting(int code) {
				return null;
			}
		});
		((ResultSettingServiceImpl) service).setAssigner(new MulticastAssigner() {

			@Override
			public void start() {
			}

			@Override
			public String retryIp() {
				return "127.0.0.1";
			}

			@Override
			public int nextPort() {
				return 65535;
			}

			@Override
			public String nextIp() {
				return "127.0.0.1";
			}

			@Override
			public long nextConsumption() {
				return 1;
			}
		});
	}
}
