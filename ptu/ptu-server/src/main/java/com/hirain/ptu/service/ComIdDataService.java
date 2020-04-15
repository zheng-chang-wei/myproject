package com.hirain.ptu.service;

import com.hirain.ptu.common.model.CommonResponse;
import com.hirain.ptu.common.model.DataOverview;
import com.hirain.ptu.common.model.DisplayDataCommonRequest;
import com.hirain.ptu.model.ComIdData;
import com.hirain.ptu.model.CsPortData;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

public interface ComIdDataService extends IService<ComIdData> {

  @Cacheable(value = "comIdDataService", key = "'getComIdTableDatas' +#p0")
  List<ComIdData> getComIdTableDatas(DisplayDataCommonRequest commonRequest);

  @Cacheable(value = "comIdDataService", key = "'getChartData' + #p0")
  List<CommonResponse> getChartData(DisplayDataCommonRequest commonRequest) throws Exception;

  @Cacheable(value = "comIdDataService", key = "'selectTimeRange'")
  DataOverview selectTimeRange();

  @CacheEvict(value = "comIdDataService", allEntries = true)
  void insertComIdData(List<ComIdData> comIdDatas);

  @CacheEvict(value = "comIdDataService", allEntries = true)
  void deleteByTime(String deadLineTime) throws ParseException;

  @CacheEvict(value = "comIdDataService", allEntries = true)
  void dropTable();
}
