/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.flow.controller;

import java.util.HashMap;
import java.util.Map;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.flow.domain.ResultBean;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月28日 下午2:13:33
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月28日 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
public class FlowController {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@PostMapping("/start")
	public ResultBean create() {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("maintenance");
		Task task = queryTask(processInstance.getId());
		return new ResultBean(processInstance.getId(), task.getName());
	}

	@PostMapping("/commit")
	public ResultBean commit(String processId, boolean result) {
		Map<String, Object> map = new HashMap<>();
		map.put("commit", result);
		map.put("terminate", false);
		return apply(processId, map);
	}

	@PostMapping("/terminate")
	public ResultBean terminate(String processId) {
		Map<String, Object> map = new HashMap<>();
		map.put("terminate", true);
		return apply(processId, map);
	}

	@GetMapping("/task")
	public ResultBean getTask(String processId) {
		Task task = queryTask(processId);
		return new ResultBean(processId, task != null ? task.getName() : "none");
	}

	/**
	 * @param processId
	 * @param map
	 * @return
	 */
	private ResultBean apply(String processId, Map<String, Object> map) {
		Task task = queryTask(processId);
		if (task == null) {
			return new ResultBean(processId, "none");
		} else {
			taskService.complete(task.getId(), map);
			Task next = queryTask(processId);
			return new ResultBean(processId, next != null ? next.getName() : "关闭");
		}
	}

	/**
	 * @param processId
	 * @return
	 */
	private Task queryTask(String processId) {
		return taskService.createTaskQuery().processInstanceId(processId).singleResult();
	}
}
