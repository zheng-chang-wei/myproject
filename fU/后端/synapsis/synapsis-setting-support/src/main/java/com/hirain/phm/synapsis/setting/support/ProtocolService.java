/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support;

import java.util.List;

import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 24, 2020 9:15:25 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 24, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface ProtocolService {

	/**
	 * @param settingId
	 * @param type
	 * @return
	 * @throws SynapsisException
	 */
	List<VariableGroup> getVariables(Integer settingId, String type) throws SynapsisException;

	Object getProtocolObject(Integer settingId, String type);

}
