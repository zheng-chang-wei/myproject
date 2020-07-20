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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 11:53:42 AM
 * @Description
 *              <p>
 *              对应某一个算法
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Table(name = "t_algorithm_setting")
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AlgorithmSetting")
public class AlgorithmSetting {

	@Id
	@GeneratedValue(generator = "JDBC")
	@JSONField(serialize = false)
	@XmlTransient
	private Integer id;

	@JSONField(serialize = false)
	@XmlTransient
	private Integer settingId;

	@XmlAttribute(name = "enable", required = true)
	private Boolean enable;

	@XmlAttribute(name = "slotId", required = true)
	private Integer slotId;

	@XmlElement(name = "name", required = true)
	private String name;

	@XmlElement(name = "code", required = true)
	private Integer code;

	@XmlElement(name = "type", required = true)
	private Integer type;

	@XmlElement(name = "filename", required = true)
	private String filename;

	// @JSONField(serialize = false)
	@XmlTransient
	private String fileOriginalName;

	@JSONField(serialize = false)
	@XmlTransient
	private Integer subsystemId;

	@Transient
	@XmlElement(name = "subsystem", required = true)
	private String subsystem;

	@XmlTransient
	private String videoIp;

	@XmlElement
	private String videoUrl;

	@Transient
	@XmlElementWrapper(name = "variableGroups")
	@XmlElement(name = "variableGroup", required = true, type = VariableGroup.class)
	private List<VariableGroup> variableGroups;

}
