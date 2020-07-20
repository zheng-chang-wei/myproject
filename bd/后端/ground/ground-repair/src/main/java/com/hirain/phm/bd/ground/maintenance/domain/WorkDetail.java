/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月29日 下午3:58:09
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月29日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_workdetail")
public class WorkDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Long id;

	private Integer projectId;

	@Transient
	private String project;

	/**
	 * 运行公里数
	 */
	private Integer kilometers;

	/**
	 * 列车编号
	 */
	private String trainId;

	/**
	 * 车门编号
	 */
	private String doorId;

	/**
	 * 车门种类
	 */
	private Integer doorTypeId;

	@Transient
	private String doorType;

	/**
	 * 运营影响
	 */
	private Integer effectId;

	@Transient
	private String effect;

	/**
	 * 故障发生阶段
	 */
	private Integer stageId;

	@Transient
	private String stage;

	/**
	 * 故障模式
	 */
	private Integer modeId;

	@Transient
	private String faultMode;

	private Integer levelOneId;

	/**
	 * 一级部件
	 */
	@Transient
	private String levelOne;

	private Integer levelTwoId;

	/**
	 * 二级部件
	 */
	@Transient
	private String levelTwo;

	/**
	 * 故障件名称
	 */
	private String component;

	/**
	 * 故障件数量
	 */
	private Integer count;

	/**
	 * 发生地点
	 */
	private String location;

	/**
	 * 故障时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date faultTime;

	/**
	 * 临时处理措施
	 */
	private String temporary;

	/**
	 * 更换或调整数量
	 */
	private Integer replaceOrAdjustCount;

	/**
	 * 维修工时(单人)
	 */
	private String repairTime;

	/**
	 * 故障描述
	 */
	private String description;

	/**
	 * 初步原因分析
	 */
	private String reason;

	/**
	 * 是否需要配件
	 */
	private Boolean needParts;

	/**
	 * 配件名称
	 */
	private String partName;

	/**
	 * 配件数量
	 */
	private Integer partCount;

	/**
	 * 是否需要分析报告
	 */
	private Boolean needReport;

	/**
	 * 图片路径
	 */
	private String imagePath;

	/**
	 * 视频名称
	 */
	private String videoName;

	/**
	 * 现场处理人数
	 */
	private Integer processorCount;

	/**
	 * 现场处理人
	 */
	private String processor;

	/**
	 * 自定义变量名称 逗号隔开
	 */
	private String variables;

	/**
	 * 自定义变量值 逗号隔开
	 */
	private String valuess;

	private String repair;

	private String solution;
}
