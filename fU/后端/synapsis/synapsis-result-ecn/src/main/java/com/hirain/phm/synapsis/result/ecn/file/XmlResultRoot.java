/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.file;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.hirain.phm.synapsis.result.ecn.domain.AlgorithmIndexSetting;
import com.hirain.phm.synapsis.result.ecn.domain.CommonSegmentSetting;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 4:37:44 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "result")
public class XmlResultRoot {

	@XmlElement(name = "header", required = true)
	private CommonSegmentSetting headerSetting;

	@XmlElementWrapper(name = "indexSettings")
	@XmlElement(name = "indexSetting", required = true, type = AlgorithmIndexSetting.class)
	private List<AlgorithmIndexSetting> indexSettings;
}
