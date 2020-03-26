/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.data.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.data.param.FileTreeNode;
import com.hirain.phm.synapsis.data.param.NodeLevel;
import com.hirain.phm.synapsis.data.service.DataFileService;
import com.hirain.phm.synapsis.exception.SynapsisException;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 3:00:22 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class DataFileServiceImpl implements DataFileService {

	@Value("${synapsis.data.root}")
	private String root;

	/**
	 * @see com.hirain.phm.synapsis.data.service.DataFileService#listRoot()
	 */
	@Override
	public List<FileTreeNode> listRoot() throws SynapsisException {
		return listNodes(root, NodeLevel.Root);
	}

	/**
	 * @see com.hirain.phm.synapsis.data.service.DataFileService#listNodes(com.hirain.phm.synapsis.data.param.FileTreeNode)
	 */
	@Override
	public List<FileTreeNode> listNodes(FileTreeNode node) throws SynapsisException {
		if (node.getLevel().equals(NodeLevel.Leaf)) {
			return new ArrayList<>();
		}
		return listNodes(node.getPath(), node.getLevel());
	}

	/**
	 * @see com.hirain.phm.synapsis.data.service.DataFileService#listNodes(java.lang.String, NodeLevel)
	 */
	@Override
	public List<FileTreeNode> listNodes(String path, NodeLevel parentLevel) throws SynapsisException {
		File folder = new File(path);
		if (!folder.exists()) {
			throw new SynapsisException("文件路径不正确：" + path);
		}
		NodeLevel level = getLevel(parentLevel);
		List<FileTreeNode> nodes = new ArrayList<>();
		File rootDir = new File(path);
		File[] files = rootDir.listFiles();
		for (File file : files) {
			FileTreeNode node = new FileTreeNode();
			node.setPath(file.getAbsolutePath());
			node.setName(file.getName());
			nodes.add(node);
			node.setLevel(level);
		}
		return nodes;
	}

	/**
	 * @param parentLevel
	 */
	private NodeLevel getLevel(NodeLevel parentLevel) {
		int index = parentLevel.ordinal() + 1;
		return NodeLevel.values()[index];
	}

	/**
	 * @throws SynapsisException
	 * @see com.hirain.phm.synapsis.data.service.DataFileService#listNodes(java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public List<FileTreeNode> listNodes(FileTreeNode node, Date startDate, Date endDate) throws SynapsisException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		NodeLevel level = getLevel(node.getLevel());
		List<FileTreeNode> nodes = new ArrayList<>();
		File folder = new File(node.getPath());
		File[] files = folder.listFiles();
		for (File file : files) {
			String dateStr = getDate(node, folder, file);
			if (dateStr != null) {
				Date date;
				try {
					date = sdf.parse(dateStr);
					if (date != null && date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
						FileTreeNode child = new FileTreeNode();
						child.setPath(file.getAbsolutePath());
						child.setName(file.getName());
						child.setLevel(level);
						nodes.add(child);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return nodes;
	}

	/**
	 * @param node
	 * @param folder
	 * @param file
	 * @return
	 */
	private String getDate(FileTreeNode node, File folder, File file) {
		String date = null;
		if (node.getLevel().equals(NodeLevel.Root)) {
			date = file.getName() + "01日";
		} else if (node.getLevel().equals(NodeLevel.Month)) {
			date = folder.getName() + file.getName();

		} else if (node.getLevel().equals(NodeLevel.Date)) {
			date = folder.getParentFile().getName() + folder.getName();
		}
		return date;
	}

	/**
	 * @throws SynapsisException
	 * @see com.hirain.phm.synapsis.data.service.DataFileService#listRoot(java.util.Date, java.util.Date)
	 */
	@Override
	public List<FileTreeNode> listRoot(Date startDate, Date endDate) throws SynapsisException {
		FileTreeNode node = new FileTreeNode();
		node.setPath(root);
		node.setLevel(NodeLevel.Root);
		return listNodes(node, startDate, endDate);
	}

}
