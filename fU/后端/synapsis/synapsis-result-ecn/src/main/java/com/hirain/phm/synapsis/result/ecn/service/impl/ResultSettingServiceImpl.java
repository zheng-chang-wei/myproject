/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.ecn.ECNVariableQuery;
import com.hirain.phm.synapsis.ecn.impl.ECNGroupConverter;
import com.hirain.phm.synapsis.ecn.impl.ECNProcotolStream;
import com.hirain.phm.synapsis.ecn.model.Device;
import com.hirain.phm.synapsis.file.FileGenerator;
import com.hirain.phm.synapsis.protocol.ParseResult;
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
import com.hirain.phm.synapsis.result.service.ResultSegmentService;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.ECNVariable;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.Variable.VariableType;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.db.AlgorithmSettingQuery;
import com.hirain.phm.synapsis.setting.db.SettingDbService;
import com.hirain.phm.synapsis.setting.support.SettingUpdateSupporter;
import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;
import com.hirain.phm.synapsis.util.SettingFileConfig;

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

	@Autowired
	private SettingFileConfig config;

	@Autowired
	private CommonHeaderService headerService;

	@Autowired
	private AlgorithmIndexSettingService indexService;

	@Autowired
	private AlgorithmSettingQuery algorithmSettingQuery;

	@Autowired
	private SettingDbService dbService;

	@Autowired
	private FileGenerator fileGenerator;

	@Autowired
	private ECNGroupConverter converter = new ECNGroupConverter();

	@Autowired
	private ECNVariableQuery query;

	@Autowired
	private SettingUpdateSupporter supporter;

	@Autowired
	private ECNProcotolStream protocolStream;

	@Autowired
	private ResultSegmentService resultSegmentService;

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.ResultSettingService#save(Setting,
	 *      com.hirain.phm.synapsis.result.ecn.vo.AlgorithmOutputVO)
	 */
	@Override
	public void save(Integer settingId, AlgorithmOutputVO output) {
		Setting setting = dbService.selectWithDetail(settingId);
		BoardSetting board = getEcnBoard(setting);
		if (board != null) {
			Device device = getDevice(setting);
			CommonSegmentSetting commonSegment = generateCommonSegmentSetting(setting.getId(), board.getSlotId(), output);
			String endian = query.getEndian(device);
			commonSegment.setEndian(endian);
			headerService.save(commonSegment);
			List<AlgorithmIndexSetting> algorithmIndexSetting = genearteAlgorithmIndexSetting(setting, output, device);
			indexService.delete(settingId);
			if (algorithmIndexSetting.size() > 0) {
				indexService.save(algorithmIndexSetting);
			}
		}

	}

	/**
	 * @param setting
	 * @throws Exception
	 */
	public Device getDevice(Setting setting) {
		String filepath = supporter.getECNProtocolFilepath(setting);

		try {
			ParseResult result = protocolStream
					.read(config.getResource() + File.separator + setting.getName() + File.separator + "boards" + File.separator + filepath);
			return (Device) result.getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param setting
	 * @param output
	 * @param device
	 * @return
	 */
	private List<AlgorithmIndexSetting> genearteAlgorithmIndexSetting(Setting setting, AlgorithmOutputVO output, Device device) {
		Map<Long, String> map = query.getComIdCycle(device);
		return generateAlgorithmIndexSetting(setting.getId(), setting.getAlgorithmSettings(), map, output);
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
		group.setType(VariableType.ECN.name());
		group.setSlotId(slotId);
		List<ECNVariable> variables = new ArrayList<>();
		for (CommonSegment source : segments) {
			if (source.getType().equals("bus")) {
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
			segment.setType(vo.getType() == CommonSegment.SOURCE_BUS ? "bus" : "local");
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
			indexSetting.setCycleTime(cycles.get(vo.getComId()));
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
	private boolean isSpace(String name) {
		return name != null && !name.isEmpty();
	}

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.ResultSettingService#getResultSetting(java.lang.Integer)
	 */
	@Override
	public AlgorithmOutputVO getResultSetting(Integer settingId) {
		CommonSegmentSetting commonSegmentSetting = headerService.selectBy(settingId);
		AlgorithmOutputVO vo = new AlgorithmOutputVO();
		if (commonSegmentSetting != null) {
			List<AlgorithmIndexSetting> indexSetting = indexService.selectBy(settingId);
			vo.setCommonLength(commonSegmentSetting.getLength());
			vo.setDataLength(commonSegmentSetting.getDataLength());
			vo.setCommonSegments(getCommonVO(commonSegmentSetting.getSegments()));
			vo.setAlgorithms(getIndexVO(indexSetting));
		}
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
					i++;
				}
				AlgorithmSetting algorithmSetting = algorithmSettingQuery.getAlgorithmSetting(indexSetting.getSettingId(), index.getCode());
				if (algorithmSetting != null) {
					names.add(algorithmSetting.getName());
				}
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
			vo.setType(segment.getType().equals("bus") ? CommonSegment.SOURCE_BUS : CommonSegment.SOURCE_SYSTEM);
			if (vo.getType() == CommonSegment.SOURCE_BUS) {
				vo.setSource(converter.parseFrom(segment.getSource()));
			}
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * @throws Exception
	 * @see com.hirain.phm.synapsis.result.ecn.service.ResultSettingService#genearteSettingFile(int, List)
	 */
	@Override
	public void genearteSettingFile(int settingId, List<VariableGroup> groups) throws Exception {
		CommonSegmentSetting segmentSetting = headerService.selectBy(settingId);
		List<AlgorithmIndexSetting> indexSettings = indexService.selectBy(settingId);
		XmlResultRoot root = new XmlResultRoot();
		if (segmentSetting == null && (indexSettings == null || indexSettings.isEmpty())) {
			root.setEnable(false);
			CommonSegmentSetting segmentSetting1 = new CommonSegmentSetting();
			int length = resultSegmentService.getHeaderSegments().getLength();
			segmentSetting1.setLength(length);
			root.setHeaderSetting(segmentSetting1);
		} else {
			Stream<VariableGroup> filter = groups.stream().filter(g -> g.getId().equals(segmentSetting.getGroupId()));
			VariableGroup group = filter.findFirst().get();
			segmentSetting.setSubscribeGroup(group);

			root.setEnable(true);
			root.setHeaderSetting(segmentSetting);
			root.setIndexSettings(indexSettings);
		}
		fileGenerator.generate(config.getRoot() + File.separator + "resultsetting", root);
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

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.ResultSettingService#getVariableGroups(int)
	 */
	@Override
	public List<VariableGroup> getVariableGroups(int settingId) {
		CommonSegmentSetting segmentSetting = headerService.selectBy(settingId);
		if (segmentSetting == null || segmentSetting.getSubscribeGroup() == null) {
			return new ArrayList<>();
		}
		return Arrays.asList(segmentSetting.getSubscribeGroup());
	}

}
