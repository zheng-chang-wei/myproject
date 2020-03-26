/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.flowable;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hirain.phm.bd.ground.maintenance.param.FlowResult;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月28日 下午5:27:01
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月28日 jianwen.xin@hirain.com 1.0 create file
 */
@FeignClient(name = "maintenance-flow-app", url = "http://localhost:8762")
public interface HttpClient {

	@RequestMapping(method = RequestMethod.POST, value = "/start")
	FlowResult start();

	@RequestMapping(method = RequestMethod.POST, value = "/commit")
	FlowResult process(@RequestParam("processId") String processId, @RequestParam("result") boolean result);

	@RequestMapping(method = RequestMethod.POST, value = "/terminate")
	FlowResult terminate(@RequestParam("processId") String processId);

	@RequestMapping(method = RequestMethod.GET, value = "/task")
	FlowResult current(@RequestParam("processId") String processId);
}
