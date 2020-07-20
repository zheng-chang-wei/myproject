/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.message;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.common.partition.IPartitioner;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月8日 下午2:43:49
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Configuration
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DoorMessageService {

	@Autowired
	private MessageProperties props;

	@Autowired
	private IPartitioner partitioner;

	private IMessageProcessor[] processors;

	@PostConstruct
	public void init() {
		processors = new IMessageProcessor[props.getProcessorSize()];
	}

	public void messageArrived(String topic, byte[] payload) {
		int index = partitioner.partition(topic, props.getProcessorSize());
		IMessageProcessor processor = processors[index];
		if (processor == null) {
			processor = processor();
			processor.process();
			processors[index] = processor;
		}
		processor.push(topic, payload);
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public IMessageProcessor processor() {
		return new DefaultMessageProcessor();
	}

}
