package com.hirain.phm.bd.ground.push.owner;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hirain.phm.bd.ground.push.domain.FaultPushMessage;

@FeignClient(name = "push-fault", url = "${fault.owner.url}")
public interface FaultPushClient {

	@RequestMapping(method = RequestMethod.POST, value = "/restapi/v1/phm/supplier/message")
	PushResponse pushFault(@RequestParam("tooken") String tooken, @RequestBody FaultPushMessage message);
}