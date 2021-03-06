package com.hirain.phm.bd.ground.fault.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.fault.domain.FaultInfo;

public interface FaultInfoService extends IService<FaultInfo> {

	/**
	 * 插入故障信息
	 * 
	 * @param faultInfo
	 * @return
	 */
	int insertFaultInfo(FaultInfo faultInfo);

	/**
	 * 通过故障名称，故障代码和项目id查询故障信息
	 * 
	 * @param faultName
	 * @param faultCode
	 * @param projectId
	 * @return
	 */
	List<FaultInfo> findByNameAndCode(String faultName, int faultCode, Integer projectId);

	List<FaultInfo> selectAllGroupByFaultName();

	/**
	 * @param project
	 * @return
	 */
	List<FaultInfo> findFaultInfoByProject(String project);

	/**
	 * @param project
	 */
	List<FaultInfo> selectAllWithDetail(String project);

	FaultInfo selectWithDetail(Integer id);

	/**
	 * @param info
	 */
	boolean checkDuplicate(FaultInfo info);

	/**
	 * @param list
	 */
	void insertList(List<FaultInfo> list);

}
