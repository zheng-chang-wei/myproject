package com.hirain.ptu.service.impl;

import com.hirain.ptu.common.model.TableNameConstant;
import com.hirain.ptu.common.utils.DateUtil;
import com.hirain.ptu.dao.ManageMapper;
import com.hirain.ptu.model.TargetConfig;
import com.hirain.ptu.service.ManageService;
import com.hirain.ptu.service.TargetConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author changwei.zheng
 * @date 2020/3/27 18:46
 * @describe
 */
@Service
public class ManageServiceImpl implements ManageService {

  @Value("${spring.datasource.druid.username}")
  private String databaseUserName;

  @Value("${spring.datasource.druid.password}")
  private String databasePassword;

  @Value("${spring.datasource.druid.url}")
  private String url;

  @Value("${ftp.username}")
  private String ftpUserName;

  @Value("${ftp.password}")
  private String ftpPassword;

  @Value("${ftp.targetIp}")
  private String targetIp;

  @Value("${ftp.targetPath}")
  private String targetPath;

  @Autowired private ManageMapper manageMapper;

  @Autowired private TargetConfigService targetConfigService;

  @Override
  public int createComIdDataTable(String tableName, List<String> list) {
    return manageMapper.createComIdDataTable(tableName, list);
  }

  @Override
  public int createCsPortDataTable(String tableName, List<String> list) {
    return manageMapper.createCsPortDataTable(tableName, list);
  }

  @Override
  public boolean isExistTable(String tableName) {
    return manageMapper.isExistTable(tableName) == 1;
  }

  @Override
  public String lastPartition(String tableName) {
    return manageMapper.lastPartition(tableName);
  }

  @Override
  public List<String> allPartition(String tableName) {
    return manageMapper.allPartition(tableName);
  }

  @Override
  public int addPartitions(String tableName, List<String> list) {
    return manageMapper.addPartitions(tableName, list);
  }

  @Override
  public Integer dropTable(String tableName) {
    return manageMapper.dropTable(tableName);
  }

  @Override
  public Integer dropPartition(String tableName, String partitionName) {
    return manageMapper.dropPartition(tableName, partitionName);
  }

  @Override
  public int createTable(String tableName, List<String> partitions) {
    switch (tableName) {
      case TableNameConstant.COMID_DATA_TABLE_NAME:
        return manageMapper.createComIdDataTable(tableName, partitions);
      case TableNameConstant.CSPROT_DATA_TABLE_NAME:
        return manageMapper.createCsPortDataTable(tableName, partitions);
    }
    return 0;
  }

  @Override
  public void deletePartitions(String tableName, String deadlineTime) throws ParseException {
    List<String> partitions = allPartition(tableName);
    for (String partition : partitions) {
      Date partitionDate = DateUtil.parse(partition.substring(1, partition.length()), "yyyyMMdd");
      Date deadlineDate = DateUtil.parse(deadlineTime, "yyyy-MM-dd");
      if (partitionDate.before(deadlineDate)) {
        dropPartition(tableName, partition);
      }
    }
  }

  @Override
  public void createDatabase(String databaseName) {
    Connection conn = null;
    Statement stat = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      // 一开始必须填一个已经存在的数据库
      String url = this.url.replace(databaseName, "mysql");
      //
      // "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone = GMT";
      conn = DriverManager.getConnection(url, databaseUserName, databasePassword);
      stat = conn.createStatement();
      ResultSet resultSet =
          stat.executeQuery(
              "  select count(*) from information_schema.schemata where schema_name='ptu_data'");
      resultSet.next();
      int count = resultSet.getInt(1);
      if (count == 0) {
        //     创建数据库
        stat.executeUpdate(
            "CREATE DATABASE IF NOT EXISTS "
                + databaseName
                + " default charset utf8 COLLATE utf8_general_ci; ");
        initDatabase();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // 关闭链接
      try {
        if (stat != null) {
          stat.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private void initDatabase() {
    manageMapper.createComIdDataTable("t_comid_data", partitions());
    manageMapper.createCsPortDataTable("t_csport_data", partitions());
    manageMapper.createComIdObjectTable();
    manageMapper.createConditionTable();
    manageMapper.createCsPortObjectTable();
    manageMapper.createDownloadedFileTable();
    manageMapper.createTargerConfigTable();
    TargetConfig targetConfig = new TargetConfig();
    targetConfig.setUserName(ftpUserName);
    targetConfig.setPassword(ftpPassword);
    targetConfig.setTargetIp(targetIp);
    targetConfig.setTargetPath(targetPath);
    targetConfigService.save(targetConfig);
  }
  /**
   * 获取下月1号到前70天的日期
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
}
