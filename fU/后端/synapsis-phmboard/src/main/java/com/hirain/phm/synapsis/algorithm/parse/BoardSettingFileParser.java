/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.parse;

import java.io.File;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 16, 2019 2:27:43 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 16, 2019     zepei.tao@hirain.com    1.0   create file   
 */
public interface BoardSettingFileParser<T> {

	T parse(File file) throws Exception;

}
