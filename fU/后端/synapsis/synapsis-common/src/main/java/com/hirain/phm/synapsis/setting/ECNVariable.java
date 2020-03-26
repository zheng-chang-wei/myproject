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
 * @Created Dec 3, 2019 1:57:07 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Table(name = "t_ecn_variable")
@Data
@EqualsAndHashCode(callSuper = true, of = { "comId", "sourceIp", "name" })
@XmlRootElement(name = "ECNVariable")
public class ECNVariable extends Variable {

	@Id
	@GeneratedValue(generator = "JDBC")
	@JSONField(serialize = false)
	@XmlTransient
	private Long id;

	@XmlAttribute(name = "sourceIp", required = true)
	private String sourceIp;

	@XmlAttribute(name = "comId", required = true)
	private Long comId;

	@XmlAttribute(name = "byteOffset", required = true)
	private Integer byteOffset;

	@XmlAttribute(name = "bitOffset", required = true)
	private Integer bitOffset;

	@XmlAttribute(name = "bitLen", required = true)
	private Integer bitLen;

	@XmlAttribute(name = "name", required = true)
	private String name;

	@XmlAttribute(name = "unit", required = true)
	private String unit;

}
