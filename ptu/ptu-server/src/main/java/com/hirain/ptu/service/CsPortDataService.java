package com.hirain.ptu.service;

import com.hirain.ptu.common.model.CommonResponse;
import com.hirain.ptu.common.model.DisplayDataCommonRequest;
import com.hirain.ptu.model.ComIdData;
import com.hirain.ptu.model.CsPortData;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

public interface CsPortDataService extends IService<CsPortData> {

  @Cacheable(value = "csPortDataService", key = "'getCsPortTableDatas' + #p0")
  List<CsPortData> getCsPortTableDatas(DisplayDataCommonRequest commonRequest);

  @Cacheable(value = "csPortDataService", key = "'getChartData' + #p0")
  List<CommonResponse> getChartData(DisplayDataCommonRequest commonRequest) throws Exception;

  @CacheEvict(value = "csPortDataService", allEntries = true)
  void insertCsPortData(List<CsPortData> csPortDatas);

  @CacheEvict(value = "csPortDataService", allEntries = true)
  void deleteByTime(String deadLineTime) throws ParseException;

  @CacheEvict(value = "csPortDataService", allEntries = true)
  void dropTable();
}
