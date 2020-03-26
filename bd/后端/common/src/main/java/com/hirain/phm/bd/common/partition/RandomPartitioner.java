/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.common.partition;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月8日 下午4:27:06
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 jianwen.xin@hirain.com 1.0 create file
 */
public class RandomPartitioner implements IPartitioner {

	private ConcurrentMap<String, AtomicInteger> topicCounterMap = new ConcurrentHashMap<>();

	@Override
	public int partition(String topic, int size) {
		AtomicInteger counter = topicCounterMap.get(topic);
		if (counter == null) {
			counter = new AtomicInteger();
			int positive = toPositive(murmur2(topic.getBytes()));
			int value = positive % size;
			counter.set(value);
			topicCounterMap.put(topic, counter);
		}
		return counter.get();
	}

	public int toPositive(int number) {
		return number & 0x7fffffff;
	}

	/**
	 * Generates 32 bit murmur2 hash from byte array
	 * 
	 * @param data
	 *            byte array to hash
	 * @return 32 bit hash of the given array
	 */
	private int murmur2(final byte[] data) {
		int length = data.length;
		int seed = 0x9747b28c;
		// 'm' and 'r' are mixing constants generated offline.
		// They're not really 'magic', they just happen to work well.
		final int m = 0x5bd1e995;
		final int r = 24;

		// Initialize the hash to a random value
		int h = seed ^ length;
		int length4 = length / 4;

		for (int i = 0; i < length4; i++) {
			final int i4 = i * 4;
			int k = (data[i4 + 0] & 0xff) + ((data[i4 + 1] & 0xff) << 8) + ((data[i4 + 2] & 0xff) << 16) + ((data[i4 + 3] & 0xff) << 24);
			k *= m;
			k ^= k >>> r;
			k *= m;
			h *= m;
			h ^= k;
		}

		// Handle the last few bytes of the input array
		switch (length % 4) {
		case 3:
			h ^= (data[(length & ~3) + 2] & 0xff) << 16;
		case 2:
			h ^= (data[(length & ~3) + 1] & 0xff) << 8;
		case 1:
			h ^= data[length & ~3] & 0xff;
			h *= m;
		}

		h ^= h >>> 13;
		h *= m;
		h ^= h >>> 15;

		return h;
	}
}
