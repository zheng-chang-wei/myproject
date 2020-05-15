package com.hirain.ptu.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class CommonParms implements Serializable {

  private String ip;

  private String comId;

  private String port;

  private String logicalCondition;

  private String startTime;

  private String endTime;
}
