/*******************************************************************************
 * Copyright (c) 2017, 2017 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 下午2:19:31
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
public class UUUIDGenerator {

	/**
	 * 同一个时间戳的自增
	 */
	private volatile long index = 0;

	/**
	 * 当前时间戳
	 */
	private volatile long currentTime = 0;

	private volatile String formatTime = "";

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(StringUtil.FORMAT_DATETIME);

	private UUUIDGenerator() {
	}

	private final static class UUUIDGeneratorClazz {

		private final static UUUIDGenerator instance = new UUUIDGenerator();
	}

	public static UUUIDGenerator getInstance() {
		return UUUIDGeneratorClazz.instance;
	}

	/**
	 * 绝对唯一(时间+空间)
	 * 
	 * @return JDK的
	 */
	public String getUUIDByJDK() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 线程安全+时间戳+自增
	 * 但是只能保证本地机器多线程生成UUID，不能保证和其他机器不重复；
	 */
	public synchronized String getUUIDByTimeAndAutoIncreIndex() {
		final long currentTimeMillis = System.currentTimeMillis();
		if (currentTimeMillis - currentTime > 1000) {// 一秒一换
			currentTime = currentTimeMillis;
			formatTime = DATE_FORMAT.format(new Date(currentTime)).substring(2);// 100年内不重复
			index = 0;
		}
		final StringBuilder builder = new StringBuilder();
		builder.append(formatTime);
		builder.append("_");
		builder.append(String.valueOf(index++));
		return builder.toString();
	}

	// =========================================测试代码=========================================================

	private static Map<String, String> uuidsTest = new HashMap<String, String>();

	public static void main(final String[] args) {
		final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			newFixedThreadPool.execute(new Runnable() {

				@Override
				public void run() {
					while (true) {
						final String uuidByTimeAndAutoIncreIndex = UUUIDGenerator.getInstance().getUUIDByTimeAndAutoIncreIndex();
						if (uuidsTest.containsKey(uuidByTimeAndAutoIncreIndex)) {
							System.out.println("UUID error====================:" + uuidByTimeAndAutoIncreIndex);
						}
						uuidsTest.put(uuidByTimeAndAutoIncreIndex, uuidByTimeAndAutoIncreIndex);
						// System.out.println(uuidByTimeAndAutoIncreIndex);
					}
				}
			});
		}
	}
}
