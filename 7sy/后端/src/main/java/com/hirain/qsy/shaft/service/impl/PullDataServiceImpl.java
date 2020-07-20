package com.hirain.qsy.shaft.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hirain.qsy.shaft.common.util.VerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.HistoryData;
import com.hirain.qsy.shaft.common.model.HistoryReponse;
import com.hirain.qsy.shaft.handler.HistoryDataHandler;
import com.hirain.qsy.shaft.model.TrainInfo;
import com.hirain.qsy.shaft.service.HttpService;
import com.hirain.qsy.shaft.service.PullDataService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.util.StringUtil;

@Service
@Slf4j
public class PullDataServiceImpl implements PullDataService {

    @Value("${dataSource.url}")
    private String url;

    @Value("${dataSource.successCode}")
    private int successCode;

    @Autowired
    private HistoryDataHandler historyDataHandler;

    @Autowired
    private HttpService httpService;

    @Override
    public void handleHistoryData(DataRequest dataRequest) throws Exception {
        List<HistoryData> historyDatas = pullData(dataRequest);
        log.info("allHistoryDatas");
        log.info(historyDatas.size() + "");
        clearHistoryDatas(historyDatas);
        log.info("clearHistoryDatas");
        log.info(historyDatas.size() + "");
        List<List<HistoryData>> classifyedHistoryData = classifyHistoryData(historyDatas);
        log.info("classifyedHistoryData");
        log.info(classifyedHistoryData.size() + "");
        saveData(classifyedHistoryData);
    }

    @Override
    public List<HistoryData> pullData(DataRequest dataRequest) {
        String jsonString = JSON.toJSONString(dataRequest);
        log.info("json");
        log.info(jsonString);
        String response = httpService.sendPostRequest(url, jsonString);
        HistoryReponse historyReponse = JSON.parseObject(response, HistoryReponse.class);
        if (historyReponse.getCode() == successCode) {
            return JSONArray.parseArray(historyReponse.getMsg().toString(), HistoryData.class);
        } else {
            log.error(historyReponse.getMsg().toString());
            return null;
        }
    }

    /**
     * 将相同车型和车号的数据放到一个list中
     *
     * @param historyDatas
     * @return
     */
    private List<List<HistoryData>> classifyHistoryData(List<HistoryData> historyDatas) {
        List<TrainInfo> trainInfoList = new ArrayList<>();
        List<List<HistoryData>> list = new ArrayList<>();
        for (HistoryData historyData : historyDatas) {
            int index = getTrainInfoIndex(trainInfoList, historyData);
            if (index == -1) {
                List<HistoryData> historyDataList = new ArrayList<>();
                historyDataList.add(historyData);
                list.add(historyDataList);
            } else {
                list.get(index).add(historyData);
            }
        }
        return list;
    }

    private int getTrainInfoIndex(List<TrainInfo> trainInfoList, HistoryData historyData) {
        String trainNum = historyData.getTrainNum();
        String trainType = historyData.getTrainType();
        for (int i = 0; i < trainInfoList.size(); i++) {
            TrainInfo trainInfo = trainInfoList.get(i);
            if (trainInfo.getTrainNum().equals(trainNum) && trainInfo.getTrainType().equals(trainType)) {
                return i;
            }
        }
        // 走到这说明trainInfoList中不存在对应的车号和车型
        TrainInfo trainInfo = new TrainInfo();
        trainInfo.setTrainNum(trainNum);
        trainInfo.setTrainType(trainType);
        trainInfoList.add(trainInfo);
        return -1;

    }

    private void clearHistoryDatas(List<HistoryData> historyDatas) {
        Iterator<HistoryData> iterator = historyDatas.iterator();
        while (iterator.hasNext()) {
            HistoryData historyData = iterator.next();
            if (isNull(historyData)) {
                iterator.remove();
            }
        }
    }

    private boolean isNull(HistoryData historyData) {
        if (StringUtil.isEmpty(historyData.getOriginalprimarykey())) {
            return true;
        }
        Field[] fields = historyData.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String value = (String) field.get(historyData);
                if (value == null || value.isEmpty()) {
                    return true;
                }
                String name = field.getName();
                if (name.contains("axle")||name.contains("ambientTemperature")) {
                    if (!VerifyUtil.isNumber(value)) {
                        return true;
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    private void saveData(List<List<HistoryData>> classifyedHistoryData) throws Exception {
        for (List<HistoryData> historyDataList : classifyedHistoryData) {
            historyDataHandler.asyncSaveData(historyDataList);
        }
    }
}
