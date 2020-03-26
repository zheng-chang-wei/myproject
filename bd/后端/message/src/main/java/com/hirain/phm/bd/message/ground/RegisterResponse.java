/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message.ground;

import com.hirain.phm.bd.message.header.MessageHeader;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月25日 上午9:30:37
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月25日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterResponse extends MessageHeader {

	private boolean success;

	private String error;

	private String caCert;

	private String cert;

	private String key;

	private String password;

	private int state;

	private int offset;
}
