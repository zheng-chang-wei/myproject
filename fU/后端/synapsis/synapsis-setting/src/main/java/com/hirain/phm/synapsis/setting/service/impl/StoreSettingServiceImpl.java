/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service.impl;

import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.setting.StoreSetting;
import com.hirain.phm.synapsis.setting.common.BaseService;
import com.hirain.phm.synapsis.setting.service.StoreSettingService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 21, 2020 5:13:07 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class StoreSettingServiceImpl extends BaseService<StoreSetting> implements StoreSettingService {

	/**
	 * @see com.hirain.phm.synapsis.setting.service.StoreSettingService#deleteBySettingId(java.lang.Integer)
	 */
	@Override
	public void deleteBySettingId(Integer settingId) {
		Example example = new Example(StoreSetting.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("settingId", settingId);
		deleteByExample(example);
	}

}
