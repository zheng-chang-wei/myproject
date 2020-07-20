/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 6, 2019 3:01:48 PM
 * @Description
 *              <p>
 *              对应一块PHM卡上的所有算法
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 6, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PHMAlgorithmSetting")
public class PHMAlgorithmSetting {

	@XmlElementWrapper(name = "algorithmSettings")
	@XmlElement(name = "algorithmSetting", required = true, type = AlgorithmSetting.class)
	private List<AlgorithmSetting> algorithmSettings;

	@XmlElement(name = "retryIp", required = true)
	private String retryIp;

	public PHMAlgorithmSetting() {
	}

}
