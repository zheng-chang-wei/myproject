package com.hirain.qsy.shaft.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.qsy.shaft.common.annotation.Log;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.common.util.HttpContextUtils;
import com.hirain.qsy.shaft.common.util.IPUtils;
import com.hirain.qsy.shaft.model.User;
import com.hirain.qsy.shaft.service.SessionService;

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

	@Autowired
	SessionService sessionService;

	@Log("登录")
	@PostMapping(value = "/login")
	public ResponseBo login(String accountname, String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(accountname, password);
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject != null)
				subject.logout();
			sessionService.forceLogoutByUserName(accountname);
			subject.login(token);
			Session session = subject.getSession();
			HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
			String ip = IPUtils.getIpAddr(request);
			session.setAttribute("ip", ip);
			session.setAttribute("user", subject.getPrincipal());
			return ResponseBo.ok();
		} catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
			log.error(e.getMessage());
			return ResponseBo.error(e.getMessage());
		} catch (AuthenticationException e) {
			log.error(e.getMessage(), e);
			return ResponseBo.error("认证失败！");
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			return ResponseBo.error("系统异常！");
		}
	}

	@PostMapping("/logout")
	public String logOut(HttpSession session) {

		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		// session.removeAttribute("user");
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
