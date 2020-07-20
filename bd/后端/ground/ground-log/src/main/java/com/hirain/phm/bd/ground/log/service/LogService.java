package com.hirain.phm.bd.ground.log.service;

import java.util.Date;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.log.domain.SysLog;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年9月20日 下午5:13:44
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年9月20日 changwei.zheng@hirain.com 1.0 create file
 */
public interface LogService extends IService<SysLog> {

	List<SysLog> listLogs(SysLog log);

	void deleteLogs(String logIds);

	@Async
	void saveLog(ProceedingJoinPoint point, SysLog log) throws JsonProcessingException;

	void deleteLogsByTime(Date time);
}
