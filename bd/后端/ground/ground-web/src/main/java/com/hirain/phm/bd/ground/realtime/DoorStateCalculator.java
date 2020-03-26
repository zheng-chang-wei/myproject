/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.realtime;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hirain.phm.bd.message.decode.IDoorStateCalculator;
import com.hirain.phm.bd.message.decode.KeyValueDecoder;
import com.hirain.phm.bd.message.decode.RunDataFrame;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月17日 下午2:14:04
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月17日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class DoorStateCalculator implements IDoorStateCalculator {

	private static List<String> props = Arrays.asList("门隔离", "门紧急解锁", "门存在故障", "门防挤压", "门开好", "关门过程", "开门过程", "门关好");

	private static List<String> states = Arrays.asList("隔离", "紧急解锁", "门故障", "防挤压", "门完全打开", "关门过程中", "开门过程中", "门完全关闭");

	private static List<String> keys = KeyValueDecoder.keys;

	@Override
	public String calculate(RunDataFrame frame) {
		List<String> values = frame.getValues();
		for (int i = 0; i < props.size(); i++) {
			if (compare(keys, props.get(i), values)) {
				return states.get(i);
			}
		}
		return "";
	}

	private boolean compare(List<String> keys, String prop, List<String> values) {
		int index = keys.indexOf(prop);
		if (index < 0) {
			return false;
		}
		String value = values.get(index);
		if (pares(value)) {
			return true;
		}
		return false;
	}

	private boolean pares(String value) {
		return Integer.parseInt(value) == 1;
	}
}
