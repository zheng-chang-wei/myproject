/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 1:37:30 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Table(name = "t_variable_group")
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "VariableGroup")
@XmlType(propOrder = { "type", "consumptionId", "slotId", "multicastIp", "multicastPort", "variables", })
public class VariableGroup {

	@Id
	@GeneratedValue(generator = "JDBC")
	@JSONField(serialize = false)
	@XmlTransient
	private Integer id;

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

	@Transient
	@XmlElementWrapper(name = "variables")
	@XmlElementRef(name = "variable", required = true)
	private List<? extends Variable> variables;

}
