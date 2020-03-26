/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.service;

import java.util.List;

import com.hirain.phm.synapsis.result.ecn.domain.AlgorithmIndexSetting;
import com.hirain.phm.synapsis.result.ecn.domain.CommonSegmentSetting;
import com.hirain.phm.synapsis.result.ecn.vo.AlgorithmOutputVO;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 2:58:17 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface ResultSettingService {

	/**
	 * @param setting
	 * @param output
	 * @return
	 */
	CommonSegmentSetting generateCommonSegmentSetting(Setting setting, AlgorithmOutputVO output);

	/**
	 * @param setting
	 * @param output
	 * @return
	 */
	List<AlgorithmIndexSetting> genearteAlgorithmIndexSetting(Setting setting, AlgorithmOutputVO output);

	/**
	 * @param setting
	 * @param output
	 */
	void save(Integer settingId, AlgorithmOutputVO output);

	/**
	 * @param id
	 */
	AlgorithmOutputVO getResultSetting(Integer id);

	/**
	 * @param settingId
	 * @throws Exception
	 */
	void genearteSettingFile(int settingId) throws Exception;

	/**
	 * @param settingId
	 * @param setting
	 * @return
	 */
	ValidateResult validate(int settingId, Setting setting);

	/**
	 * @param settingId
	 */
	void delete(int settingId);

}
