package com.hirain.ptu.service.impl;

import com.hirain.ptu.common.model.*;
import com.hirain.ptu.common.utils.HumpConversion;
import com.hirain.ptu.dao.ComIdDataMapper;
import com.hirain.ptu.model.ComIdData;
import com.hirain.ptu.service.ComIdDataService;
import com.hirain.ptu.service.DataOverviewService;
import com.hirain.ptu.websocket.WebSocketServer;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ComIdDataServiceImpl extends BaseService<ComIdData> implements ComIdDataService {

  @Autowired ComIdDataMapper comIdDataMapper;

  @Autowired DataOverviewService dataOverviewService;

  @Override
  public List<ComIdData> getComIdTableDatas(DisplayDataCommonRequest commonRequest) {
    List<String> comIds = commonRequest.getComIds();
    List<String> ips = commonRequest.getIps();
    List<ComIdData> list = new ArrayList<>();
    for (int i = 0; i < comIds.size(); i++) {
      CommonParms2 commonParms =
          new CommonParms2(
              ips.get(i),
              comIds.get(i),
              null,
              null,
              getExpression(commonRequest.getLogicalCondition()),
              commonRequest.getTime());
      List<ComIdData> tableDatas = comIdDataMapper.getTableData(commonParms);
      list.addAll(tableDatas);
    }
    return list;
  }

  @Override
  public List<CommonResponse> getChartData(DisplayDataCommonRequest commonRequest) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    List<String> comIds = commonRequest.getComIds();
    List<String> ips = commonRequest.getIps();
    List<CommonResponse> list = new ArrayList<>();
    List<List<ComIdData>> comIdObjs = new ArrayList<>();
    for (int i = 0; i < comIds.size(); i++) {
      CommonParms2 commonParms =
          new CommonParms2(
              ips.get(i),
              comIds.get(i),
              null,
              null,
              getExpression(commonRequest.getLogicalCondition()),
              commonRequest.getTime());
      comIdObjs.add(comIdDataMapper.getTableData(commonParms));
    }
    List<Date> timeList = new ArrayList<>();
    for (ComIdData comIdObj : comIdObjs.get(0)) {
      timeList.add(comIdObj.getDate());
    }
    for (int j = 0; j < commonRequest.getFeatures().size(); j++) {
      CommonResponse commonResponse = new CommonResponse();
      commonResponse.setTimes(timeList);
      List<List<String>> values = new ArrayList<>();
      for (List<ComIdData> comIdObjList : comIdObjs) {
        List<String> arrayList = new ArrayList<>();
        for (ComIdData comIdObj : comIdObjList) {
          String property = BeanUtils.getProperty(comIdObj, commonRequest.getFeatures().get(j));
          arrayList.add(property);
        }
        values.add(arrayList);
      }
      commonResponse.setValues(values);
      list.add(commonResponse);
    }
    return list;
  }

  @Override
  public void insertComIdData(String tableName, List<ComIdData> comIdDatas) {
    comIdDataMapper.insertList(comIdDatas);
  }

  @Async
  @Override
  public void deleteByTime(String deadLineTime) {
    comIdDataMapper.deleteByTime(deadLineTime);
    WebSocketServer.sendMessage("admin", new WebSocketResponse(1, null));
  }

  private List<String> transformFeatures(List<String> features) {
    List<String> list = new ArrayList<>();
    for (String feature : features) {
      list.add(HumpConversion.camelToUnderline(feature));
    }
    return list;
  }

  private String getExpression(String expression) {
    // 驼峰式命名list
    List<String> humpList = getFeatures(expression);
    for (String feature : humpList) {
      // 将表达式中的驼峰命名方式改为下划线
      expression = expression.replace(feature, HumpConversion.camelToUnderline(feature));
      expression = expression.replace("&&", " and ");
      expression = expression.replace("||", " or ");
    }
    return expression;
  }

  private List<String> getFeatures(String expressionString) {
    // 驼峰式命名list
    List<String> humpList = new ArrayList<>();
    String[] ands = expressionString.split("&&");
    for (String and : ands) {
      String[] ors = and.split("\\|\\|");
      for (String or : ors) {
        String logicalOperator = getLogicalOperator(or);
        humpList.add(or.split(logicalOperator)[0]);
      }
    }
    return humpList;
  }

  private String getLogicalOperator(String element) {
    String[] logicalOperatorOptions = {">", "<", "=", "!="};
    for (int index = 0; index < logicalOperatorOptions.length; index++) {
      String logicalOperator = logicalOperatorOptions[index];
      if (element.indexOf(logicalOperator) != -1) {
        return logicalOperator;
      }
    }
    return null;
  }
}
