/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import java.util.Date;
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
import javax.xml.bind.annotation.XmlType;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 11:26:07 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Table(name = "t_setting")
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Setting")
@XmlType(propOrder = { "name", "line", "train", "rawStrategy", "resultStrategy", "rawSpace", "resultSpace", "timeOn", "selected", "retryIp",
		"timeVariables", "storeVariables", "boardSettings", "algorithmSettings", })
public class Setting {

	@Id
	@GeneratedValue(generator = "JDBC")
	// @JSONField(serialize = false)
	@XmlTransient
	private Integer id;

	@XmlAttribute(name = "name", required = true)
	private String name;

	@XmlAttribute(name = "line", required = true)
	private String line;

	@XmlAttribute(name = "train", required = true)
	private String train;

	@JSONField(serialize = false)
	@XmlTransient
	private Date lastModify;

	@XmlElement(name = "rawStrategy", required = true)
	private Integer rawStrategy;

	@XmlElement(name = "resultStrategy", required = true)
	private Integer resultStrategy;

	@XmlElement(name = "rawSpace", required = true)
	private Integer rawSpace;

	@XmlElement(name = "resultSpace", required = true)
	private Integer resultSpace;

	@XmlAttribute(name = "timeOn", required = true)
	private Boolean timeOn;

	@XmlAttribute(name = "selected", required = true)
	private Boolean selected;

	@XmlElement(name = "retryIp", required = true)
	private String retryIp;

	@Transient
	@XmlElement(name = "timeVariables", required = true)
	private VariableGroup timeVariables;

	@Transient
	@XmlElementWrapper(name = "storeVariables")
	@XmlElement(name = "variableGroup", required = true, type = VariableGroup.class)
	private List<VariableGroup> storeVariables;

	@Transient
	@XmlElementWrapper(name = "boardSettings")
	@XmlElement(name = "boardSetting", required = true, type = BoardSetting.class)
	private List<BoardSetting> boardSettings;

	@Transient
	@XmlElementWrapper(name = "algorithmSettings")
	@XmlElement(name = "algorithmSetting", required = true, type = AlgorithmSetting.class)
	private List<AlgorithmSetting> algorithmSettings;

}
