/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.domain;

import java.util.List;

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

import com.hirain.phm.synapsis.setting.VariableGroup;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 2:32:07 PM
 * @Description
 *              <p>
 *              公共包头字段来源配置
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_header_setting")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "HeaderSetting")
public class CommonSegmentSetting {

	@Id
	@XmlTransient
	private Long id;

	@XmlTransient
	private Integer settingId;

	@XmlAttribute
	private Integer length;

	@XmlAttribute
	private Integer dataLength;

	@Transient
	@XmlElement
	private VariableGroup subscribeGroup;

	@XmlTransient
	private Integer groupId;

	@Transient
	@XmlElementWrapper(name = "segments")
	@XmlElement(name = "segment", required = true, type = CommonSegment.class)
	private List<CommonSegment> segments;

}
