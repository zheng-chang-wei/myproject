package com.hirain.qsy.shaft.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.hirain.qsy.shaft.common.annotation.Log;
import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.common.model.RespCodeEnum;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.common.model.UserRequest;
import com.hirain.qsy.shaft.common.util.PageService;
import com.hirain.qsy.shaft.model.User;
import com.hirain.qsy.shaft.service.SessionService;
import com.hirain.qsy.shaft.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月8日 上午10:45:50
 * @Description
 *              <p>
 *              用户controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	SessionService sessionService;

	@Autowired
	PageService pageService;

	@GetMapping("/getUser")
	@RequiresAuthentication
	public ResponseBo getUser(Long userId) {
		try {
			return ResponseBo.ok(this.userService.findById(userId));
		} catch (Exception e) {
			log.error("获取用户失败", e);
			return ResponseBo.error("获取用户失败，请联系网站管理员！");
		}
	}

	@GetMapping("/getCurrentUser")
	@RequiresAuthentication
	public ResponseBo getUser() {
		try {
			return ResponseBo.ok(this.userService.findById(((User) SecurityUtils.getSubject().getPrincipal()).getId()));
		} catch (Exception e) {
			log.error("获取用户失败", e);
			return ResponseBo.error("获取用户失败，请联系网站管理员！");
		}
	}

	@GetMapping("/list")
	@RequiresAuthentication
	public ResponseBo userList(QueryRequest request, UserRequest user) {
		try {
			Long currentUserId = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
			// 超级管理员用户id为1
			if (currentUserId == 1) {
				return ResponseBo.ok(pageService.selectByPageNumSize(request, () -> this.userService.findUserWithRole(user)));
			} else {
				user.setId(currentUserId);
				user.setParentId(currentUserId);
				return ResponseBo.ok(pageService.selectByPageNumSize(request, () -> this.userService.findUserWithRoleByParentId(user)));
			}
		} catch (Exception e) {
			log.error("获取用户失败", e);
			return ResponseBo.error("获取用户失败，请联系网站管理员！");
		}
	}

	@Log("新增用户")
	@PostMapping("/addUser")
	@RequiresAuthentication
	public ResponseBo addUser(User user, Long[] roles) {
		try {
			if (userService.findByName(user.getUsername()) == null) {
				User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
				user.setParentId(currentUser.getId());
				user.setParentName(currentUser.getName());
				this.userService.addUser(user, roles);
				return ResponseBo.ok("新增用户成功！");
			} else {
				return ResponseBo.warn(RespCodeEnum.RepeatName);
			}
		} catch (Exception e) {
			log.error("新增用户失败", e);
			return ResponseBo.error("新增用户失败，请联系网站管理员！");
		}
	}

	@Log("修改当前用户信息")
	@PostMapping("/editUser")
	@RequiresAuthentication
	public ResponseBo editUser(User user, String oldPassword) {
		try {
			if (oldPassword == null) {
				User userOther = userService.findByName(user.getUsername());
				if (userOther == null || userOther.getId().equals(user.getId())) {
					this.userService.updateNotNull(user);
					return ResponseBo.ok("修改用户成功！");
				} else {
					return ResponseBo.warn(RespCodeEnum.RepeatName);
				}
			} else {
				User currentUser = userService.selectByKey(((User) SecurityUtils.getSubject().getPrincipal()).getId());
				if (currentUser.getPassword().equals(oldPassword)) {
					this.userService.updateNotNull(user);
					return ResponseBo.ok("修改用户成功！");
				} else {
					return ResponseBo.error("旧密码错误");
				}
			}
		} catch (Exception e) {
			log.error("修改用户失败", e);
			return ResponseBo.error("修改用户失败，请联系网站管理员！");
		}
	}

	@Log("修改用户")
	@PostMapping("/updateUser")
	@RequiresAuthentication
	public ResponseBo updateUser(User user, Long[] roles) {
		try {
			User userOther = userService.findByName(user.getUsername());
			if (userOther == null || userOther.getId().equals(user.getId())) {
				this.userService.updateUser(user, roles);
				return ResponseBo.ok("修改用户成功！");
			} else {
				return ResponseBo.warn(RespCodeEnum.RepeatName);
			}
		} catch (Exception e) {
			log.error("修改用户失败", e);
			return ResponseBo.error("修改用户失败，请联系网站管理员！");
		}
	}

	@Log("删除用户")
	@PostMapping("/deleteUser")
	@RequiresAuthentication
	public ResponseBo deleteUsers(String[] userIds) {
		try {
			List<String> userIdList = Arrays.asList(userIds);
			User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
			if (userIdList.contains(String.valueOf(currentUser.getId()))) {
				return ResponseBo.warn(RespCodeEnum.CanNotDeleteCurrentLoginUser);
			} else if (userIdList.contains("1")) {
				return ResponseBo.warn(RespCodeEnum.CanNotDeleteAdministrator);
			} else {
				this.userService.deleteUsers(userIdList);
				sessionService.forceLogoutByUserId(userIds);
				return ResponseBo.ok("删除用户成功！");
			}
		} catch (Exception e) {
			log.error("删除用户失败", e);
			return ResponseBo.error("删除用户失败，请联系网站管理员！");
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}
}
