/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 1:46:06 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Table(name = "t_ad_variable")
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "ADVariable")
public class ADVariable extends Variable {

	@Id
	@GeneratedValue(generator = "JDBC")
	@JSONField(serialize = false)
	@XmlTransient
	private Long id;

	@XmlAttribute(name = "chnId", required = true)
	private Integer chnId;

	@XmlAttribute(name = "sampleRate", required = true)
	private Double sampleRate;

	@XmlAttribute(name = "clockSrc", required = true)
	private Double clockSrc;

	@XmlAttribute(name = "frequenceFactor", required = true)
	private Double frequenceFactor;

}
