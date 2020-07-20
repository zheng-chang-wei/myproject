/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.param;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 28, 2020 5:48:00 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class SubhealthExportObject {

	@ExcelProperty("所属系统")
	@ColumnWidth(15)
	private String system;

	@ExcelProperty("信息来源")
	@ColumnWidth(15)
	private String source;

	@ExcelProperty("项目")
	@ColumnWidth(15)
	private String project;

	@ExcelProperty("车辆号")
	@ColumnWidth(10)
	private String trainNo;

	@ExcelProperty("车厢号")
	@ColumnWidth(10)
	private Integer carNo;

	@ExcelProperty("门地址")
	@ColumnWidth(10)
	private String doorAddr;

	@ExcelProperty("发生时间")
	@ColumnWidth(20)
	private Date startTime;

	@ExcelProperty("亚健康名称")
	@ColumnWidth(20)
	private String subhealthName;

	@ColumnWidth(50)
	@ExcelProperty("算法依据")
	private String description;

	@ColumnWidth(50)
	@ExcelProperty("处理措施")
	private String repair;

	@ColumnWidth(50)
	@ExcelProperty("检修指南")
	private String solution;
}
