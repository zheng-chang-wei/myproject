/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created Jun 3, 2019 10:03:35 AM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Jun 3, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.subhealth.service;

import java.util.List;

import com.hirain.phm.bd.ground.subhealth.param.AnnualCountResponse;
import com.hirain.phm.bd.ground.subhealth.param.SubhealthDetailParams;
import com.hirain.phm.bd.ground.subhealth.param.SubhealthDetailResponseParams;

public interface SubhealthQueryService {

	public List<SubhealthDetailResponseParams> selectByParams(SubhealthDetailParams subdeDetailParams);

	/**
	 * 博得首页查询亚健康预警的接口 @author zepei.tao
	 */
	List<SubhealthDetailResponseParams> selectToday();

	/**
	 * @param year
	 * @param month
	 * @return
	 */
	Integer selectMonthCounts(int year, int month);

	/**
	 * @param year
	 * @return
	 */
	int selectAnnualCount(int year);

	/**
	 * @param year
	 * @return
	 */
	AnnualCountResponse selectYearCounts(int year);

	/**
	 * @param details
	 */
	void getRepairAndSolution(List<SubhealthDetailResponseParams> details);

}
