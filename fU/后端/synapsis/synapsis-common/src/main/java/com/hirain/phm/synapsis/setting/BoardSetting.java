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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.alibaba.fastjson.annotation.JSONField;

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
@Table(name = "t_board_setting")
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BoardSetting")
@XmlType(propOrder = { "enable", "slotId", "ip", "type", "filename", "retryIp", "variableGroups" })
public class BoardSetting {

	@Id
	@GeneratedValue(generator = "JDBC")
	@JSONField(serialize = false)
	@XmlTransient
	private Integer id;

	@JSONField(serialize = false)
	@XmlTransient
	private Integer settingId;

	@XmlAttribute(name = "enable", required = true)
	private Boolean enable;

	@XmlAttribute(name = "slotId", required = true)
	private Integer slotId;

	@XmlElement(name = "ip", required = true)
	private String ip;

	@XmlElement(name = "type", required = true)
	private String type;

	/**
	 * 板卡变量的来源
	 * <br>
	 * MVB和ECN：数据流文件，记录数据流文件名称
	 * AD：数据库中的VariableGroup，记录group的主键
	 */
	@XmlElement(name = "filename", required = true)
	private String filename;

	// @JSONField(serialize = false)
	@XmlTransient
	private String fileOriginalName;

	@Transient
	@XmlElement(name = "retryIp", required = true)
	private String retryIp;

	@Transient
	@XmlElementWrapper(name = "variableGroups")
	@XmlElement(name = "variableGroup", required = true, type = VariableGroup.class)
	private List<VariableGroup> variableGroups;

	/**
	 * 重要：数据流文件解析得到的变量组（MVB和ECN），AD板卡配置的变量组
	 * <br>
	 * <strong>和variableGroups的区别：</strong>
	 * <li>variableGroup是被使用的变量列表，即采集板卡需要采集并发出的变量。</li>
	 * <li>group是板卡具有的变量列表，</li>
	 */
	@Transient
	@XmlTransient
	@JSONField(serialize = false)
	private VariableGroup group;
}
