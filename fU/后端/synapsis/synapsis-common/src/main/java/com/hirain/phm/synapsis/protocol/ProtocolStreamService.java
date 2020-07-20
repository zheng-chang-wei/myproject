/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.protocol;

import com.hirain.phm.synapsis.exception.SynapsisException;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 2:04:28 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface ProtocolStreamService {

	/**
	 * 解析数据流文件
	 * 
	 * @param type
	 * @param filename
	 * @return
	 * @throws SynapsisException
	 */
	ParseResult read(String type, String filename) throws SynapsisException;

	/**
	 * 生成xml的数据流文件
	 * 
	 * @param type
	 * @param filepath
	 * @param object
	 * @throws SynapsisException
	 */
	void write(String type, String filepath, Object object) throws SynapsisException;
}
