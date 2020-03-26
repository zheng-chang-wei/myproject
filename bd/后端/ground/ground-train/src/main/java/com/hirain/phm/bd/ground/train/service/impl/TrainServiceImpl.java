/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.train.dao.TrainMapper;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.ground.train.param.TrainCountResponse;
import com.hirain.phm.bd.ground.train.param.TrainParam;
import com.hirain.phm.bd.ground.train.param.TrainParamHeader;
import com.hirain.phm.bd.ground.train.service.TrainService;
import com.hirain.phm.bode.core.ICar;
import com.hirain.phm.bode.core.ITrain;
import com.hirain.phm.bode.core.util.SystemInfoUtil;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Apr 25, 2019 4:33:02 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 25, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class TrainServiceImpl extends BaseService<Train> implements TrainService {

	@Autowired
	private TrainMapper mapper;

	/**
	 * @see com.hirain.phm.bd.ground.train.service.TrainService.service.ITrainService#insertTrain(com.hirain.phm.bd.ground.db.model.LineRecord)
	 */
	@Override
	public void insertTrain(Train train) {
		mapper.insert(train);

	}

	/**
	 * @see com.hirain.phm.bd.ground.train.service.TrainService#findTrainsByParams(com.hirain.phm.bd.ground.train.param.TrainParam)
	 */
	@Override
	public List<TrainParam> findTrainsByParams(TrainParam trainParms) {
		return mapper.findTrainsByParams(trainParms);
	}

	@Override
	public List<Train> findTrains(TrainParamHeader trainParms) {
		return mapper.findTrains(trainParms);
	}

	@Override
	public List<Train> findTrainParamHeaderByUserId(Long userId) {
		return mapper.findTrainsByUserId(userId);
	}

	/**
	 * @see com.hirain.phm.bd.ground.train.service.TrainService#select(java.lang.String, java.lang.String)
	 */
	@Override
	public Train select(String project, String train) {
		return mapper.selectByProjectAndNo(project, train);
	}

	/**
	 * @see com.hirain.phm.bd.ground.train.service.TrainService#selectByCityAndLine(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Train> selectByCityAndLine(String city, String line) {
		return mapper.selectByCityAndLine(city, line);
	}

	/**
	 * @see com.hirain.phm.bd.ground.train.service.TrainService#selectByProject(java.lang.String)
	 */
	@Override
	public List<Train> selectByProject(String project) {
		return mapper.selectByProject(project);
	}

	/**
	 * @see com.hirain.phm.bd.ground.train.service.TrainService#countOnline()
	 */
	@Override
	public Integer countOnline() {
		Train record = new Train();
		record.setTrainOnline(true);
		return mapper.selectCount(record);
	}

	/**
	 * @see com.hirain.phm.bd.ground.train.service.TrainService#countAll()
	 */
	@Override
	public Integer countAll() {
		return mapper.selectCount(new Train());
	}

	/**
	 * @see com.hirain.phm.bd.ground.train.service.TrainService#countOnlineDoor()
	 */
	@Override
	public Integer countOnlineDoor() {
		Integer count = mapper.selectCountOfOnlineDoor();
		if (count == null) {
			count = 0;
		}
		return count;
	}

	/**
	 * @see com.hirain.phm.bd.ground.train.service.TrainService#countTrain()
	 */
	@Override
	public TrainCountResponse countTrain() {
		TrainCountResponse response = new TrainCountResponse();
		response.setTotal(countAll());
		response.setOnline(countOnline());
		response.setOnlineDoor(countOnlineDoor());
		return response;
	}

	@Override
	public ITrain getTrainConfigInfo(String project, String trainNo) throws Exception {
		Train train = select(project, trainNo);
		if (train != null) {
			String configInfo = train.getConfigInfo();
			if (configInfo != null) {
				return SystemInfoUtil.convertXmlBytes2SystemInfo(configInfo.getBytes());
			}
		}
		return null;
	}

	@Override
	public Integer getTrainDoorCount(String projectName) throws Exception {
		TrainParamHeader trainParms = new TrainParamHeader();
		trainParms.setProject(projectName);
		List<Train> trains = findTrains(trainParms);
		if (trains != null && trains.size() > 0) {
			Integer count = 0;
			ITrain train = getTrainConfigInfo(projectName, trains.get(0).getTrainNo());
			List<ICar> cars = train.getCars();
			for (ICar iCar : cars) {
				count += iCar.getDoors().size();
			}
			return count;
		}
		return null;
	}
}
