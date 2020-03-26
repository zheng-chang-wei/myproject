package com.hirain.qsy.shaft.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.common.util.PageService;
import com.hirain.qsy.shaft.model.SysLog;
import com.hirain.qsy.shaft.service.LogService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月8日 上午10:47:02
 * @Description
 *              <p>
 *              日志controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/log")
@Slf4j
public class LogController {

	@Autowired
	private LogService logService;

	@Autowired
	PageService pageService;

	@GetMapping("/list")
	@RequiresAuthentication
	public ResponseBo logList(QueryRequest request, SysLog sysLog) {
		try {
			return ResponseBo.ok(pageService.selectByPageNumSize(request, () -> this.logService.findAllLogs(sysLog)));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseBo.error("系统异常");
		}
	}

	@PostMapping("/delete")
	@RequiresAuthentication
	public ResponseBo deleteLogss(String ids) {
		try {
			this.logService.deleteLogs(ids);
			return ResponseBo.ok("删除日志成功！");
		} catch (Exception e) {
			log.error("删除日志失败", e);
			return ResponseBo.error("删除日志失败，请联系网站管理员！");
		}
	}
}
