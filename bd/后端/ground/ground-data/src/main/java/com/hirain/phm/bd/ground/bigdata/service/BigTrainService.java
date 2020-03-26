/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.service;

import java.util.List;

import com.hirain.phm.bd.ground.bigdata.domain.BigTrainEntity;
import com.hirain.phm.bd.ground.bigdata.param.GroundData;
import com.hirain.phm.bd.ground.bigdata.param.GroundDataParam;
import com.hirain.phm.bd.ground.common.service.IService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年6月5日 下午5:41:53
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年6月5日 jianwen.xin@hirain.com 1.0 create file
 */
public interface BigTrainService extends IService<BigTrainEntity> {

	/**
	 * @param param
	 * @return
	 */
	List<GroundData> findBigTrainByParam(GroundDataParam param);

	BigTrainEntity selectByTrainId(Integer trainId);
}
