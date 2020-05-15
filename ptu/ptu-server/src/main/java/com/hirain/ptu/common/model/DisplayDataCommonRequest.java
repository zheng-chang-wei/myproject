package com.hirain.ptu.common.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/** 数据展示界面接收前端数据通用类 */
@Data
public class DisplayDataCommonRequest implements Serializable {

  private List<String> comIds;

  private List<String> ips;

  private List<String> ports;

  private List<String> features;

  private String logicalCondition;

  private String startTime;

  private String endTime;
}
