package com.hirain.ptu.handler;

import com.hirain.ptu.common.model.TableNameConstant;
import com.hirain.ptu.dao.DownloadedFileMapper;
import com.hirain.ptu.model.*;
import com.hirain.ptu.service.ComIdDataService;
import com.hirain.ptu.service.CsPortDataService;
import com.hirain.ptu.service.DataOverviewService;
import com.hirain.ptu.service.ManageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author changwei.zheng
 * @date 2020/4/4 20:10
 * @describe
 */
@Service
public class DataHandler {

  @Autowired ComIdDataService comIdDataService;
  @Autowired CsPortDataService csPortDataService;

  @Autowired ManageService manageService;

  @Autowired DataOverviewService dataOverviewService;

  @Autowired DownloadedFileMapper downloadedFileMapper;

  @Transactional
  public synchronized void insertComIdData(List<ComIdData> comIdDataList, String fileName) {
    Date startDate = comIdDataList.get(0).getDate();
    Date endDate = comIdDataList.get(comIdDataList.size() - 1).getDate();
    updateDataOverTime(startDate, endDate, TableNameConstant.COMID_TYPE);
    insertComIdData(comIdDataList);
    downloadedFileMapper.insert(new DownloadedFile(fileName));
  }

  @Transactional
  public synchronized void insertCsPortData(List<CsPortData> csPortDataList, String fileName) {
    Date startDate = csPortDataList.get(0).getDate();
    Date endDate = csPortDataList.get(csPortDataList.size() - 1).getDate();
    updateDataOverTime(startDate, endDate, TableNameConstant.CSPORT_TYPE);
    insertCsPortData(csPortDataList);
    downloadedFileMapper.insert(new DownloadedFile(fileName));
  }

  private void updateDataOverTime(Date startDate, Date endDate, String type) {
    DataOverview dataOverview = dataOverviewService.selectByType(type);
    if (dataOverview == null) {
      dataOverviewService.save(new DataOverview(type, startDate, endDate));
    } else {
      if (startDate.before(dataOverview.getStartTime())) {
        dataOverview.setStartTime(startDate);
      }
      if (endDate.after(dataOverview.getEndTime())) {
        dataOverview.setEndTime(endDate);
      }
      dataOverviewService.updateNotNull(dataOverview);
    }
  }

  private void insertComIdData(List<ComIdData> comIdDatas) {

    Date date = comIdDatas.get(comIdDatas.size() - 1).getDate();
    // 查看分区是否够用，不够用添加分区
    addPartition(TableNameConstant.COMID_DATA_TABLE_NAME, date);
    comIdDataService.insertComIdData(comIdDatas);
  }

  private void insertCsPortData(List<CsPortData> csPortDatas) {
    Date date = csPortDatas.get(csPortDatas.size() - 1).getDate();
    // 查看分区是否够用，不够用添加分区
    addPartition(TableNameConstant.CSPROT_DATA_TABLE_NAME, date);
    csPortDataService.insertCsPortData(csPortDatas);
  }

  /**
   * Date转LocalDateTime
   *
   * @param date
   * @return
   */
  private LocalDateTime date2LocalDateTime(Date date) {
    Instant instant = date.toInstant();
    ZoneId zoneId = ZoneId.systemDefault();
    LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
    return localDateTime;
  }

  /**
   * 获取下月的今天到前70天的日期
   *
   * @return
   */
  private List<String> partitions(LocalDateTime date) {
    final List<String> partitions = new ArrayList<>();
    LocalDateTime nextMonth = date.plusMonths(1);
    for (int i = 60; i >= 0; i--) {
      LocalDateTime dateTime = nextMonth.plusDays(-i);
      final String time = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
      partitions.add(time);
    }
    return partitions;
  }

  private void addPartition(String tableName, Date date) {
    String partition = manageService.lastPartition(tableName);
    // 插入数据的最大时间
    LocalDateTime maxInsertDate = date2LocalDateTime(date);
    if (StringUtils.isNotEmpty(partition)) {
      partition = partition.substring(1);
      // 最后一个分区时间
      final LocalDateTime lastPartitionDate =
          LocalDateTime.parse(partition + "000000", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

      final Duration duration = Duration.between(lastPartitionDate, maxInsertDate);
      final long days = duration.toDays();
      if (days >= 0) {
        manageService.addPartitions(tableName, partitions(lastPartitionDate, days));
      }
    } else {
      manageService.createTable(tableName, partitions(maxInsertDate));
    }
  }

  private List<String> partitions(LocalDateTime lastPartitionDate, long days) {
    final List<String> partitions = new ArrayList<>();
    for (int i = 0; i <= days + 31; i++) {
      LocalDateTime dateTime = lastPartitionDate.plusDays(i + 1);
      final String time = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
      partitions.add(time);
    }
    return partitions;
  }
}
