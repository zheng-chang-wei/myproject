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
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@XmlTransient
	private Date lastModify;

	@XmlAttribute(name = "selected", required = true)
	private Boolean selected;

	@XmlTransient
	private String userName;

	@XmlElement(name = "retryIp", required = true)
	@Transient
	private String retryIp;

	@Transient
	@XmlElementRef
	private StoreSetting storeSetting;

	@Transient
	@XmlElementRef
	private TimeSetting timeSetting;

	@Transient
	@XmlElementWrapper(name = "boardSettings")
	@XmlElementRef(name = "boardSetting", required = true)
	private List<BoardSetting> boardSettings;

	@Transient
	@XmlElementWrapper(name = "algorithmSettings")
	@XmlElement(name = "algorithmSetting", required = true, type = AlgorithmSetting.class)
	private List<AlgorithmSetting> algorithmSettings;

}
