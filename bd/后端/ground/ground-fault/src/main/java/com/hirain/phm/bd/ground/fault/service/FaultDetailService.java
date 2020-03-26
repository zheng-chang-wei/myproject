package com.hirain.phm.bd.ground.fault.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.fault.domain.FaultDetail;
import com.hirain.phm.bd.ground.fault.param.AnnualCountResponse;
import com.hirain.phm.bd.ground.fault.param.FaultDetailRequestParms;
import com.hirain.phm.bd.ground.fault.param.FaultDetailWithSuggestionParams;
import com.hirain.phm.bd.message.header.MessageHeader;
import com.hirain.phm.bd.message.train.FaultMessage;

public interface FaultDetailService extends IService<FaultDetail> {

	List<FaultDetailWithSuggestionParams> findFaultDetailsByParams(FaultDetailRequestParms faultDetailParms);

	void handleFaultMessage(MessageHeader header, FaultMessage faultMessage);

	/**
	 * @return
	 */
	List<FaultDetailWithSuggestionParams> selectToday(String projectName, String trainNo);

	/**
	 * 博得首页查询故障预警的接口  @author zepei.tao
	 */
	List<FaultDetailWithSuggestionParams> selectToday();

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

}
