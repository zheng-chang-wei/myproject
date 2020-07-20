/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.protocol.ProtocolStreamService;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.db.SettingDbService;
import com.hirain.phm.synapsis.setting.support.ProtocolService;
import com.hirain.phm.synapsis.setting.support.SettingUpdateSupporter;
import com.hirain.phm.synapsis.setting.support.variable.VariableConvertManager;
import com.hirain.phm.synapsis.util.SettingFileConfig;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 24, 2020 9:17:09 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 24, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Slf4j
public class ProtocolServiceImpl implements ProtocolService {

	@Autowired
	private VariableConvertManager manager;

	@Autowired
	private SettingDbService settingService;

	@Autowired
	private SettingUpdateSupporter supporter;

	@Autowired
	private ProtocolStreamService streamService;

	@Autowired
	private SettingFileConfig config;

	/**
	 * @throws SynapsisException
	 * @see com.hirain.phm.synapsis.runtime.service.ProtocolService#getVariables(java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<VariableGroup> getVariables(Integer settingId, String type) throws SynapsisException {
		ProtocolCacheObject protocolObject = generateProtocolObject(settingId, type);
		if (protocolObject != null) {
			try {
				List<? extends Variable> variables = manager.convert(type, protocolObject.getObject());
				if (variables != null) {
					VariableGroup group = new VariableGroup();
					group.setSlotId(protocolObject.getSlotId());
					group.setType(type);
					group.setVariables(variables);
					return Arrays.asList(group);
				}
			} catch (SynapsisException e) {
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}

	/**
	 * @see com.hirain.phm.synapsis.runtime.service.ProtocolService#getProtocolObject(java.lang.Integer, java.lang.String)
	 */
	@Override
	public Object getProtocolObject(Integer settingId, String type) {
		try {
			ProtocolCacheObject object = generateProtocolObject(settingId, type);
			if (object != null) {
				return object.getObject();
			}
		} catch (SynapsisException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * @param settingId
	 * @param type
	 * @return
	 * @throws SynapsisException
	 */
	private ProtocolCacheObject generateProtocolObject(Integer settingId, String type) throws SynapsisException {
		ProtocolCacheObject po = null;
		BoardSetting boardSetting = getBoardSetting(settingId, type);
		String filename = null;
		if (boardSetting != null) {
			filename = supporter.getFilename(boardSetting);
		}
		if (filename == null) {
			return null;
		}
		Setting setting = settingService.selectById(settingId);
		String path = config.getResource() + File.separator + setting.getName() + File.separator + "boards" + File.separator + filename;
		try {
			ParseResult result = streamService.read(type, path);
			if (result.getCode() == ParseResult.SUCCESS) {
				po = new ProtocolCacheObject();
				po.setObject(result.getData());
				po.setSlotId(boardSetting.getSlotId());
			}
		} catch (SynapsisException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		return po;
	}

	private BoardSetting getBoardSetting(Integer settingId, String type) {
		return settingService.findBoardSetting(settingId, type);
	}

	@Data
	class ProtocolCacheObject {

		private Integer slotId;

		private String type;

		private Object object;
	}
}
