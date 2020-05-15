package com.hirain.ptu.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据库查询结果
 */
@Data
public class ComIdDataOverview implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;
  private String ip;

  private Integer comId;

  private Boolean periodStabilityPHM;

  private Boolean lostRatePHM;

  private Boolean abnomalLostPHM;

  private Float frameCnt;


}
