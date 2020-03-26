package com.hirain.phm.bd.ground.authority.service;

import java.util.List;

import com.hirain.phm.bd.ground.authority.domain.RepairUserRole;
import com.hirain.phm.bd.ground.common.service.IService;

public interface ApprovalRoleUserService extends IService<RepairUserRole> {

	public void deleteApprovalRoleUserByUserId(List<String> userIds);

	List<Integer> getRepairRolesByUserId(Long userId);
}
