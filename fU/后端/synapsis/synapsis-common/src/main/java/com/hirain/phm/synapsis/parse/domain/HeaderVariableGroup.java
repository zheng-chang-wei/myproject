/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.parse.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.hirain.phm.synapsis.setting.Variable;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 13, 2020 10:13:29 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "VariableGroup")
public class HeaderVariableGroup {

	@XmlAttribute(name = "type", required = true)
	private String type;

	@XmlAttribute(name = "consumptionId", required = true)
	private Long consumptionId;

	@XmlAttribute(name = "slotId", required = true)
	private Integer slotId;

	@XmlElement(name = "multicastIp", required = true)
	private String multicastIp;

	@XmlElement(name = "multicastPort", required = true)
	private Integer multicastPort;

	@XmlElementWrapper(name = "variables")
	@XmlElementRef(name = "variable", required = true)
	private List<? extends Variable> variables;
}
