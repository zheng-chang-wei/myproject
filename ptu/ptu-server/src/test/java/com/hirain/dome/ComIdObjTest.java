package com.hirain.dome;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.hirain.BaseTest;
import com.hirain.ptu.dao.ComIdObjectMapper;
import com.hirain.ptu.dao.CsPortDataMapper;
import com.hirain.ptu.dao.CsPortObjectMapper;
import com.hirain.ptu.model.ComIdObject;
import com.hirain.ptu.model.CsPortData;
import com.hirain.ptu.model.CsPortObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.ptu.Application;
import com.hirain.ptu.dao.ComIdDataMapper;
import com.hirain.ptu.model.ComIdData;

public class ComIdObjTest extends BaseTest {

  @Autowired ComIdDataMapper comIdDataMapper;

  @Autowired CsPortDataMapper portDataMapper;

  @Autowired ComIdObjectMapper comIdObjectMapper;

  @Autowired CsPortObjectMapper csPortObjectMapper;

  @Test
  public void testInsert() {
    testInsertComIdData();
    testInsertCsport();
  }

  @Test
  public void testInsertComIdData() {
    long time = new Date().getTime();
    Calendar cal = Calendar.getInstance();
    List<ComIdObject> comIdObjects = comIdObjectMapper.select10();
    List<ComIdData> list1 = new ArrayList<>();
    for (int j = 0; j < 1000; j++) {
      cal.setTime(new Date(time + 20000 * j));
      for (ComIdObject comIdObj : comIdObjects) {
        ComIdData obj = new ComIdData();
        obj.setDate(cal.getTime());
        setData(obj);
        obj.setComId(comIdObj.getComId());
        obj.setIp(comIdObj.getIp());
        list1.add(obj);
      }
      comIdDataMapper.insertList(list1);
      list1.clear();
    }
  }

  @Test
  public void testInsertCsport() {
    long time = new Date().getTime();
    Calendar cal = Calendar.getInstance();
    List<CsPortObject> csPortObjects = csPortObjectMapper.select10();
    List<CsPortData> list1 = new ArrayList<>();
    for (int j = 0; j < 1000; j++) {
      cal.setTime(new Date(time + 20000 * j));
      for (CsPortObject csPortObject : csPortObjects) {
        CsPortData obj = new CsPortData();
        obj.setDate(cal.getTime());
        setData(obj);
        obj.setComId(csPortObject.getComId());
        obj.setIp(csPortObject.getIp());
        obj.setPort(csPortObject.getPort());
        list1.add(obj);
      }
      portDataMapper.insertList(list1);
      list1.clear();
    }
  }

  private void setData(ComIdData obj) {
    obj.setIp("10.0.0.1");
    obj.setComId(1001);
    //    obj.setPeriodStabilityPHM(new Random().nextInt(2)==1);
    //    obj.setLostRatePHM(new Random().nextInt(2) == 1);
    //    obj.setAbnomalLostPHM(new Random().nextInt(2) == 1);
    obj.setPeriodStabilityPHM((byte) new Random().nextInt(2));
    obj.setLostRatePHM((byte) new Random().nextInt(2));
    obj.setAbnomalLostPHM((byte) new Random().nextInt(2));
    obj.setWindowTime(new Random().nextInt(2000));
    obj.setPeriodMean(new Random().nextInt(2000));
    obj.setPeriodStd(new Random().nextInt(2000));
    obj.setLostRate(new Random().nextFloat());
    obj.setLostMaxRate(new Random().nextFloat());
    obj.setLifeSignalStopRate(new Random().nextFloat());
    obj.setLifeSignalStopMaxRate(new Random().nextFloat());
    obj.setFrameCnt(new Random().nextFloat());
  }

  private void setData(CsPortData obj) {
    obj.setIp("10.0.0.1");
    obj.setComId(1001);
    obj.setPort(1);
    //    obj.setEnable(new Random().nextInt(2) == 1);
    //    obj.setLinkFlashPHM(new Random().nextInt(2) == 1);
    //    obj.setLinkPHM(new Random().nextInt(2) == 1);
    //    obj.setRxErrPredictPHM(new Random().nextInt(2) == 1);
    //    obj.setRxErrRatePHM(new Random().nextInt(2) == 1);
    //    obj.setRxTrafficPHM(new Random().nextInt(2) == 1);
    //    obj.setTxErrPredictPHM(new Random().nextInt(2) == 1);
    //    obj.setTxErrRatePHM(new Random().nextInt(2) == 1);
    //    obj.setTxTrafficPHM(new Random().nextInt(2) == 1);
    obj.setEnable((byte) new Random().nextInt(2));
    obj.setLinkFlashPHM((byte) new Random().nextInt(2));
    obj.setLinkPHM((byte) new Random().nextInt(2));
    obj.setRxErrPredictPHM((byte) new Random().nextInt(2));
    obj.setRxErrRatePHM((byte) new Random().nextInt(2));
    obj.setRxTrafficPHM((byte) new Random().nextInt(2));
    obj.setTxErrPredictPHM((byte) new Random().nextInt(2));
    obj.setTxErrRatePHM((byte) new Random().nextInt(2));
    obj.setTxTrafficPHM((byte) new Random().nextInt(2));
    obj.setLinkMean(new Random().nextFloat());
    obj.setRxErrRateMean(new Random().nextInt(2));
    obj.setRxErrRateStd(new Random().nextInt(2));
    obj.setRxMcast(new Random().nextInt(2));
    obj.setRxTrafficMean(new Random().nextInt(2));
    obj.setRxTrafficStd(new Random().nextInt(2));
    obj.setTxErrRateMean(new Random().nextInt(2));
    obj.setTxErrRateStd(new Random().nextInt(2));
    obj.setTxMcast(new Random().nextInt(2));
    obj.setTxTrafficMean(new Random().nextInt(2));
    obj.setTxTrafficStd(new Random().nextInt(2));
  }
}
