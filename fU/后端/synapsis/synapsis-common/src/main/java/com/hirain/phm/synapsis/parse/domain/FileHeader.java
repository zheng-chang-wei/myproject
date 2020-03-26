/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.parse.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 16, 2020 11:39:40 AM
 * @Description
 *              <p>
 *              文件信息头
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 16, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "storeVariables")
@Data
public class FileHeader {

	@XmlTransient
	private String version;

	@XmlElement(name = "variableGroup", required = true, type = HeaderVariableGroup.class)
	private List<HeaderVariableGroup> headers;
}
