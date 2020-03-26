/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.bigdata.param.DeleteDataParam;
import com.hirain.phm.bd.ground.bigdata.param.GroundDataParam;
import com.hirain.phm.bd.ground.bigdata.service.DataManageService;
import com.hirain.phm.bd.ground.common.annotation.Log;
import com.hirain.phm.bd.ground.common.exception.SafeRunner;
import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.common.page.PageService;
import com.hirain.phm.bd.ground.common.page.QueryRequest;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年6月6日 下午1:49:03
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年6月6日 jianwen.xin@hirain.com 1.0 create file
 */
@Api(value = "地面端数据管理Controller")
@RestController
@RequestMapping("/dm")
@Slf4j
public class DataManageController {

	@Autowired
	private DataManageService manageService;

	@Autowired
	private PageService pageService;

	@GetMapping("/space")
	public ResponseBo getSpace() {
		try {
			return ResponseBo.ok(manageService.getSpace());
		} catch (Exception e) {
			log.error("地面端数据管理，存储空间查询错误", e);
			return ResponseBo.error("存储空间查询错误");
		}
	}

	@GetMapping("/project")
	public ResponseBo getProjectSpace() {
		return SafeRunner.run(() -> ResponseBo.ok(manageService.getProjectSpace()), ResponseBo.error("项目存储空间查询错误"));
	}

	@GetMapping("/train")
	public ResponseBo listTrainData(QueryRequest query, GroundDataParam param) {
		try {
			return ResponseBo.ok(pageService.selectByPageNumSize(query, () -> manageService.selectTrainData(param)));
		} catch (Exception e) {
			log.error("地面端数据管理，数据查询错误", e);
			return ResponseBo.error("系统异常");
		}
	}

	@PostMapping("/train/delete")
	@Log("地面端数据管理删除标记项")
	public ResponseBo deleteTrainData(@RequestBody DeleteDataParam param) {
		System.out.println(param);
		try {
			return ResponseBo.ok(manageService.delete(param));
		} catch (Exception e) {
			log.error("删除出错", e);
			return ResponseBo.error("删除出错");
		}
	}
}
