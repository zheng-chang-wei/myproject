package com.hirain.phm.bd.ground.authority.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.authority.domain.GroundRole;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.authority.service.GroundRoleService;
import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.common.page.PageService;
import com.hirain.phm.bd.ground.common.page.QueryRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月8日 上午10:46:05
 * @Description
 *              <p>
 *              角色controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/role")
@Slf4j
public class GroundRoleController {

	@Autowired
	private GroundRoleService roleService;

	@Autowired
	private PageService pageService;

	/**
	 * 用于用户查询时，角色下拉框提供数据
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/getAllRoles")
	@RequiresAuthentication
	public ResponseBo roleList() {
		try {
			return ResponseBo.ok(roleService.selectAll());
		} catch (Exception e) {
			log.error("获取角色信息失败", e);
			return ResponseBo.error("获取角色信息失败，请联系网站管理员！");
		}
	}

	@GetMapping("/list")
	@RequiresAuthentication
	public ResponseBo roleList(QueryRequest request, String roleName) {
		try {
			return ResponseBo.ok(pageService.selectByPageNumSize(request, () -> roleService.listRolesByRoleName(roleName)));
		} catch (Exception e) {
			log.error("获取角色信息失败", e);
			return ResponseBo.error("获取角色信息失败，请联系网站管理员！");
		}
	}

	@GetMapping("/findRoleByRoleId")
	@RequiresAuthentication
	public ResponseBo findRoleByRoleId(Long id) {
		try {
			GroundRole role = roleService.findRoleWithMenus(id);
			return ResponseBo.ok(role);
		} catch (Exception e) {
			log.error("获取角色信息失败", e);
			return ResponseBo.error("获取角色信息失败，请联系网站管理员！");
		}
	}

	@GetMapping("/findRoleByUserId")
	@RequiresAuthentication
	public ResponseBo findRoleByUserId() {
		try {
			User user = (User) SecurityUtils.getSubject().getPrincipal();
			List<GroundRole> roles = roleService.findRoleByUserId(user.getUserId());
			if (roles == null || roles.size() == 0) {
				return ResponseBo.error("该用户未绑定角色");
			} else {
				return ResponseBo.ok(roles.get(0).getRoleName());
			}
		} catch (Exception e) {
			log.error("获取角色信息失败", e);
			return ResponseBo.error("获取角色信息失败，请联系网站管理员！");
		}
	}

	@GetMapping("/findRoleWithMenus")
	@RequiresAuthentication
	public ResponseBo findRoleWithMenuByUserId() {
		try {
			User user = (User) SecurityUtils.getSubject().getPrincipal();
			List<GroundRole> roles = roleService.findRoleWithMenuByUserId(user.getUserId());
			if (roles == null || roles.size() == 0) {
				return ResponseBo.error("该用户未绑定角色");
			} else {
				return ResponseBo.ok(roles.get(0));
			}
		} catch (Exception e) {
			log.error("获取角色信息失败", e);
			return ResponseBo.error("获取角色信息失败");
		}
	}

	@PostMapping("/addRole")
	@RequiresAuthentication
	public ResponseBo addRole(GroundRole role, Long[] menuIds) {
		try {
			GroundRole result = roleService.findByName(role.getRoleName());
			if (result != null) {
				return ResponseBo.warn("该角色名已存在,请重新命名！");
			} else {
				roleService.addRole(role, menuIds);
				return ResponseBo.ok("新增角色成功！");
			}
		} catch (Exception e) {
			log.error("新增角色失败", e);
			return ResponseBo.error("新增角色失败，请联系网站管理员！");
		}
	}

	@PostMapping("/delete")
	@RequiresAuthentication
	public ResponseBo deleteRoles(String ids) {

		try {
			roleService.deleteRoles(ids);
			return ResponseBo.ok("删除角色成功！");
		} catch (Exception e) {
			log.error("删除角色失败", e);
			return ResponseBo.error("删除角色失败，请联系网站管理员！");
		}
	}

	@PostMapping("/updateRole")
	@RequiresAuthentication
	public ResponseBo updateRole(GroundRole role, Long[] menuIds) {
		try {
			roleService.updateRole(role, menuIds);
			return ResponseBo.ok("修改角色成功！");
		} catch (Exception e) {
			log.error("修改角色失败", e);
			return ResponseBo.error("修改角色失败，请联系网站管理员！");
		}
	}
}
