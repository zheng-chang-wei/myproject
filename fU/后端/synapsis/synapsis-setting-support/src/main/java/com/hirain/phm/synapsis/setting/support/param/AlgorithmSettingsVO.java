/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.param;

import java.util.List;

import lombok.Data;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年4月26日 下午2:50:20
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年4月26日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
public class AlgorithmSettingsVO {

	private Integer settingId;

	private List<AlgorithmSettingVO> algorithmVOList;
}
