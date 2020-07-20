/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.dictionary.domain.Solution;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 8, 2020 5:03:38 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 8, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface SolutionMapper extends CommonMapper<Solution> {

	@Select("select ts.* from t_solution ts left join t_fault_solution tfs on tfs.solution_id=ts.id where tfs.fault_info_id=#{infoId}")
	List<Solution> selectByFaultInfoId(int infoId);

	@Select("select ts.* from t_solution ts left join t_subhealth_solution tss on tss.solution_id=ts.id where tss.subhealth_info_id=#{infoId}")
	List<Solution> selectBySubhealthInfoId(int infoId);
}
