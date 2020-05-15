package com.hirain.ptu.service.impl;

import com.hirain.BaseTest;
import com.hirain.ptu.Application;
import com.hirain.ptu.service.ManageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author changwei.zheng
 * @date 2020/4/3 17:24
 * @describe
 */
public class ManageServiceImplTest extends BaseTest {
  @Autowired ManageService manageService;

  @Test
  public void createComIdDataTable() {
    manageService.createComIdDataTable("t_comid_data", partitions());
  }

  @Test
  public void createCsPortDataTable() {
    manageService.createCsPortDataTable("t_csport_data", partitions());
  }

  @Test
  public void isExistTable() {
    boolean t_target_config = manageService.isExistTable("t_target");
    System.out.println(t_target_config);
  }

  @Test
  public void lastPartition() {
    String t_comid_data = manageService.lastPartition("t_comid_data");
    String t_csport_data = manageService.lastPartition("t_csport_data");
    System.out.println(t_comid_data);
    System.out.println(t_csport_data);
  }

  @Test
  public void addPartitions() {}

  @Test
  public void dropTable() {}

  @Test
  public void testPartitions() {
    List<String> partitions = partitions();
    System.out.println(partitions);
  }

  @Test
  public void testPartitions2() {
    List<String> partitions = partitions(LocalDateTime.now(), 10);
    System.out.println(partitions);
  }

  private List<String> partitions(LocalDateTime lastPartitionDate, long days) {
    final List<String> partitions = new ArrayList<>();
    for (int i = 0; i <= days + 31; i++) {
      // 下月第一天
      LocalDateTime dateTime = lastPartitionDate.plusDays(i + 1);
      final String time = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
      partitions.add(time);
    }
    return partitions;
  }
  /**
   * 获取下月1号到前24个月1号的日期
   *
   * @return
   */
  private List<String> partitions() {
    final List<String> partitions = new ArrayList<>();
    final LocalDateTime date = LocalDateTime.now();
    LocalDateTime nextMonth = date.plusMonths(1);
    for (int i = 70; i >= 0; i--) {
      LocalDateTime firstDayOfNextMouth = nextMonth.plusDays(-i);
      final String time = firstDayOfNextMouth.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
      partitions.add(time);
    }
    return partitions;
  }

  @Test
  public void allPartition() {
    System.out.println(manageService.allPartition("t_comid_data"));
    dropPartition();
    System.out.println(manageService.allPartition("t_comid_data"));
  }

  @Test
  public void dropPartition() {
    manageService.dropPartition("t_comid_data", "p20200226");
  }

  @Test
  public void createDatabase() {
    manageService.createDatabase("ptu_data");
  }
}
