/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.extend;

import java.util.List;

import com.hirain.phm.synapsis.setting.VariableGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 22, 2020 6:21:36 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 22, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtenderSetting {

	private int settingId;

	private List<VariableGroup> groups;
}
