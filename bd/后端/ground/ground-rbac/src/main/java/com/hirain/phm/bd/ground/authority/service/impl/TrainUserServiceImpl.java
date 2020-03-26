package com.hirain.phm.bd.ground.authority.service.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.authority.service.ProjectUserService;
import com.hirain.phm.bd.ground.authority.service.TrainUserService;
import com.hirain.phm.bd.ground.common.event.TrainOnlineEvent;
import com.hirain.phm.bd.ground.kafka.IKafkaProducer;
import com.hirain.phm.bd.ground.util.GroundAccessHelper;
import com.hirain.phm.bd.message.ground.TrainUser;
import com.hirain.phm.bd.message.ground.UserUpdateMessage;

@Service
public class TrainUserServiceImpl implements TrainUserService {

	@Autowired
	private ProjectUserService projectUserService;

	@Autowired
	private IKafkaProducer producer;

	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();;

	@Override
	public void updateTrainUser(TrainOnlineEvent event) {
		List<User> projectUsers = projectUserService.getUsersOfProject(event.getProject());
		List<TrainUser> users = new ArrayList<>();
		if (projectUsers != null) {
			projectUsers.forEach(u -> {
				TrainUser user = new TrainUser();
				user.setUsername(u.getUserName());
				user.setPassword(u.getPassword());
				users.add(user);
			});
		}
		UserUpdateMessage message = new UserUpdateMessage();
		message.setCity(event.getCity());
		message.setLine(event.getLine());
		message.setTrain(event.getTrain());
		message.setUsers(users);
		message.setSid(GroundAccessHelper.GT_USERUPADTE_SID);
		executor.schedule(() -> {
			String json = JsonUtil.toString(message);
			String key = "response/" + message.getCity() + "/" + message.getLine() + "/" + message.getTrain();
			producer.send(GroundAccessHelper.GT_KAFKA_TOPIC, key, json.getBytes(Charset.forName("utf-8")));
		}, 60, TimeUnit.SECONDS);
	}
}
