/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.rbac.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.synapsis.rbac.domain.User;
import com.hirain.phm.synapsis.response.ResultBean;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 13, 2020 12:01:43 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@Slf4j
public class LoginController {

	@PostMapping("/login")
	public ResultBean<String> login(String username, String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject != null) {
				subject.logout();
			}
			subject.login(token);
			subject.getSession().setAttribute("user", subject.getPrincipal());
			return new ResultBean<>("登录成功");
		} catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
			log.error(e.getMessage(), e);
			return new ResultBean<>(e.getMessage(), e);
		} catch (AuthenticationException e) {
			log.error(e.getMessage(), e);
			return new ResultBean<>("认证失败", e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResultBean<>("认证失败", e);
		}
	}

	@PostMapping("/logout")
	public ResultBean<String> logOut(HttpSession session) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return new ResultBean<>("logout");
	}

	@CrossOrigin
	@GetMapping(value = "/notlogin")
	public ResultBean<String> notlogin() {
		log.info("Not Login");
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		if (user == null) {
			return new ResultBean<>("NotLogin", null);
		} else {
			return new ResultBean<>();
		}
	}
}
