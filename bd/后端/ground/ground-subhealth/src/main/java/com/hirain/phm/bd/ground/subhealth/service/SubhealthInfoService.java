/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created May 29, 2019 2:01:14 PM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 29, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.subhealth.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthInfo;

public interface SubhealthInfoService extends IService<SubhealthInfo> {

	/**
	 * @param project
	 * @return
	 */
	List<SubhealthInfo> selectAllWithDetail(String project);

	/**
	 * @param info
	 */
	boolean chechDuplicate(SubhealthInfo info);

	/**
	 * @param project
	 * @return
	 */
	List<SubhealthInfo> selectByProject(String project);

	/**
	 * @param subhealthInfoId
	 * @return
	 */
	SubhealthInfo selectWithDetail(Integer subhealthInfoId);

	/**
	 * @param list
	 */
	void insertList(List<SubhealthInfo> list);

}
