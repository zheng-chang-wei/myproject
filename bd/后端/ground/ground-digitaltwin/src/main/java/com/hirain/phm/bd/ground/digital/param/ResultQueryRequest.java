/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.digital.param;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 7, 2020 2:49:58 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 7, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class ResultQueryRequest {

	private String project;

	private String train;

	private int carId;

	private int doorId;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date start;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date end;
}
