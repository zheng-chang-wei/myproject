/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 21, 2020 3:42:57 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Table(name = "t_store_setting")
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "StoreSetting")
public class StoreSetting {

	@Id
	@XmlTransient
	private Integer settingId;

	@XmlElement(name = "rawStrategy", required = true)
	private Integer rawStrategy;

	@XmlElement(name = "resultStrategy", required = true)
	private Integer resultStrategy;

	@XmlElement(name = "rawSpace", required = true)
	private Integer rawSpace;

	@XmlElement(name = "resultSpace", required = true)
	private Integer resultSpace;

	@Transient
	@XmlElementWrapper(name = "storeVariables")
	@XmlElement(name = "variableGroup", required = true, type = VariableGroup.class)
	private List<VariableGroup> storeVariables;
}
