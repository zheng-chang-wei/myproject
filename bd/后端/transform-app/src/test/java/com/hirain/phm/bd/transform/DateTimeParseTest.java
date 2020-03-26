/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月16日 下午1:37:54
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月16日 jianwen.xin@hirain.com 1.0 create file
 */
public class DateTimeParseTest {

	public static void main(String[] args) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss SSS");
		LocalDateTime now = LocalDateTime.now();
		String value = now.format(formatter);
		System.out.println(value);

		LocalDateTime time = LocalDateTime.parse(value, formatter);
		System.out.println(time);
	}
}
