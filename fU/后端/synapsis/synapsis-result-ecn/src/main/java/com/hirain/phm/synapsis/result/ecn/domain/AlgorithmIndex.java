/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 2:39:50 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_algorithm_index")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AlgorithmIndex")
public class AlgorithmIndex {

	@Id
	@XmlTransient
	private Long id;

	@XmlTransient
	private Long indexSettingId;

	@XmlAttribute
	private Integer index;

	@XmlAttribute
	private Integer code;

}
