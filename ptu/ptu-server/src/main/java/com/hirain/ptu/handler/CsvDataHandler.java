package com.hirain.ptu.handler;

import com.hirain.ptu.common.model.WebSocketResponse;
import com.hirain.ptu.common.utils.DateUtil;
import com.hirain.ptu.model.ComIdData;
import com.hirain.ptu.model.CsPortData;
import com.hirain.ptu.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author changwei.zheng
 * @date 2020/4/4 20:10
 * @describe
 */
@Service
public class CsvDataHandler {

  @Autowired DataHandler dataHandler;

  /**
   * 读取CSV文件
   *
   * @return
   */
  @Async
  public void readComIdCSV(InputStream fileInputStream, String fileName) throws Exception {
    List<ComIdData> comIdDataList = new ArrayList<>();
    String line = null;
    BufferedReader bufferedReader = null;
    InputStreamReader inputStreamReader = null;
    try {
      inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
      bufferedReader = new BufferedReader(inputStreamReader);
      bufferedReader.readLine();
      while ((line = bufferedReader.readLine()) != null) {
        ComIdData comIdData = new ComIdData();
        // 数据行
        String[] items = line.split(",");
        setComIdData(comIdData, items);
        comIdDataList.add(comIdData);
      }
      if (comIdDataList.size()!=0){
        dataHandler.insertComIdData(comIdDataList, fileName);
      }
      WebSocketServer.sendMessage("admin", new WebSocketResponse(3, fileName));
    } catch (Exception e) {
      throw e;
    } finally {
      closeIO(fileInputStream, bufferedReader, inputStreamReader);
    }
  }

  @Async
  public void readCsPortCSV(InputStream fileInputStream, String fileName) throws Exception {
    List<CsPortData> csPortDataDataList = new ArrayList<>();
    String line = null;
    BufferedReader bufferedReader = null;
    InputStreamReader inputStreamReader = null;
    try {
      inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
      bufferedReader = new BufferedReader(inputStreamReader);
      bufferedReader.readLine();
      while ((line = bufferedReader.readLine()) != null) {
        CsPortData csPortData = new CsPortData();
        // 数据行
        String[] items = line.split(",");
        setCsPortData(csPortData, items);
        csPortDataDataList.add(csPortData);
      }
      dataHandler.insertCsPortData(csPortDataDataList, fileName);
      WebSocketServer.sendMessage("admin", new WebSocketResponse(3, fileName));
    } catch (Exception e) {
      throw e;
    } finally {
      closeIO(fileInputStream, bufferedReader, inputStreamReader);
    }
  }

  private void setComIdData(ComIdData comIdData, String[] items) throws ParseException {
    comIdData.setDate(
        DateUtil.parse(items[0].trim() + " " + items[1].trim(), "yyyy-MM-dd HH:mm:ss"));
    comIdData.setIp(items[2].trim());
    comIdData.setComId(Integer.valueOf(items[3].trim()));
    comIdData.setPeriodStabilityPHM((byte) Float.valueOf(items[4].trim()).intValue());
    comIdData.setLostRatePHM((byte) Float.valueOf(items[5].trim()).intValue());
    comIdData.setAbnomalLostPHM((byte) Float.valueOf(items[6].trim()).intValue());
    comIdData.setWindowTime(Float.valueOf(items[7].trim()).intValue());
    comIdData.setPeriodMean(Float.valueOf(items[8].trim()).intValue());
    comIdData.setPeriodStd(Float.valueOf(items[9].trim()).intValue());
    comIdData.setLostRate(Float.valueOf(items[10].trim()));
    comIdData.setLostMaxRate(Float.valueOf(items[11].trim()));
    comIdData.setLifeSignalStopRate(Float.valueOf(items[12].trim()));
    comIdData.setLifeSignalStopMaxRate(Float.valueOf(items[13].trim()));
  }

  private void setCsPortData(CsPortData csPortData, String[] items) throws ParseException {
    csPortData.setDate(
        DateUtil.parse(items[0].trim() + " " + items[1].trim(), "yyyy-MM-dd HH:mm:ss"));
    csPortData.setIp(items[2].trim());
    csPortData.setComId(Float.valueOf(items[3].trim()).intValue());
    csPortData.setPort(Float.valueOf(items[4].trim()).intValue());
    csPortData.setLinkPHM((byte) Float.valueOf(items[5].trim()).intValue());
    csPortData.setLinkFlashPHM((byte) Float.valueOf(items[6].trim()).intValue());
    csPortData.setRxTrafficPHM((byte) Float.valueOf(items[7].trim()).intValue());
    csPortData.setRxErrRatePHM((byte) Float.valueOf(items[8].trim()).intValue());
    csPortData.setRxErrPredictPHM((byte) Float.valueOf(items[9].trim()).intValue());
    csPortData.setTxTrafficPHM((byte) Float.valueOf(items[10].trim()).intValue());
    csPortData.setTxErrRatePHM((byte) Float.valueOf(items[11].trim()).intValue());
    csPortData.setTxErrPredictPHM((byte) Float.valueOf(items[12].trim()).intValue());
    csPortData.setEnable((byte) Float.valueOf(items[13].trim()).intValue());
    csPortData.setLinkMean(Float.valueOf(items[14].trim()));
    csPortData.setRxMcast(Float.valueOf(items[15].trim()).intValue());
    csPortData.setRxTrafficMean(Float.valueOf(items[16].trim()).intValue());
    csPortData.setRxTrafficStd(Float.valueOf(items[17].trim()).intValue());
    csPortData.setRxErrRateMean(Float.valueOf(items[18].trim()).intValue());
    csPortData.setRxErrRateStd(Float.valueOf(items[19].trim()).intValue());
    csPortData.setTxMcast(Float.valueOf(items[20].trim()).intValue());
    csPortData.setTxTrafficMean(Float.valueOf(items[21].trim()).intValue());
    csPortData.setTxTrafficStd(Float.valueOf(items[22].trim()).intValue());
    csPortData.setTxErrRateMean(Float.valueOf(items[23].trim()).intValue());
    csPortData.setTxErrRateStd(Float.valueOf(items[24].trim()).intValue());
  }


  private void closeIO(
      InputStream fileInputStream,
      BufferedReader bufferedReader,
      InputStreamReader inputStreamReader)
      throws IOException {
    if (bufferedReader != null) {
      bufferedReader.close();
    }
    if (inputStreamReader != null) {
      inputStreamReader.close();
    }
    if (fileInputStream != null) {
      fileInputStream.close();
    }
  }
}
