/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.synapsis.board.message.BoardStartupNoticeMessage;
import com.hirain.phm.synapsis.runtime.service.RuntimeService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 2:22:51 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
@Slf4j
public class SelfcheckEventHandler {

	@Autowired
	private RuntimeService service;

	/**
	 * 监听板卡自检报文
	 * 
	 * @param boardMessage
	 */
	@EventListener
	@Async
	public void selfcheck(BoardStartupNoticeMessage boardMessage) {
		log.info("handle selfcheck");
		service.init(boardMessage.getBoards());
	}
}
