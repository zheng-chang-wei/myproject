/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.hirain.phm.synapsis.BaseTest;
import com.hirain.phm.synapsis.result.ecn.domain.AlgorithmIndex;
import com.hirain.phm.synapsis.result.ecn.domain.AlgorithmIndexSetting;
import com.hirain.phm.synapsis.result.ecn.domain.CommonSegment;
import com.hirain.phm.synapsis.result.ecn.domain.CommonSegmentSetting;
import com.hirain.phm.synapsis.result.ecn.service.AlgorithmIndexSettingService;
import com.hirain.phm.synapsis.result.ecn.service.CommonHeaderService;
import com.hirain.phm.synapsis.result.ecn.service.ResultSettingService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 26, 2020 3:44:20 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 26, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@ActiveProfiles({ "dev", "xml" })
public class TestResultSettingService extends BaseTest {

	@Autowired
	private CommonHeaderService headerService;

	@Autowired
	private AlgorithmIndexSettingService indexSettingService;

	@Autowired
	private ResultSettingService service;

	@Test
	public void saveCommonSegment() {
		CommonSegmentSetting segmentSetting = new CommonSegmentSetting();
		segmentSetting.setSettingId(1);
		segmentSetting.setLength(20);
		segmentSetting.setDataLength(20);
		segmentSetting.setEndian("little");
		VariableGroup group = new VariableGroup();
		group.setType("ECN");
		group.setSlotId(1);
		ECNVariable variable = new ECNVariable();
		variable.setName("test");
		variable.setComId(12345L);
		variable.setSourceIp("local");
		group.setVariables(Arrays.asList(variable));
		segmentSetting.setSubscribeGroup(group);

		CommonSegment segment1 = new CommonSegment();
		segment1.setIndex(0);
		segment1.setName("公共包头变量1");
		segment1.setDataType(BusDataType.BITS.name());
		segment1.setByteLen(1);
		segment1.setType("bus");
		segment1.setSource(variable);
		CommonSegment segment2 = new CommonSegment();
		segment2.setIndex(1);
		segment2.setName("公共包头变量2");
		segment2.setDataType(BusDataType.BOOLEAN.name());
		segment2.setByteLen(1);
		segment2.setType("local");
		segment2.setProperty("year");
		segmentSetting.setSegments(Arrays.asList(segment1, segment2));
		headerService.save(segmentSetting);
	}

	@Test
	public void saveIndex() {
		AlgorithmIndexSetting setting = new AlgorithmIndexSetting();
		setting.setSettingId(1);
		setting.setComId(12345L);
		setting.setCycleTime("60");
		AlgorithmIndex index1 = new AlgorithmIndex();
		index1.setCode(10);
		index1.setIndex(1);
		AlgorithmIndex index2 = new AlgorithmIndex();
		index2.setCode(32);
		index2.setIndex(2);
		setting.setAlgorithms(Arrays.asList(index1, index2));
		indexSettingService.delete(1);
		indexSettingService.save(Arrays.asList(setting));
	}

	@Test
	public void generateFile() throws Exception {
		List<VariableGroup> groups = service.getVariableGroups(1);
		groups.forEach(g -> {
			g.setConsumptionId(1L);
			g.setMulticastIp("10.40.1.12");
			g.setMulticastPort(65535);
		});
		service.genearteSettingFile(1, groups);
	}

	@Test
	public void generateFileWithNotSetting() throws Exception {
		service.genearteSettingFile(3, new ArrayList<VariableGroup>());
	}
}
