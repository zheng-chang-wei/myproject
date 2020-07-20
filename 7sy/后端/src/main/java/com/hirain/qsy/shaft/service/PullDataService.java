package com.hirain.qsy.shaft.service;

import java.util.List;

import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.HistoryData;

public interface PullDataService {

	void handleHistoryData(DataRequest dataRequest) throws Exception;

	List<HistoryData> pullData(DataRequest dataRequest);

}
