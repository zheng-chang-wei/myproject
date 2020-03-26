/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.redis;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 15, 2019 3:49:38 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 15, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class WorksheetIntegrateJob implements Job {

	@Autowired
	private WorksheetRedisMapper redisMapper;

	/**
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		redisMapper.integrateSheets();
	}

}
