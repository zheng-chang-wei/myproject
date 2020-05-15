package com.hirain.ptu.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CsPortDataOverview implements Serializable {
  private static final long serialVersionUID = 1L;

  private String ip;
  private Integer comId;
  private Integer port;

  private Boolean linkPHM;

  private Boolean linkFlashPHM;

  private Boolean rxTrafficPHM;

  private Boolean rxErrRatePHM;

  private Boolean rxErrPredictPHM;

  private Boolean txTrafficPHM;

  private Boolean txErrRatePHM;

  private Boolean txErrPredictPHM;

  private Boolean enable;

  private Float frameCnt;
}
