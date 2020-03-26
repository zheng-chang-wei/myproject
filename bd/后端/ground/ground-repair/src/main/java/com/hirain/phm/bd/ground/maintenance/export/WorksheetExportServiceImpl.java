/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.export;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam;
import com.hirain.phm.bd.ground.maintenance.service.FlowReadService;

import lombok.Setter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 1:50:54 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class WorksheetExportServiceImpl implements WorksheetExportService {

	@Setter
	@Autowired
	private ExportTool tool;

	@Setter
	@Autowired
	private FlowReadService service;

	/**
	 * @throws Exception
	 * @see com.hirain.phm.bd.ground.maintenance.export.WorksheetExportService#export(com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam,
	 *      java.io.OutputStream)
	 */
	@Override
	public void export(WorkSheetQueryParam param, OutputStream stream) throws Exception {
		List<WorkSheet> sheets = service.listWorkSheetsWithDetail(param);
		if (sheets == null || sheets.isEmpty()) {
			throw new Exception("可导出的内容为空");
		}
		List<WorksheetRow> rows = getRows(sheets);
		tool.write(stream, rows, WorksheetRow.class);
	}

	/**
	 * @param sheets
	 * @return
	 */
	private List<WorksheetRow> getRows(List<WorkSheet> sheets) {
		List<WorksheetRow> rows = new ArrayList<>();
		for (WorkSheet sheet : sheets) {
			WorksheetRow row = WorksheetRow.valueOf(sheet);
			row.setSeq(rows.size() + 1);
			rows.add(row);
		}
		return rows;
	}

}
