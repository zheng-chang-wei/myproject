package com.hirain.phm.bd.ground.train;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.ground.train.service.ProjectService;
import com.hirain.phm.bd.ground.train.service.TrainService;
import com.hirain.phm.bd.message.train.RegisterMessage;

@SpringBootTest(classes = TrainTestApplication.class)
@RunWith(SpringRunner.class)
public class TrainRegisterTest {

	@Autowired
	private IRegisterHandler handler;

	@Autowired
	private TrainService trainService;

	@Autowired
	private ProjectService projectService;

	@Test
	public void test() {
		RegisterMessage message = new RegisterMessage();
		message.setProject("广州地铁1号线");
		message.setCity("广州");
		message.setLine("1");
		message.setTrain("12");
		message.setMac1("60-14-B3-7D-C1-B5");
		message.setMac2("60-14-B3-7D-C1-B5");
		message.setState(1);
		message.setSsl(false);

		handler.handle(message);

		Project project = projectService.selectByName(message.getProject());
		assertNotNull(project);
		assertEquals(message.getCity(), project.getCity());
		assertEquals(message.getLine(), project.getLine());

		Train train = trainService.select(message.getProject(), message.getTrain());
		assertEquals(project.getId(), train.getProjectId());
	}

	@Test
	public void testProjectQuery() {
		Project project = projectService.selectByName("深圳地铁7号线");
		assertEquals("深圳地铁7号线", project.getName());
	}
}
