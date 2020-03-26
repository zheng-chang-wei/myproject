/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.export;

import java.util.Date;

import com.hirain.common.csv.annotation.CsvProperty;
import com.hirain.common.csv.annotation.DateFormat;
import com.hirain.phm.bd.ground.common.event.FaultTopType;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;
import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 6:54:06 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class WorksheetRow {

	@CsvProperty("序号")
	private int seq;

	@CsvProperty("来源")
	private String source;

	@DateFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@CsvProperty("发生时间")
	private Date timestamp;

	@CsvProperty("故障名称")
	private String faultName;

	@CsvProperty("项目名称")
	private String project;

	@CsvProperty("列车编号")
	private String train;

	@CsvProperty("车门编号")
	private String door;

	@CsvProperty("运营影响")
	private String effect;

	@CsvProperty("车门种类")
	private String doorType;

	@CsvProperty("发生阶段")
	private String stage;

	@CsvProperty("故障模式")
	private String faultMode;

	@CsvProperty("一级部件")
	private String levelOne;

	@CsvProperty("二级部件")
	private String levelTwo;

	@CsvProperty("发生地点")
	private String location;

	@CsvProperty("故障件名称")
	private String component;

	@CsvProperty("故障件数量")
	private Integer count;

	@CsvProperty("临时处理措施")
	private String temporary;

	@CsvProperty("更换调整数量")
	private int replaceCount;

	@CsvProperty("故障描述")
	private String description;

	@CsvProperty("初步原因分析")
	private String reason;

	@CsvProperty(value = "是否需要配件", converter = BooleanValueConverter.class)
	private boolean needParts;

	@CsvProperty("配件名称")
	private String partName;

	@CsvProperty("配件数量")
	private int partCount;

	@CsvProperty(value = "是否需要分析报告", converter = BooleanValueConverter.class)
	private boolean needReport;

	@CsvProperty("现场处理人数")
	private Integer processorCount;

	@CsvProperty("现场处理人")
	private String processor;

	@CsvProperty("维修工时")
	private int hours;

	@CsvProperty("属性1名称")
	private String property1;

	@CsvProperty("属性1值")
	private String value1;

	@CsvProperty("属性2名称")
	private String property2;

	@CsvProperty("属性2值")
	private String value2;

	@CsvProperty("属性3名称")
	private String property3;

	@CsvProperty("属性3值")
	private String value3;

	@CsvProperty("属性4名称")
	private String property4;

	@CsvProperty("属性4值")
	private String value4;

	public static WorksheetRow valueOf(WorkSheet sheet) {
		WorksheetRow row = new WorksheetRow();
		WorkDetail detail = sheet.getDetail();
		row.setSource(FaultTopType.values()[sheet.getFaultType()].getName());
		row.setTimestamp(sheet.getFaultTime());
		row.setFaultName(sheet.getFaultName());
		row.setProject(sheet.getProject());
		row.setTrain(sheet.getTrainId());
		row.setDoor(sheet.getDoorId());
		row.setEffect(detail.getEffect());
		row.setDoorType(detail.getDoorType());
		row.setStage(detail.getStage());
		row.setFaultMode(detail.getFaultMode());
		row.setLevelOne(detail.getLevelOne());
		row.setLevelTwo(detail.getLevelTwo());
		row.setLocation(detail.getLocation());
		row.setComponent(detail.getComponent());
		row.setCount(detail.getCount());
		row.setTemporary(detail.getTemporary());
		row.setReplaceCount(1);// TODO
		row.setDescription(detail.getDescription());
		row.setReason(detail.getReason());
		row.setNeedParts(detail.getNeedParts());
		row.setPartName(detail.getPartName());
		row.setPartCount(detail.getPartCount());
		row.setNeedReport(detail.getNeedReport());
		row.setProcessorCount(detail.getProcessorCount());
		row.setProcessor(detail.getProcessor());
		row.setHours(0);// TODO
		// TODO 自定义属性
		return row;
	}
}
