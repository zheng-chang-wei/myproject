/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.store.service.IMessageDispatcher;
import com.hirain.phm.bd.store.service.IMessageStorager;
import com.hirain.phm.bd.store.service.MessageStoragerFactory;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月20日 下午2:23:18
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月20日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MessageDispatcher implements IMessageDispatcher {

	private String city;

	private String line;

	private String project;

	private Map<String, IMessageStorager> storeMap = new ConcurrentHashMap<>();

	@Autowired
	private MessageStoragerFactory factory;

	/**
	 * @see com.hirain.phm.bd.store.service.IMessageDispatcher#push(String,
	 *      java.lang.String)
	 * @param key
	 *            格式为project-train
	 */
	@Override
	public void push(String key, byte[] message) {
		// System.err.println(key);
		String[] split = key.split("-");
		init(split[0]);
		String train = split[1];
		IMessageStorager storager = storeMap.get(train);
		if (storager == null) {
			storager = factory.create(train);
			storager.init(project, train);
			storeMap.put(train, storager);
		}
		storager.push(message);
	}

	public void init(String city, String line) {
		this.city = city;
		this.line = line;
	}

	public void init(String project) {
		this.project = project;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the line
	 */
	public String getLine() {
		return line;
	}

	public String getProject() {
		return project;
	}

}
