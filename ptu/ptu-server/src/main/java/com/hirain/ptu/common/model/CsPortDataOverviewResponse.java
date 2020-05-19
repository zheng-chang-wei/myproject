package com.hirain.ptu.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hirain.ptu.model.ComIdData;
import com.hirain.ptu.model.ComIdObject;
import com.hirain.ptu.model.CsPortData;
import com.hirain.ptu.model.CsPortObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CsPortDataOverviewResponse implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date date;

  private String ip;

  private Integer comId;

  private Integer port;

  /** 网口断开 */
  private Byte linkPHM;
  /** 网口闪断 */
  private Byte linkFlashPHM;
  /** 流量异常-收 */
  private Byte rxTrafficPHM;
  /** 误码异常-收 */
  private Byte rxErrRatePHM;
  /** 误码趋势异常-收 */
  private Byte rxErrPredictPHM;
  /** 流量异常-发 */
  private Byte txTrafficPHM;
  /** 误码异常-预留 */
  private Byte txErrRatePHM;
  /** 误码趋势异常-预留 */
  private Byte txErrPredictPHM;
  /** 使能状态 */
  private Byte enable;

  private Integer trainNo;

  private Integer cardNo;

  private Boolean error = false;

  public void setCsPortData(CsPortDataOverview csPortData) {
    this.ip = csPortData.getIp();
    this.comId = csPortData.getComId();
    this.port = csPortData.getPort();
    this.linkPHM = csPortData.getLinkPHM() ? (byte) 1 : (byte) 0;
    this.linkFlashPHM = csPortData.getLinkFlashPHM() ? (byte) 1 : (byte) 0;
    this.rxTrafficPHM = csPortData.getRxTrafficPHM() ? (byte) 1 : (byte) 0;
    this.rxErrRatePHM = csPortData.getRxErrRatePHM() ? (byte) 1 : (byte) 0;
    this.rxErrPredictPHM = csPortData.getRxErrPredictPHM() ? (byte) 1 : (byte) 0;
    this.txTrafficPHM = csPortData.getTxTrafficPHM() ? (byte) 1 : (byte) 0;
    this.txErrRatePHM = csPortData.getTxErrRatePHM() ? (byte) 1 : (byte) 0;
    this.txErrPredictPHM = csPortData.getTxErrPredictPHM() ? (byte) 1 : (byte) 0;
    this.enable = csPortData.getEnable() ? (byte) 1 : (byte) 0;
    this.error =
        (this.enable == 1
            && (this.linkPHM
                    + this.linkFlashPHM
                    + this.rxTrafficPHM
                    + this.rxErrRatePHM
                    + this.rxErrPredictPHM
                    + this.txTrafficPHM
                    + this.txErrRatePHM
                    + this.txErrPredictPHM)
                >= 1);
  }

  public void setCsPortObject(CsPortObject csPortObject) {
    this.trainNo = csPortObject.getTrainNo();
    this.cardNo = csPortObject.getCardNo();
  }
}
