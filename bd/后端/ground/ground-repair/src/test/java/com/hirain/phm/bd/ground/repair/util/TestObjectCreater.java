/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.repair.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hirain.phm.bd.ground.common.event.FaultTopType;
import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;
import com.hirain.phm.bd.ground.maintenance.param.AfterSaleInput;
import com.hirain.phm.bd.ground.maintenance.param.CommonInput;
import com.hirain.phm.bd.ground.maintenance.param.QualityInvestInput;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月30日 上午9:33:48
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月30日 jianwen.xin@hirain.com 1.0 create file
 */
public class TestObjectCreater {

	public static Map<String, String> create1() {
		Map<String, String> map = new HashMap<>();
		map.put("project", "上海s1");
		map.put("kilometers", "200");
		map.put("trainId", "01");
		map.put("doorId", "3车2门");
		map.put("faultTime", new Date().toString());
		map.put("effect", "effect");
		map.put("doorType", "一类门");
		map.put("stage", "门开");
		map.put("levelOne", "部件1");
		map.put("levelTwo", "部件2");
		map.put("mode", "门故障");
		map.put("location", "上海站");
		map.put("component", "部件3");
		map.put("count", "1");
		map.put("needParts", "true");
		map.put("partName", "配件1");
		map.put("partCount", "1");
		map.put("needReport", "false");
		map.put("temporary", "");
		map.put("description", "");
		map.put("reason", "");
		map.put("imagePath", "");
		map.put("processorCount", "1");
		map.put("processor", "Mike");
		return map;
	}

	public static WorkDetail create() {
		WorkDetail detail = new WorkDetail();
		detail.setKilometers(200);
		detail.setTrainId("01");
		detail.setDoorId("3车2门");
		detail.setFaultTime(new Date());
		detail.setDoorTypeId(1);
		detail.setEffectId(1);
		detail.setStageId(1);
		detail.setModeId(1);
		detail.setLevelOne("部件1");
		detail.setLevelTwo("部件2");
		detail.setLocation("上海站");
		detail.setComponent("部件3");
		detail.setCount(1);
		detail.setNeedParts(true);
		detail.setPartName("配件1");
		detail.setPartCount(1);
		detail.setNeedReport(false);
		detail.setTemporary("");
		detail.setDescription("");
		detail.setReason("");
		detail.setImagePath("");
		detail.setProcessorCount(1);
		detail.setProcessor("Mike");
		return detail;
	}

	/**
	 * @return
	 */
	public static AfterSaleInput createAfterSale() {
		AfterSaleInput quality = new AfterSaleInput();
		quality.setAuditor("Mike");
		quality.setDate(new Date());
		quality.setFaultNo("0x111");
		quality.setSpareNo("0x111");
		quality.setDescription("");
		quality.setStatistics(true);
		return quality;
	}

	public static QualityInvestInput createQualityInvest() {
		QualityInvestInput input = new QualityInvestInput();
		input.setInvestResult("xxx");
		input.setDepartments(new String[] { "项目部" });
		input.setSuggestion("xx");
		return input;
	}

	public static QualityInvestInput createMultiDeparts() {
		QualityInvestInput input = new QualityInvestInput();
		input.setInvestResult("xxx");
		input.setDepartments(new String[] { "项目部", "质保部" });
		input.setSuggestion("xx");
		return input;
	}

	public static CommonInput createQuality() {
		CommonInput input = new CommonInput();
		input.setDescription("xx");
		return input;
	}

	/**
	 * @return
	 */
	public static CommonInput createResolve() {
		CommonInput input = new CommonInput();
		input.setDescription("xx");
		return input;
	}

	public static UnifiedFaultRecord getFaultRecord() {
		UnifiedFaultRecord record = new UnifiedFaultRecord();
		record.setProjectId(1);
		record.setTrainId("17");
		record.setCarriageId(1);
		record.setDoorId(1);
		record.setCode(1);
		record.setFaultName("门电机开路故障");
		record.setTime(new Date());
		record.setType(FaultTopType.Fault);
		record.setId(12l);
		return record;
	}
}
