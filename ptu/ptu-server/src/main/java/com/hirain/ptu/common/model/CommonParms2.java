package com.hirain.ptu.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommonParms2 {

  private String ip;
  private String comId;
  private String port;

  private List<String> features;

  private String logicalCondition;

  private String time;
}
