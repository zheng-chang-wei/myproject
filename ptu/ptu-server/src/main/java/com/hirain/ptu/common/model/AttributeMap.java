package com.hirain.ptu.common.model;

import java.util.HashMap;
import java.util.Map;

public class AttributeMap {

  public static Map<String, String> comIdMapData = new HashMap<>();
  public static Map<String, String> csPortMapData = new HashMap<>();
  public static Map<String, String> featuresMap = new HashMap<>();

  static {
    // comId对象配置文件
    comIdMapData.put("ComID", "comId");
    comIdMapData.put("源IP地址", "ip");
    comIdMapData.put("组播地址", "multicastAddress");
    comIdMapData.put("周期（us）", "cycle");
    comIdMapData.put("报文长度", "packetLength");
    comIdMapData.put("车厢位置", "carriagePosition");
    comIdMapData.put("备注1", "remark1");
    comIdMapData.put("备注2", "remark2");
    // csPort对象配置文件
    csPortMapData.put("ComID号", "comId");
    csPortMapData.put("源IP地址", "ip");
    csPortMapData.put("车号", "trainNo");
    csPortMapData.put("板卡号", "cardNo");
    csPortMapData.put("端口号", "port");

    featuresMap.put("周期稳定性异常", "periodStabilityPHM");
    featuresMap.put("丢帧率异常", "lostRatePHM");
    featuresMap.put("丢帧行为异常", "abnomalLostPHM");
    featuresMap.put("时间窗-us", "windowTime");
    featuresMap.put("周期均值-us", "periodMean");
    featuresMap.put("周期标准差-us", "periodStd");
    featuresMap.put("丢帧率", "lostRate");
    featuresMap.put("最长连续丢帧率", "lostMaxRate");
    featuresMap.put("生命信号停滞率", "lifeSignalStopRate");
    featuresMap.put("最长连续生命信号停滞率", "lifeSignalStopMaxRate");

    featuresMap.put("网口断开", "linkPHM");
    featuresMap.put("网口闪断", "linkFlashPHM");
    featuresMap.put("流量异常-收", "rxTrafficPHM");
    featuresMap.put("误码异常-收", "rxErrRatePHM");
    featuresMap.put("误码趋势异常-收", "rxErrPredictPHM");
    featuresMap.put("流量异常-发", "txTrafficPHM");
    featuresMap.put("误码异常-预留", "txErrRatePHM");
    featuresMap.put("误码趋势异常-预留", "txErrPredictPHM");
    featuresMap.put("使能状态", "enable");
    featuresMap.put("连接占比", "linkMean");
    featuresMap.put("组播流量-收", "rxMcast");
    featuresMap.put("流量均值-收", "rxTrafficMean");
    featuresMap.put("流量标准差-收", "rxTrafficStd");
    featuresMap.put("误码率均值-收", "rxErrRateMean");
    featuresMap.put("误码率标准差-收", "rxErrRateStd");
    featuresMap.put("组播流量-发", "txMcast");
    featuresMap.put("流量均值-发", "txTrafficMean");
    featuresMap.put("流量标准差-发", "txTrafficStd");
    featuresMap.put("误码率均值-预留", "txErrRateMean");
    featuresMap.put("误码率标准差-预留", "txErrRateStd");
    featuresMap.put("收到报文数", "frameCnt");
  }
}
