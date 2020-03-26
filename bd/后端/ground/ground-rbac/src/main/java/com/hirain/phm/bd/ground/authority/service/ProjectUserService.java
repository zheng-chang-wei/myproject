package com.hirain.phm.bd.ground.authority.service;

import java.util.List;

import com.hirain.phm.bd.ground.authority.domain.ProjectUser;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.common.service.IService;

public interface ProjectUserService extends IService<ProjectUser> {

	void deleteUserRolesByUserId(List<String> userIds);

	List<Long> getProjectsByUserID(Long userId);

	List<User> getUsersOfProject(String project);

	List<User> getUsersByProjectID(Integer projectId);
}
