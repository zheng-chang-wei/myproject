/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.export;

import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hirain.common.csv.CsvExport;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 4:34:43 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class CsvExportTool implements ExportTool {

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.export.ExportTool#write(java.lang.String, java.util.List, java.lang.Class)
	 */
	@Override
	public void write(String filename, List<?> data, Class<?> clz) throws Exception {
		CsvExport.write(filename, data, clz);
	}

	/**
	 * @throws Exception
	 * @see com.hirain.phm.bd.ground.maintenance.export.ExportTool#write(java.io.OutputStream, java.util.List, java.lang.Class)
	 */
	@Override
	public void write(OutputStream stream, List<?> data, Class<?> clz) throws Exception {
		CsvExport.write(stream, data, clz, "GBK");
	}

}
