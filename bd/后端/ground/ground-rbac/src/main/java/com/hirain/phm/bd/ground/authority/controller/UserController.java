package com.hirain.phm.bd.ground.authority.controller;

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

import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.authority.service.UserService;
import com.hirain.phm.bd.ground.common.annotation.Log;
import com.hirain.phm.bd.ground.common.model.RespCodeEnum;
import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.common.page.PageService;
import com.hirain.phm.bd.ground.common.page.QueryRequest;

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
	PageService pageService;

	@GetMapping("/getUserById")
	@RequiresAuthentication
	public ResponseBo getUser(Long userId) {
		try {
			return ResponseBo.ok(userService.findById(userId));
		} catch (Exception e) {
			log.error("获取用户失败", e);
			return ResponseBo.error("获取用户失败，请联系网站管理员！");
		}
	}

	/**
	 * @unused
	 * @return
	 */
	@GetMapping("/getCurrentUser")
	@RequiresAuthentication
	public ResponseBo getUser() {
		try {
			return ResponseBo.ok(userService.findById(((User) SecurityUtils.getSubject().getPrincipal()).getUserId()));
		} catch (Exception e) {
			log.error("获取用户失败", e);
			return ResponseBo.error("获取用户失败，请联系网站管理员！");
		}
	}

	@GetMapping("/list")
	@RequiresAuthentication
	public ResponseBo userList(QueryRequest request, UserRequest user) {
		try {
			return ResponseBo.ok(pageService.selectByPageNumSize(request, () -> userService.findUserWithCondition(user)));
		} catch (Exception e) {
			log.error("获取用户失败", e);
			return ResponseBo.error("获取用户失败，请联系网站管理员！");
		}
	}

	@PostMapping("/addUser")
	@RequiresAuthentication
	@Log("添加用户")
	public ResponseBo addUser(User user, Long[] roleIds, Integer[] checkedApprovalRoleIds, String[] projectIds) {
		try {
			if (userService.findByName(user.getUserName()) == null) {
				userService.addUser(user, roleIds, checkedApprovalRoleIds, projectIds);
				return ResponseBo.ok("新增用户成功！");
			} else {
				return ResponseBo.warn(RespCodeEnum.RepeatName);
			}
		} catch (Exception e) {
			log.error("新增用户失败", e);
			return ResponseBo.error("新增用户失败，请联系网站管理员！");
		}
	}

	@PostMapping("/updateUser")
	@RequiresAuthentication
	@Log("编辑用户")
	public ResponseBo updateUser(User user, Long[] roleIds, Integer[] checkedApprovalRoleIds, String[] projectIds) {
		try {
			User userOther = userService.findByName(user.getUserName());
			if (userOther == null || userOther.getUserId().equals(user.getUserId())) {
				userService.updateUser(user, roleIds, checkedApprovalRoleIds, projectIds);
				return ResponseBo.ok("修改用户成功！");
			} else {
				return ResponseBo.warn(RespCodeEnum.RepeatName);
			}
		} catch (Exception e) {
			log.error("修改用户失败", e);
			return ResponseBo.error("修改用户失败，请联系网站管理员！");
		}
	}

	@PostMapping("/deleteUser")
	@RequiresAuthentication
	@Log("删除用户")
	public ResponseBo deleteUsers(String[] userIds) {
		try {
			List<String> userIdList = Arrays.asList(userIds);
			User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
			if (userIdList.contains(String.valueOf(currentUser.getUserId()))) {
				return ResponseBo.warn(RespCodeEnum.CanNotDeleteCurrentLoginUser);
			} else if (userIdList.contains("1")) {
				return ResponseBo.warn(RespCodeEnum.CanNotDeleteAdministrator);
			} else {
				userService.deleteUsers(userIdList);
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
