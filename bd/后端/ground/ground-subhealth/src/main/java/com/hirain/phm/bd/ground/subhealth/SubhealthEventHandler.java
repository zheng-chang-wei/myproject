/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created May 22, 2019 5:51:58 PM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 22, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.subhealth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hirain.phm.bd.ground.common.event.NoSidEvent;
import com.hirain.phm.bd.ground.common.event.StatisticsEvent;
import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthDetail;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthAnalyseService;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthDetailService;
import com.hirain.phm.bd.ground.util.GroundAccessHelper;
import com.hirain.phm.bd.message.train.SubhealthPacket;

@Configuration
public class SubhealthEventHandler {

	@Autowired
	private SubhealthAnalyseService analyseService;

	@Autowired
	private SubhealthDetailService subhealthDetailService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@EventListener
	@Async
	public void onSubhealthEvent(NoSidEvent event) {
		if (event.getTopic().equals(GroundAccessHelper.SUBHEALTH_KAFKA_TOPIC)) {
			// linux上默认gson对象时间解析错误，故设置时间格式
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss:SSS").create();
			SubhealthPacket packet = gson.fromJson(event.getValue(), SubhealthPacket.class);
			handleSubhealth(packet);
		}
	}

	@EventListener
	@Async
	public void onSubhealthEvent(SubhealthPacket packet) {
		handleSubhealth(packet);
	}

	private void handleSubhealth(SubhealthPacket packet) {
		List<SubhealthDetail> detailList = analyseService.analyse(packet);
		detailList.forEach((detail) -> {
			UnifiedFaultRecord record = analyseService.getUnifiedFaultRecord(detail);
			publisher.publishEvent(record);
		});
	}

	@EventListener
	@Async
	public void onStatisticsEvent(StatisticsEvent event) {
		if (event.getTopCode() == 1) {
			SubhealthDetail subhealthDetail = new SubhealthDetail();
			subhealthDetail.setId(Integer.valueOf((int) event.getFaultId()));
			subhealthDetail.setStatistics(event.isStatistics());
			subhealthDetailService.updateNotNull(subhealthDetail);
		}
	}
}
