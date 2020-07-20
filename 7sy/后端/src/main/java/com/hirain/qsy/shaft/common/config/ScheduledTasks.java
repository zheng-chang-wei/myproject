package com.hirain.qsy.shaft.common.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.service.LogService;
import com.hirain.qsy.shaft.service.PullDataService;

@Component
public class ScheduledTasks {

    @Autowired
    LogService logService;

    @Autowired
    PullDataService pullDataService;

    // 每月1号12点执行一次
    @Scheduled(cron = "0 0 12 1 * ?")
    public void reportCurrentByCron() {
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.MONTH, -3);
        logService.deleteLogsByTime(rightNow.getTime());
    }

    // 每2小时执行一次
    @Scheduled(cron = "0 0 */2 * * ?")
//    @Scheduled(cron = "0 */1 * * * ?")
    public void pullData() throws Exception {
        DataRequest dataRequest = new DataRequest();
        // 今天
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDayAgo = now.plusDays(-2);
        dataRequest.setStartTime(oneDayAgo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        dataRequest.setEndTime(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        pullDataService.handleHistoryData(dataRequest);
    }

}
