/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.push.dao;

import org.apache.ibatis.annotations.Select;

import com.hirain.phm.bd.ground.push.mail.FaultDesc;

import tk.mybatis.mapper.common.Marker;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 1, 2020 4:11:05 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 1, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface FaultDescMapper extends Marker {

	@Select("select type,code,level,description from t_push where type=#{type} and code=#{code}")
	FaultDesc findByTypeAndCode(int type, int code);

}
