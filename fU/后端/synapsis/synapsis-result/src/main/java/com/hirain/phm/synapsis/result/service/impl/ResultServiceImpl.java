/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.service.impl;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.result.domain.AlgorithmHeader;
import com.hirain.phm.synapsis.result.domain.AlgorithmResult;
import com.hirain.phm.synapsis.result.domain.CommonHeader;
import com.hirain.phm.synapsis.result.message.AlgorithmResultMessage;
import com.hirain.phm.synapsis.result.param.ResultQueryParam;
import com.hirain.phm.synapsis.result.service.AlgorithmResultService;
import com.hirain.phm.synapsis.result.service.ResultDataLoader;
import com.hirain.phm.synapsis.result.service.ResultService;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.db.AlgorithmSettingQuery;

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
		CommonHeader header = new CommonHeader();
		header.setSystemTime(message.getSystemTime());
		header.setLongiDegree(message.getLongiDegree());
		header.setLongiMinute(message.getLongiMinute());
		header.setLongiDirection(String.valueOf(message.getLongiDirection()));

		header.setLatiDegree(message.getLatiDegree());
		header.setLatiMinute(message.getLatiMinute());
		header.setLatiDirection(String.valueOf(message.getLatiDirection()));

		header.setVersion(message.getProtocolVersion());
		header.setCrc(message.getCrc());
		return header;
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

}
