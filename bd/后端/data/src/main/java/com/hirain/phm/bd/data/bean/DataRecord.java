/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.bean;

import java.util.Date;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 4, 2020 5:36:19 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 4, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class DataRecord {

	public static final int OPEN_STATE = 1;

	public static final int CLOSE_STATE = 2;

	private String project;

	private String train;

	private int carriageId;

	private int doorId;

	private Date timestamp;

	private String data;

	private int state;

}
