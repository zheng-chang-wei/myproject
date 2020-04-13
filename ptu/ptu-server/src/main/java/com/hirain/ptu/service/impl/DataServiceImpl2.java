//package com.hirain.ptu.service.impl;
//
//import com.hirain.ptu.common.exception.ExcelFormatException;
//import com.hirain.ptu.common.model.CommonParms2;
//import com.hirain.ptu.common.model.CommonResponse;
//import com.hirain.ptu.common.model.DisplayDataCommonRequest;
//import com.hirain.ptu.common.utils.HumpConversion;
//import com.hirain.ptu.dao.ComIdDataMapper;
//import com.hirain.ptu.dao.CsPortDataMapper;
//import com.hirain.ptu.model.ComIdData;
//import com.hirain.ptu.model.CsPortData;
//import com.hirain.ptu.service.DataService;
//import org.apache.commons.beanutils.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Service("DataServiceImpl2")
//public class DataServiceImpl2 extends BaseService<ComIdData> implements DataService {
//
//  @Autowired ComIdDataMapper comIdDataMapper;
//
//  @Autowired CsPortDataMapper csPortDataMapper;
//
//  @Override
//  public List<ComIdData> getComIdTableDatas(DisplayDataCommonRequest commonRequest) {
//    List<String> comIds = commonRequest.getComIds();
//    List<String> ips = commonRequest.getIps();
//    //    List<String> tableList = allTableExist(commonRequest);
//    List<ComIdData> list = new ArrayList<>();
//    for (int i = 0; i < comIds.size(); i++) {
//      CommonParms2 commonParms =
//          new CommonParms2(
//              ips.get(i),
//              comIds.get(i),
//              null,
//              commonRequest.getFeatures(),
//              getExpression(commonRequest.getLogicalCondition()),
//              commonRequest.getTime());
//      List<ComIdData> tableDatas = comIdDataMapper.getTableData(commonParms);
//      list.addAll(tableDatas);
//    }
//    return list;
//  }
//
//  @Override
//  public List<CsPortData> getCsPortTableDatas(DisplayDataCommonRequest commonRequest) {
//    List<String> comIds = commonRequest.getComIds();
//    List<String> ips = commonRequest.getIps();
//    List<String> ports = commonRequest.getPorts();
//    //    List<String> tableList = allTableExist(commonRequest);
//    List<CsPortData> list = new ArrayList<>();
//    for (int i = 0; i < comIds.size(); i++) {
//      CommonParms2 commonParms =
//          new CommonParms2(
//              ips.get(i),
//              comIds.get(i),
//              ports.get(i),
//              commonRequest.getFeatures(),
//              getExpression(commonRequest.getLogicalCondition()),
//              commonRequest.getTime());
//      List<CsPortData> tableDatas = csPortDataMapper.getTableData(commonParms);
//      for (CsPortData csPortData : tableDatas) {
//        csPortData.setComId(Integer.valueOf(comIds.get(i)));
//        csPortData.setIp(ips.get(i));
//        csPortData.setPort(Integer.valueOf(ports.get(i)));
//      }
//      list.addAll(tableDatas);
//    }
//    return list;
//  }
//
//  @Override
//  public List<CommonResponse> getChartData(DisplayDataCommonRequest commonRequest) {
//    //    List<ComIdAndIp> comIdAndIps = commonRequest.getComIdAndIps();
//    //    for (ComIdAndIp comIdAndIp : comIdAndIps) {}
//    //
//    //    return comIdObjMapper.getChartData(commonRequest);
//    return null;
//  }
//
//  @Override
//  public List<CommonResponse> get10Obj10Features() throws Exception {
//    long currentTimeMillis = System.currentTimeMillis();
//    String[] features = new String[5];
//    features[0] = "period_stability_phm";
//    features[1] = "lost_rate_phm";
//    features[2] = "abnomal_lost_phm";
//    features[3] = "window_time";
//    features[4] = "period_mean";
//
//    Integer[] comIds = new Integer[10];
//    for (int i = 0; i < comIds.length; i++) {
//      comIds[i] = 1000 + i;
//    }
//    String[] reSetFeatures = reSetFeatures(features);
//    String[] times = new String[2];
//    times[0] = "2020-3-18";
//    times[1] = "2020-3-19";
//    List<CommonResponse> list = new ArrayList<>();
//    List<List<ComIdData>> comIdObjs = new ArrayList<>();
//    for (int i = 0; i < comIds.length; i++) {
//      //      CommonParms request2 = new CommonParms();
//      //      request2.setComId(comIds[i]);
//      //      request2.setIp("192");
//      //      request2.setFeatures(Arrays.asList(features));
//      //      request2.setTimes(times);
//      comIdObjs.add(comIdDataMapper.get10Obj10Features(null));
//    }
//    System.out.println((System.currentTimeMillis() - currentTimeMillis) / 1000);
//    List<Date> timeList = new ArrayList<>();
//    for (ComIdData comIdObj : comIdObjs.get(0)) {
//      timeList.add(comIdObj.getDate());
//    }
//    for (int j = 0; j < reSetFeatures.length; j++) {
//      CommonResponse commonResponse = new CommonResponse();
//      commonResponse.setTimes(timeList);
//      List<List<String>> values = new ArrayList<>();
//      for (List<ComIdData> comIdObjList : comIdObjs) {
//        List<String> arrayList = new ArrayList<>();
//        for (ComIdData comIdObj : comIdObjList) {
//          String property = BeanUtils.getProperty(comIdObj, reSetFeatures[j]);
//          arrayList.add(property);
//        }
//        values.add(arrayList);
//      }
//      commonResponse.setValues(values);
//      list.add(commonResponse);
//    }
//    System.out.println((System.currentTimeMillis() - currentTimeMillis) / 1000);
//    return list;
//  }
//
//  @Override
//  public synchronized Boolean isTableExist(String tableName) {
//    List<String> tableNames = comIdDataMapper.getTableByTableName(tableName);
//    return tableNames.size() != 0;
//  }
//
//  @Override
//  public String getComIdTableName(String comId, String ip) {
//    return "t_comid_" + comId + "_" + ip.replace(".", "_");
//  }
//
//  @Override
//  public String getCsPostTableName(String comId, String ip, String port) {
//    return "t_csport_" + comId + "_" + ip.replace(".", "_") + "_" + port;
//  }
//
//  @Override
//  public void insertComIdData(String tableName, List<ComIdData> comIdDatas) {
//    comIdDataMapper.insertList(comIdDatas);
//    //    comIdDataMapper.insertCollectList(tableName, comIdDatas);
//  }
//
//  @Override
//  public void insertCsPortData(String tableName, List<CsPortData> csPortDatas) {
//    csPortDataMapper.insertList(csPortDatas);
//    //    csPortDataMapper.insertCollectList(tableName, csPortDatas);
//  }
//
//  private String[] reSetFeatures(String[] features) {
//    String[] values = new String[features.length];
//    for (int j = 0; j < features.length; j++) {
//      String[] split = features[j].split("_");
//      if (split.length > 1) {
//        for (int i = 1; i < split.length; i++) {
//          split[i] =
//              String.valueOf(split[i].charAt(0)).toUpperCase()
//                  + split[i].substring(1, split[i].length());
//        }
//      }
//      String value = "";
//      for (String string : split) {
//        value += string;
//      }
//      values[j] = value;
//    }
//    return values;
//  }
//
//  private List<String> transformFeatures(List<String> features) {
//    List<String> list = new ArrayList<>();
//    for (String feature : features) {
//      list.add(HumpConversion.camelToUnderline(feature));
//    }
//    return list;
//  }
//
//  private String getExpression(String expression) {
//    // 驼峰式命名list
//    List<String> humpList = getFeatures(expression);
//    for (String feature : humpList) {
//      // 将表达式中的驼峰命名方式改为下划线
//      expression = expression.replace(feature, HumpConversion.camelToUnderline(feature));
//      expression = expression.replace("&&", " and ");
//      expression = expression.replace("||", " or ");
//    }
//    return expression;
//  }
//
//  private List<String> getFeatures(String expressionString) {
//    // 驼峰式命名list
//    List<String> humpList = new ArrayList<>();
//    String[] ands = expressionString.split("&&");
//    for (String and : ands) {
//      String[] ors = and.split("\\|\\|");
//      for (String or : ors) {
//        String logicalOperator = getLogicalOperator(or);
//        humpList.add(or.split(logicalOperator)[0]);
//      }
//    }
//    return humpList;
//  }
//
//  private String getLogicalOperator(String element) {
//    String[] logicalOperatorOptions = {">", "<", "=", "!="};
//    for (int index = 0; index < logicalOperatorOptions.length; index++) {
//      String logicalOperator = logicalOperatorOptions[index];
//      if (element.indexOf(logicalOperator) != -1) {
//        return logicalOperator;
//      }
//    }
//    return null;
//  }
//
//  private List<String> allTableExist(DisplayDataCommonRequest commonRequest) {
//    List<String> tableList = new ArrayList<>();
//    List<String> comIds = commonRequest.getComIds();
//    List<String> ips = commonRequest.getIps();
//    List<String> ports = commonRequest.getPorts();
//    String errorMsg = "";
//    for (int i = 0; i < comIds.size(); i++) {
//      String comId = comIds.get(i);
//      String ip = ips.get(i);
//      String tableName = "";
//      String port = "";
//      if (ports != null && ports.size() != 0) {
//        port = ports.get(i);
//        tableName = getCsPostTableName(comId, ip, port);
//      } else {
//        tableName = getComIdTableName(comId, ip);
//      }
//      tableList.add(tableName);
//      List<String> tableNames = comIdDataMapper.getTableByTableName(tableName);
//      if (tableNames.size() == 0) {
//        if (errorMsg.length() != 0) {
//          errorMsg += "</br></br>";
//        }
//        if (port.length() == 0) {
//          errorMsg += "comId:" + comId + ", ip:" + ip + " 数据不存在，请删除该ComId对象";
//        } else {
//          errorMsg += "comId:" + comId + ", ip:" + ip + ", port:" + port + " 数据不存在，请删除该ComId对象";
//        }
//      }
//    }
//    if (errorMsg.length() != 0) {
//      throw new ExcelFormatException(errorMsg);
//    }
//    return tableList;
//  }
//}
