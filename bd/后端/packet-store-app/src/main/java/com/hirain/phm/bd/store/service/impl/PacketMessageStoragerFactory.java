/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store.service.impl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.store.service.IMessageStorager;
import com.hirain.phm.bd.store.service.MessageStoragerFactory;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 5, 2020 10:26:45 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 5, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
@Component
public class PacketMessageStoragerFactory implements MessageStoragerFactory {

	/**
	 * @see com.hirain.phm.bd.store.service.MessageStoragerFactory#create(java.lang.String)
	 */
	@Override
	public IMessageStorager create(String train) {
		return get();
	}

	@Bean("messagestorager")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public IMessageStorager get() {
		return new PacketMessageStorager();
	}

}
