package com.hirain.phm.bd.ground.fault.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.fault.domain.FaultDetail;
import com.hirain.phm.bd.ground.fault.param.AnnualCountResponse;
import com.hirain.phm.bd.ground.fault.param.FaultDetailRequestParams;
import com.hirain.phm.bd.ground.fault.param.FaultDetailResponseParams;
import com.hirain.phm.bd.message.header.MessageHeader;
import com.hirain.phm.bd.message.train.FaultMessage;

public interface FaultDetailService extends IService<FaultDetail> {

	List<FaultDetailResponseParams> findFaultDetailsByParams(FaultDetailRequestParams faultDetailParms);

	void handleFaultMessage(MessageHeader header, FaultMessage faultMessage);

	/**
	 * 博得首页查询故障预警的接口 @author zepei.tao
	 */
	List<FaultDetailResponseParams> selectToday();

	/**
	 * @param year
	 * @return
	 */
	AnnualCountResponse selectYearCounts(int year);

	/**
	 * @param year
	 * @param month
	 * @return
	 */
	Integer selectMonthCounts(int year, int month);

	/**
	 * @param i
	 */
	int selectAnnualCount(int year);

	/**
	 * @param list
	 */
	void getRepairAndSolution(List<FaultDetailResponseParams> list);

}
