/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.variable.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.protocol.VariableConverter;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.support.variable.VariableConvertManager;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 3:57:27 PM
 * @Description
 *              <p>
 *              从数据流对象中获取变量列表
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Slf4j
public class VariableConvertManagerImpl implements VariableConvertManager {

	@Autowired(required = false)
	private Map<String, VariableConverter> converters;

	/** 
	 * @see com.hirain.phm.synapsis.setting.support.variable.VariableConvertManager#convert(java.lang.String, java.lang.Object)
	 */
	@Override
	public List<? extends Variable> convert(String type, Object object) throws SynapsisException {
		if (converters != null && converters.containsKey(type + "-converter")) {
			VariableConverter converter = converters.get(type + "-converter");
			try {
				return converter.convert(object);
			} catch (SynapsisException e) {
				throw e;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new SynapsisException("获取变量出错,type=[" + type + "]", e);
			}
		}
		throw new SynapsisException("no converter for type:" + type);
	}

}
