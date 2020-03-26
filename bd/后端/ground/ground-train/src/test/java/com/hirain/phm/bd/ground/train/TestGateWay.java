/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.train.controller.TrainGateWay;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.train.domain.Train;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年11月11日 下午5:26:28
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年11月11日 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TrainTestApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TestGateWay {

	@Autowired
	private TrainGateWay gw;

	@Test
	public void findTrainByUserId() {
		List<Train> trains = gw.findTrainByUserId(1l);
		assertNotNull(trains);
		assertFalse(trains.isEmpty());
		assertEquals(1, trains.get(0).getProjectId().intValue());
	}

	@Test
	public void selectTrainsByCityAndLine() {
		List<Train> trains = gw.selectTrainsByCityAndLine("深圳", "7");
		assertNotNull(trains);
		assertFalse(trains.isEmpty());
		assertEquals("717", trains.get(0).getTrainNo());
	}

	@Test
	public void selectTrain() {
		Train train = gw.selectTrain("深圳地铁7号线", "717");
		assertNotNull(train);
		assertEquals("717", train.getTrainNo());
		assertEquals(1, train.getProjectId().intValue());
	}

	@Test
	public void selectProjectByName() {
		Project project = gw.selectProjectByName("深圳地铁7号线");
		assertNotNull(project);
		assertEquals("深圳地铁7号线", project.getName());
	}
}
