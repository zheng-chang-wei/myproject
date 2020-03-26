/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.param;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 5, 2020 2:44:52 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 5, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class DataRecordVO {

	public static final String[] STATE_ARRAY = new String[] { "", "开门", "关门" };

	private String project;

	private String train;

	private int carriageId;

	private int doorId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date timestamp;

	private String state;

}
