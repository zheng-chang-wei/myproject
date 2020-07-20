/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.owner.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.model.ResultBean;
import com.hirain.phm.bd.ground.mail.domain.MailUser;
import com.hirain.phm.bd.ground.mail.service.MailUserService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 2, 2020 10:43:50 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 2, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/bode/mail")
public class MailUserController {

	@Autowired
	private MailUserService service;

	@GetMapping("/emails")
	public ResultBean<List<MailUser>> list() {
		return new ResultBean<>(service.selectAll());
	}

	@PostMapping("/emails")
	@Transactional
	public ResultBean<String> commit(String emails) {
		String[] emailArr = emails.split(";");
		service.deleteAll();
		List<MailUser> users = Arrays.asList(emailArr).stream().map(email -> {
			MailUser user = new MailUser();
			user.setName("业主");
			user.setEmail(email);
			return user;
		}).collect(Collectors.toList());
		System.err.println(users);
		service.saveAll(users);
		return new ResultBean<>();
	}

}
