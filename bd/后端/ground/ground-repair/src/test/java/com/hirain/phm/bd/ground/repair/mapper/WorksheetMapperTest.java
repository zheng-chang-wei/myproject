/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.repair.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.common.event.FaultTopType;
import com.hirain.phm.bd.ground.maintenance.dao.WorkSheetMapper;
import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam;
import com.hirain.phm.bd.ground.repair.TestApplication;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 28, 2020 10:49:07 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("dev")
public class WorksheetMapperTest {

	@Autowired
	private WorkSheetMapper mapper;

	@Test
	public void testListWorksheet() {
		WorkSheetQueryParam param = new WorkSheetQueryParam();
		param.setProject("深圳7号线一期");
		param.setTrainNo("717");
		param.setFaultType(FaultTopType.Fault.getCode());
		List<WorkSheet> sheets = mapper.listWorkSheetWithDetail(param);
		assertNotNull(sheets);
		sheets.forEach(System.err::println);
		WorkSheet sheet = sheets.get(0);
		assertEquals(param.getProject(), sheet.getProject());
		assertNotNull(sheet.getUser());
		assertNotNull(sheet.getFaultMode());
		assertNotNull(sheet.getDetail());
	}

	@Test
	public void testListWorksheetOfProject() {
		WorkSheetQueryParam param = new WorkSheetQueryParam();
		param.setProject("深圳7号线一期");
		param.setTrainNo("717");
		param.setFaultType(FaultTopType.Fault.getCode());
		List<WorkSheet> sheets = mapper.listWorkSheetOfProjects(Arrays.asList(2l), null, null);
		assertNotNull(sheets);
		sheets.forEach(System.err::println);
		WorkSheet sheet = sheets.get(0);
		assertEquals(param.getProject(), sheet.getProject());
		assertNotNull(sheet.getUser());
		assertNotNull(sheet.getFaultMode());
		assertNotNull(sheet.getDetail());
	}

	@Test
	public void testSelectById() {
		WorkSheet sheet = mapper.selectBySheetId(1l);
		System.out.println(sheet);
		assertNotNull(sheet.getUser());
		assertNotNull(sheet.getFaultMode());
		assertNotNull(sheet.getDetail());
	}

	@Test
	@Ignore
	public void testInsert() {
		WorkSheet sheet = new WorkSheet();
		sheet.setCreateTime(new Date());
		sheet.setUserId(1l);
		sheet.setState("创建工单");
		sheet.setProjectId(2);
		sheet.setFaultType(FaultTopType.Fault.getCode());
		sheet.setFaultName("测试故障");
		sheet.setFaultTime(new Date());
		sheet.setModeId(1);
		sheet.setTrainId("717");
		sheet.setDoorId("1");
		sheet.setFaultId(11l);
		sheet.setFaultCode(1);
		mapper.insertUseGeneratedKeys(sheet);
	}
}
