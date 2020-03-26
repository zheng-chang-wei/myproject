/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.protocol;

import java.io.File;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 2:00:27 PM
 * @Description
 *              <p>
 *              数据流文件解析和生成
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface ProtocolStream {

	default ParseResult read(String filename) throws Exception {
		throw new NoSuchMethodException("no method");
	}

	default ParseResult read(File file) throws Exception {
		throw new NoSuchMethodException("no method");
	}

	default void write(String filename, Object obj) throws Exception {

	}
}
