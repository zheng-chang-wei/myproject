/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.dictionary.domain.Repair;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 8, 2020 5:03:20 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 8, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface RepairMapper extends CommonMapper<Repair> {

	@Select("select tr.* from t_repair tr left join t_fault_repair tfr on tfr.repair_id=tr.id where tfr.fault_info_id=#{infoId}")
	List<Repair> selectByFaultInfoId(int infoId);

	@Select("select tr.* from t_repair tr left join t_subhealth_repair tsr on tsr.repair_id=tr.id where tsr.subhealth_info_id=#{infoId}")
	List<Repair> selectBySubhealthInfoId(int infoId);

}
