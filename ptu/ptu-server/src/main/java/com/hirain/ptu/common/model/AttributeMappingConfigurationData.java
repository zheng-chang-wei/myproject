package com.hirain.ptu.common.model;

import java.util.HashMap;
import java.util.Map;

public class AttributeMappingConfigurationData {

  public static Map<String, String> comIdMapData = new HashMap<>();
  public static Map<String, String> csPortMapData = new HashMap<>();

  static {
    comIdMapData.put("day", "day");
    comIdMapData.put("time", "time");
    comIdMapData.put("IP", "ip");
    comIdMapData.put("Comid", "comId");
    comIdMapData.put("PeriodStabilityPHM", "periodStabilityPHM");
    comIdMapData.put("LostRatePHM", "lostRatePHM");
    comIdMapData.put("AbnomalLostPHM", "abnomalLostPHM");
    comIdMapData.put("WindowTime", "windowTime");
    comIdMapData.put("PeriodMean", "periodMean");
    comIdMapData.put("PeriodStd", "periodStd");
    comIdMapData.put("LostRate", "lostRate");
    comIdMapData.put("LostMaxRate", "lostMaxRate");
    comIdMapData.put("LifeSignalStopRate", "lifeSignalStopRate");
    comIdMapData.put("LifeSignalStopMaxRate", "lifeSignalStopMaxRate");

    csPortMapData.put("day", "day");
    csPortMapData.put("time", "time");
    csPortMapData.put("IP", "ip");
    csPortMapData.put("port", "port");
    csPortMapData.put("ComID", "comId");
    csPortMapData.put("LinkPHM", "linkPHM");
    csPortMapData.put("LinkFlashPHM", "linkFlashPHM");
    csPortMapData.put("RxTrafficPHM", "rxTrafficPHM");
    csPortMapData.put("RxErrRatePHM", "rxErrRatePHM");
    csPortMapData.put("RxErrPredictPHM", "rxErrPredictPHM");
    csPortMapData.put("TxTrafficPHM", "txTrafficPHM");
    csPortMapData.put("TxErrRatePHM", "txErrRatePHM");
    csPortMapData.put("TxErrPredictPHM", "txErrPredictPHM");
    csPortMapData.put("Enable", "enable");
    csPortMapData.put("LinkMean", "linkMean");
    csPortMapData.put("RxMcast", "rxMcast");
    csPortMapData.put("RxTrafficMean", "rxTrafficMean");
    csPortMapData.put("RxTrafficStd", "rxTrafficStd");
    csPortMapData.put("RxErrRateMean", "rxErrRateMean");
    csPortMapData.put("RxErrRateStd", "rxErrRateStd");
    csPortMapData.put("TxMcast", "txMcast");
    csPortMapData.put("TxTrafficMean", "txTrafficMean");
    csPortMapData.put("TxTrafficStd", "txTrafficStd");
    csPortMapData.put("TxErrRateMean", "txErrRateMean");
    csPortMapData.put("TxErrRateStd", "txErrRateStd");
  }
}
