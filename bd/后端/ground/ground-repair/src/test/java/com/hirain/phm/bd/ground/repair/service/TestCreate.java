/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.repair.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.maintenance.FlowException;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;
import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;
import com.hirain.phm.bd.ground.maintenance.param.WorksheetPacket;
import com.hirain.phm.bd.ground.maintenance.service.FlowReadService;
import com.hirain.phm.bd.ground.maintenance.service.FlowService;
import com.hirain.phm.bd.ground.repair.TestApplication;
import com.hirain.phm.bd.ground.repair.util.TestObjectCreater;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 12, 2019 1:49:37 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 12, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TestCreate {

	@Autowired
	private FlowService flowService;

	@Autowired
	private FlowReadService readService;

	@Autowired
	public WebApplicationContext context;

	@Before
	public void setUp() {
		SecurityUtils.setSecurityManager(context.getBean(SecurityManager.class));
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("admin", "MQ==");
		subject.login(token);
		subject.getSession().setAttribute("user", subject.getPrincipal());
	}

	@Test
	public void testCreate() throws FlowException {
		UnifiedFaultRecord record = TestObjectCreater.getFaultRecord();
		WorkSheet sheet = flowService.createWorksheet(record);
		Long sheetId = sheet.getId();
		WorksheetPacket packet = readService.getWorksheet(sheetId);
		assertNotNull(packet);
		WorkSheet workSheet = packet.getSheet();
		assertEquals("深圳地铁7号线", workSheet.getProject());
		assertNotNull(workSheet.getDetail());

		WorkDetail detail = workSheet.getDetail();
		fill(detail);

		commit(sheetId, detail);
	}

	@Test
	public void testCreateAndCommit() throws FlowException {
		WorkDetail detail = new WorkDetail();
		fill(detail);
		detail.setProject("深圳地铁7号线");
		detail.setTrainId("717");
		detail.setFaultTime(new Date());

		flowService.createAndCommit(detail);

		WorksheetPacket packet = readService.getWorksheet(1l);
		assertNotNull(packet);
		assertEquals("售后审核", packet.getSheet().getState());
		assertEquals(detail.getProject(), packet.getSheet().getProject());
		assertEquals("admin", packet.getSheet().getUser());
	}

	private void commit(Long sheetId, WorkDetail detail) throws FlowException {
		flowService.commitWorkSheet(sheetId, detail);

		WorksheetPacket packet2 = readService.getWorksheet(sheetId);
		assertEquals("售后审核", packet2.getSheet().getState());
	}

	/**
	 * @param detail
	 */
	private void fill(WorkDetail detail) {
		detail.setComponent("1");
		detail.setCount(1);
		detail.setDescription("1");
		detail.setDoorId("1车1门");
		detail.setDoorTypeId(1);
		detail.setEffectId(1);
		detail.setStageId(1);
		detail.setModeId(1);
		detail.setKilometers(100);
		detail.setLevelOne("1");
		detail.setLevelTwo("2");
		detail.setLocation("1");
		detail.setNeedParts(true);
		detail.setNeedReport(false);
		detail.setPartCount(1);
		detail.setPartName("1");
		detail.setProcessor("1");
		detail.setProcessorCount(1);
		detail.setReason("1");
		detail.setTemporary("1");
	}
}
