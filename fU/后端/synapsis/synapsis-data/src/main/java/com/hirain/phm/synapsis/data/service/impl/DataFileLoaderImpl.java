/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.data.service.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.compress.CompressSupport;
import com.hirain.phm.synapsis.data.param.FileTreeNode;
import com.hirain.phm.synapsis.data.service.DataFileLoader;
import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.parse.DataFileCache;
import com.hirain.phm.synapsis.parse.DataFileParser;
import com.hirain.phm.synapsis.parse.VariableTypeHelper;
import com.hirain.phm.synapsis.parse.domain.FileDataContent;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.HeaderVariableGroup;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.VariableGroup;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 16, 2020 9:26:40 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 16, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Slf4j
@Service
public class DataFileLoaderImpl implements DataFileLoader {

	@Autowired
	private DataFileParser parser;

	@Autowired
	private VariableTypeHelper helper;

	@Autowired
	private CompressSupport compress;

	@Autowired
	private DataFileCache cache;

	private Comparator<Variable> comparator = (o1, o2) -> o1.getSignalName().compareTo(o2.getSignalName());

	public DataFileLoaderImpl() {

	}

	/**
	 */
	public DataFileLoaderImpl(DataFileParser parser, DataFileCache cache) {
		this.parser = parser;
		this.cache = cache;
	}

	/**
	 * @throws SynapsisException
	 * @see com.hirain.phm.synapsis.data.service.DataFileLoader#getVariables(java.util.List)
	 */
	@Override
	public FileHeader getVariables(List<FileTreeNode> fileNodes) throws SynapsisException {
		List<FileHeaderWrapper> wrappers = parseHeader(fileNodes);
		if (wrappers.size() > 0) {
			String result = isSameHeaders(wrappers);
			if (result != null) {
				throw new SynapsisException(result);
			}
			FileHeader header = wrappers.get(0).headerList;
			cache.cache(fileNodes.get(0).getPath(), header);
			return header;
		}
		return null;
	}

	/**
	 * @param fileNodes
	 * @return
	 */
	private List<FileHeaderWrapper> parseHeader(List<FileTreeNode> fileNodes) {
		List<FileHeaderWrapper> wrappers = new ArrayList<>();
		for (FileTreeNode node : fileNodes) {
			FileHeader headerList = parseHeader(node);
			if (headerList != null) {
				FileHeaderWrapper wrapper = new FileHeaderWrapper();
				wrapper.node = node;
				wrapper.headerList = headerList;
				wrappers.add(wrapper);
			}
		}
		return wrappers;
	}

	/**
	 * @param node
	 * @return
	 */
	private FileHeader parseHeader(FileTreeNode node) {
		File file = getDataHeaderFile(node);
		if (file != null) {
			try {
				return parser.parseHeader(file);
			} catch (JAXBException | IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}

	/**
	 * @param node
	 * @return
	 */
	private File getDataHeaderFile(FileTreeNode node) {
		File folder = new File(node.getPath());
		File[] files = folder.listFiles((FilenameFilter) (dir, name) -> name.startsWith("data_header"));
		if (files.length > 0) {
			return files[0];
		}
		return null;
	}

	/**
	 * 比较数据头是否一致
	 * 
	 * @param wrappers
	 * @throws SynapsisException
	 */
	private String isSameHeaders(List<FileHeaderWrapper> wrappers) {
		getVariables(wrappers.get(0));
		for (int i = 1; i < wrappers.size(); i++) {
			getVariables(wrappers.get(i));
			boolean equal = isSameHeader(wrappers.get(i - 1), wrappers.get(i));
			if (!equal) {
				FileTreeNode node1 = wrappers.get(i - 1).node;
				FileTreeNode node2 = wrappers.get(i).node;
				return node1.getPath() + "和" + node2.getPath() + "配置不一致，不能同时选择，请重新选择";
			}
		}
		return null;
	}

	/**
	 * @param first
	 */
	private void getVariables(FileHeaderWrapper first) {
		FileHeader headerList = first.headerList;
		List<Variable> variables = new ArrayList<>();
		for (HeaderVariableGroup header : headerList.getHeaders()) {
			List<Variable> temp = new ArrayList<>(header.getVariables());
			Collections.sort(temp, comparator);
			variables.addAll(temp);
		}
		first.variables = variables;
	}

	/**
	 * @param h1
	 * @param h2
	 * @return
	 */
	private boolean isSameHeader(FileHeaderWrapper h1, FileHeaderWrapper h2) {
		List<? extends Variable> variables1 = h1.variables;
		List<? extends Variable> variables2 = h2.variables;
		if (variables1.size() == variables2.size()) {
			for (int i = 0; i < variables1.size(); i++) {
				if (!variables1.get(i).getSignalName().equals(variables2.get(i).getSignalName())) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * @throws SynapsisException
	 * @see com.hirain.phm.synapsis.data.service.DataFileLoader#getVariableDatas(java.util.List, int, java.util.List)
	 */
	@Override
	public List<VariableData> getVariableDatas(List<FileTreeNode> nodes, int offset, List<VariableGroup> selectedVariables) throws SynapsisException {
		List<VariableData> result = new ArrayList<>();
		for (VariableGroup group : selectedVariables) {
			File dataFile = getDataFile(nodes, offset, group);
			if (dataFile == null) {
				throw new SynapsisException("文件已全部读取完毕");
			}
			HeaderVariableGroup header = getHeaderVariableGroup(nodes.get(0), group);
			try {
				byte[] compressBs = FileUtils.readFileToByteArray(dataFile);
				byte[] datas = compress.decompress(compressBs);
				FileDataContent dataContent = new FileDataContent(datas);
				List<VariableData> variableDatas = parser.parseData(header, dataContent, group.getVariables(), helper.getValuePostProcessor(header));
				result.addAll(variableDatas);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return result;
	}

	/**
	 * 根据偏移获取数据文件
	 * 
	 * @param nodes
	 * @param offset
	 */
	private File getDataFile(List<FileTreeNode> nodes, int offset, VariableGroup group) {
		String prefix = helper.getDataFilePrefix(group);
		int fileCount = offset;
		for (FileTreeNode node : nodes) {
			File folder = new File(node.getPath());
			File[] files = folder.listFiles((FilenameFilter) (dir, name) -> name.startsWith(prefix));
			Arrays.sort(files);
			if (fileCount <= files.length) {
				return files[fileCount - 1];
			} else {
				fileCount -= files.length;
			}
		}
		return null;
	}

	/**
	 * @param node
	 * @param group
	 * @return
	 */
	private HeaderVariableGroup getHeaderVariableGroup(FileTreeNode node, VariableGroup group) {
		FileHeader header = (FileHeader) cache.get(node.getPath());
		if (header == null) {
			header = parseHeader(node);
			cache.cache(node.getPath(), header);
		}
		for (HeaderVariableGroup headerGroup : header.getHeaders()) {
			if (helper.isSameGroup(headerGroup, group)) {
				return headerGroup;
			}
		}
		return null;
	}

	/**
	 * @param helper
	 *            the helper to set
	 */
	public void setHelper(VariableTypeHelper helper) {
		this.helper = helper;
	}

	/**
	 * @param compress
	 *            the compress to set
	 */
	public void setCompress(CompressSupport compress) {
		this.compress = compress;
	}

	private class FileHeaderWrapper {

		FileTreeNode node;

		FileHeader headerList;

		List<? extends Variable> variables;
	}

}
