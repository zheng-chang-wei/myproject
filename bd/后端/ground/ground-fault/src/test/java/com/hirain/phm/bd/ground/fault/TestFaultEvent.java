/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.fault.domain.FaultInfo;
import com.hirain.phm.bd.ground.fault.param.FaultDetailRequestParams;
import com.hirain.phm.bd.ground.fault.param.FaultDetailResponseParams;
import com.hirain.phm.bd.ground.fault.service.FaultDetailService;
import com.hirain.phm.bd.ground.fault.service.FaultInfoService;
import com.hirain.phm.bd.message.train.FaultMessage;
import com.hirain.phm.bd.message.train.FaultPacket;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年11月11日 下午2:08:00
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年11月11日 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TestFaultEvent {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private FaultDetailService service;

	@Autowired
	private FaultInfoService infoService;

	@Test
	public void test() throws InterruptedException {
		FaultPacket packet = createPacket();
		publisher.publishEvent(packet);
		TimeUnit.MILLISECONDS.sleep(500);

		FaultDetailRequestParams param = new FaultDetailRequestParams();
		param.setProject(packet.getProject());
		param.setTrainNo(packet.getTrain());
		List<FaultDetailResponseParams> details = service.findFaultDetailsByParams(param);
		assertNotNull(details);
		assertFalse(details.isEmpty());
		assertEquals(packet.getProject(), details.get(0).getProject());

		List<FaultInfo> infos = infoService.findFaultInfoByProject(packet.getProject());
		assertNotNull(infos);
		assertFalse(infos.isEmpty());
		assertEquals("测试故障", infos.get(0).getFaultName());
	}

	public FaultPacket createPacket() {
		FaultMessage message = new FaultMessage();
		message.setCarriageId(1);
		message.setDoorId(1);
		message.setFaultId(1);
		message.setFaultName("测试故障");
		message.setTimestamp(new Date());
		FaultPacket packet = new FaultPacket();
		packet.setProject("深圳地铁7号线");
		packet.setTrain("717");
		packet.setMessages(Arrays.asList(message));
		return packet;
	}
}
