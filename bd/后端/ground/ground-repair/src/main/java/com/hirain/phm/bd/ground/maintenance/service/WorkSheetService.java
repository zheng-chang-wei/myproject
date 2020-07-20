/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.maintenance.domain.ExternalFault;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;
import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月29日 下午6:52:27
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月29日 jianwen.xin@hirain.com 1.0 create file
 */
public interface WorkSheetService extends IService<WorkSheet> {

	/**
	 * @param detail
	 * @return
	 */
	WorkSheet addWorkSheet(WorkDetail detail);

	/**
	 * @param sheet
	 * @param state
	 * @param stepId
	 */
	void updateSheetState(WorkSheet sheet, String state, Long stepId);

	/**
	 * @param sheet
	 * @param detail
	 * @return
	 */
	WorkSheet updateSheetDetail(WorkSheet sheet, WorkDetail detail);

	WorkSheet updateSheetFault(WorkSheet sheet, ExternalFault fault);

	WorkSheet selectWithLock(Long id);

	/**
	 * @param sheet
	 * @param fault
	 */
	WorkSheet updateSheetFault(WorkSheet sheet, UnifiedFaultRecord fault);

	List<WorkSheet> selectNewAndCloseSheets(List<Integer> trainIds);

	List<WorkSheet> selectByUserId(Long userId);

	/**
	 * @param lineIds
	 */
	List<WorkSheet> selectEditSheet(List<Long> lineIds);

	/**
	 * @param param
	 * @param userId
	 * @return
	 */
	List<WorkSheet> listWorkSheetWithDetail(WorkSheetQueryParam param, Long userId);

	List<WorkSheet> listWorkSheetOfProjects(List<Long> projects, WorkSheetQueryParam param, Long userId);

	/**
	 * @param id
	 * @return
	 */
	WorkSheet selectBySheetId(long id);

	List<Integer> getFaultTypes();

	/**
	 * @param projects
	 * @param param
	 * @param userId
	 * @return
	 */
	Integer countWorkSheetOfProjects(List<Long> projects, WorkSheetQueryParam param, Long userId);

	/**
	 * @param param
	 * @param userId
	 * @return
	 */
	Integer countWorkSheetWithDetail(WorkSheetQueryParam param, Long userId);
}
