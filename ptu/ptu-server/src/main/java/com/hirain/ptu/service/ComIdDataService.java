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

public interface ComIdDataService extends IService<ComIdData> {

  @Cacheable(value = "comIdObjService", key = "'getComIdTableDatas' +#p0")
  List<ComIdData> getComIdTableDatas(DisplayDataCommonRequest commonRequest);

  @Cacheable(value = "comIdObjService", key = "'getChartData' + #p0")
  List<CommonResponse> getChartData(DisplayDataCommonRequest commonRequest)
      throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

  @CacheEvict(value = "comIdObjService", allEntries = true)
  void insertComIdData(String tableName, List<ComIdData> comIdDatas);

  @CacheEvict(value = "comIdObjService", allEntries = true)
  void deleteByTime(String deadLineTime);
}
