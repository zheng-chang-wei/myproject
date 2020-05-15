package com.hirain.ptu.dao;

import com.hirain.BaseTest;
import com.hirain.ptu.common.model.ComIdDataOverview;
import com.hirain.ptu.common.model.CommonParms;
import com.hirain.ptu.common.model.DisplayDataCommonRequest;
import com.hirain.ptu.model.ComIdData;
import com.hirain.ptu.model.ComIdObject;
import com.hirain.ptu.service.ObjectConfigService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author changwei.zheng
 * @date 2020/5/12 16:31
 * @describe
 */
public class ComIdDataMapperTest extends BaseTest {
  @Autowired ComIdDataMapper comIdDataMapper;
  @Autowired ObjectConfigService objectConfigService;

  @Test
  public void getComIdDataOverview() {
    String startTime = "2020-05-03";
    String endTime = "2020-05-05";
    CommonParms commonParms = new CommonParms(null, null, null, null, startTime, endTime);
    List<ComIdDataOverview> comIdDataOverview = comIdDataMapper.getComIdDataOverview(commonParms);

    List<ComIdData> comIdDatas = listComIds(commonParms);

    for (ComIdDataOverview comIdData2 : comIdDataOverview) {
      comIdData2.setFrameCnt(Float.valueOf(String.format("%.2f", comIdData2.getFrameCnt())));
      for (ComIdData comIdData1 : comIdDatas) {
        if (comIdData1.getIp().equals(comIdData2.getIp())
            && comIdData1.getComId().equals(comIdData2.getComId())) {
          Assert.assertEquals(
              comIdData2.getPeriodStabilityPHM(), comIdData1.getPeriodStabilityPHM());
          Assert.assertEquals(comIdData2.getAbnomalLostPHM(), comIdData1.getAbnomalLostPHM());
          Assert.assertEquals(comIdData2.getLostRatePHM(), comIdData1.getLostRatePHM());
          Assert.assertEquals(comIdData2.getFrameCnt(), comIdData1.getFrameCnt());
        }
      }
    }
  }

  public List<ComIdData> listComIds(CommonParms commonRequest) {
    List<ComIdObject> comIdObjects = objectConfigService.listComIds();
    List<ComIdData> comIdDatas = new ArrayList<>();
    for (ComIdObject comIdObject : comIdObjects) {
      List<ComIdData> tableData =
          comIdDataMapper.getTableData(
              new CommonParms(
                  comIdObject.getIp(),
                  String.valueOf(comIdObject.getComId()),
                  null,
                  null,
                  commonRequest.getStartTime(),
                  commonRequest.getEndTime()));
      boolean periodStabilityPHM = false;
      boolean lostRatePHM = false;
      boolean abnomalLostPHM = false;
      float frameCnt = 0f;
      for (ComIdData comIdData : tableData) {
        periodStabilityPHM = comIdData.getPeriodStabilityPHM() == 1 || periodStabilityPHM;
        lostRatePHM = comIdData.getLostRatePHM() == 1 || lostRatePHM;
        abnomalLostPHM = comIdData.getAbnomalLostPHM() == 1 || abnomalLostPHM;
        frameCnt += (comIdData.getFrameCnt() / comIdData.getWindowTime()) * 1000000;
      }
      ComIdData comIdData = new ComIdData();
      comIdData.setComId(comIdObject.getComId());
      comIdData.setIp(comIdObject.getIp());
      comIdData.setPeriodStabilityPHM(periodStabilityPHM ? (byte) 1 : (byte) 0);
      comIdData.setLostRatePHM(lostRatePHM ? (byte) 1 : (byte) 0);
      comIdData.setAbnomalLostPHM(abnomalLostPHM ? (byte) 1 : (byte) 0);
      comIdData.setFrameCnt(Float.valueOf(String.format("%.2f", frameCnt / tableData.size())));

      comIdDatas.add(comIdData);
    }
    return comIdDatas;
  }
}
