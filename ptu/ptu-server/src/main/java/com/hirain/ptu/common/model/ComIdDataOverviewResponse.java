package com.hirain.ptu.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ComIdDataOverviewResponse implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  private Integer allComIdObjectSize;
  private Integer hasDataComIdObjectSize = 0;
  private Integer errorCount = 0;
  private Float groupFlow1;
  private Float groupFlow2;
  private List<ComIdDataOverviewTableData> comIdDataOverviewTableDataList;

  public Integer getHasDataComIdObjectSize() {
    Integer hasDataComIdObjectSize = 0;
    for (ComIdDataOverviewTableData comIdDataOverviewTableData : comIdDataOverviewTableDataList) {
      if (comIdDataOverviewTableData.getFrameCnt() != 0) {
        hasDataComIdObjectSize++;
      }
    }
    return hasDataComIdObjectSize;
  }

  public void setComIdDataOverviewTableDataList(
      List<ComIdDataOverviewTableData> comIdDataOverviewTableDataList) {
    this.comIdDataOverviewTableDataList = comIdDataOverviewTableDataList;
    float groupFlow1 = 0f;
    float groupFlow2 = 0f;
    for (ComIdDataOverviewTableData comIdDataOverviewTableData : comIdDataOverviewTableDataList) {
      if (comIdDataOverviewTableData.getAbnomalLostPHM()
              + comIdDataOverviewTableData.getLostRatePHM()
              + comIdDataOverviewTableData.getPeriodStabilityPHM()
          >= 1&&comIdDataOverviewTableData.getFrameCnt()>0) {
        errorCount++;
      }
      if (comIdDataOverviewTableData.getIp().startsWith("10.0")) {
        groupFlow1 +=
            (comIdDataOverviewTableData.getFrameCnt()
                    * (comIdDataOverviewTableData.getPacketLength() + 82))
                / 1024;
      } else if (comIdDataOverviewTableData.getIp().startsWith("172.16")) {
        groupFlow2 +=
            (comIdDataOverviewTableData.getFrameCnt()
                    * (comIdDataOverviewTableData.getPacketLength() + 82))
                / 1024;
      }
    }
    setGroupFlow1(Float.valueOf(String.format("%.2f", groupFlow1 / getHasDataComIdObjectSize())));
    setGroupFlow2(Float.valueOf(String.format("%.2f", groupFlow2 / getHasDataComIdObjectSize())));
  }
}
