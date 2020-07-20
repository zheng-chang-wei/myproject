/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created May 24, 2019 3:57:29 PM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 24, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.subhealth.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthInfo;

public interface SubhealthInfoMapper extends CommonMapper<SubhealthInfo> {

	@Select("select ts.* from t_subhealth_type ts left join t_project tp on tp.id=ts.project_id where tp.name=#{project}")
	@Results({

			@Result(column = "id", property = "id", id = true),
			@Result(property = "repairList", column = "id", many = @Many(select = "com.hirain.phm.bd.ground.dictionary.dao.RepairMapper.selectBySubhealthInfoId")),
			@Result(property = "solutionList", column = "id", many = @Many(select = "com.hirain.phm.bd.ground.dictionary.dao.SolutionMapper.selectBySubhealthInfoId"))

	})
	List<SubhealthInfo> selectAllWithDetail(String project);

	@Select("select ts.* from t_subhealth_type ts left join t_project tp on tp.id=ts.project_id where tp.name=#{project}")
	List<SubhealthInfo> selectByProject(String project);

	@Select("select ts.* from t_subhealth_type ts where ts.id=#{subhealthInfoId}")
	@Results({

			@Result(column = "id", property = "id", id = true),
			@Result(property = "repairList", column = "id", many = @Many(select = "com.hirain.phm.bd.ground.dictionary.dao.RepairMapper.selectBySubhealthInfoId")),
			@Result(property = "solutionList", column = "id", many = @Many(select = "com.hirain.phm.bd.ground.dictionary.dao.SolutionMapper.selectBySubhealthInfoId"))

	})
	SubhealthInfo selectWithDetail(Integer subhealthInfoId);

}
