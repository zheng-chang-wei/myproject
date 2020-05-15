package com.hirain.ptu.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hirain.ptu.model.ComIdObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ComIdDataOverviewTableData implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date date;

  private String ip;

  private Integer comId;

  private Byte periodStabilityPHM;

  private Byte lostRatePHM;

  private Byte abnomalLostPHM;

  private Float frameCnt;

  /** 报文长度 */
  private Integer packetLength;
  /** 车厢位置 */
  private Integer carriagePosition;
  /** 备注 */
  private String remark1;

  public void setComIdData(ComIdDataOverview comIdData) {
    this.ip = comIdData.getIp();
    this.comId = comIdData.getComId();
    this.periodStabilityPHM = comIdData.getPeriodStabilityPHM() ? (byte) 1 : (byte) 0;
    this.lostRatePHM = comIdData.getLostRatePHM() ? (byte) 1 : (byte) 0;
    this.abnomalLostPHM = comIdData.getAbnomalLostPHM() ? (byte) 1 : (byte) 0;
    this.frameCnt = Float.valueOf(String.format("%.2f", comIdData.getFrameCnt()));
  }

  public void setComIdObject(ComIdObject comIdObject) {
    this.carriagePosition = comIdObject.getCarriagePosition();
    this.remark1 = comIdObject.getRemark1();
    this.packetLength=comIdObject.getPacketLength();
  }
}
