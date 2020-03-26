/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.decode;

import java.util.List;

import com.hirain.phm.bd.message.CommonMessage;
import com.hirain.phm.bd.message.decode.KeyValueDecoder;
import com.hirain.phm.bd.message.decode.RunDataFrame;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月17日 上午10:28:57
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月17日 jianwen.xin@hirain.com 1.0 create file
 */
public class TestNewDecoder {

	static String msg = "0C166000001504000C04210421013B010CFA0009020100000C0000000000243C";

	public static void main(String[] args) {
		byte[] bs = messages();

		KeyValueDecoder decoder = new KeyValueDecoder();
		CommonMessage message = new CommonMessage();
		message.setDatas(bs);
		message.setMilli(100);
		RunDataFrame frame = decoder.decode(message);

		List<String> keys = KeyValueDecoder.keys;
		for (int i = 0; i < keys.size(); i++) {
			System.out.println(keys.get(i) + "=" + frame.getValues().get(i));
		}
	}

	public static byte[] messages() {
		byte[] bs = new byte[32];
		int index = 0;
		for (int i = 0; i < msg.length() - 1; i = i + 2) {
			String sub = msg.substring(i, i + 2);
			int v = Integer.parseInt(sub, 16);
			bs[index++] = (byte) v;
		}
		return bs;
	}
}
