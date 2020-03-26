/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.common.csv.exception;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 5:31:22 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class CsvExportException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	CsvExportException() {
		super();
	}

	CsvExportException(String message, Throwable cause) {
		super(message, cause);
	}

	CsvExportException(String message) {
		super(message);
	}

	CsvExportException(Throwable cause) {
		super(cause);
	}

}
