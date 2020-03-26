/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.common;

import java.util.List;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hirain.phm.synapsis.setting.VariableGroup;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 16, 2020 11:42:18 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 16, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "storeVariables")
@Data
public class StoreVariables {

	@Transient
	// @XmlElementWrapper(name = "storeVariables")
	@XmlElement(name = "variableGroup", required = true, type = VariableGroup.class)
	private List<VariableGroup> storeVariables;
}
