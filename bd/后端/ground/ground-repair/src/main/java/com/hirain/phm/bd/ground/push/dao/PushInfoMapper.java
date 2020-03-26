/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.push.dao;

import org.apache.ibatis.annotations.Select;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.push.domain.PushInfo;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Oct 18, 2019 10:53:19 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Oct 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface PushInfoMapper extends CommonMapper<PushInfo> {

	@Select("select * from t_push where type=#{type} and code=#{code}")
	public PushInfo findByKey(int type, int code);
}
