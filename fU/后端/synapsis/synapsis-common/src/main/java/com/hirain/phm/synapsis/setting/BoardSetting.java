/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

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

import com.hirain.phm.synapsis.setting.property.BoardProperty;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 11:43:56 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_board_setting")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BoardSetting")
public class BoardSetting {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	private Integer settingId;

	@XmlAttribute(name = "enable", required = true)
	private Boolean enable;

	@XmlAttribute(name = "slotId", required = true)
	private Integer slotId;

	@XmlAttribute(name = "type", required = true)
	private String type;

	@XmlAttribute(name = "retryIp", required = true)
	@Transient
	private String retryIp;

	@XmlElementRef
	@Transient
	private BoardProperty property;

	@XmlTransient
	private String content;

	@Transient
	@XmlElementWrapper(name = "variableGroups")
	@XmlElement(name = "variableGroup", required = true, type = VariableGroup.class)
	private List<VariableGroup> variableGroups;

}
