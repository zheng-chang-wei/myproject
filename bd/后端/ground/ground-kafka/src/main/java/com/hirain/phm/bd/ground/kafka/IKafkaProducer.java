/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.kafka;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 1, 2019 9:17:44 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 1, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface IKafkaProducer {

	public void send(String topic, String key, byte[] payload);
}
