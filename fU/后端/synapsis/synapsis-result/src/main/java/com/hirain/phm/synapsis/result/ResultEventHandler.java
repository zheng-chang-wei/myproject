/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.synapsis.result.message.AlgorithmResultMessage;
import com.hirain.phm.synapsis.result.service.ResultService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 20, 2020 9:54:27 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 20, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class ResultEventHandler {

	@Autowired
	private ResultService resultService;

	@EventListener
	@Async
	public void on(AlgorithmResultMessage resultMessage) {
		resultService.parseAndSave(resultMessage);
	}
}
