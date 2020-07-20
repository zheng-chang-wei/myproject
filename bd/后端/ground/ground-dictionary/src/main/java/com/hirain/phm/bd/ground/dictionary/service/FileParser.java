/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.hirain.phm.bd.ground.dictionary.domain.FirstComponent;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 13, 2020 2:09:31 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface FileParser {

	/**
	 * @param inputStream
	 * @throws IOException
	 */
	List<FirstComponent> parse(InputStream inputStream) throws IOException;

}
