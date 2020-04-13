package com.hirain.ptu.handler;

import com.hirain.ptu.model.ComIdData;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class BatchDataHandler {

  public String batDataAdd(Map<?, ?> map) {
    List<ComIdData> comIdDatas = (List<ComIdData>) map.get("list");
    String tableName = (String) map.get("tableName");
    StringBuilder sb = new StringBuilder();
    sb.append("insert into ")
        .append(tableName)
        .append(
            "(date, period_stability_p_h_m,lost_rate_p_h_m,abnomal_lost_p_h_m,window_time,period_mean,period_std,lost_rate,lost_max_rate,life_signal_stop_rate,life_signal_stop_max_rate) values ");
    MessageFormat mf =
        new MessageFormat(
            "(#'{'list[{0}].date},#'{'list[{0}].periodStabilityPHM},,#'{'list[{0}].lostRatePHM},,#'{'list[{0}].abnomalLostPHM},,#'{'list[{0}].windowTime},,#'{'list[{0}].periodMean},,#'{'list[{0}].periodStd},,#'{'list[{0}].lostRate},,#'{'list[{0}].lostMaxRate},,#'{'list[{0}].lifeSignalStopRate},,#'{'list[{0}].lifeSignalStopMaxRate})");
    for (int i = 0; i < comIdDatas.size(); i++) {
      sb.append(mf.format(new Object[] {i}));
      if (i < comIdDatas.size() - 1) {
        sb.append(",");
      }
    }
    return sb.toString();
  }
}
