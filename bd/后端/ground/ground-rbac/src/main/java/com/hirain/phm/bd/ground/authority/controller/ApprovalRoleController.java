package com.hirain.phm.bd.ground.authority.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.authority.service.ApprovalRoleService;
import com.hirain.phm.bd.ground.common.model.ResponseBo;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月8日 上午10:46:05
 * @Description
 *              <p>
 *              审批角色controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/approvalRole")
@Slf4j
public class ApprovalRoleController {

	@Autowired
	private ApprovalRoleService roleService;


	@GetMapping("/getAllApprovalRoles")
	@RequiresAuthentication
	public ResponseBo getAllRoles() {
		try {
			return ResponseBo.ok(this.roleService.selectAll());
		} catch (Exception e) {
			log.error("获取角色信息失败", e);
			return ResponseBo.error("获取角色信息失败，请联系网站管理员！");
		}
	}


}
