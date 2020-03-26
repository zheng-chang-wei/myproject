/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.common.partition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月22日 上午10:24:19
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月22日 jianwen.xin@hirain.com 1.0 create file
 */
public class RoundPartitioner implements IPartitioner {

	private AtomicInteger counter = new AtomicInteger(0);

	private Map<String, Integer> cache = new ConcurrentHashMap<>();

	public static void main(String[] args) {
		String prefix = "sh/sl/01";
		RoundPartitioner partitioner = new RoundPartitioner();
		for (int i = 0; i < 20; i++) {
			int partition = partitioner.partition(prefix + i, 10);
			System.out.println(partition);
		}
	}

	/**
	 * @see com.hirain.phm.bd.transform.partition.IPartitioner#partition(java.lang.String, int)
	 */
	@Override
	public int partition(String msg, int size) {
		Integer count = cache.get(msg);
		if (count == null) {
			int value = nextValue(size);
			cache.put(msg, value);
			count = value;
		}
		return count;
	}

	private int nextValue(int size) {
		int value = counter.getAndIncrement();
		int count = value % size;
		return count;
	}

}
