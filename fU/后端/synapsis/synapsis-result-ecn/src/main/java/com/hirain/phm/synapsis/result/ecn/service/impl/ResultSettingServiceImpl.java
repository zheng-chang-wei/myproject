/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.ecn.ECNVariableQuery;
import com.hirain.phm.synapsis.ecn.impl.ECNGroupConverter;
import com.hirain.phm.synapsis.ecn.model.Device;
import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.file.FileGenerator;
import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.protocol.ProtocolService;
import com.hirain.phm.synapsis.result.ecn.domain.AlgorithmIndex;
import com.hirain.phm.synapsis.result.ecn.domain.AlgorithmIndexSetting;
import com.hirain.phm.synapsis.result.ecn.domain.CommonSegment;
import com.hirain.phm.synapsis.result.ecn.domain.CommonSegmentSetting;
import com.hirain.phm.synapsis.result.ecn.file.XmlResultRoot;
import com.hirain.phm.synapsis.result.ecn.service.AlgorithmIndexSettingService;
import com.hirain.phm.synapsis.result.ecn.service.CommonHeaderService;
import com.hirain.phm.synapsis.result.ecn.service.ResultSettingService;
import com.hirain.phm.synapsis.result.ecn.vo.AlgorithmIndexVO;
import com.hirain.phm.synapsis.result.ecn.vo.AlgorithmOutputVO;
import com.hirain.phm.synapsis.result.ecn.vo.CommonSegmentVO;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.ECNVariable;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.Variable.VariableType;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.db.AlgorithmSettingQuery;
import com.hirain.phm.synapsis.setting.db.SettingDbService;
import com.hirain.phm.synapsis.setting.db.VariableDbService;
import com.hirain.phm.synapsis.setting.support.MulticastAssigner;
import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;

