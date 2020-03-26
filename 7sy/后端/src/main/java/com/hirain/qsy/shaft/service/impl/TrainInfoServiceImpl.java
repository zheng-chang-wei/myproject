package com.hirain.qsy.shaft.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.qsy.shaft.common.util.RedisService;
import com.hirain.qsy.shaft.dao.TrainInfoMapper;
import com.hirain.qsy.shaft.model.TrainInfo;
import com.hirain.qsy.shaft.service.TrainInfoService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class TrainInfoServiceImpl extends BaseService<TrainInfo> implements TrainInfoService {

	@Autowired
	private TrainInfoMapper trainInfoMapper;

	@Autowired
	RedisService redisService;

	@Override
	public List<Integer> findIdByTrainNum(String trainNum) {
		return trainInfoMapper.findIdByTrainNum(trainNum);
	}

	@Override
	public Integer findTrainInfoByTypeAndNum(String trainNum, String trainType) {
		return trainInfoMapper.findTrainInfoByTypeAndNum(trainNum, trainType);
	}

	@Override
	public synchronized Integer findTrainIdByTypeAndNum(String trainNum, String trainType) {
		Integer trainId = trainInfoMapper.findTrainInfoByTypeAndNum(trainNum, trainType);
		if (trainId == null) {
			TrainInfo info = new TrainInfo();
			info.setTrainType(trainType);
			info.setTrainNum(trainNum);
			save(info);
			redisService.deleteBypPttern("TrainInfoService*");
			return info.getId();
		} else {
			return trainId;
		}
	}

	@Override
	public List<String> getAllTrainType() {
		return trainInfoMapper.queryAllTrainType();
	}

	/**
	 * 根据车辆类型查询车号
	 * 
	 * @see com.hirain.qsy.shaft.service.TrainInfoService#queryTrainNumByType(java.lang.String)
	 */
	@Override
	public List<String> queryTrainNumByType(String trainType) {
		return trainInfoMapper.queryTrainNumByType(trainType);
	}

	@Override
	public List<TrainInfo> findList(String trainType, String trianNum) {
		Example example = new Example(TrainInfo.class);
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(trianNum)) {
			criteria.andCondition("train_num =", trianNum);
		}
		if (StringUtils.isNotBlank(trainType)) {
			criteria.andCondition("train_type =", trainType);
		}
		example.setOrderByClause("train_num");
		return this.selectByExample(example);
	}

	@Override
	public List<String> findTrainNums() {
		return trainInfoMapper.queryTrainNums();
	}

	@Override
	@Transactional
	public void deleteByTrainId(Integer trainId) {
		trainInfoMapper.deleteByPrimaryKey(trainId);
	}

}
