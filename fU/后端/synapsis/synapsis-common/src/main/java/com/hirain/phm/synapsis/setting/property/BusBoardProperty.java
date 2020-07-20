/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.property;

import java.util.List;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hirain.phm.synapsis.setting.VariableGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 21, 2020 10:29:17 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@XmlSeeAlso({ ECNBoardProperty.class, MVBBoardProperty.class })
@XmlRootElement(name = "BusProperty")
@JSONType(serialzeFeatures = { SerializerFeature.WriteClassName })
public class BusBoardProperty extends BoardProperty {

	@XmlElement
	@Transient
	private String filename;

	@XmlElement
	@Transient
	private String originalName;

	@XmlElementWrapper(name = "variableGroups")
	@XmlElement(name = "variableGroup", required = true, type = VariableGroup.class)
	@Transient
	@JSONField(serialize = false)
	private List<VariableGroup> variableGroups;
}
