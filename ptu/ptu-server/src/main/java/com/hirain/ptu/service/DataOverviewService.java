package com.hirain.ptu.service;

import com.hirain.ptu.model.Condition;
import com.hirain.ptu.model.DataOverview;

import java.text.ParseException;
import java.util.List;

/**
 * @author changwei.zheng
 * @date 2020/3/30 14:18
 * @describe
 */
public interface DataOverviewService extends IService<DataOverview> {

  void deleteByTime(String deadlineTime,String type) throws ParseException;

  List<DataOverview> getDataOverview();

  void deleteComIdAll();

  void deleteCsPortAll();

  DataOverview selectByType(String type);
}
