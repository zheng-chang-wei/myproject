/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.event.FaultTopType;
import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.maintenance.dao.WorkSheetMapper;
import com.hirain.phm.bd.ground.maintenance.domain.ExternalFault;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;
import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam;
import com.hirain.phm.bd.ground.maintenance.service.FaultModeService;
import com.hirain.phm.bd.ground.maintenance.service.WorkSheetService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月29日 下午6:53:24
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月29日 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class WorkSheetServiceImpl extends BaseService<WorkSheet> implements WorkSheetService {

	@Autowired
	private WorkSheetMapper mapper;

	@Autowired
	FaultModeService faultModeService;

	/**
	 * @param detail
	 * @return
	 */
	@Override
	public WorkSheet addWorkSheet(WorkDetail detail) {
		WorkSheet sheet = new WorkSheet();
		updateSheetDetail(sheet, detail);
		save(sheet);
		return sheet;
	}

	/**
	 * @param sheet
	 * @param detail
	 */
	@Override
	public WorkSheet updateSheetDetail(WorkSheet sheet, WorkDetail detail) {
		sheet.setProjectId(detail.getProjectId());
		sheet.setTrainId(detail.getTrainId());
		sheet.setDoorId(detail.getDoorId());
		sheet.setModeId(detail.getModeId());
		sheet.setCreateTime(new Date());
		sheet.setDetailId(detail.getId());
		updateNotNull(sheet);
		return sheet;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.WorkSheetService#updateSheetFault(com.hirain.phm.bd.ground.maintenance.domain.WorkSheet,
	 *      com.hirain.phm.bd.ground.maintenance.domain.ExternalFault)
	 */
	@Override
	public WorkSheet updateSheetFault(WorkSheet sheet, ExternalFault fault) {
		sheet.setFaultId(fault.getId());
		sheet.setFaultType(FaultTopType.External.getCode());
		sheet.setFaultName(fault.getFaultMode());
		sheet.setFaultTime(fault.getTime());
		updateNotNull(sheet);
		return sheet;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.WorkSheetService#updateSheetFault(com.hirain.phm.bd.ground.maintenance.domain.WorkSheet,
	 *      com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord)
	 */
	@Override
	public WorkSheet updateSheetFault(WorkSheet sheet, UnifiedFaultRecord fault) {
		sheet.setFaultId(fault.getId());
		sheet.setFaultType(fault.getType().getCode());
		sheet.setFaultName(fault.getFaultName());
		sheet.setFaultTime(fault.getTime());
		sheet.setFaultCode(fault.getCode());
		sheet.setProjectId(fault.getProjectId());
		updateNotNull(sheet);
		return sheet;
	}

	/**
	 * @param sheet
	 * @param state
	 * @param stepId
	 */
	@Override
	public void updateSheetState(WorkSheet sheet, String state, Long stepId) {
		sheet.setStepId(stepId);
		sheet.setState(state);
		updateAll(sheet);
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.WorkSheetService#selectWithLock(java.lang.Long)
	 */
	@Override
	public WorkSheet selectWithLock(Long id) {
		return mapper.selectWithLock(id);
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.WorkSheetService#selectNewAndCloseSheets(java.lang.Long[])
	 */
	@Override
	public List<WorkSheet> selectNewAndCloseSheets(List<Integer> trainIds) {
		return mapper.selectByLines(trainIds);
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.WorkSheetService#selectByUserId(java.lang.Long)
	 */
	@Override
	public List<WorkSheet> selectByUserId(Long userId) {
		return mapper.selectByUserId(userId);
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.WorkSheetService#selectEditSheet(java.util.List)
	 */
	@Override
	public List<WorkSheet> selectEditSheet(List<Long> projectIds) {
		return mapper.selectEditSheet(projectIds);
	}

	@Override
	public List<WorkSheet> listWorkSheetWithDetail(WorkSheetQueryParam param, Long userId) {
		return mapper.listWorkSheetWithDetail(param.getProject(), param.getTrainNo(), param.getFaultType(), userId, param.getLimit(),
				param.getOffset());
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.WorkSheetService#listWorkSheetOfProjects(java.util.List,
	 *      com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam, Integer)
	 */
	@Override
	public List<WorkSheet> listWorkSheetOfProjects(List<Long> projects, WorkSheetQueryParam param, Long userId) {
		return mapper.listWorkSheetOfProjects(projects, param.getTrainNo(), param.getFaultType(), userId, param.getLimit(), param.getOffset());
	}

	@Override
	public WorkSheet selectBySheetId(long id) {
		return mapper.selectBySheetId(id);
	}

	@Override
	public List<Integer> getFaultTypes() {
		return mapper.getFaultTypes();
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.WorkSheetService#countWorkSheetOfProjects(java.util.List,
	 *      com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam, java.lang.Long)
	 */
	@Override
	public Integer countWorkSheetOfProjects(List<Long> projects, WorkSheetQueryParam param, Long userId) {
		return mapper.countWorkSheetOfProjects(projects, param.getTrainNo(), param.getFaultType(), userId);
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.WorkSheetService#countWorkSheetWithDetail(com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam,
	 *      java.lang.Long)
	 */
	@Override
	public Integer countWorkSheetWithDetail(WorkSheetQueryParam param, Long userId) {
		return mapper.countWorksheetWithDetail(param.getProject(), param.getTrainNo(), param.getFaultType(), userId);
	}
}
