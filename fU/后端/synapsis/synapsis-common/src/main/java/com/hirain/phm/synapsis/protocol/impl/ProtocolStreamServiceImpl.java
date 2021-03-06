/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.protocol.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.protocol.ProtocolStreamService;
import com.hirain.phm.synapsis.protocol.ProtocolStream;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 2:06:35 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Slf4j
public class ProtocolStreamServiceImpl implements ProtocolStreamService {

	@Autowired(required = false)
	private Map<String, ProtocolStream> stream;

	/**
	 * @see com.hirain.phm.synapsis.protocol.ProtocolStreamService#read(java.lang.String, java.lang.String)
	 */
	@Override
	public ParseResult read(String type, String filename) throws SynapsisException {
		if (stream != null && stream.containsKey(type)) {
			try {
				ProtocolStream parser = stream.get(type);
				ParseResult parseResult = parser.read(filename);
				return parseResult;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new SynapsisException("数据流文件解析错误:" + type, e);
			}
		}
		throw new SynapsisException("no parser for type:" + type);
	}

	/**
	 * @see com.hirain.phm.synapsis.protocol.ProtocolStreamService#toFile(java.lang.String, java.lang.Object)
	 */
	@Override
	public void write(String type, String filepath, Object object) throws SynapsisException {
		ProtocolStream parser = stream.get(type);
		try {
			parser.write(filepath, object);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new SynapsisException("数据流文件序列化错误:" + type, e);
		}
	}

}
