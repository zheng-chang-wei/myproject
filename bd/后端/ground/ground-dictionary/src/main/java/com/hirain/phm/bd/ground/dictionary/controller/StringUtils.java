/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.controller;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 9, 2020 6:25:31 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class StringUtils {

	private StringUtils() {

	}

	public static boolean isEmpty(String source) {
		return source == null || source.isEmpty();
	}

	public static boolean isNotEmpty(String source) {
		return !isEmpty(source);
	}
}
