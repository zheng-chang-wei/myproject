/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.topic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.common.pinyin.PinyinUtil;
import com.hirain.phm.bd.message.header.MessageHeader;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月4日 下午2:38:04
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月4日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class TopicGenerator {

	@Value("${mqtt.ssl.topic}")
	public String kafkaTopic;

	public String mqtt2Kafka(String mqttTopic) {
		return kafkaTopic;
	}

	public String mqtt2Kafka(MessageHeader header) {
		String city = PinyinUtil.getFullSpell(header.getCity());
		return "message-" + city + "-" + header.getLine();
	}

	public String mqtt2KafkaWithMsg(String mqttTopic) {
		String[] split = mqttTopic.split("/");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			if (i == 1) {
				sb.append(PinyinUtil.getFullSpell(split[i]));
			} else {
				sb.append(split[i]);
			}
			sb.append("-");
		}
		String topic = sb.substring(0, sb.length() - 1);
		return topic;
	}

	/**
	 * @param mqttTopic
	 *            xxx/city/line/project/train
	 * @return kakfa 消息的key，key的格式非常关键，请勿随意修改！！！
	 *         project-train
	 */
	public String kafkaKey(String mqttTopic) {
		String[] split = mqttTopic.split("/");
		StringBuilder sb = new StringBuilder();
		for (int i = 3; i < split.length; i++) {
			sb.append(split[i]);
			sb.append("-");
		}
		return sb.substring(0, sb.length() - 1);
	}

	public String kafkaKey(MessageHeader header) {
		if (header == null) {
			return null;
		}
		return header.getProject() + "-" + header.getTrain();
	}

	public String kafka2Mqtt(MessageHeader header) {
		if (header.getSid() == 0x11) {
			return "ca/" + header.getCity() + "/" + header.getLine() + "/" + header.getTrain();
		} else {
			return "response/" + header.getCity() + "/" + header.getLine() + "/" + header.getTrain();
		}
	}
}
