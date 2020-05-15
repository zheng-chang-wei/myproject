package com.hirain.ptu.service.impl;

import com.hirain.ptu.common.exception.CustomException;
import com.hirain.ptu.common.model.*;
import com.hirain.ptu.common.utils.HumpConversion;
import com.hirain.ptu.dao.ComIdDataMapper;
import com.hirain.ptu.model.ComIdData;
import com.hirain.ptu.service.ComIdDataService;
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
public class ComIdDataServiceImpl extends BaseService<ComIdData> implements ComIdDataService {

  @Autowired ComIdDataMapper comIdDataMapper;

  @Autowired ManageService manageService;

  @Override
  public List<ComIdData> getComIdTableDatas(DisplayDataCommonRequest commonRequest) {
    List<String> comIds = commonRequest.getComIds();
    List<String> ips = commonRequest.getIps();
    List<ComIdData> list = new ArrayList<>();
    for (int i = 0; i < comIds.size(); i++) {
      CommonParms commonParms =
          new CommonParms(
              ips.get(i),
              comIds.get(i),
              null,
              HumpConversion.getExpression(commonRequest.getLogicalCondition()),
              commonRequest.getStartTime(),
              commonRequest.getEndTime());
      List<ComIdData> tableDatas = comIdDataMapper.getTableData(commonParms);
      list.addAll(tableDatas);
    }
    return list;
  }

  @Override
  public List<CommonResponse> getChartData(DisplayDataCommonRequest commonRequest)
      throws Exception {
    List<String> comIds = commonRequest.getComIds();
    List<String> ips = commonRequest.getIps();
    List<CommonResponse> list = new ArrayList<>();
    //所有对象数据，每个对象一个list
    List<List<ComIdData>> comIdDatas = new ArrayList<>();
    String errorMsg = "";
    for (int i = 0; i < comIds.size(); i++) {
      CommonParms commonParms =
          new CommonParms(
              ips.get(i),
              comIds.get(i),
              null,
              HumpConversion.getExpression(commonRequest.getLogicalCondition()),
              commonRequest.getStartTime(),
              commonRequest.getEndTime());
      List<ComIdData> tableData = comIdDataMapper.getTableData(commonParms);
      errorMsg += checkError(comIds.get(i), ips.get(i), tableData.size());
      comIdDatas.add(tableData);
    }
    if (errorMsg.length() != 0) {
      throw new CustomException(errorMsg);
    }
    List<Date> timeList = new ArrayList<>();
    for (ComIdData comIdObj : comIdDatas.get(0)) {
      timeList.add(comIdObj.getDate());
    }
    for (int j = 0; j < commonRequest.getFeatures().size(); j++) {
      CommonResponse commonResponse = new CommonResponse();
      commonResponse.setTimes(timeList);
      List<List<String>> values = new ArrayList<>();
      for (List<ComIdData> comIdObjList : comIdDatas) {
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

  private String checkError(String comId, String ip, int tableDataSize) {
    String errorMsg = "";
    if (tableDataSize == 0) {
      if (errorMsg.length() != 0) {
        errorMsg += "</br></br>";
      }
      errorMsg += "comId:" + comId + ", ip:" + ip + " 数据为空";
    }
    return errorMsg;
  }

  @Override
  @Transactional
  public void insertComIdData(List<ComIdData> comIdDatas) {
    comIdDataMapper.insertList(comIdDatas);
  }

  @Override
  @Transactional
  public void deleteByTime(String deadLineTime) throws ParseException {
    manageService.deletePartitions(TableNameConstant.COMID_DATA_TABLE_NAME, deadLineTime);
    comIdDataMapper.deleteByTime(deadLineTime);
  }

  @Override
  @Transactional
  public void dropTable() {
    manageService.dropTable(TableNameConstant.COMID_DATA_TABLE_NAME);
  }

  @Override
  public DataOverview selectTimeRange() {
    if (manageService.isExistTable(TableNameConstant.COMID_DATA_TABLE_NAME)) {
      return comIdDataMapper.selectTimeRange();
    } else {
      return null;
    }
  }
}
