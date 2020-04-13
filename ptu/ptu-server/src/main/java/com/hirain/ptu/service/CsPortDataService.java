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

  @Cacheable(value = "comIdObjService", key = "'getCsPortTableDatas' + #p0")
  List<CsPortData> getCsPortTableDatas(DisplayDataCommonRequest commonRequest);

  @Cacheable(value = "comIdObjService", key = "'getChartData' + #p0")
  List<CommonResponse> getChartData(DisplayDataCommonRequest commonRequest) throws Exception;

  @CacheEvict(value = "comIdObjService", allEntries = true)
  void insertCsPortData(String tableName, List<CsPortData> csPortDatas);

  @CacheEvict(value = "comIdObjService", allEntries = true)
  void deleteByTime(String deadLineTime);
}
