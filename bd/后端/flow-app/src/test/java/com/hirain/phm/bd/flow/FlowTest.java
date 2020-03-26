/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.flow;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年7月17日 下午2:13:31
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年7月17日 jianwen.xin@hirain.com 1.0 create file
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowAppApplication.class)
public class FlowTest {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Test
	public void test() {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("maintenance");

		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		assertEquals("创建工单", task.getName());

		Map<String, Object> commit = new HashMap<>();
		commit.put("commit", true);
		taskService.complete(task.getId(), commit);

		task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		assertEquals("售后审核", task.getName());

		Map<String, Object> terminate = new HashMap<>();
		terminate.put("terminate", true);
		taskService.complete(task.getId(), terminate);
		task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

		System.out.println(task);

		taskService.complete(task.getId());

		HistoricProcessInstance result = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstance.getId())
				.singleResult();
		System.out.println(result.getEndActivityId());
	}

}
