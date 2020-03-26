/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.hirain.phm.bd.ground.bigdata.param.DataRecordVO;
import com.hirain.phm.bd.ground.bigdata.param.DataRequest;
import com.hirain.phm.bd.ground.bigdata.service.DataRecordService;
import com.hirain.phm.bd.ground.common.exception.SafeRunner;
import com.hirain.phm.bd.ground.common.model.ResponseBo;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 5, 2020 2:11:15 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 5, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/data")
public class DataRecordController {

	@Autowired
	private DataRecordService service;

	@GetMapping("/records")
	public ResponseBo query(DataRequest request) {
		return SafeRunner.run(() -> ResponseBo.ok(service.getDataRecords(request)), ResponseBo.error("查询失败"));
	}

	@GetMapping("/datas")
	public ResponseBo getData(DataRecordVO record, String[] variables) {
		return SafeRunner.run(() -> {
			try {
				return ResponseBo.ok(service.getData(record, Arrays.asList(variables)));
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseBo.error(e.getMessage());
			}
		}, ResponseBo.error("查询失败"));
	}

	@GetMapping("/count")
	public ResponseBo count(DataRequest request) {
		return SafeRunner.run(() -> ResponseBo.ok(service.count(request)), ResponseBo.error("查询失败"));
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}
}
