/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.service;

import java.util.List;

import com.hirain.phm.bd.ground.bigdata.domain.BigProjectEntity;
import com.hirain.phm.bd.ground.bigdata.param.DeleteDataParam;
import com.hirain.phm.bd.ground.bigdata.param.GroundData;
import com.hirain.phm.bd.ground.bigdata.param.GroundDataParam;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年6月6日 下午1:57:10
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年6月6日 jianwen.xin@hirain.com 1.0 create file
 */
public interface DataManageService {

	/**
	 * @return
	 */
	Integer getSpace();

	/**
	 * @param params
	 * @return
	 */
	List<GroundData> selectTrainData(GroundDataParam params);

	String delete(DeleteDataParam param) throws Exception;

	/**
	 * @return
	 */
	List<BigProjectEntity> getProjectSpace();

}
