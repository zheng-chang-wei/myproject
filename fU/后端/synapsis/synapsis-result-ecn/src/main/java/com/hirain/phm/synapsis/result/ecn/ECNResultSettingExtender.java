/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.result.ecn.service.ResultSettingService;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;
import com.hirain.phm.synapsis.setting.support.extend.SettingSupportExtender;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 4:58:09 PM
 * @Description
 *              <p>
 *              上传结果到ECN总线配置的扩展，供总体配置流程调用
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component("ecn-result")
public class ECNResultSettingExtender implements SettingSupportExtender {

	private ResultSettingService ecnSettingService;

	@Override
	public void activate(int settingId) throws Exception {
		try {
			ecnSettingService.genearteSettingFile(settingId);
		} catch (Exception e) {
			throw new SynapsisException("算法结果上传ECN总线配置文件生成失败。", e);
		}
	}

	@Override
	public ValidateResult validate(int settingId, Setting setting) {
		return ecnSettingService.validate(settingId, setting);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.extend.SettingSupportExtender#delete(int)
	 */
	@Override
	public void delete(int settingId) throws Exception {
		ecnSettingService.delete(settingId);
	}
}
