/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.data.param;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 3:01:09 PM
 * @Description
 *              <p>
 *              每个文件节点表示一个小时内的所有文件
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class FileTreeNode {

	/**
	 * 一小时数据文件目录
	 */
	private String path;

	private String name;

	private NodeLevel level;
}
