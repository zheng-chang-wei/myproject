package com.hirain.ptu.service.impl;

import com.hirain.ptu.common.exception.CustomException;
import com.hirain.ptu.common.model.*;
import com.hirain.ptu.common.utils.HumpConversion;
import com.hirain.ptu.dao.CsPortDataMapper;
import com.hirain.ptu.model.CsPortData;
import com.hirain.ptu.service.CsPortDataService;
import com.hirain.ptu.service.ManageService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
              HumpConversion.getExpression(commonRequest.getLogicalCondition()),
              commonRequest.getStartTime(),
              commonRequest.getEndTime());
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
    String errorMsg = "";
    for (int i = 0; i < comIds.size(); i++) {
      CommonParms commonParms =
          new CommonParms(
              ips.get(i),
              comIds.get(i),
              ports.get(i),
              HumpConversion.getExpression(commonRequest.getLogicalCondition()),
              commonRequest.getStartTime(),
              commonRequest.getEndTime());
      List<CsPortData> tableData = csPortDataMapper.getTableData(commonParms);
      if (tableData.size() == 0) {
        if (errorMsg.length() != 0) {
          errorMsg += "</br></br>";
        }
        errorMsg +=
            "comId:" + comIds.get(i) + ", ip:" + ips.get(i) + ", port:" + ports.get(i) + " 数据为空";
      }
      csPortObjDatas.add(tableData);
    }
    if (errorMsg.length() != 0) {
      throw new CustomException(errorMsg);
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
  public DataOverview selectTimeRange() {
    if (manageService.isExistTable(TableNameConstant.CSPROT_DATA_TABLE_NAME)) {
      return csPortDataMapper.selectTimeRange();
    } else {
      return null;
    }
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
  }

  @Override
  @Transactional
  public void dropTable() {
    manageService.dropTable(TableNameConstant.CSPROT_DATA_TABLE_NAME);
  }
}
