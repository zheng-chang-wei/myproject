/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.data.performance;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 17, 2020 10:51:23 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 17, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class TimeCounter {

	public static double run(String title, TimeRunner runner, int times) {
		int sum = 0;
		for (int i = 0; i < times; i++) {
			long time1 = System.currentTimeMillis();
			runner.run();
			long time2 = System.currentTimeMillis();
			long duration = time2 - time1;
			System.out.println(title + ":" + duration);
			sum += duration;
		}
		double aver = (double) sum / times;
		System.out.println("average of " + title + ":" + aver);
		return aver;
	}
}
