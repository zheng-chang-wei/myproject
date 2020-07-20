/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.log.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.synapsis.annotation.Log;
import com.hirain.phm.synapsis.log.domain.UserLog;
import com.hirain.phm.synapsis.log.service.UserLogService;
import com.hirain.phm.synapsis.page.PageService;
import com.hirain.phm.synapsis.page.QueryRequest;
import com.hirain.phm.synapsis.response.ResultBean;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Apr 16, 2020 7:21:58 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 16, 2020 zepei.tao@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/userlog")
@Slf4j
public class LogController {

	@Autowired
	private UserLogService userLogService;

	@Autowired
	PageService pageService;

	@GetMapping("/list")
	@Log("列出所有用户日志")
	public ResultBean<List<UserLog>> logList(QueryRequest request, UserLog sysLog) {
		try {
			return pageService.selectByPage(request, () -> userLogService.listLogs(sysLog));
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResultBean<>("系统异常", e);
		}
	}

	@PostMapping("/delete")
	@Log("删除某个用户日志")
	public ResultBean<String> deleteLogs(String ids) {
		try {
			userLogService.deleteLogs(ids);
			return new ResultBean<>("删除日志成功！");
		} catch (Exception e) {
			log.error("删除日志失败", e);
			return new ResultBean<>("删除日志失败，请联系网站管理员！");
		}
	}

}
