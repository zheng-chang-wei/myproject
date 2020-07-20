/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.digital;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.ground.digital.dao.DigitalTwinParamMapper;
import com.hirain.phm.bd.ground.digital.dao.DigitalTwinResultMapper;
import com.hirain.phm.bd.ground.digital.domain.DigitalTwinPacket;
import com.hirain.phm.bd.ground.digital.domain.DigitalTwinParam;
import com.hirain.phm.bd.ground.digital.domain.DigitalTwinResult;
import com.hirain.phm.bd.ground.digital.param.ResultQueryRequest;
import com.hirain.phm.bd.ground.digital.param.ResultQueryResponse;
import com.hirain.phm.bd.ground.digital.service.DigitalTwinService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Sep 25, 2019 11:17:37 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Sep 25, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class TestDigital {

	@Autowired
	private DigitalTwinService service;

	@Autowired
	private DigitalTwinResultMapper resultMapper;

	@Autowired
	private DigitalTwinParamMapper paramMapper;

	static String value = "{\r\n" + "	\"City\":\"深圳\",\r\n" + "	\"Line\":\"7\",\r\n" + "	\"TrainID\":\"717\",\r\n" + "	\"CarID\":8,\r\n"
			+ "	\"DoorID\":2,\r\n" + "	\"Debug\":0,\r\n" + "	\"Date\":\"2020-4-01 22:30:00:000\",\r\n" + "	\"MotorPara\":{\r\n"
			+ "		\"Rs\":0.975,\r\n" + "		\"Ls\":0.0005,\r\n" + "		\"Flux\":0.0085,\r\n" + "		\"Kt\":0.085\r\n" + "	},\r\n"
			+ "	\"MechPara\":{\r\n" + "		\"J\":0.00000129,\r\n" + "		\"Damping\":0.00012,\r\n" + "		\"Tf\":0.06\r\n" + "	}\r\n" + "}";

	@Test
	public void testInsert() throws ParseException {
		DigitalTwinPacket packet = JsonUtil.fromString(value, DigitalTwinPacket.class);
		packet.setProject("深圳7号线一期");
		System.out.println(packet);
		service.update(packet);

		DigitalTwinResult condition = new DigitalTwinResult();
		condition.setTrainId(1);
		condition.setCarId(8);
		condition.setDoorId(2);
		condition.setParamId(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse("2020-04-01 00:00:00");
		condition.setTimestamp(date);
		List<DigitalTwinResult> resultList = resultMapper.selectByCondition(condition, date, null);
		assertFalse(resultList.isEmpty());
		DigitalTwinResult result = resultList.get(0);
		assertEquals(0.975, result.getParamValue(), 0.00001);
	}

	@Test
	public void testSelect() {
		DigitalTwinResult condition = new DigitalTwinResult();
		condition.setTrainId(344);
		condition.setCarId(8);
		condition.setDoorId(2);
		condition.setParamId(1);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -12);
		condition.setTimestamp(calendar.getTime());
		List<DigitalTwinResult> results = resultMapper.selectByCondition(condition, null, null);
		results.forEach(System.out::println);
	}

	@Test
	public void testSelectParam() {
		List<DigitalTwinParam> params = paramMapper.selectByType("电机参数");
		params.forEach(System.out::println);

		List<DigitalTwinParam> all = paramMapper.selectAll();

		System.out.println("------------all--------------------------");
		all.forEach(System.out::println);
	}

	@Test
	public void testResults() {
		ResultQueryRequest request = new ResultQueryRequest();
		request.setCarId(8);
		request.setDoorId(2);
		request.setProject("深圳地铁7号线");
		request.setTrain("717");
		request.setStart(DateUtil.parse("2020-03-07"));
		request.setEnd(DateUtil.now());
		ResultQueryResponse response = service.getAllResults(request);
		System.out.println(response);

		ResultQueryResponse response2 = service.getResultsByType(request, "电机参数");
		assertEquals(4, response2.getResults().size());

		ResultQueryResponse response3 = service.getResultsByParamName(request, "电机电阻");
		assertEquals(1, response3.getResults().size());
	}

}
