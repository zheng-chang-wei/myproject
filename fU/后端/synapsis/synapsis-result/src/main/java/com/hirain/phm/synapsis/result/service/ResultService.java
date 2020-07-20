/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.result.domain.AlgorithmResult;
import com.hirain.phm.synapsis.result.message.AlgorithmResultMessage;
import com.hirain.phm.synapsis.result.param.ResultQueryParam;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 20, 2020 10:03:44 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 20, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface ResultService {

	/**
	 * @param message
	 */
	void parseAndSave(AlgorithmResultMessage message);

	/**
	 * 
	 */
	List<AlgorithmResult> listResults();

	/**
	 * @param param
	 */
	List<AlgorithmResult> listResults(ResultQueryParam param);

	/**
	 * @throws Exception
	 */
	FileHeader getFileHeader(long resultId) throws Exception;

	String getVideoPath(long resultId) throws SynapsisException;

	/**
	 * @param selected
	 * @throws Exception
	 */
	List<VariableData> getVariableDatas(long l, List<VariableGroup> selected) throws Exception;

	/**
	 * 根据id删除数据库中的分析结果
	 * 
	 * @param id
	 */
	void deleteById(long id);

	/**
	 * 删除指定路径的文件
	 * 
	 * @param filePath
	 * @return
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
