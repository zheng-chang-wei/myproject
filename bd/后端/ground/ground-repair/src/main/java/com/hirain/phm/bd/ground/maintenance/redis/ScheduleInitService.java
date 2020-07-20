/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.redis;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月22日 下午5:45:02
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月22日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Order(1)
@ConfigurationProperties("repair.integration")
public class ScheduleInitService implements ApplicationRunner {

	@Autowired
	private Scheduler scheduler;

	@Setter
	@Getter
	private String cronExpression;

	/**
	 * @see org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments)
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		scheduler.start();

		startIntegrateJob();
	}

	/**
	 * @throws SchedulerException
	 */
	private void startIntegrateJob() throws SchedulerException {
		JobDetail jobDetail = JobBuilder.newJob(WorksheetIntegrateJob.class).withIdentity("worksheet-integrate").build();

		CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(cronExpression);
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("worksheet-integrate").withSchedule(cronSchedule).build();
		scheduler.scheduleJob(jobDetail, trigger);
	}

}
