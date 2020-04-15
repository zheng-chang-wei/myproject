package com.hirain.ptu.service.impl;

import com.hirain.ptu.common.model.ResponseBo;
import com.hirain.ptu.common.model.TableNameConstant;
import com.hirain.ptu.common.model.WebSocketResponse;
import com.hirain.ptu.common.utils.DateUtil;
import com.hirain.ptu.dao.ComIdDataMapper;
import com.hirain.ptu.dao.CsPortDataMapper;
import com.hirain.ptu.dao.DataOverviewMapper;
import com.hirain.ptu.model.DataOverview;
import com.hirain.ptu.service.DataOverviewService;
import com.hirain.ptu.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataUnit;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author changwei.zheng
 * @date 2020/3/30 14:18
 * @describe
 */
@Service
public class DataOverviewServiceImpl extends BaseService<DataOverview>
    implements DataOverviewService {

  @Autowired DataOverviewMapper dataOverviewMapper;

  @Autowired
  ComIdDataMapper comIdDataMapper;

  @Autowired
  CsPortDataMapper csPortDataMapper;


  @Override
  @Transactional
  public void deleteByTime(String deadlineTime,String type) throws ParseException {
    DataOverview dataOverview=dataOverviewMapper.selectByType(type);
    Date date = DateUtil.parse(deadlineTime, "yyyy-MM-dd");
    dataOverview.setStartTime(date);
    updateNotNull(dataOverview);
  }


  @Override
  public List<DataOverview> getDataOverview() {
    List<DataOverview> list = new ArrayList<>();
    list.add(comIdDataMapper.selectTimeRange());
    list.add(csPortDataMapper.selectTimeRange());
    return list;
  }

  @Override
  @Transactional
  public void deleteComIdAll() {
    dataOverviewMapper.deleteByType(TableNameConstant.COMID_TYPE);
  }

  @Override
  @Transactional
  public void deleteCsPortAll() {
    dataOverviewMapper.deleteByType(TableNameConstant.CSPORT_TYPE);
  }

  @Override
  public DataOverview selectByType(String type) {
    return dataOverviewMapper.selectByType(type);
  }
}
