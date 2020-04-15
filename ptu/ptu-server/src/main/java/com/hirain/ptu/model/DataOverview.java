package com.hirain.ptu.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author changwei.zheng
 * @date 2020/4/7 10:53
 * @describe 数据概况
 */
@Data
@Table(name = "t_data_overview")
public class DataOverview {
  @Id
  @GeneratedValue(generator = "JDBC")
  private Integer id;

  private String type;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date startTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date endTime;

  public DataOverview() {}

  public DataOverview(String type, Date startTime, Date endTime) {
    this.type = type;
    this.startTime = startTime;
    this.endTime = endTime;
  }
}
