/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.common.model.PageResultBean;
import com.hirain.phm.bd.ground.common.model.ResultBean;
import com.hirain.phm.bd.ground.common.page.QueryRequest;
import com.hirain.phm.bd.ground.common.page.RepairPageService;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetRecord;
import com.hirain.phm.bd.ground.maintenance.service.FlowMobileService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Oct 14, 2019 2:15:10 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Oct 14, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/mobile/repair")
public class MobileRepairController {

	@Autowired
	private FlowMobileService service;

	@Autowired
	private RepairPageService pageService;

	@GetMapping("/worksheets/edit")
	public ResultBean<List<WorkSheetRecord>> getEditSheets() {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		return new ResultBean<>(service.listEditSheets(user));
	}

	@GetMapping("/worksheets")
	public PageResultBean<WorkSheetRecord> getHistorySheets(QueryRequest request) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		return pageService.selectByPage(request, () -> service.listHistorySheets(user));
	}
}
