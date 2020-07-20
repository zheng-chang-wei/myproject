/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.handle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.synapsis.update.domain.BoardUpdateSchedule;
import com.hirain.phm.synapsis.update.domain.Schedule;
import com.hirain.phm.synapsis.update.message.VersionActiveResponseMessage;
import com.hirain.phm.synapsis.update.message.VersionDownloadResponseMessage;
import com.hirain.phm.synapsis.update.message.VersionUpdateResponseMessage;
import com.hirain.phm.synapsis.update.service.VersionService;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 10, 2020 11:16:08 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 10, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Configuration
public class UpdateEventHandler {

	@Autowired
	private VersionService versionService;

	/**
	 * 收到CPU上报的版本更新结果
	 */
	@EventListener
	@Async
	public void handleUpdateResponse(VersionUpdateResponseMessage message) {
		versionService.clearSchedules();
	}

	/**
	 * 收到CPU上报的单块板卡的更新包下载结果
	 * 
	 * @param message
	 */
	@EventListener
	@Async
	public void handleDownloadResponse(VersionDownloadResponseMessage message) {
		BoardUpdateSchedule schedule = new BoardUpdateSchedule();
		schedule.setSlotID(message.getSlotId());
		schedule.setSchedule(message.isSuccess() ? Schedule.DOING : Schedule.FAULT);
		versionService.updateBoardUpdateSchedule(schedule);
	}

	/**
	 * 收到CPU上报的单块板卡的更新包更新结果
	 * 
	 * @param message
	 */
	@EventListener
	@Async
	public void handleActiveResponse(VersionActiveResponseMessage message) {
		BoardUpdateSchedule schedule = new BoardUpdateSchedule();
		schedule.setSlotID(message.getSlotId());
		schedule.setSchedule(message.isSuccess() ? Schedule.DONE : Schedule.FAULT);
		versionService.updateBoardUpdateSchedule(schedule);
	}

}
