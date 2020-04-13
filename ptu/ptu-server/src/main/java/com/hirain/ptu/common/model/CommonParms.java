package com.hirain.ptu.common.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonParms {

  private String tableName;

  private List<String> features;

  private String logicalCondition;

  private String time;
}
