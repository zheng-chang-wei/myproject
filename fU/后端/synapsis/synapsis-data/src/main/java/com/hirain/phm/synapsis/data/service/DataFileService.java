/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.data.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.hirain.phm.synapsis.data.param.FileTreeNode;
import com.hirain.phm.synapsis.data.param.NodeLevel;
import com.hirain.phm.synapsis.exception.SynapsisException;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 3:36:56 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface DataFileService {

	/**
	 * 获得根目录下的文件夹列表
	 * 
	 * @throws SynapsisException
	 */
	List<FileTreeNode> listRoot() throws SynapsisException;

	/**
	 * 获得指定节点目录下的文件夹列表
	 * 
	 * @param node
	 * @return
	 * @throws SynapsisException
	 */
	List<FileTreeNode> listNodes(FileTreeNode node) throws SynapsisException;

	/**
	 * 获得指定节点目录下的文件夹列表
	 * 
	 * @param path
	 * @param level
	 * @return
	 * @throws SynapsisException
	 */
	List<FileTreeNode> listNodes(String path, NodeLevel level) throws SynapsisException;

	/**
	 * 获得指定节点目录下的文件夹列表，文件时间范围在startDate和endDate之间
	 * 
	 * @param root
	 * @param startDate
	 * @param endDate
	 * @throws SynapsisException
	 */
	List<FileTreeNode> listNodes(FileTreeNode node, Date startDate, Date endDate) throws SynapsisException;

	/**
	 * 获得根目录下的文件夹列表，文件时间范围在startDate和endDate之间
	 * 
	 * @param startDate
	 * @param endDate
	 * @throws SynapsisException
	 */
	List<FileTreeNode> listRoot(Date startDate, Date endDate) throws SynapsisException;

	/**
	 * 删除指定路径的文件
	 * 
	 * @param filePath
	 *            文件绝对路径
	 */
	boolean deleteFile(String filePath);

	/**
	 * 下载文件到指定路径
	 * 
	 * @param sourceFilePath
	 *            源文件路径
	 * @return
	 */
	void downloadFile(HttpServletResponse response, String... sourceFilePath) throws Exception;

}