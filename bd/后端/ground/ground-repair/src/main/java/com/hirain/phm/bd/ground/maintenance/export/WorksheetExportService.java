/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.export;

import java.io.OutputStream;

import com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 1:50:22 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface WorksheetExportService {

	/**
	 * @param param
	 * @param fileOutputStream
	 * @throws Exception
	 */
	void export(WorkSheetQueryParam param, OutputStream stream) throws Exception;

}
