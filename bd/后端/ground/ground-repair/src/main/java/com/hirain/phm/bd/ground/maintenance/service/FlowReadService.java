/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service;

import java.util.List;

import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;
import com.hirain.phm.bd.ground.maintenance.domain.WorkStep;
import com.hirain.phm.bd.ground.maintenance.param.FaultType;
import com.hirain.phm.bd.ground.maintenance.param.SheetCountResponse;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetRecord;
import com.hirain.phm.bd.ground.maintenance.param.WorksheetPacket;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 28, 2020 10:55:54 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface FlowReadService {

	WorkStep findLastSameStep(Long sheetId, String state);

	List<WorkSheetRecord> listWorkSheets(WorkSheetQueryParam param);

	List<WorkSheet> listWorkSheetsWithDetail(WorkSheetQueryParam param);

	WorksheetPacket getWorksheet(Long sheetId);

	/**
	 * @return
	 */
	SheetCountResponse countSheets();

	List<FaultType> getFaultTypes();
}
