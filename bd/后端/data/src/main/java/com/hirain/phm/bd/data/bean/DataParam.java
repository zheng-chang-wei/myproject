/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.bean;

import java.util.Date;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 4, 2020 5:39:20 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 4, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class DataParam {

	private String project;

	private String train;

	private Date date;

	private int limit;

	private int offset;

}
