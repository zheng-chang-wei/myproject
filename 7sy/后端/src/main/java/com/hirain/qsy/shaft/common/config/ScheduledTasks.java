package com.hirain.qsy.shaft.common.config;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hirain.qsy.shaft.service.LogService;

@Component
public class ScheduledTasks {

	@Autowired
	LogService logService;

	// 每月1号12点执行一次
	@Scheduled(cron = "0 0 12 1 * ?")
	public void reportCurrentByCron() {
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.MONTH, -3);
		logService.deleteLogsByTime(rightNow.getTime());
	}
}
