/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.repair.export;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;

import com.hirain.phm.bd.ground.common.event.FaultTopType;
import com.hirain.phm.bd.ground.maintenance.export.CsvExportTool;
import com.hirain.phm.bd.ground.maintenance.export.WorksheetExportService;
import com.hirain.phm.bd.ground.maintenance.export.WorksheetExportServiceImpl;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 1:42:33 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class WorkSheetExportTest {

	@Test
	public void exportWorkSheet() throws Exception {
		String filename = System.getProperty("user.dir") + File.separator + "service.csv";
		WorksheetExportService service = new WorksheetExportServiceImpl();
		((WorksheetExportServiceImpl) service).setService(new TestFlowReadService());
		((WorksheetExportServiceImpl) service).setTool(new CsvExportTool());
		WorkSheetQueryParam param = new WorkSheetQueryParam();
		param.setProject("项目1");
		param.setTrainNo("1");
		param.setFaultType(FaultTopType.Fault.getCode());
		service.export(param, new FileOutputStream(filename));
	}
}
