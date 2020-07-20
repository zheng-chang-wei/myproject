/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 9:34:40 AM
 * @Description
 *              <p>
 *              分析结果字段
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "segment")
public class ResultSegment {

	@XmlAttribute
	private int index;

	@XmlAttribute
	private String name;

	@XmlAttribute(name = "data-type")
	private String dataType;

	@XmlAttribute(name = "byte-len")
	private int byteLen;

}
