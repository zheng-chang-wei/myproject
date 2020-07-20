/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.repair.export;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hirain.phm.bd.ground.common.page.QueryRequest;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;
import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;
import com.hirain.phm.bd.ground.maintenance.domain.WorkStep;
import com.hirain.phm.bd.ground.maintenance.param.FaultType;
import com.hirain.phm.bd.ground.maintenance.param.SheetCountResponse;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetRecord;
import com.hirain.phm.bd.ground.maintenance.param.WorksheetPacket;
import com.hirain.phm.bd.ground.maintenance.service.FlowReadService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 28, 2020 11:44:03 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class TestFlowReadService implements FlowReadService {

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowReadService#findLastSameStep(java.lang.Long, java.lang.String)
	 */
	@Override
	public WorkStep findLastSameStep(Long sheetId, String state) {
		return null;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowReadService#listWorkSheets(com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam,
	 *      QueryRequest)
	 */
	@Override
	public List<WorkSheetRecord> listWorkSheets(WorkSheetQueryParam param, QueryRequest queryRequest) {
		return null;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowReadService#listWorkSheetsWithDetail(com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam)
	 */
	@Override
	public List<WorkSheet> listWorkSheetsWithDetail(WorkSheetQueryParam param) {
		List<WorkSheet> sheets = new ArrayList<>();
		Field[] fields = WorkDetail.class.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
		}
		for (int i = 0; i < 10; i++) {
			WorkDetail detail = new WorkDetail();
			try {
				for (Field field : fields) {
					Class<?> type = field.getType();
					if (type.equals(String.class)) {
						field.set(detail, "1");
					} else if (type.equals(Integer.class)) {
						field.set(detail, 1);
					} else if (type.equals(Date.class)) {
						field.set(detail, new Date());
					} else if (type.equals(Boolean.class)) {
						field.set(detail, true);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			WorkSheet sheet = new WorkSheet();
			sheet.setDetail(detail);
			sheet.setDoorId("1");
			sheet.setFaultMode("1");
			sheet.setFaultTime(new Date());
			sheet.setFaultName("1");
			sheet.setFaultType(param.getFaultType());
			sheet.setProject(param.getProject());
			sheet.setTrainId(param.getTrainNo());
			sheets.add(sheet);
		}
		return sheets;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowReadService#getWorksheet(java.lang.Long)
	 */
	@Override
	public WorksheetPacket getWorksheet(Long sheetId) {
		return null;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowReadService#countSheets()
	 */
	@Override
	public SheetCountResponse countSheets() {
		return null;
	}

	@Override
	public List<FaultType> getFaultTypes() {
		return null;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowReadService#countWorkSheets(com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam)
	 */
	@Override
	public Integer countWorkSheets(WorkSheetQueryParam param) {
		// TODO Auto-generated method stub
		return null;
	}

}
