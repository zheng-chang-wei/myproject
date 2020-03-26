package com.hirain.phm.bd.ground.authority.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.phm.bd.ground.authority.dao.ProjectUserMapper;
import com.hirain.phm.bd.ground.authority.domain.ProjectUser;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.authority.service.ProjectUserService;
import com.hirain.phm.bd.ground.common.service.BaseService;

@Service("lineUserRoleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProjectUserServiceImpl extends BaseService<ProjectUser> implements ProjectUserService {

	@Autowired
	private ProjectUserMapper mapper;

	@Override
	@Transactional
	public void deleteUserRolesByUserId(List<String> userIds) {
		batchDelete(userIds, "userId", ProjectUser.class);
	}

	@Override
	public List<Long> getProjectsByUserID(Long userId) {
		return mapper.getProjectUserRolesByUserID(userId);
	}

	@Override
	public List<User> getUsersOfProject(String project) {
		return mapper.getUsersOfProject(project);
	}

	/**
	 * @see com.hirain.phm.bd.ground.authority.service.ProjectUserService#getUsersByProjectID(java.lang.Integer)
	 */
	@Override
	public List<User> getUsersByProjectID(Integer projectId) {
		return mapper.getUsersByProjectID(projectId);
	}

}
