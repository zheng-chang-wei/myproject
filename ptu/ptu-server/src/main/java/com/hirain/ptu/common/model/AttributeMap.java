package com.hirain.ptu.common.model;

import java.util.HashMap;
import java.util.Map;

public class AttributeMap {

  public static Map<String, String> comIdMapData = new HashMap<>();
  public static Map<String, String> csPortMapData = new HashMap<>();

  static {
    comIdMapData.put("ComID", "comId");
    comIdMapData.put("源IP地址", "ip");
    comIdMapData.put("车厢位置", "carriagePosition");
    comIdMapData.put("备注", "remark");

    csPortMapData.put("ComID号", "comId");
    csPortMapData.put("源IP地址", "ip");
    csPortMapData.put("车号", "trainNo");
    csPortMapData.put("板卡号", "cardNo");
    csPortMapData.put("端口号", "port");
  }
}
