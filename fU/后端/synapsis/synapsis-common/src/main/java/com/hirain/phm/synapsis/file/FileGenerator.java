/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.file;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 6:51:02 PM
 * @Description
 *              <p>
 *              配置文件生成接口
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface FileGenerator {

	void generate(String filepath, Object object) throws Exception;
}
