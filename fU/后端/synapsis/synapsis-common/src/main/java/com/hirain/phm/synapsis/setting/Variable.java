/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 2:27:53 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Variable")
@XmlSeeAlso({ MVBVariable.class, ECNVariable.class, ADVariable.class })
public class Variable {

	@JSONField(serialize = false)
	@XmlTransient
	private Integer groupId;

	@XmlAttribute(name = "signalName", required = true)
	private String signalName;

	@XmlAttribute(name = "dataType", required = true)
	private Integer dataType;

	public enum VariableType {

		MVB,

		ECN,

		AD;
	}

}
