package com.hirain.phm.bd.ground.maintenance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.bd.ground.common.event.StatisticsEvent;
import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.maintenance.domain.ExternalFault;
import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;
import com.hirain.phm.bd.ground.maintenance.service.ExternalFaultService;
import com.hirain.phm.bd.ground.maintenance.service.FlowService;
import com.hirain.phm.bd.ground.push.service.PushFaultService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RepairEventHandler {

	@Autowired
	private ExternalFaultService externalFaultService;

	@Autowired
	private FlowService flowService;

	@Autowired
	private List<PushFaultService> pushList;

	@EventListener
	@Async
	public void onStatistics(StatisticsEvent event) {
		if (event.getTopCode() == 3) {
			ExternalFault fault = new ExternalFault();
			fault.setId(event.getFaultId());
			fault.setStatistics(event.isStatistics());
			externalFaultService.updateNotNull(fault);
		}
	}

	@EventListener
	@Async
	public void onUnifiedFault(UnifiedFaultRecord record) {
		log.info(record.toString());
		WorkSheet sheet = flowService.createWorksheet(record);
		pushList.forEach(service -> {
			service.push(record, sheet.getId());
		});
	}
}
