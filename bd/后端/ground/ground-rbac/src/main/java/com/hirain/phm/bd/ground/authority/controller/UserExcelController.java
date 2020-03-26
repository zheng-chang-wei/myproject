/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.authority.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.authority.service.UserExcelService;
import com.hirain.phm.bd.ground.authority.service.UserService;
import com.hirain.phm.bd.ground.common.model.ResponseBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Jul 25, 2019 9:27:01 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 25, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Api(value = "导入用户表controller", tags = { "导入用户表接口" })
@RestController
@RequestMapping(value = "resolve")
@Slf4j
public class UserExcelController {

	@Autowired
	private UserExcelService excelService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBo uploadUsers(@RequestParam("file") MultipartFile file) {
		List<User> users;
		try {
			users = excelService.resolveExcel(file);
			userService.insertUsers(users);
			return ResponseBo.ok(users);
		} catch (Exception e) {
			log.error("导入用户失败", e);
			return ResponseBo.error("导入用户失败！" + e.getMessage());
		}
	}

}
