package com.hirain.phm.bd.ground.fault.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.event.FaultTopType;
import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.fault.dao.FaultDetailMapper;
import com.hirain.phm.bd.ground.fault.domain.FaultDetail;
import com.hirain.phm.bd.ground.fault.domain.FaultInfo;
import com.hirain.phm.bd.ground.fault.param.AnnualCountResponse;
import com.hirain.phm.bd.ground.fault.param.FaultDetailRequestParms;
import com.hirain.phm.bd.ground.fault.param.FaultDetailWithSuggestionParams;
import com.hirain.phm.bd.ground.fault.service.FaultDataService;
import com.hirain.phm.bd.ground.fault.service.FaultDetailService;
import com.hirain.phm.bd.ground.fault.service.FaultInfoService;
import com.hirain.phm.bd.ground.train.controller.TrainGateWay;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.ground.util.RedisUtil;
import com.hirain.phm.bd.message.header.MessageHeader;
import com.hirain.phm.bd.message.train.FaultMessage;

@Service
public class FaultDetailServiceImpl extends BaseService<FaultDetail> implements FaultDetailService {

	@Autowired
	private FaultDetailMapper faultDetailMapper;

	@Autowired
	private TrainGateWay trainGW;

	@Autowired
	private FaultInfoService faultInfoService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private RedisUtil redis;

	@Autowired
	private FaultDataService dataService;

	@Override
	public List<FaultDetailWithSuggestionParams> findFaultDetailsByParams(FaultDetailRequestParms faultDetailParms) {
		return faultDetailMapper.findFaultDetailsByParms(faultDetailParms);

	}

	@Override
	public void handleFaultMessage(MessageHeader header, FaultMessage faultMessage) {
		// 查询列车
		Train train = trainGW.selectTrain(header.getProject(), header.getTrain());
		// 查询线路
		String faultName = faultMessage.getFaultName();
		int faultCode = faultMessage.getFaultId();
		// 查询故障信息
		List<FaultInfo> faultInfos = faultInfoService.findByNameAndCode(faultName, faultCode, train.getProjectId());
		int faultInfoId = 0;
		// 如果故障信息不存在，插入故障信息
		if (faultInfos.isEmpty()) {
			FaultInfo faultInfo = new FaultInfo();
			faultInfo.setFaultName(faultName);
			faultInfo.setFaultCode(faultCode);
			faultInfo.setProjectId(train.getProjectId());
			faultInfoId = faultInfoService.insertFaultInfo(faultInfo);
		} else {
			faultInfoId = faultInfos.get(0).getId();
		}
		FaultDetail faultDetail = new FaultDetail();
		faultDetail.setCarNo(faultMessage.getCarriageId());
		faultDetail.setDebugMode(faultMessage.isDebug());
		faultDetail.setDoorAddr(faultMessage.getDoorId());
		faultDetail.setFaultInfoId(faultInfoId);
		faultDetail.setFaultTime(faultMessage.getTimestamp());
		faultDetail.setTrainId(train.getId());
		faultDetail.setStatistics(false);
		save(faultDetail);
		saveFaultData(faultDetail.getId(), faultMessage);
		addFaultToMonthRecord(faultDetail.getFaultTime());

		pushFaultMessage(faultDetail);
	}

	/**
	 * @param faultId
	 * @param faultMessage
	 */
	private void saveFaultData(Long faultId, FaultMessage faultMessage) {
		dataService.saveFaultData(faultId, faultMessage);
	}

	/**
	 * @param time
	 */
	private void addFaultToMonthRecord(Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		redis.hmincrement("fault-" + year, String.valueOf(month));
	}

	public void pushFaultMessage(FaultDetail faultDetail) {
		UnifiedFaultRecord record = new UnifiedFaultRecord();
		record.setType(FaultTopType.Fault);
		record.setId(faultDetail.getId());
		record.setTime(faultDetail.getFaultTime());
		FaultInfo faultInfo = faultInfoService.selectByKey(faultDetail.getFaultInfoId());
		record.setFaultName(faultInfo.getFaultName());
		record.setCode(faultInfo.getFaultCode());

		Train train = trainGW.findTrainById(faultDetail.getTrainId());
		record.setTrainId(train.getTrainNo());
		record.setProjectId(train.getProjectId());

		record.setCarriageId(faultDetail.getCarNo());
		record.setDoorId(faultDetail.getDoorAddr());
		publisher.publishEvent(record);
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultDetailService#selectToday()
	 */
	@Override
	public List<FaultDetailWithSuggestionParams> selectToday(String projectName, String trainNo) {
		return faultDetailMapper.selectToday(projectName, trainNo);
	}

	/** 
	 * @see com.hirain.phm.bd.ground.fault.service.FaultDetailService#selectToday()
	 */
	@Override
	public List<FaultDetailWithSuggestionParams> selectToday() {
		String todayDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
		String startTime = todayDate + " 00:00:00";
		String endTime = todayDate + " 23:59:59";
		return faultDetailMapper.selectToday4Bode(startTime, endTime);
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultDetailService#selectYearCounts(int)
	 */
	@Override
	public AnnualCountResponse selectYearCounts(int year) {
		AnnualCountResponse response = new AnnualCountResponse();
		response.setYear(year);
		Map<Object, Object> map = redis.hget("fault-" + year);
		List<Integer> values = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			Object value = map.get(String.valueOf(i + 1));
			values.add(value == null ? 0 : Integer.parseInt(value.toString()));
		}
		response.setCounts(values);
		return response;
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultDetailService#selectMonthCounts(int, int)
	 */
	@Override
	public Integer selectMonthCounts(int year, int month) {
		Object value = redis.hmget("fault-" + year, String.valueOf(month));
		return value == null ? 0 : Integer.parseInt(value.toString());
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultDetailService#selectAnnualCount(int)
	 */
	@Override
	public int selectAnnualCount(int year) {
		Map<Object, Object> counts = redis.hget("fault-" + year);
		int sum = 0;
		for (Object count : counts.values()) {
			sum += Integer.parseInt(count.toString());
		}
		return sum;
	}
}
