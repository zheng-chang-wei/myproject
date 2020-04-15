package com.hirain.ptu.service.impl;

import com.hirain.ptu.common.exception.CustomException;
import com.hirain.ptu.common.model.*;
import com.hirain.ptu.common.utils.DateUtil;
import com.hirain.ptu.common.utils.HumpConversion;
import com.hirain.ptu.dao.CsPortDataMapper;
import com.hirain.ptu.model.CsPortData;
import com.hirain.ptu.service.CsPortDataService;
import com.hirain.ptu.service.DataOverviewService;
import com.hirain.ptu.service.ManageService;
import com.hirain.ptu.websocket.WebSocketServer;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CsPortDataServiceImpl extends BaseService<CsPortData> implements CsPortDataService {

  @Autowired CsPortDataMapper csPortDataMapper;
  @Autowired ManageService manageService;
  @Autowired DataOverviewService dataOverviewService;

  @Override
  public List<CsPortData> getCsPortTableDatas(DisplayDataCommonRequest commonRequest) {
    List<String> comIds = commonRequest.getComIds();
    List<String> ips = commonRequest.getIps();
    List<String> ports = commonRequest.getPorts();
    List<CsPortData> list = new ArrayList<>();
    for (int i = 0; i < comIds.size(); i++) {
      CommonParms commonParms =
          new CommonParms(
              ips.get(i),
              comIds.get(i),
              ports.get(i),
              null,
              getExpression(commonRequest.getLogicalCondition()),
              commonRequest.getTime());
      List<CsPortData> tableDatas = csPortDataMapper.getTableData(commonParms);
      for (CsPortData csPortData : tableDatas) {
        csPortData.setComId(Integer.valueOf(comIds.get(i)));
        csPortData.setIp(ips.get(i));
        csPortData.setPort(Integer.valueOf(ports.get(i)));
      }
      list.addAll(tableDatas);
    }
    return list;
  }

  @Override
  public List<CommonResponse> getChartData(DisplayDataCommonRequest commonRequest)
      throws Exception {
    List<String> comIds = commonRequest.getComIds();
    List<String> ips = commonRequest.getIps();
    List<String> ports = commonRequest.getPorts();
    List<CommonResponse> list = new ArrayList<>();
    List<List<CsPortData>> csPortObjDatas = new ArrayList<>();
    for (int i = 0; i < comIds.size(); i++) {
      CommonParms commonParms =
          new CommonParms(
              ips.get(i),
              comIds.get(i),
              ports.get(i),
              null,
              getExpression(commonRequest.getLogicalCondition()),
              commonRequest.getTime());
      List<CsPortData> tableData = csPortDataMapper.getTableData(commonParms);
      if (tableData.size() == 0) {
        throw new CustomException(ips.get(i) + "_" + comIds.get(i) + "_" + ports.get(i) + " 对象无数据");
      }
      csPortObjDatas.add(tableData);
    }
    List<Date> timeList = new ArrayList<>();
    for (CsPortData csPortData : csPortObjDatas.get(0)) {
      timeList.add(csPortData.getDate());
    }
    for (int j = 0; j < commonRequest.getFeatures().size(); j++) {
      CommonResponse commonResponse = new CommonResponse();
      commonResponse.setTimes(timeList);
      List<List<String>> values = new ArrayList<>();
      for (List<CsPortData> comIdObjList : csPortObjDatas) {
        List<String> arrayList = new ArrayList<>();
        for (CsPortData comIdObj : comIdObjList) {
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
  @Transactional
  public void insertCsPortData(List<CsPortData> csPortDatas) {
    csPortDataMapper.insertList(csPortDatas);
  }

  @Override
  @Transactional
  public void deleteByTime(String deadLineTime) throws ParseException {
    manageService.deletePartitions(TableNameConstant.CSPROT_DATA_TABLE_NAME, deadLineTime);
    csPortDataMapper.deleteByTime(deadLineTime);
    dataOverviewService.deleteByTime(deadLineTime, TableNameConstant.CSPORT_TYPE);
  }

  @Override
  @Transactional
  public void dropTable() {
    manageService.dropTable(TableNameConstant.CSPROT_DATA_TABLE_NAME);
    dataOverviewService.deleteCsPortAll();
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
