/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.rbac;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.rbac.domain.Role;
import com.hirain.phm.synapsis.rbac.domain.User;
import com.hirain.phm.synapsis.rbac.service.UserService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 13, 2020 11:22:31 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class SynapsisAuthRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	/**
	 * 授权模块，获取用户角色和权限
	 *
	 * @param principal
	 *            principal
	 * @return AuthorizationInfo 权限信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		String username = user.getUsername();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		User userAll = userService.selectUserAll(username);

		Role role = userAll.getRole();
		info.setRoles(new HashSet<>(Arrays.asList(role.getRolename())));

		Set<String> permissions = role.getPermissions().stream().map(p -> p.getPermission()).collect(Collectors.toSet());
		info.setStringPermissions(permissions);

		return info;
	}

	/**
	 * 用户认证
	 *
	 * @param token
	 *            AuthenticationToken 身份认证 token
	 * @return AuthenticationInfo 身份认证信息
	 * @throws AuthenticationException
	 *             认证相关异常
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());
		User user = userService.selectByName(username);
		if (user == null) {
			throw new UnknownAccountException("用户名或密码错误！");
		}
		if (!password.equals(user.getPassword())) {
			throw new IncorrectCredentialsException("用户名或密码错误！");
		}
		return new SimpleAuthenticationInfo(username, password, getName());
	}

}
