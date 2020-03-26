package com.hirain.phm.bd.ground.authority.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.phm.bd.ground.authority.controller.UserRequest;
import com.hirain.phm.bd.ground.authority.dao.UserMapper;
import com.hirain.phm.bd.ground.authority.domain.GroundRole;
import com.hirain.phm.bd.ground.authority.domain.GroundUserRole;
import com.hirain.phm.bd.ground.authority.domain.ProjectUser;
import com.hirain.phm.bd.ground.authority.domain.RepairUserRole;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.authority.service.ApprovalRoleUserService;
import com.hirain.phm.bd.ground.authority.service.GroundUserRoleService;
import com.hirain.phm.bd.ground.authority.service.ProjectUserService;
import com.hirain.phm.bd.ground.authority.service.UserService;
import com.hirain.phm.bd.ground.common.service.BaseService;

import tk.mybatis.mapper.entity.Example;

@Service("userService")
public class UserServiceImpl extends BaseService<User> implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private GroundUserRoleService groundUserRoleService;

	@Autowired
	private ProjectUserService lineUserService;

	@Autowired
	private ApprovalRoleUserService approvalRoleUserService;

	@Override
	public User findByName(String userName) {
		Example example = new Example(User.class);
		example.createCriteria().andCondition("lower(user_name)=", userName.toLowerCase());
		List<User> list = selectByExample(example);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public List<User> findUserWithCondition(UserRequest user) {
		return userMapper.findUserWithCondition(user);
	}

	@Override
	@Transactional
	public void addUser(User user, Long[] roleIds, Integer[] approvalRoleIds, String[] projectIds) {
		user.setCreateTime(new Date());
		save(user);
		setUserGroundRoles(user, roleIds);
		setUserProject(user, projectIds);
		setUserApprovalRoles(user, approvalRoleIds);
	}

	@Override
	@Transactional
	public void addUser(User user) {
		user.setCreateTime(new Date());
		save(user);
		List<GroundRole> groundRoleIds = user.getGroundRoles();
		List<Long> roleIds = new ArrayList<>();
		for (GroundRole groundRole : groundRoleIds) {
			roleIds.add(groundRole.getGroundRoleId());
		}
		setUserGroundRoles(user, roleIds.toArray(new Long[0]));
	}

	private void setUserGroundRoles(User user, Long[] roleIds) {
		for (Long roleId : roleIds) {
			GroundUserRole ur = new GroundUserRole();
			ur.setUserId(user.getUserId());
			ur.setGroundRoleId(roleId);
			groundUserRoleService.save(ur);
		}
	}

	private void setUserProject(User user, String[] projectIds) {
		for (String project : projectIds) {
			ProjectUser lur = new ProjectUser();
			lur.setUserId(user.getUserId());
			lur.setProjectId(Long.valueOf(project));
			lineUserService.save(lur);
		}
	}

	private void setUserApprovalRoles(User user, Integer[] approvalRoleIds) {
		for (Integer approvalRoleId : approvalRoleIds) {
			RepairUserRole approvalRoleUser = new RepairUserRole();
			approvalRoleUser.setRepairRoleId(approvalRoleId);
			approvalRoleUser.setUserId(user.getUserId());
			approvalRoleUserService.save(approvalRoleUser);
		}

	}

	@Override
	@Transactional
	public void updateUser(User user, Long[] roleIds, Integer[] approvalRoleIds, String[] projectIds) {
		user.setModifyTime(new Date());
		updateNotNull(user);
		deleteRoles(user);
		setUserGroundRoles(user, roleIds);
		setUserProject(user, projectIds);
		setUserApprovalRoles(user, approvalRoleIds);
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		user.setModifyTime(new Date());
		updateNotNull(user);
		deleteRoles(user);
		List<GroundRole> groundRoleIds = user.getGroundRoles();
		List<Long> roleIds = new ArrayList<>();
		for (GroundRole groundRole : groundRoleIds) {
			roleIds.add(groundRole.getGroundRoleId());
		}
		setUserGroundRoles(user, roleIds.toArray(new Long[0]));
	}

	private void deleteRoles(User user) {
		List<String> userIds = new ArrayList<>();
		userIds.add(String.valueOf(user.getId()));
		deleteRelations(userIds);
	}

	@Override
	@Transactional
	public void deleteUsers(List<String> userIds) {
		batchDelete(userIds, "userId", User.class);
		deleteRelations(userIds);
	}

	private void deleteRelations(List<String> userIds) {
		groundUserRoleService.deleteUserRolesByUserId(userIds);
		lineUserService.deleteUserRolesByUserId(userIds);
		approvalRoleUserService.deleteApprovalRoleUserByUserId(userIds);
	}

	@Override
	@Transactional
	public void updatePassword(String password) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		Example example = new Example(User.class);
		example.createCriteria().andCondition("user_name=", user.getUserName());
		userMapper.updateByExampleSelective(user, example);
	}

	@Override
	public User findById(Long userId) {
		User user = userMapper.findUserWithRole(userId);
		return user;
	}

	@Override
	public void insertUsers(List<User> users) {
		for (User user : users) {
			User dbUser = findByName(user.getUserName());
			if (dbUser == null) {
				addUser(user);
			} else {
				dbUser.setPassword(user.getPassword());
				dbUser.setName(user.getName());
				dbUser.setEmail(user.getEmail());
				dbUser.setMobile(user.getMobile());
				dbUser.setWechat(user.getWechat());
				dbUser.setDeptId(user.getDeptId());
				dbUser.setGroundRoles(user.getGroundRoles());
				updateUser(dbUser);
			}
		}
	}

}
