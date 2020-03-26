/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.message.header.MessageHeader;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月4日 下午4:26:49
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月4日 jianwen.xin@hirain.com 1.0 create file
 */
public class JsonTest {

	public static void main(String[] args) {
		MessageHeader header = new MessageHeader();
		header.setCity("sh");
		header.setLine("s1");
		header.setTrain("020202");
		header.setSid(0x11);
		String json = JsonUtil.toString(header);
		System.out.println(json);
	}
}
