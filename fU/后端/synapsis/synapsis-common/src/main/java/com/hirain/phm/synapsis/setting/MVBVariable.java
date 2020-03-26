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
 * @Created Dec 3, 2019 1:54:26 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Table(name = "t_mvb_variable")
@Data
@EqualsAndHashCode(callSuper = true, of = { "device", "port", "name" })
@XmlRootElement(name = "MVBVariable")
public class MVBVariable extends Variable {

	@Id
	@GeneratedValue(generator = "JDBC")
	@JSONField(serialize = false)
	@XmlTransient
	private Long id;

	@XmlAttribute(name = "device", required = true)
	private Integer device;

	@XmlAttribute(name = "deviceName", required = true)
	private String deviceName;

	@XmlAttribute(name = "carriage", required = true)
	private String carriage;

	@XmlAttribute(name = "system", required = true)
	private String system;

	@XmlAttribute(name = "port", required = true)
	private Integer port;

	@XmlAttribute(name = "fcode", required = true)
	private Integer fcode;

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
