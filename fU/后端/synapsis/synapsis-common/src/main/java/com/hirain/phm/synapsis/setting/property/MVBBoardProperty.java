/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.property;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;

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
@XmlRootElement(name = "MVBProperty")
@JSONType(serialzeFeatures = { SerializerFeature.WriteClassName })
public class MVBBoardProperty extends BusBoardProperty {

	/**
	 * 0：both 1:line_A 2:line_B
	 */
	@XmlElement
	@Transient
	private Integer trustLine;

	/**
	 * 默认emd模式
	 */
	@XmlElement
	@Transient
	private String mode = "emd";

}
