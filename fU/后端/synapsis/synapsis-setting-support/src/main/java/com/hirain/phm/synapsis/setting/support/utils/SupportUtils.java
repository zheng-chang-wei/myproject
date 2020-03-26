/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.utils;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 9, 2020 5:22:09 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class SupportUtils {

	private SupportUtils() {
	}

	public static boolean isExcelFile(String fileName) {
		if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
			return true;
		}
		return false;
	}

	/**
	 * 获取文件名称[不含后缀名]
	 */
	public static String getFilePrefix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		if (splitIndex == -1) {
			return "";
		}
		return fileName.substring(0, splitIndex).replaceAll("\\s*", "");
	}
}
