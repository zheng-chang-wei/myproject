/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.ground.train.param.TrainCountResponse;
import com.hirain.phm.bd.ground.train.param.TrainParam;
import com.hirain.phm.bd.ground.train.param.TrainParamHeader;
import com.hirain.phm.bode.core.ITrain;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Apr 25, 2019 4:29:09 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 25, 2019 zepei.tao@hirain.com 1.0 create file
 */
public interface TrainService extends IService<Train> {

	/**
	 * 向数据表中插入一条列车数据
	 */
	void insertTrain(Train train);

	/**
	 * 通过各参数来查询相应的列车的集合
	 * 
	 * @param trainParms
	 * @return
	 */
	List<TrainParam> findTrainsByParams(TrainParam trainParms);

	List<Train> findTrains(TrainParamHeader trainParms);

	List<Train> findTrainParamHeaderByUserId(Long userId);

	/**
	 * @param project
	 * @param train
	 */
	Train select(String project, String train);

	/**
	 * @param city
	 * @param line
	 * @return
	 */
	List<Train> selectByCityAndLine(String city, String line);

	/**
	 * @param project
	 * @return
	 */
	List<Train> selectByProject(String project);

	/**
	 * @return
	 */
	Integer countOnline();

	/**
	 * @return
	 */
	Integer countAll();

	/**
	 * @return
	 */
	Integer countOnlineDoor();

	/**
	 * @return
	 */
	TrainCountResponse countTrain();

	/**
	 * 获取列车配置信息
	 * 
	 * @param project
	 * @param trainNo
	 * @return
	 * @throws Exception
	 */
	ITrain getTrainConfigInfo(String project, String trainNo) throws Exception;

	/**
	 * 获取列车门个数
	 * 
	 * @param project
	 * @param trainNo
	 * @return
	 * @throws Exception
	 */
	Integer getTrainDoorCount(String project) throws Exception;

}
