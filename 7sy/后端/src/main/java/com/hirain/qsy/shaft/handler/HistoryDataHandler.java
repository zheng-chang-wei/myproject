package com.hirain.qsy.shaft.handler;

import com.alibaba.fastjson.JSON;
import com.hirain.qsy.shaft.common.model.HistoryData;
import com.hirain.qsy.shaft.model.InitialData;
import com.hirain.qsy.shaft.model.PythonData;
import com.hirain.qsy.shaft.service.ExceptionDataService;
import com.hirain.qsy.shaft.service.InitialDataService;
import com.hirain.qsy.shaft.service.RedisCacheService;
import com.hirain.qsy.shaft.service.TrainInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class HistoryDataHandler {

    @Autowired
    private TrainInfoService trainInfoService;

    @Autowired
    private InitialDataService initialDataService;

    @Autowired
    private ExceptionDataService exceptionDataService;

    @Autowired
    private RedisCacheService redisCacheService;


    @Async
    @Transactional
    public void asyncSaveData(List<HistoryData> historyDataList) throws Exception {
        log.info("historyDataList");
        log.info(historyDataList.size() + "");
        HistoryData firstHistoryData = historyDataList.get(0);
        Integer trainId = trainInfoService.findTrainIdByTypeAndNum(firstHistoryData.getTrainNum(), firstHistoryData.getTrainType());
        List<InitialData> initialDatas = transformToInitialData(historyDataList, trainId);
        log.info("initialDatas");
        log.info(initialDatas.size() + "");
        if (initialDatas.size() > 100) {
            initialDataService.createTable(initialDatas, trainId);
            int save = initialDataService.save(initialDatas, trainId);
            if (save != 0) {
                saveExceptionData(historyDataList, trainId);
                redisCacheService.deleteBypPttern("DataService*");
                redisCacheService.deleteBypPttern("StatisticsService*");
            }
        }
    }

    private List<InitialData> transformToInitialData(List<HistoryData> historyDatas, Integer trainId) {
        sortHistoryDataByAcquisitionTime(historyDatas);
        List<InitialData> list = new ArrayList<>();
        for (HistoryData historyData : historyDatas) {
            InitialData initialData = new InitialData();
            initialData.setTrainId(trainId);
            setInitialData(historyData, initialData);
            list.add(initialData);
        }
        return list;
    }

    private void sortHistoryDataByAcquisitionTime(List<HistoryData> historyDatas) {
        Collections.sort(historyDatas, new Comparator<HistoryData>() {

            @Override
            public int compare(HistoryData o1, HistoryData o2) {
                return o1.getAcquisitionTime().compareTo(o2.getAcquisitionTime());
            }
        });
    }

    private void setInitialData(HistoryData historyData, InitialData initialData) {
        initialData.setAcquisitionTime(historyData.getAcquisitionTime());
        initialData.setAmbientTemperature1(Integer.valueOf(historyData.getAmbientTemperature1()));
        initialData.setAmbientTemperature2(Integer.valueOf(historyData.getAmbientTemperature2()));
        initialData.setGpsSpeed(Integer.valueOf(historyData.getGpsSpeed()));
        initialData.setOriginalprimarykey(historyData.getOriginalprimarykey());
        initialData.setAxle11(Integer.valueOf(historyData.getAxle11()));
        initialData.setAxle12(Integer.valueOf(historyData.getAxle12()));
        initialData.setAxle13(Integer.valueOf(historyData.getAxle13()));
        initialData.setAxle14(Integer.valueOf(historyData.getAxle14()));
        initialData.setAxle15(Integer.valueOf(historyData.getAxle15()));
        initialData.setAxle16(Integer.valueOf(historyData.getAxle16()));

        initialData.setAxle21(Integer.valueOf(historyData.getAxle21()));
        initialData.setAxle22(Integer.valueOf(historyData.getAxle22()));
        initialData.setAxle23(Integer.valueOf(historyData.getAxle23()));
        initialData.setAxle24(Integer.valueOf(historyData.getAxle24()));
        initialData.setAxle25(Integer.valueOf(historyData.getAxle25()));
        initialData.setAxle26(Integer.valueOf(historyData.getAxle26()));

        initialData.setAxle31(Integer.valueOf(historyData.getAxle31()));
        initialData.setAxle32(Integer.valueOf(historyData.getAxle32()));
        initialData.setAxle33(Integer.valueOf(historyData.getAxle33()));
        initialData.setAxle34(Integer.valueOf(historyData.getAxle34()));
        initialData.setAxle35(Integer.valueOf(historyData.getAxle35()));
        initialData.setAxle36(Integer.valueOf(historyData.getAxle36()));

        initialData.setAxle41(Integer.valueOf(historyData.getAxle41()));
        initialData.setAxle42(Integer.valueOf(historyData.getAxle42()));
        initialData.setAxle43(Integer.valueOf(historyData.getAxle43()));
        initialData.setAxle44(Integer.valueOf(historyData.getAxle44()));
        initialData.setAxle45(Integer.valueOf(historyData.getAxle45()));
        initialData.setAxle46(Integer.valueOf(historyData.getAxle46()));

        initialData.setAxle51(Integer.valueOf(historyData.getAxle51()));
        initialData.setAxle52(Integer.valueOf(historyData.getAxle52()));
        initialData.setAxle53(Integer.valueOf(historyData.getAxle53()));
        initialData.setAxle54(Integer.valueOf(historyData.getAxle54()));
        initialData.setAxle55(Integer.valueOf(historyData.getAxle55()));
        initialData.setAxle56(Integer.valueOf(historyData.getAxle56()));

        initialData.setAxle61(Integer.valueOf(historyData.getAxle61()));
        initialData.setAxle62(Integer.valueOf(historyData.getAxle62()));
        initialData.setAxle63(Integer.valueOf(historyData.getAxle63()));
        initialData.setAxle64(Integer.valueOf(historyData.getAxle64()));
        initialData.setAxle65(Integer.valueOf(historyData.getAxle65()));
        initialData.setAxle66(Integer.valueOf(historyData.getAxle66()));
    }

    private void saveExceptionData(List<HistoryData> historyDataList, Integer trainId) throws Exception {
        List<Date> acquisitionTimeList = new ArrayList<>();
        List<String> primaryKeyList = new ArrayList<>();
        PythonData pythonData = transformToPythonData(historyDataList, acquisitionTimeList, primaryKeyList);
        exceptionDataService.saveData(JSON.toJSONString(pythonData), acquisitionTimeList, primaryKeyList, trainId);
    }

    private PythonData transformToPythonData(List<HistoryData> historyDataList, List<Date> acquisitionTimeList, List<String> primaryKeyList)
            throws Exception {
        List<Integer> gpsSpeed = new ArrayList<>();
        List<Integer> ambientTemperatures1 = new ArrayList<>();
        List<Integer> ambientTemperatures2 = new ArrayList<>();
        PythonData pythonData = new PythonData();
        List<List<Integer>> temperatures = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            temperatures.add(new ArrayList<>());
        }
        for (HistoryData historyData : historyDataList) {
            pythonData.setTrain_No(Integer.valueOf(historyData.getTrainNum()));
            acquisitionTimeList.add(historyData.getAcquisitionTime());
            primaryKeyList.add(historyData.getOriginalprimarykey());
            gpsSpeed.add(Integer.valueOf(historyData.getGpsSpeed()));
            ambientTemperatures1.add(Integer.valueOf(historyData.getAmbientTemperature1()));
            ambientTemperatures2.add(Integer.valueOf(historyData.getAmbientTemperature2()));
            addTemperatures(temperatures, historyData);

        }
        setTemperature(pythonData, temperatures);
        pythonData.setAmbientTemperature_1(ambientTemperatures1);
        pythonData.setAmbientTemperature_2(ambientTemperatures2);
        pythonData.setGPSSpeed(gpsSpeed);
        return pythonData;
    }

    private void addTemperatures(List<List<Integer>> temperatures, HistoryData historyData) throws Exception {
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                String property = BeanUtils.getProperty(historyData, "axle" + i + j);
                temperatures.get(j + (i - 1) * 6 - 1).add(Integer.valueOf(property));
            }
        }
    }

    private void setTemperature(PythonData pythonData, List<List<Integer>> temperatures) throws Exception {
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                List<Integer> value = temperatures.get(j + (i - 1) * 6 - 1);
                String name = "temperatureOnPoint_" + i + j;
                BeanUtils.setProperty(pythonData, name, value);
            }
        }
    }

}
