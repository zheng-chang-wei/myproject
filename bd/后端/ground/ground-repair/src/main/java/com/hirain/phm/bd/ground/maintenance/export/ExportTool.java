/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 2:28:33 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface ExportTool {

	/**
	 * @param filename
	 * @param data
	 * @throws IOException
	 */
	void write(String filename, List<?> data, Class<?> clz) throws Exception;

	/**
	 * @param stream
	 * @param data
	 * @param clz
	 * @throws Exception
	 */
	void write(OutputStream stream, List<?> data, Class<?> clz) throws Exception;

}
