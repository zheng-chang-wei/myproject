package com.hirain.ptu.handler;

import com.hirain.ptu.dao.DownloadedFileMapper;
import com.hirain.ptu.model.ComIdData;
import com.hirain.ptu.model.CsPortData;
import com.hirain.ptu.model.DataOverview;
import com.hirain.ptu.model.DownloadedFile;
import com.hirain.ptu.service.ComIdDataService;
import com.hirain.ptu.service.CsPortDataService;
import com.hirain.ptu.service.DataOverviewService;
import com.hirain.ptu.service.ManageService;
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
  public synchronized void insertComIdData(
      List<DataOverview> dataOverviews, List<ComIdData> comIdDataList, String fileName) {
    updateDataOverTime(dataOverviews);
    insertComIdData(comIdDataList);
    downloadedFileMapper.insert(new DownloadedFile(fileName));
  }

  @Transactional
  public synchronized void insertCsPortData(
      List<DataOverview> dataOverviews, List<CsPortData> csPortDataList, String fileName) {
    updateDataOverTime(dataOverviews);
    insertCsPortData(csPortDataList);
    downloadedFileMapper.insert(new DownloadedFile(fileName));
  }

  private void updateDataOverTime(List<DataOverview> dataOverviews) {
    List<DataOverview> dataOverviewSelecteds = dataOverviewService.selectAll();
    for (DataOverview dataOverview : dataOverviews) {
      DataOverview sameDataOverview = getSameDataOverview(dataOverview, dataOverviewSelecteds);
      if (sameDataOverview == null) {
        dataOverviewService.save(dataOverview);
      } else if (!sameDataOverview.getStartTime().equals(dataOverview.getStartTime())
          && !sameDataOverview.getEndTime().equals(dataOverview.getEndTime())) {
        dataOverviewService.updateNotNull(sameDataOverview);
      }
    }
  }

  private void insertComIdData(List<ComIdData> comIdDatas) {

    Date date = comIdDatas.get(comIdDatas.size() - 1).getDate();
    // 查看分区是否够用，不够用添加分区
    addPartition("t_comid_data", date);
    comIdDataService.insertComIdData("t_comid_data", comIdDatas);
  }

  private void insertCsPortData(List<CsPortData> csPortDatas) {
    Date date = csPortDatas.get(csPortDatas.size() - 1).getDate();
    // 查看分区是否够用，不够用添加分区
    addPartition("t_csport_data", date);
    csPortDataService.insertCsPortData("t_csport_data", csPortDatas);
  }

  private DataOverview getSameDataOverview(
      DataOverview dataOverview, List<DataOverview> dataOverviewSelecteds) {
    for (DataOverview dataOverviewSelected : dataOverviewSelecteds) {
      if (dataOverviewSelected.getComId().equals(dataOverview.getComId())
          && dataOverviewSelected.getIp().equals(dataOverview.getIp())
          && dataOverviewSelected.getPort().equals(dataOverview.getPort())) {
        if (dataOverview.getStartTime().before(dataOverviewSelected.getStartTime())) {
          dataOverviewSelected.setStartTime(dataOverview.getStartTime());
        }
        if (dataOverviewSelected.getEndTime().before(dataOverview.getEndTime())) {
          dataOverviewSelected.setEndTime(dataOverview.getEndTime());
        }
        return dataOverviewSelected;
      }
    }
    return null;
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
  private List<String> partitions() {
    final List<String> partitions = new ArrayList<>();
    final LocalDateTime date = LocalDateTime.now();
    LocalDateTime nextMonth = date.plusMonths(1);
    for (int i = 70; i >= 0; i--) {
      LocalDateTime dateTime = nextMonth.plusDays(-i);
      final String time = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
      partitions.add(time);
    }
    return partitions;
  }

  private void addPartition(String tableName, Date date) {
    String partition = manageService.lastPartition(tableName);
    if (partition.startsWith("p")) {
      partition = partition.substring(1);
    }
    // 最后一个分区时间
    final LocalDateTime lastPartitionDate =
        LocalDateTime.parse(partition + "000000", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    // 插入数据的最大时间
    LocalDateTime maxInsertDate = date2LocalDateTime(date);
    final Duration duration = Duration.between(lastPartitionDate, maxInsertDate);
    final long days = duration.toDays();
    if (days >= 0) {
      manageService.addPartitions(tableName, partitions(lastPartitionDate, days));
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
