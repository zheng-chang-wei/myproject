/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.communication;

import com.hirain.phm.bd.ground.train.param.TrainParam;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created May 20, 2019 3:58:56 PM  
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 20, 2019     zepei.tao@hirain.com             1.0        create file 
 */
public interface CommunicationService {

	/**
	 * 停止接收
	 */
	void stopReceived(TrainParam trainParam);

	/**
	* 恢复车辆
	*/
	void resumeReceived(TrainParam trainParam);

	/**
	* 注销车辆
	*/
	void logOff(TrainParam trainParam);

}
