/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hirain.phm.synapsis.setting.VariableGroup;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec May 11, 2020 2:27:26 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 11, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Data
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "storeVariables")
public class StoreVariables {

	public StoreVariables() {
	}

	@XmlElement(name = "variableGroup", required = true, type = VariableGroup.class)
	private List<VariableGroup> variableGroups;

}
