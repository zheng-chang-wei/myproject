package com.hirain.dome;

import com.hirain.ptu.Application;
import com.hirain.ptu.handler.CsvDataHandler;
import com.hirain.ptu.service.ManageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ReadCSVServiceTest {

  @Autowired CsvDataHandler csvHandler;

  @Autowired ManageService manageService;

  @Test
  public void createCsPortDataTable() {
    manageService.createCsPortDataTable("t_csport_data", partitions());
    manageService.createComIdDataTable("t_comid_data", partitions());
  }

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

  @Test
  public void testInsertComId() throws Exception {
    String path = "E:\\01项目相关\\PTU\\英可测试数据--给长伟\\ComID20200217092432_00000111.csv";
    FileInputStream fileInputStream = new FileInputStream(path);
    csvHandler.readComIdCSV(fileInputStream, "ComID20200217092432_00000111");
  }

  @Test
  public void testInsertCsPort() throws Exception {
    String path = "E:\\01项目相关\\PTU\\英可测试数据--给长伟\\CSport20200217090619_11001000.csv";
    try {
      long currentTimeMillis = System.currentTimeMillis();
      FileInputStream fileInputStream = new FileInputStream(path);
      csvHandler.readCsPortCSV(fileInputStream, "CSport20200217090619_11001000");
      System.out.println(System.currentTimeMillis() - currentTimeMillis);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
