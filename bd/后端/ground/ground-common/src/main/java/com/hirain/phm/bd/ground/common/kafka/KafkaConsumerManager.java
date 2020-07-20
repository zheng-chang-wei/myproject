/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.common.kafka;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 23, 2020 4:34:03 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 23, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface KafkaConsumerManager {

	void createAndStart(String city, String line);
}
