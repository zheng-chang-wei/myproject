package com.hirain.qsy.shaft.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.common.util.PageService;
import com.hirain.qsy.shaft.service.SessionService;

@RestController
@RequestMapping(value = "/session")
public class SessionController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SessionService sessionService;

	@Autowired
	PageService pageService;

	@GetMapping("/list")
	@RequiresAuthentication
	public ResponseBo list(QueryRequest request) {
		try {
			return ResponseBo.ok(pageService.selectByPageNumSize(request, () -> this.sessionService.list()));
		} catch (Exception e) {
			log.error("获取在线信息失败", e);
			return ResponseBo.error("获取在线信息失败，请联系网站管理员！");
		}
	}

	@PostMapping("/forceLogout")
	@RequiresAuthentication
	public ResponseBo forceLogout(String id) {
		try {
			sessionService.forceLogout(id);
			return ResponseBo.ok();
		} catch (Exception e) {
			log.error("踢出用户失败", e);
			return ResponseBo.error("踢出用户失败");
		}

	}
}
