package com.hirain.phm.bd.ground.train.dm;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.train.TrainTestApplication;
import com.hirain.phm.bd.ground.train.param.TrainDMResponse;
import com.hirain.phm.bd.ground.train.param.TrainParamHeader;
import com.hirain.phm.bd.ground.train.service.TrainStorageInfoService;
import com.hirain.phm.bd.message.train.StorageMessage;

@SpringBootTest(classes = TrainTestApplication.class)
@RunWith(SpringRunner.class)
public class TrainDmTest {

	@Autowired
	private TrainStorageInfoService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Test
	public void testSelect() {
		TrainParamHeader header = new TrainParamHeader();
		// header.setCityName("上海");
		// header.setLineName("1");
		// header.setTrainNo("1236");
		List<TrainDMResponse> list = service.findByTrainParam(header);
		list.forEach(System.out::println);
	}

	@Test
	public void testUpdateStorage() {
		StorageMessage msg = createMessage();
		service.handleStorageMessage(msg);

		TrainParamHeader header = new TrainParamHeader();
		header.setProject("上海1号线");
		header.setTrainNo("1236");
		List<TrainDMResponse> list = service.findByTrainParam(header);
		list.forEach(System.out::println);
	}

	private StorageMessage createMessage() {
		StorageMessage msg = new StorageMessage();
		msg.setTotal(120747);
		msg.setUsed(728.58);
		msg.setFaultSpace(0.72);
		msg.setStartDate("2019-07-16");
		msg.setEndDate("2019-09-10");
		msg.setCity("上海");
		msg.setLine("1");
		msg.setTrain("1236");
		msg.setSid(0x02);
		return msg;
	}

	@Test
	public void testEvent() {
		StorageMessage message = createMessage();
		publisher.publishEvent(message);

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		TrainParamHeader header = new TrainParamHeader();
		header.setProject("上海1号线");
		header.setTrainNo("1236");
		List<TrainDMResponse> list = service.findByTrainParam(header);
		list.forEach(System.out::println);
	}
}
