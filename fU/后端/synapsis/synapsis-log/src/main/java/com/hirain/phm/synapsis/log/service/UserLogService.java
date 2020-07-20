/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.log.service;

import java.time.LocalDateTime;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hirain.phm.synapsis.log.IService;
import com.hirain.phm.synapsis.log.domain.UserLog;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Apr 16, 2020 7:24:27 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 16, 2020 zepei.tao@hirain.com 1.0 create file
 */
public interface UserLogService extends IService<UserLog> {

	List<UserLog> listLogs(UserLog log);

	void deleteLogs(String logIds);

	@Async
	void saveLog(ProceedingJoinPoint point, UserLog log) throws JsonProcessingException;

	void deleteLogsByTime(LocalDateTime time);
}
