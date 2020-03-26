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

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 2:39:11 PM
 * @Description
 *              <p>
 *              上传报文中算法顺序的配置
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_result_setting")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "indexSetting")
public class AlgorithmIndexSetting {

	@Id
	@XmlTransient
	private Long id;

	@XmlTransient
	private Integer settingId;

	@XmlAttribute
	private Long comId;

	@XmlAttribute
	private String cycle;

	@Transient
	@XmlElementWrapper(name = "algorithms")
	@XmlElement(name = "index", required = true, type = AlgorithmIndex.class)
	private List<AlgorithmIndex> algorithms;
}