import lombok.Setter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 2:59:51 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class ResultSettingServiceImpl implements ResultSettingService {

	@Value("${setting.file.root}")
	@Setter
	private String rootDirectory;// 配置生效时的文件根目录

	@Value("${setting.file.resource}")
	private String resourceDirectory;// 配置保存时的资源文件根目录

	@Autowired
	@Setter
	private MulticastAssigner assigner;

	@Autowired
	@Setter
	private CommonHeaderService headerService;

	@Autowired
	@Setter
	private AlgorithmIndexSettingService indexService;

	@Autowired
	@Setter
	private VariableDbService groupService;

	@Autowired
	@Setter
	private AlgorithmSettingQuery algorithmSettingQuery;

	@Autowired
	@Setter
	private SettingDbService dbService;

	@Autowired
	@Setter
	private FileGenerator fileGenerator;

	@Autowired
	private ECNGroupConverter converter = new ECNGroupConverter();

	@Autowired
	private ECNVariableQuery query;

	@Autowired
	private ProtocolService protocolService;

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.ResultSettingService#save(Setting,
	 *      com.hirain.phm.synapsis.result.ecn.vo.AlgorithmOutputVO)
	 */
	@Override
	public void save(Integer settingId, AlgorithmOutputVO output) {
		Setting setting = dbService.selectWithDetail(settingId);
		BoardSetting board = getEcnBoard(setting);
		CommonSegmentSetting commonSegment = generateCommonSegmentSetting(setting.getId(), board.getSlotId(), output);
		List<AlgorithmIndexSetting> algorithmIndexSetting = genearteAlgorithmIndexSetting(setting, output);
		groupService.saveGroups(board.getId(), Arrays.asList(commonSegment.getSubscribeGroup()));
		commonSegment.setGroupId(commonSegment.getSubscribeGroup().getId());
		headerService.save(commonSegment);
		indexService.save(algorithmIndexSetting);
	}

	/**
	 * @param setting
	 * @param output
	 * @return
	 */
	@Override
	public List<AlgorithmIndexSetting> genearteAlgorithmIndexSetting(Setting setting, AlgorithmOutputVO output) {
		Map<Long, String> map = getComIdCycle(setting);
		return generateAlgorithmIndexSetting(setting.getId(), setting.getAlgorithmSettings(), map, output);
	}

	/**
	 * @param setting
	 */
	private Map<Long, String> getComIdCycle(Setting setting) {
		Map<Long, String> map = new HashMap<>();
		Device device = null;
		for (BoardSetting boardSetting : setting.getBoardSettings()) {
			if (boardSetting.getType().equals(BoardType.ECN.getType())) {
				String filename = boardSetting.getFilename();
				String filepath = resourceDirectory + File.separator + setting.getName() + File.separator + filename;
				try {
					ParseResult result = protocolService.parse(boardSetting.getType(), filepath);
					device = (Device) result.getData();
				} catch (SynapsisException e) {
					e.printStackTrace();
				}
			}
		}
		if (device != null) {
			map.putAll(query.getComIdCycle(device));
		}
		return map;
	}

	/**
	 * @param setting
	 * @param output
	 * @return
	 */
	@Override
	public CommonSegmentSetting generateCommonSegmentSetting(Setting setting, AlgorithmOutputVO output) {
		BoardSetting board = getEcnBoard(setting);
		return generateCommonSegmentSetting(setting.getId(), board.getSlotId(), output);
	}

	/**
	 * @param setting
	 */
	private BoardSetting getEcnBoard(Setting setting) {
		for (BoardSetting board : setting.getBoardSettings()) {
			if (board.getType().equals("ECN")) {
				return board;
			}
		}
		return null;
	}

	/**
	 * @param settingId
	 * @param slotId
	 * @param output
	 * @return
	 */
	private CommonSegmentSetting generateCommonSegmentSetting(Integer settingId, Integer slotId, AlgorithmOutputVO output) {
		CommonSegmentSetting commonSetting = new CommonSegmentSetting();
		commonSetting.setSettingId(settingId);
		commonSetting.setLength(output.getCommonLength());
		commonSetting.setDataLength(output.getDataLength());
		List<CommonSegment> segments = getSegments(output.getCommonSegments());
		commonSetting.setSegments(segments);
		VariableGroup group = generateSubscribeGroup(slotId, segments);
		commonSetting.setSubscribeGroup(group);
		return commonSetting;
	}

	/**
	 * @param slotId
	 * @param segments
	 */
	private VariableGroup generateSubscribeGroup(Integer slotId, List<CommonSegment> segments) {
		VariableGroup group = new VariableGroup();
		group.setConsumptionId(assigner.nextConsumption());
		group.setMulticastIp(assigner.nextIp());
		group.setMulticastPort(assigner.nextPort());
		group.setType(VariableType.ECN.name());
		group.setSlotId(slotId);
		List<ECNVariable> variables = new ArrayList<>();
		for (CommonSegment source : segments) {
			if (source.getType() == CommonSegment.SOURCE_BUS) {
				variables.add(source.getSource());
			}
		}
		if (variables.isEmpty()) {
			return null;
		}
		group.setVariables(variables);
		return group;
	}

	/**
	 * @param segmentsVO
	 * @return
	 */
	private List<CommonSegment> getSegments(List<CommonSegmentVO> segmentsVO) {
		List<CommonSegment> segments = new ArrayList<>();
		for (CommonSegmentVO vo : segmentsVO) {
			CommonSegment segment = new CommonSegment();
			segment.setType(vo.getType());
			if (vo.getType() == CommonSegment.SOURCE_BUS) {
				segment.setSource(converter.converFrom(vo.getSource()));
			} else {
				segment.setProperty(vo.getProperty());
			}
			segment.setIndex(vo.getIndex());
			segment.setName(vo.getName());
			segment.setDataType(vo.getDataType());
			segment.setByteLen(vo.getByteLen());
			segments.add(segment);
		}
		return segments;
	}

	/**
	 * @param settingId
	 * @param algorithmSettings
	 * @param cycles
	 * @param output
	 */
	private List<AlgorithmIndexSetting> generateAlgorithmIndexSetting(Integer settingId, List<AlgorithmSetting> algorithmSettings,
			Map<Long, String> cycles, AlgorithmOutputVO output) {
		Map<String, Integer> algorithmMap = algorithmSettings.stream()
				.collect(Collectors.toMap(AlgorithmSetting::getName, AlgorithmSetting::getCode));
		List<AlgorithmIndexSetting> settings = new ArrayList<>();
		for (AlgorithmIndexVO vo : output.getAlgorithms()) {
			AlgorithmIndexSetting indexSetting = new AlgorithmIndexSetting();
			indexSetting.setComId(vo.getComId());
			indexSetting.setSettingId(settingId);
			indexSetting.setCycle(cycles.get(vo.getComId()));
			List<AlgorithmIndex> indexList = new ArrayList<>();
			List<String> names = vo.getAlgorithms();
			for (int i = 0; i < names.size(); i++) {
				String name = names.get(i);
				if (isSpace(name)) {
					AlgorithmIndex index = new AlgorithmIndex();
					index.setCode(algorithmMap.get(name));
					index.setIndex(i);
					indexList.add(index);
				}
			}
			indexSetting.setAlgorithms(indexList);
			settings.add(indexSetting);
		}
		return settings;
	}

	/**
	 * @param name
	 * @return
	 */
	public boolean isSpace(String name) {
		return name != null && !name.isEmpty();
	}

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.ResultSettingService#getResultSetting(java.lang.Integer)
	 */
	@Override
	public AlgorithmOutputVO getResultSetting(Integer settingId) {
		CommonSegmentSetting commonSegmentSetting = headerService.selectBy(settingId);
		List<AlgorithmIndexSetting> indexSetting = indexService.selectBy(settingId);
		AlgorithmOutputVO vo = new AlgorithmOutputVO();
		vo.setCommonLength(commonSegmentSetting.getLength());
		vo.setDataLength(commonSegmentSetting.getDataLength());
		vo.setCommonSegments(getCommonVO(commonSegmentSetting.getSegments()));
		vo.setAlgorithms(getIndexVO(indexSetting));
		return vo;
	}

	/**
	 * @param indexSettings
	 * @return
	 */
	private List<AlgorithmIndexVO> getIndexVO(List<AlgorithmIndexSetting> indexSettings) {
		List<AlgorithmIndexVO> voList = new ArrayList<>();
		for (AlgorithmIndexSetting indexSetting : indexSettings) {
			AlgorithmIndexVO vo = new AlgorithmIndexVO();
			vo.setComId(indexSetting.getComId());
			List<String> names = new ArrayList<>();
			int i = 0;
			for (AlgorithmIndex index : indexSetting.getAlgorithms()) {
				while (i < index.getIndex()) {
					names.add("");
				}
				AlgorithmSetting algorithmSetting = algorithmSettingQuery.getAlgorithmSetting(indexSetting.getSettingId(), index.getCode());
				names.add(algorithmSetting.getName());
			}
			vo.setAlgorithms(names);
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * @param segments
	 * @return
	 */
	private List<CommonSegmentVO> getCommonVO(List<CommonSegment> segments) {
		List<CommonSegmentVO> voList = new ArrayList<>();
		for (CommonSegment segment : segments) {
			CommonSegmentVO vo = new CommonSegmentVO();
			vo.setProperty(segment.getProperty());
			vo.setByteLen(segment.getByteLen());
			vo.setDataType(segment.getDataType());
			vo.setIndex(segment.getIndex());
			vo.setName(segment.getName());
			vo.setType(segment.getType());
			if (vo.getType() == CommonSegment.SOURCE_BUS) {
				vo.setSource(converter.parseFrom(segment.getSource()));
			}
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * @throws Exception
	 * @see com.hirain.phm.synapsis.result.ecn.service.ResultSettingService#genearteSettingFile(int)
	 */
	@Override
	public void genearteSettingFile(int settingId) throws Exception {
		CommonSegmentSetting segmentSetting = headerService.selectBy(settingId);
		List<AlgorithmIndexSetting> indexSettings = indexService.selectBy(settingId);
		if (segmentSetting == null && indexSettings == null) {
			return;
		}
		XmlResultRoot root = new XmlResultRoot();
		root.setHeaderSetting(segmentSetting);
		root.setIndexSettings(indexSettings);
		fileGenerator.generate(rootDirectory + File.separator + "resultsetting", root);
	}

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.ResultSettingService#validate(int, com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	public ValidateResult validate(int settingId, Setting setting) {
		// TODO 校验结果上传配置
		return null;
	}

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.ResultSettingService#delete(int)
	 */
	@Override
	public void delete(int settingId) {
		headerService.delete(settingId);
		indexService.delete(settingId);
	}

}
