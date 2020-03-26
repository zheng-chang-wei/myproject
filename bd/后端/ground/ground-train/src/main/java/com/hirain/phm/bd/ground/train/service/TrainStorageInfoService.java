/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created Jun 6, 2019 4:04:11 PM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Jun 6, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.train.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.event.TrainOnlineEvent;
import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.train.domain.TrainStorageInfo;
import com.hirain.phm.bd.ground.train.param.DeleteDataParam;
import com.hirain.phm.bd.ground.train.param.TrainDMResponse;
import com.hirain.phm.bd.ground.train.param.TrainParamHeader;
import com.hirain.phm.bd.message.train.StorageMessage;

public interface TrainStorageInfoService extends IService<TrainStorageInfo> {

	/**
	 * @param packet
	 */
	void handleStorageMessage(StorageMessage packet);

	List<TrainDMResponse> findByTrainParam(TrainParamHeader trainParam);

	String deleteTrainData(DeleteDataParam param);

	void updateTrainInfo(TrainOnlineEvent message);

}
