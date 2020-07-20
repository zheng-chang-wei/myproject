/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.online;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.ground.train.service.TrainService;

import lombok.Getter;
import lombok.Setter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 16, 2020 6:19:55 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 16, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@ConfigurationProperties("ground.train")
@EnableScheduling
public class HeartHandlerImpl implements HeartHandler {

	private Map<String, Token> cache = new ConcurrentHashMap<>();

	@Autowired
	private TrainService trainService;

	@Getter
	@Setter
	private String heartCron;

	@Getter
	@Setter
	private int maxBreakCount;

	/**
	 * @see com.hirain.phm.bd.ground.train.online.HeartHandler#heart(java.lang.String, java.lang.String)
	 */
	@Override
	public void heart(String project, String train) {
		Token token = cache.get(project + "_" + train);
		if (token == null) {
			Train trainObject = trainService.select(project, train);
			token = new Token();
			token.trainId = trainObject.getId();
			token.project = project;
			token.train = train;
			if (!trainObject.getTrainOnline()) {
				trainObject.setTrainOnline(true);
				trainService.updateNotNull(trainObject);
				System.err.println(project + " " + train + " online");
			}
			cache.put(project + "_" + train, token);
			token.heart = new AtomicBoolean();
			token.breakCount = new AtomicInteger();
		}
		token.breakCount.set(0);
		token.heart.set(true);
	}

	@Scheduled(cron = "${ground.train.heart-cron}")
	public void checkOnline() {
		System.err.println("check online");
		for (Token token : cache.values()) {
			if (!token.heart.get()) {
				int breakCount = token.breakCount.incrementAndGet();
				if (breakCount >= maxBreakCount) {
					Train train = new Train();
					train.setId(token.trainId);
					train.setTrainOnline(false);
					trainService.updateNotNull(train);
					cache.remove(token.project + "_" + token.train);
					System.err.println(token.project + " " + token.train + " offline");
				}
				System.err.println(token.project + " " + token.train + " break " + breakCount);
			}
			token.heart.set(false);
		}
	}

	class Token {

		Integer trainId;

		String project;

		String train;

		AtomicBoolean heart;

		AtomicInteger breakCount;

	}
}
