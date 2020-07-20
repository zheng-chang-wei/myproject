package com.hirain.phm.bd.ground.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.common.page.PageService;
import com.hirain.phm.bd.ground.common.page.QueryRequest;
import com.hirain.phm.bd.ground.log.domain.SysLog;
import com.hirain.phm.bd.ground.log.service.LogService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年9月20日 下午5:14:23
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年9月20日 changwei.zheng@hirain.com 1.0 create file
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
	public ResponseBo logList(QueryRequest request, SysLog sysLog) {
		try {
			return ResponseBo.ok(pageService.selectByPageNumSize(request, () -> this.logService.listLogs(sysLog)));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseBo.error("系统异常");
		}
	}

	@PostMapping("/delete")
	public ResponseBo deleteLogs(String ids) {
		try {
			this.logService.deleteLogs(ids);
			return ResponseBo.ok("删除日志成功！");
		} catch (Exception e) {
			log.error("删除日志失败", e);
			return ResponseBo.error("删除日志失败，请联系网站管理员！");
		}
	}
}
