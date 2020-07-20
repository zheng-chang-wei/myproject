/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 9, 2020 6:45:08 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class LifeExportObject {

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
	private String train;

	@ExcelProperty("部件名称")
	@ColumnWidth(20)
	private String item;

	@ExcelProperty("评估时间")
	@ColumnWidth(20)
	private String time;

	@ExcelProperty("剩余寿命")
	@ColumnWidth(10)
	private Double life;
}
