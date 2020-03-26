package com.hirain.phm.bd.ground.authority.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.common.annotation.Log;
import com.hirain.phm.bd.ground.common.model.ResponseBo;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月8日 上午10:46:46
 * @Description
 *              <p>
 *              登录controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
@Slf4j
public class LoginController {

	@PostMapping(value = "/login")
	@Log("登录")
	public ResponseBo login(String accountname, String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(accountname, password);
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject != null) {
				subject.logout();
			}
			subject.login(token);
			subject.getSession().setAttribute("user", subject.getPrincipal());
			return ResponseBo.ok();
		} catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
			log.error(e.getMessage());
			return ResponseBo.error(e.getMessage());
		} catch (AuthenticationException e) {
			log.error(e.getMessage());
			return ResponseBo.error("认证失败！");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return ResponseBo.error("认证失败！");
		}
	}

	@PostMapping("/logout")
	public String logOut(HttpSession session) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "login";
	}

	@GetMapping(value = "/notlogin")
	public ResponseBo notlogin() {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		if (user == null) {
			return ResponseBo.error("NotLogin");
		} else {
			return ResponseBo.ok();
		}
	}
}
