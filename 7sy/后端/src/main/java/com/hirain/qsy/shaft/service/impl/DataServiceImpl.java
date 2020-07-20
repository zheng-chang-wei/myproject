package com.hirain.qsy.shaft.service.impl;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.qsy.shaft.common.model.DataResponse;
import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.model.TrainInfo;
import com.hirain.qsy.shaft.service.DataService;
import com.hirain.qsy.shaft.service.ExceptionDataService;
import com.hirain.qsy.shaft.service.InitialDataService;
import com.hirain.qsy.shaft.service.RedisCacheService;
import com.hirain.qsy.shaft.service.TrainInfoService;

@Service
public class DataServiceImpl implements DataService {

	@Autowired
	private InitialDataService initialDataService;

	@Autowired
	private ExceptionDataService exceptionDataService;

	@Autowired
	private TrainInfoService trainInfoService;

	@Autowired
	private RedisCacheService redisCacheService;

	@Override
	public Map<String, Object> getList(QueryRequest request, String trainType, String trainNum) {
		List<TrainInfo> trainInfoList = trainInfoService.findList(trainType, trainNum);
		int totle = trainInfoList.size();
		List<DataResponse> dataResponses = new ArrayList<>();
		if (totle != 0) {
			int pageNum = request.getPageNum();
			int pageSize = request.getPageSize();
			for (int i = (pageNum - 1) * pageSize; i < (pageNum - 1) * pageSize + pageSize; i++) {
				if (i >= trainInfoList.size()) {
					break;
				}
				TrainInfo trainInfo = trainInfoList.get(i);
				Map<String, Object> initialDataMap = initialDataService.findMaxAndMinTime(trainInfo.getId());
				if (initialDataMap != null && !initialDataMap.isEmpty()) {
					DataResponse dataResponse = new DataResponse();
					dataResponse.setId(trainInfo.getId());
					dataResponse.setTrainNum(trainInfo.getTrainNum());
					dataResponse.setTrainType(trainInfo.getTrainType());
					dataResponse.setEarliestTime(initialDataMap.get("min").toString());
					dataResponse.setLatestTime(initialDataMap.get("max").toString());
					dataResponses.add(dataResponse);
				}
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("rows", dataResponses);
		map.put("total", totle);
		return map;
	}

	@Override
	public void delete(Integer trainId, String deadline) {
		exceptionDataService.deleteByTrainNumAndTime(trainId, deadline);
		initialDataService.deleteByTrainNumAndTime(trainId, deadline);
		redisCacheService.deleteCache(trainId, deadline);
	}

	@Override
	public Map<String, Object> getStorage() {
		Long total = 0L;
		Long free = 0L;
		File[] listRoots = File.listRoots();
		for (File file : listRoots) {
			total += file.getTotalSpace();
			free += file.getFreeSpace();
		}
		DecimalFormat df = new DecimalFormat("#.00");
		String totalString = df.format(total / 1024d / 1024d / 1024d);
		String freeString = df.format(free / 1024d / 1024d / 1024d);
		Map<String, Object> map = new HashMap<>();
		map.put("total", totalString);
		map.put("free", freeString);
		return map;
	}

	@Override
	@Transactional
	public void dropTable(Integer trainId) {
		trainInfoService.deleteByTrainId(trainId);
		initialDataService.dropTable(trainId);
		exceptionDataService.dropTable(trainId);
		redisCacheService.deleteCache(trainId);
	}
}
