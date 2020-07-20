package com.hirain.phm.bd.ground.fault.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.fault.domain.FaultInfo;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年5月6日 下午5:30:36
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月6日 changwei.zheng@hirain.com 1.0 create file
 */
public interface FaultInfoMapper extends CommonMapper<FaultInfo> {

	@Select("SELECT fault_name,fault_code FROM t_fault_info group by fault_name,fault_code")
	List<FaultInfo> selectAllGroupByParam();

	@Select("select tf.* from t_fault_info tf left join t_project tp on tp.id=tf.project_id where tp.name=#{project} order by tf.fault_code")
	List<FaultInfo> findFaultInfoByProject(String project);

	@Select("select tf.* from t_fault_info tf left join t_project tp on tp.id=tf.project_id where tp.name=#{project}")
	@Results({

			@Result(column = "id", property = "id", id = true),
			@Result(property = "repairList", column = "id", many = @Many(select = "com.hirain.phm.bd.ground.dictionary.dao.RepairMapper.selectByFaultInfoId")),
			@Result(property = "solutionList", column = "id", many = @Many(select = "com.hirain.phm.bd.ground.dictionary.dao.SolutionMapper.selectByFaultInfoId"))

	})
	List<FaultInfo> selectAllWithDetail(String project);

	@Select("select tf.* from t_fault_info tf where tf.id=#{id}")
	@Results({

			@Result(column = "id", property = "id", id = true),
			@Result(property = "repairList", column = "id", many = @Many(select = "com.hirain.phm.bd.ground.dictionary.dao.RepairMapper.selectByFaultInfoId")),
			@Result(property = "solutionList", column = "id", many = @Many(select = "com.hirain.phm.bd.ground.dictionary.dao.SolutionMapper.selectByFaultInfoId"))

	})
	FaultInfo selectWithDetail(Integer id);
}