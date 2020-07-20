/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.result.ResultConfig;
import com.hirain.phm.synapsis.result.domain.AlgorithmHeader;
import com.hirain.phm.synapsis.result.domain.AlgorithmResult;
import com.hirain.phm.synapsis.result.domain.CommonHeader;
import com.hirain.phm.synapsis.result.message.AlgorithmResultMessage;
import com.hirain.phm.synapsis.result.message.AlgorithmResultMessage.Head;
import com.hirain.phm.synapsis.result.param.ResultQueryParam;
import com.hirain.phm.synapsis.result.service.AlgorithmResultService;
import com.hirain.phm.synapsis.result.service.ResultDataLoader;
import com.hirain.phm.synapsis.result.service.ResultService;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.db.AlgorithmSettingQuery;

import cn.hutool.core.util.ZipUtil;
import lombok.Setter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 20, 2020 10:04:13 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 20, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class ResultServiceImpl implements ResultService {

	@Setter
	@Autowired
	private AlgorithmSettingQuery query;

	@Setter
	@Autowired
	private AlgorithmResultService service;

	@Setter
	@Autowired
	private ResultDataLoader dataLoader;

	@Autowired
	private ResultConfig config;

	/**
	 * 要导出的文件所在目录
	 */
	private String exportRoot = System.getProperty("user.dir") + "//result_files";

	private String zipRoot = System.getProperty("user.dir") + "//result_files.zip";

	/**
	 * @see com.hirain.phm.synapsis.result.service.ResultService#parseAndSave(com.hirain.phm.synapsis.result.message.AlgorithmResultMessage)
	 */
	@Override
	public void parseAndSave(AlgorithmResultMessage message) {
		if (message.getValue() == 0) {
			return;
		}
		AlgorithmResult result = generateResult(message);
		service.save(result);
	}

	/**
	 * @param message
	 */
	private AlgorithmResult generateResult(AlgorithmResultMessage message) {
		AlgorithmResult result = new AlgorithmResult();
		AlgorithmSetting setting = query.getAlgorithmSetting(message.getCode());
		result.setAlgorithm(setting.getName());
		result.setSubsystem(setting.getSubsystem());

		result.setCode(message.getCode());
		result.setFileType(message.getFileType());

		result.setName(message.getItemName());
		result.setValue(message.getValue());
		result.setTimestamp(message.getTimestamp());

		result.setHeader(generateHeader(message));
		result.setAlgorithmHeader(generateAlgorithmHeader(message));
		return result;
	}

	/**
	 * @param message
	 * @return
	 */
	private AlgorithmHeader generateAlgorithmHeader(AlgorithmResultMessage message) {
		AlgorithmHeader header = new AlgorithmHeader();
		header.setHeaderType(message.getHeaderType());
		header.setAlgorithmId(message.getId());
		header.setSupplier(message.getSupplier());
		header.setVersion(message.getAlgorithmVersion());
		header.setLifeSignal(message.getLifeSignal());
		header.setCarriage(message.getCarriage());
		header.setEnd(message.getEnd());
		header.setDeviceSeq(message.getDeviceSeq());
		header.setSubsystem(message.getSubsystem());
		header.setCrc(message.getDataCrc());
		return header;
	}

	/**
	 * @param message
	 * @return
	 */
	private CommonHeader generateHeader(AlgorithmResultMessage message) {
		if (message.getHead() != null) {
			CommonHeader header = new CommonHeader();
			Head head = message.getHead();
			header.setSystemTime(head.getSystemTime());
			header.setLongiDegree(head.getLongiDegree());
			header.setLongiMinute(head.getLongiMinute());
			header.setLongiDirection(String.valueOf(head.getLongiDirection()));

			header.setLatiDegree(head.getLatiDegree());
			header.setLatiMinute(head.getLatiMinute());
			header.setLatiDirection(String.valueOf(head.getLatiDirection()));

			header.setVersion(head.getProtocolVersion());
			header.setCrc(head.getCrc());
			return header;
		} else {
			return null;
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.result.service.ResultService#listResults()
	 */
	@Override
	public List<AlgorithmResult> listResults() {
		return service.listResults();
	}

	/**
	 * @see com.hirain.phm.synapsis.result.service.ResultService#listResults(com.hirain.phm.synapsis.result.param.ResultQueryParam)
	 */
	@Override
	public List<AlgorithmResult> listResults(ResultQueryParam param) {
		return service.listResults(param);
	}

	/**
	 * @throws SynapsisException
	 * @throws IOException
	 * @throws JAXBException
	 * @see com.hirain.phm.synapsis.result.service.ResultService#getFileHeader(long)
	 */
	@Override
	public FileHeader getFileHeader(long resultId) throws Exception {
		AlgorithmResult result = service.selectById(resultId);
		if (result.getFileType() != AlgorithmResult.FILE_DATA) {
			throw new SynapsisException("文件不是数据文件，无法解析变量列表");
		}
		return dataLoader.getFileHeader(result);
	}

	/**
	 * @throws Exception
	 * @see com.hirain.phm.synapsis.result.service.ResultService#getVariableDatas(long, java.util.List)
	 */
	@Override
	public List<VariableData> getVariableDatas(long resultId, List<VariableGroup> selected) throws Exception {
		AlgorithmResult result = service.selectById(resultId);
		return dataLoader.getVariableDatas(result, selected);
	}

	/**
	 * @throws SynapsisException
	 * @see com.hirain.phm.synapsis.result.service.ResultService#getVideoPath(long)
	 */
	@Override
	public String getVideoPath(long resultId) throws SynapsisException {
		AlgorithmResult result = service.selectById(resultId);
		if (result.getFileType() != AlgorithmResult.FILE_VIDEO) {
			throw new SynapsisException("文件不是视频文件，无法播放");
		}
		return dataLoader.getVideo(result);
	}

	/**
	 * @see com.hirain.phm.synapsis.result.service.ResultService#deleteById(java.lang.Long)
	 */
	@Override
	public void deleteById(long resultId) {
		service.deleteById(resultId);
	}

	/**
	 * @see com.hirain.phm.synapsis.result.service.ResultService#deleteFile(java.lang.String)
	 */
	@Override
	public boolean deleteFile(String filePath) {
		boolean result = true;
		File file = new File(filePath);
		if (file.exists()) {
			result = FileUtils.deleteQuietly(file);
		}
		return result;
	}

	/**
	 * @see com.hirain.phm.synapsis.result.service.ResultService#downloadFile(javax.servlet.http.HttpServletResponse, java.lang.String[])
	 */
	@Override
	public void downloadFile(HttpServletResponse response, String... sourceFilePath) throws Exception {
		File uploadRootDir = new File(exportRoot);
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		copyFile(exportRoot, sourceFilePath);
		ZipUtil.zip(exportRoot);
		exportFile(response, "download_resultfiles");
		FileUtils.deleteDirectory(uploadRootDir);
	}

	private void copyFile(String destFilePath, String... srcFilePath) throws IOException {
		for (String path : srcFilePath) {
			File srcDir = new File(config.getRoot() + File.separator + path);
			if (srcDir.exists()) {
				File destDir = new File(destFilePath);
				FileUtils.copyDirectoryToDirectory(srcDir, destDir);
			}
		}
	}

	private void exportFile(HttpServletResponse response, String fileName) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		File file = new File(zipRoot);
		String extension = getExtension(file.getName());
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName + extension, "UTF-8"));
		InputStream input = new FileInputStream(file);
		OutputStream out = response.getOutputStream();
		byte[] buff = new byte[1024];
		int index = 0;
		while ((index = input.read(buff)) != -1) {
			out.write(buff, 0, index);
			out.flush();
		}
		out.close();
		input.close();
	}

	private String getExtension(String filename) {
		int lastIndexOf = filename.lastIndexOf(".");
		String extension = filename.substring(lastIndexOf);
		return extension;
	}

}
