package com.hirain.ptu.service.impl;

import com.hirain.ptu.common.model.ResponseBo;
import com.hirain.ptu.common.model.WebSocketResponse;
import com.hirain.ptu.common.utils.DateUtil;
import com.hirain.ptu.dao.DataOverviewMapper;
import com.hirain.ptu.model.DataOverview;
import com.hirain.ptu.service.DataOverviewService;
import com.hirain.ptu.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataUnit;

import java.text.ParseException;
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

  @Async("asyncServiceExecutor")
  public void test() {
    List<DataOverview> dataOverviewSelecteds = selectAll();
    System.out.println(dataOverviewSelecteds);
  }

  @Async
  @Override
  public void deleteByTime(String deadLineTime) throws ParseException {
    List<DataOverview> dataOverviews = dataOverviewMapper.selectAll();
    for (DataOverview dataOverview : dataOverviews) {
      Date date = DateUtil.parse(deadLineTime, "yyyy-MM-dd");
      if (date.after(dataOverview.getEndTime())) {
        dataOverviewMapper.delete(dataOverview);
      } else if (date.after(dataOverview.getStartTime())) {
        dataOverview.setStartTime(date);
        dataOverviewMapper.updateByPrimaryKeySelective(dataOverview);
      }
    }
    WebSocketServer.sendMessage("admin", new WebSocketResponse(1,null));
  }
}
