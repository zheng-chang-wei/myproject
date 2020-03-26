/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.service.test;

import java.util.Arrays;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.ECNVariable;
import com.hirain.phm.synapsis.setting.MVBVariable;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.db.AlgorithmSettingQuery;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 23, 2019 1:42:42 PM
 * @Description
 *              <p>
 *              测试环境
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 23, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Profile("test")
@Primary
public class TestAlgorithmSettingQuery implements AlgorithmSettingQuery {

	/**
	 * @see com.hirain.phm.synapsis.setting.db.AlgorithmSettingQuery#getAlgorithmSetting(int)
	 */
	@Override
	public AlgorithmSetting getAlgorithmSetting(int code) {
		AlgorithmSetting setting = new AlgorithmSetting();
		VariableGroup group1 = new VariableGroup();
		group1.setType("MVB");
		group1.setVariables(Arrays.asList(new MVBVariable(), new MVBVariable(), new MVBVariable()));
		VariableGroup group2 = new VariableGroup();
		group2.setType("ECN");
		group1.setVariables(Arrays.asList(new ECNVariable(), new ECNVariable()));
		setting.setVariableGroups(Arrays.asList(group1, group2));
		return setting;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.AlgorithmSettingQuery#getAlgorithmSetting(int, int)
	 */
	@Override
	public AlgorithmSetting getAlgorithmSetting(int settingId, int code) {
		AlgorithmSetting setting = new AlgorithmSetting();
		VariableGroup group1 = new VariableGroup();
		group1.setType("MVB");
		group1.setVariables(Arrays.asList(new MVBVariable(), new MVBVariable(), new MVBVariable()));
		VariableGroup group2 = new VariableGroup();
		group2.setType("ECN");
		group1.setVariables(Arrays.asList(new ECNVariable(), new ECNVariable()));
		setting.setVariableGroups(Arrays.asList(group1, group2));
		return setting;
	}

}
