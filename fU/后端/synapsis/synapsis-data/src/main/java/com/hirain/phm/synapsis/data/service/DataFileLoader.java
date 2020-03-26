/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.data.service;

import java.util.List;

import com.hirain.phm.synapsis.data.param.FileTreeNode;
import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 16, 2020 9:24:11 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 16, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface DataFileLoader {

	/**
	 * 获得变量列表
	 * 注意需要先确定所选文件是同一配置下产生的
	 * 
	 * @param fileNodes
	 * @return
	 * @throws SynapsisException
	 */
	FileHeader getVariables(List<FileTreeNode> fileNodes) throws SynapsisException;

	/**
	 * @param nodes
	 *            所选文件组
	 * @param offset
	 *            数据段偏移，5分钟为一个数据段
	 * @param selectedVariables
	 *            所选变量
	 * @return
	 * @throws SynapsisException
	 */
	List<VariableData> getVariableDatas(List<FileTreeNode> nodes, int offset, List<VariableGroup> selectedVariables) throws SynapsisException;
}
