/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.extend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 22, 2020 9:28:15 AM
 * @Description
 *              <p>
 *              配置操作扩展管理
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 22, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class SupportExtenderManagerImpl implements SupportExtenderManager {

	@Autowired(required = false)
	private List<SettingSupportExtender> extenders;

	/**
	 * @see com.hirain.phm.synapsis.setting.support.extend.SupportExtenderManager#activate(ExtenderSetting)
	 */
	@Override
	public void activate(ExtenderSetting extenderSetting) throws Exception {
		if (extenders != null) {
			for (SettingSupportExtender extender : extenders) {
				extender.activate(extenderSetting);
			}
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.extend.SupportExtenderManager#validate(int, com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	public ValidateResult validate(int settingId, Setting setting) {
		ValidateResult result = new ValidateResult();
		if (extenders != null) {
			List<String> errors = new ArrayList<>();
			for (SettingSupportExtender extender : extenders) {
				ValidateResult extendResult = extender.validate(settingId, setting);
				if (extendResult.isError()) {
					errors.addAll(extendResult.getErrors());
				}
			}
			if (!errors.isEmpty()) {
				result.setError(true);
				result.setErrors(errors);
			}
		}
		return result;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.extend.SupportExtenderManager#delete(int)
	 */
	@Override
	public void delete(int settingId) throws Exception {
		if (extenders != null) {
			for (SettingSupportExtender extender : extenders) {
				extender.delete(settingId);
			}
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.extend.SupportExtenderManager#getVariableGroup(int)
	 */
	@Override
	public Collection<? extends VariableGroup> getVariableGroup(int settingId) {
		List<VariableGroup> groups = new ArrayList<>();
		if (extenders != null) {
			for (SettingSupportExtender extender : extenders) {
				groups.addAll(extender.getVariableGroups(settingId));
			}
		}
		return groups;
	}

}
