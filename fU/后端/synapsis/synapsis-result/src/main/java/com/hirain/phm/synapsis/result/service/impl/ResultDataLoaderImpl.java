/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.service.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.compress.CompressSupport;
import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.parse.DataFileCache;
import com.hirain.phm.synapsis.parse.DataFileParser;
import com.hirain.phm.synapsis.parse.VariableTypeHelper;
import com.hirain.phm.synapsis.parse.domain.FileDataContent;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.HeaderVariableGroup;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.result.ResultConfig;
import com.hirain.phm.synapsis.result.domain.AlgorithmResult;
import com.hirain.phm.synapsis.result.service.ResultDataLoader;
import com.hirain.phm.synapsis.setting.VariableGroup;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 11, 2020 2:14:49 PM
 * @Description
 *              <p>
 *              分析结果关联的数据加载
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 11, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Slf4j
public class ResultDataLoaderImpl implements ResultDataLoader {

	@Setter
	@Autowired
	private DataFileParser parser;

	@Setter
	@Autowired
	private DataFileCache fileCache;

	@Setter
	@Autowired
	private VariableTypeHelper helper;

	@Setter
	@Autowired
	private CompressSupport compress;

	@Autowired
	@Setter
	private ResultConfig config;

	/**
	 * @throws SynapsisException
	 * @throws IOException
	 * @throws JAXBException
	 */
	@Override
	public FileHeader getFileHeader(AlgorithmResult result) throws Exception {
		String foldername = getFilename(result);
		File headerFile = getHeaderFile(foldername);
		FileHeader header = parser.parseHeader(headerFile);
		fileCache.cache("resultId:" + result.getId(), header);
		return header;
	}

	/**
	 * @param result
	 */
	private String getFilename(AlgorithmResult result) {
		// TODO 数据文件命名规范
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(result.getTimestamp());
		return result.getCode() + "_" + timestamp;
	}

	/**
	 * @param path
	 */
	private File getHeaderFile(String filename) {
		File folder = new File(config.getRoot(), filename);
		File[] files = folder.listFiles((FilenameFilter) (dir, name) -> name.startsWith("data_header"));
		if (files.length > 0) {
			return files[0];
		}
		return null;
	}

	/**
	 * @throws Exception
	 */
	@Override
	public List<VariableData> getVariableDatas(AlgorithmResult result, List<VariableGroup> selected) throws Exception {
		FileHeader header = getHeader(result);
		String foldername = getFilename(result);
		List<VariableData> variableDataList = new ArrayList<>();
		for (VariableGroup group : selected) {
			File file = getDataFile(foldername, group);
			if (file == null) {
				throw new SynapsisException("数据文件缺失");
			}
			HeaderVariableGroup headerGroup = getHeaderVariableGroup(header, group);
			try {
				byte[] compressBs = FileUtils.readFileToByteArray(file);
				byte[] datas = compress.decompress(compressBs);
				FileDataContent dataContent = new FileDataContent(datas);
				List<VariableData> variableDatas = parser.parseData(headerGroup, dataContent, group.getVariables(),
						helper.getValuePostProcessor(headerGroup));
				variableDataList.addAll(variableDatas);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return variableDataList;
	}

	/**
	 * @param resultId
	 * @throws Exception
	 */
	private FileHeader getHeader(AlgorithmResult result) throws Exception {
		Object header = fileCache.get("resultId:" + result.getId());
		if (header != null) {
			return (FileHeader) header;
		}
		return getFileHeader(result);
	}

	/**
	 * @param foldername
	 * @param group
	 */
	private File getDataFile(String foldername, VariableGroup group) {
		String prefix = helper.getDataFilePrefix(group);
		File folder = new File(config.getRoot(), foldername);
		File[] files = folder.listFiles((FilenameFilter) (dir, name) -> name.startsWith(prefix));
		if (files.length > 0) {
			return files[0];
		}
		return null;
	}

	private HeaderVariableGroup getHeaderVariableGroup(FileHeader header, VariableGroup group) {
		for (HeaderVariableGroup headerGroup : header.getHeaders()) {
			if (helper.isSameGroup(headerGroup, group)) {
				return headerGroup;
			}
		}
		return null;
	}

	/**
	 * @see com.hirain.phm.synapsis.result.service.ResultDataLoader#getVideo(com.hirain.phm.synapsis.result.domain.AlgorithmResult)
	 */
	@Override
	public String getVideo(AlgorithmResult result) {
		String filename = getFilename(result);
		return config.getRoot() + File.separator + filename;
	}

}
