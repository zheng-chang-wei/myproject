/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.setting;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.algorithm.domain.StoreVariables;
import com.hirain.phm.synapsis.algorithm.parse.AlgorithmSettingFileParser;
import com.hirain.phm.synapsis.algorithm.parse.BoardSettingFileParser;
import com.hirain.phm.synapsis.algorithm.util.BeanConstants;
import com.hirain.phm.synapsis.algorithm.util.XMLUtil;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.PHMAlgorithmSetting;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 16, 2019 2:18:26 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 16, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class SettingServiceImpl implements SettingService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AlgorithmSettingFileParser<PHMAlgorithmSetting> parser;

	@Autowired
	private BoardSettingFileParser<BoardSetting> boardParser;

	@Autowired
	@Qualifier(BeanConstants.PHMALGORITHMSETTING)
	private PHMAlgorithmSetting phmAlgorithmSetting;

	@Autowired
	@Qualifier(BeanConstants.BOARDSETTING)
	private BoardSetting boardSetting;

	@Autowired
	@Qualifier(BeanConstants.MESSAGECACHEROOTPATH)
	private String cacheParentPath;

	@Value("${data.header.file.name}")
	private String dataHeaderFileName;

	private File dataHeaderFile = null;

	/**
	 * @see com.hirain.phm.synapsis.algorithm.setting.SettingService#parseAlgorithmSetting(java.io.File)
	 */
	@Override
	public PHMAlgorithmSetting parseAlgorithmSetting(File file) throws Exception {
		PHMAlgorithmSetting parseResult = parser.parse(file);
		copyAlgorithmSetting(parseResult, phmAlgorithmSetting);
		return phmAlgorithmSetting;
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.setting.SettingService#parseBoardSetting(java.io.File)
	 */
	@Override
	public BoardSetting parseBoardSetting(File file) throws Exception {
		BoardSetting result = boardParser.parse(file);
		copyBoardSetting(result, boardSetting);
		return boardSetting;
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.setting.SettingService#storageVariableSetting()
	 */
	@Override
	public void storageVariableSetting() {
		final File cacheParentFolder = new File(cacheParentPath);
		if (!cacheParentFolder.exists()) {
			cacheParentFolder.mkdirs();
		}
		String filePath = cacheParentPath + dataHeaderFileName;
		dataHeaderFile = new File(filePath);
		try {
			if (dataHeaderFile.exists()) {
				return;
			} else {
				dataHeaderFile.createNewFile();
				writeTxt();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.setting.SettingService#getDataHeaderFile()
	 */
	@Override
	public File getDataHeaderFile() {
		return dataHeaderFile;
	}

	private void writeTxt() {
		ByteBuffer byteBuffer = null;
		List<VariableGroup> allVariableGroups = new ArrayList<>();
		try {
			FileChannel outFc = new FileOutputStream(dataHeaderFile, true).getChannel();
			List<AlgorithmSetting> algorithmSettings = phmAlgorithmSetting.getAlgorithmSettings();
			for (AlgorithmSetting algorithmSetting : algorithmSettings) {
				List<VariableGroup> variableGroups = algorithmSetting.getVariableGroups();
				allVariableGroups.addAll(variableGroups);
			}
			StoreVariables list = new StoreVariables(allVariableGroups);
			String content = XMLUtil.convertToXml(list);

			byteBuffer = ByteBuffer.wrap(content.getBytes("UTF-8"));
			outFc.write(byteBuffer);

			outFc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void copyAlgorithmSetting(PHMAlgorithmSetting source, PHMAlgorithmSetting target) {
		target.setRetryIp(source.getRetryIp());
		target.setAlgorithmSettings(source.getAlgorithmSettings());
	}

	private void copyBoardSetting(BoardSetting source, BoardSetting target) {
		target.setType(source.getType());// 目前只用到type属性
	}

}
