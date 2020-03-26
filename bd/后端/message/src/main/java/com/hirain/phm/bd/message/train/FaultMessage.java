/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message.train;

import java.util.Date;
import java.util.List;

import com.hirain.phm.bd.message.CommonMessage;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月25日 上午9:38:29
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月25日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class FaultMessage {

	private int carriageId;

	private int doorId;

	private Date timestamp;

	private boolean debug;

	private int faultId;

	private String faultName;

	private int state;

	/**
	 * 原始数据
	 */
	private List<CommonMessage> messages;

}
