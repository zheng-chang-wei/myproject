/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.maintenance.domain.WorkStep;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月29日 下午4:26:48
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月29日 jianwen.xin@hirain.com 1.0 create file
 */
public interface WorkStepMapper extends CommonMapper<WorkStep> {

	@Select("select * from t_step where sheet_id=#{sheetId} and type=#{type} order by seq desc limit 1,1")
	WorkStep findLastSampleType(Long sheetId, String type);

	@Select("select * from t_step where sheet_id=#{sheetId} order by seq")
	List<WorkStep> selectBySheetId(Long sheetId);
}
