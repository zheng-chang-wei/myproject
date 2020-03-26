/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.param;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 9, 2019 11:27:53 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 9, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ADGroupVO extends VariableGroupVO {

	private List<ADVariableVO> variables;
}
