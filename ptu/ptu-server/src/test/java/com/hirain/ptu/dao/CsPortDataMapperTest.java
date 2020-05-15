package com.hirain.ptu.dao;

import com.hirain.BaseTest;
import com.hirain.ptu.common.model.CommonParms;
import com.hirain.ptu.common.model.CsPortDataOverview;
import com.hirain.ptu.model.ComIdData;
import com.hirain.ptu.model.ComIdObject;
import com.hirain.ptu.model.CsPortData;
import com.hirain.ptu.model.CsPortObject;
import com.hirain.ptu.service.ObjectConfigService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author changwei.zheng
 * @date 2020/5/12 18:32
 * @describe
 */
public class CsPortDataMapperTest extends BaseTest {
  @Autowired CsPortDataMapper csPortDataMapper;
  @Autowired ObjectConfigService objectConfigService;

  @Test
  public void getCsPortDataOverview() {
    String startTime = "2020-05-03";
    String endTime = "2020-05-05";
    CommonParms commonParms = new CommonParms(null, null, null, null, startTime, endTime);
    List<CsPortDataOverview> csPortDataOverview = csPortDataMapper.getCsPortDataOverview(commonParms);

    List<CsPortData> csPortDatas = listCsPorts(commonParms);

    for (CsPortDataOverview csPortData2 : csPortDataOverview) {
      for (CsPortData csPortData1 : csPortDatas) {
        if (csPortData1.getIp().equals(csPortData2.getIp())
            && csPortData1.getComId().equals(csPortData2.getComId())
            && csPortData1.getPort().equals(csPortData2.getPort())) {
          Assert.assertEquals(csPortData2.getLinkPHM(), csPortData1.getLinkPHM());
          Assert.assertEquals(csPortData2.getLinkFlashPHM(), csPortData1.getLinkFlashPHM());
          Assert.assertEquals(csPortData2.getTxTrafficPHM(), csPortData1.getTxTrafficPHM());
          Assert.assertEquals(csPortData2.getTxErrRatePHM(), csPortData1.getTxErrRatePHM());
          Assert.assertEquals(csPortData2.getRxErrPredictPHM(), csPortData1.getRxErrPredictPHM());
          Assert.assertEquals(csPortData2.getTxTrafficPHM(), csPortData1.getTxTrafficPHM());
          Assert.assertEquals(csPortData2.getTxErrRatePHM(), csPortData1.getTxErrRatePHM());
          Assert.assertEquals(csPortData2.getTxErrPredictPHM(), csPortData1.getTxErrPredictPHM());
          Assert.assertEquals(csPortData2.getEnable(), csPortData1.getEnable());
        }
      }
    }
  }

  public List<CsPortData> listCsPorts(CommonParms commonRequest) {
    List<CsPortObject> csPortObjects = objectConfigService.listCsPorts();
    List<CsPortData> csPortDatas = new ArrayList<>();
    for (CsPortObject csPortObject : csPortObjects) {
      List<CsPortData> tableData =
          csPortDataMapper.getTableData(
              new CommonParms(
                  csPortObject.getIp(),
                  String.valueOf(csPortObject.getComId()),
                  csPortObject.getPort() + "",
                  null,
                  commonRequest.getStartTime(),
                  commonRequest.getEndTime()));
      Boolean linkPHM = false;

      Boolean linkFlashPHM = false;

      Boolean rxTrafficPHM = false;

      Boolean rxErrRatePHM = false;

      Boolean rxErrPredictPHM = false;

      Boolean txTrafficPHM = false;

      Boolean txErrRatePHM = false;

      Boolean txErrPredictPHM = false;

      Boolean enable = false;
      for (CsPortData csPortData : tableData) {
        linkPHM = csPortData.getLinkPHM() == 1 || linkPHM;
        linkFlashPHM = csPortData.getLinkFlashPHM() == 1 || linkFlashPHM;
        rxTrafficPHM = csPortData.getTxTrafficPHM() == 1 || rxTrafficPHM;
        rxErrRatePHM = csPortData.getTxErrRatePHM() == 1 || rxErrRatePHM;
        rxErrPredictPHM = csPortData.getRxErrPredictPHM() == 1 || rxErrPredictPHM;
        txTrafficPHM = csPortData.getTxTrafficPHM() == 1 || txTrafficPHM;
        txErrRatePHM = csPortData.getTxErrRatePHM() == 1 || txErrRatePHM;
        txErrPredictPHM = csPortData.getTxErrPredictPHM() == 1 || txErrPredictPHM;
        enable = csPortData.getEnable() == 1 || enable;
      }
      CsPortData csPortData = new CsPortData();
      csPortData.setComId(csPortObject.getComId());
      csPortData.setIp(csPortObject.getIp());
      csPortData.setPort(csPortObject.getPort());
      csPortData.setLinkPHM(linkPHM ? (byte) 1 : (byte) 0);
      csPortData.setLinkFlashPHM(linkFlashPHM ? (byte) 1 : (byte) 0);
      csPortData.setRxTrafficPHM(rxTrafficPHM ? (byte) 1 : (byte) 0);
      csPortData.setRxErrRatePHM(rxErrRatePHM ? (byte) 1 : (byte) 0);
      csPortData.setRxErrPredictPHM(rxErrPredictPHM ? (byte) 1 : (byte) 0);
      csPortData.setTxTrafficPHM(txTrafficPHM ? (byte) 1 : (byte) 0);
      csPortData.setTxErrRatePHM(txErrRatePHM ? (byte) 1 : (byte) 0);
      csPortData.setTxErrPredictPHM(txErrPredictPHM ? (byte) 1 : (byte) 0);
      csPortData.setEnable(enable ? (byte) 1 : (byte) 0);

      csPortDatas.add(csPortData);
    }
    return csPortDatas;
  }
}
