package com.hirain.ptu.common.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	// 每月1号12点执行一次
	@Scheduled(cron = "0 0 12 1 * ?")
	public void reportCurrentByCron() {
	}
}
