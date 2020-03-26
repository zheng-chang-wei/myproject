/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.authority.controller.RBACGateWay;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetRecord;
import com.hirain.phm.bd.ground.maintenance.service.FlowMobileService;
import com.hirain.phm.bd.ground.maintenance.service.WorkSheetService;
import com.hirain.phm.bd.ground.train.controller.TrainGateWay;
import com.hirain.phm.bd.ground.train.domain.Project;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Oct 14, 2019 2:31:05 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Oct 14, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class FlowMobileServiceImpl implements FlowMobileService {

	@Autowired
	private WorkSheetService sheetService;

	@Autowired
	private RBACGateWay rbacGW;

	@Autowired
	private TrainGateWay trainGW;

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowMobileService#listEditSheets(User)
	 */
	@Override
	public List<WorkSheetRecord> listEditSheets(User user) {
		List<Long> projectIds = rbacGW.getProjectsByUserID(user.getUserId());
		List<WorkSheet> sheets = sheetService.selectEditSheet(projectIds);
		List<WorkSheetRecord> records = new ArrayList<>();
		sheets.forEach(sheet -> {
			WorkSheetRecord record = new WorkSheetRecord();
			record.setSheet(sheet);
			records.add(record);
		});
		return records;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowMobileService#listHistorySheets(User)
	 */
	@Override
	public List<WorkSheetRecord> listHistorySheets(User user) {
		List<WorkSheet> sheets = sheetService.selectByUserId(user.getUserId());
		List<WorkSheetRecord> records = new ArrayList<>();
		sheets.forEach(sheet -> {
			WorkSheetRecord record = new WorkSheetRecord();
			Project project = trainGW.selectProjectById(sheet.getProjectId());
			sheet.setProject(project.getName());
			record.setSheet(sheet);
			records.add(record);
		});
		return records;
	}

}
