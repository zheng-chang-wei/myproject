/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.algorithm.domain.AlgorithmStatus;
import com.hirain.phm.synapsis.algorithm.factory.AlgoControlFactory;
import com.hirain.phm.synapsis.algorithm.message.AlgoRawDataMessage;
import com.hirain.phm.synapsis.algorithm.message.AlgoResultResponseMessage;
import com.hirain.phm.synapsis.algorithm.message.AlgoUdpPortResponseMessage;
import com.hirain.phm.synapsis.algorithm.setting.SettingService;
import com.hirain.phm.synapsis.algorithm.util.BeanConstants;
import com.hirain.phm.synapsis.algorithm.util.PHMProgram;
import com.hirain.phm.synapsis.algorithm.util.SidConstant;
import com.hirain.phm.synapsis.communication.impl.CommunicationImpl;
import com.hirain.phm.synapsis.communication.impl.TransportMessage;
import com.hirain.phm.synapsis.setting.ADVariable;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.ECNVariable;
import com.hirain.phm.synapsis.setting.MVBVariable;
import com.hirain.phm.synapsis.setting.PHMAlgorithmSetting;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.Variable.VariableType;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 18, 2019 11:46:40 AM
 * @Description
 *              <p>
 *              壳子和算法交互的service
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class AlgoServiceImpl implements AlgoService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SettingService settingService;

	@Autowired
	private Map<String, AlgoControlFactory> facotries;

	@Autowired
	public CommunicationImpl communicationImpl;

	@Value("${algorithmsetting.file.path}")
	private String filePath;

	@Value("${boardsetting.file.path}")
	private String boardSettingFilePath;

	/**
	 * 存放算法文件的根目录
	 */
	@Value("${algorithm.file.root}")
	private String algoFileRoot;

	@Autowired
	@Qualifier(BeanConstants.ALGOFILE_NAMES)
	private List<String> algoFileNames;

	@Autowired
	@Qualifier(BeanConstants.ALGOCODE_UDPPORT_MAP)
	private Map<Integer, Integer> algoCode2UdpPortMap;

	@Autowired
	@Qualifier(BeanConstants.ALGOCODE_ALOGPID_MAP)
	private Map<Integer, Integer> algoCode2AlgoPidMap;

	@Autowired
	@Qualifier(BeanConstants.ALGOCODE_RTSPADDR_MAP)
	private Map<Integer, String> algoCode2RtspAddrMap;

	@Autowired
	@Qualifier(BeanConstants.ALGONAME_ALGOCODE_MAP)
	private Map<String, Integer> algoNameCodeMap;

	@Autowired
	@Qualifier(BeanConstants.MULTICAST_IPPORT_MAP)
	private Map<String, Integer> multicastIpPortMap;

	@Autowired
	@Qualifier(BeanConstants.MULTICAST_IPTYPE_MAP)
	private Map<String, VariableType> multicastIpVariableTypeMap;

	@Autowired
	@Qualifier(BeanConstants.MULTICAST_IPALOGCODE_MAP)
	private Map<String, Integer> multicastIpAlgoCodeMap;

	@Autowired
	@Qualifier(BeanConstants.ALGOCODE_ALGOSETTING_MAP)
	private Map<Integer, AlgorithmSetting> algoCode2SettingMap;

	@Autowired
	@Qualifier(BeanConstants.ALGOCODE_PREALGORESULT)
	private Map<Integer, AtomicBoolean> algoCode2PreAlgoResultMap;

	protected Queue<Integer> algoCodeQueue = new ConcurrentLinkedQueue<>();

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoService#launch()
	 */
	@Override
	public int launch() {
		try {
			for (String fileName : algoFileNames) {
				AlgoControlFactory factory = facotries.get(getExtension(fileName));
				String filePath = algoFileRoot + File.separator + fileName;
				algoCodeQueue.add(algoNameCodeMap.get(fileName));
				factory.launch(filePath);
			}
		} catch (Exception e) {
			log.error("AlgoServiceImpl.launch()--------->{}", e.getMessage(), e);
			return 1;// 算法启动异常
		}
		return 0;
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoService#shut()
	 */
	@Override
	public int shut() {
		try {
			for (String fileName : algoFileNames) {
				AlgoControlFactory factory = facotries.get(getExtension(fileName));
				Integer algoPid = algoCode2AlgoPidMap.get(algoNameCodeMap.get(fileName));
				if (algoPid != null) {
					factory.shut(algoPid);
				}
			}
		} catch (Exception e) {
			log.error("AlgoServiceImpl.shut()--------->{}", e.getMessage(), e);
			return 1;
		}
		return 0;
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoService#statusInquire()
	 */
	@Override
	public Map<Integer, AlgorithmStatus> statusInquire() {
		Map<Integer, AlgorithmStatus> map = new HashMap<>();
		for (String fileName : algoFileNames) {
			AlgoControlFactory factory = facotries.get(getExtension(fileName));
			Integer algoCode = algoNameCodeMap.get(fileName);
			Integer algoPid = algoCode2AlgoPidMap.get(algoCode);
			try {
				// 视频算法启动较慢，启动后立即查询算法状态，视频算法还未反馈algoPid，此时认为该算法为STOP状态
				AlgorithmStatus status = algoPid != null ? factory.statusInquire(algoPid) : AlgorithmStatus.STOP;
				map.put(algoCode, status);
			} catch (Exception e) {
				map.put(algoCode, AlgorithmStatus.ERROR);// 查询操作失败了话，则将此算法的状态置为ERROR
			}
		}
		return map;
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoService#sendAlgoRawData(AlgoRawDataMessage)
	 */
	@Override
	public void sendAlgoRawData(AlgoRawDataMessage rawMessage) {
		TransportMessage<AlgoRawDataMessage> transportMessage = new TransportMessage<>();
		transportMessage.setSid(SidConstant.PHM_ALGORAWDATA_COMMAND);
		transportMessage.setSource(PHMProgram.PHM_SERVICE.getCode());
		transportMessage.setTarget(PHMProgram.PHM_ALGO.getCode());
		transportMessage.setData(rawMessage);
		communicationImpl.sendAsync(transportMessage);
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoService#sendUdpPortResponse(int)
	 */
	@Override
	public void sendUdpPortResponse(int udpPort) {
		AlgoUdpPortResponseMessage message = new AlgoUdpPortResponseMessage();
		message.setResult(0);
		message.setUdpPort(udpPort);
		TransportMessage<AlgoUdpPortResponseMessage> transportMessage = new TransportMessage<>();
		transportMessage.setSid(SidConstant.PHM_UDPPORTPID_COMMAND);
		transportMessage.setSource(PHMProgram.PHM_SERVICE.getCode());
		transportMessage.setTarget(PHMProgram.PHM_ALGO.getCode());
		transportMessage.setData(message);
		communicationImpl.sendAsync(transportMessage);
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoService#sendAlgoResultResponse(int)
	 */
	@Override
	public void sendAlgoResultResponse(int udpPort) {
		AlgoResultResponseMessage message = new AlgoResultResponseMessage();
		message.setSuccess(true);
		message.setUdpPort(udpPort);
		TransportMessage<AlgoResultResponseMessage> transportMessage = new TransportMessage<>();
		transportMessage.setSid(SidConstant.PHM_ALGORESULT_COMMAND);
		transportMessage.setSource(PHMProgram.PHM_SERVICE.getCode());
		transportMessage.setTarget(PHMProgram.PHM_ALGO.getCode());
		transportMessage.setData(message);
		communicationImpl.sendAsync(transportMessage);
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoService#init()
	 */
	@Override
	public void init() throws Exception {
		algoFileNames.clear();
		File algorithmSettingFile = new File(filePath);
		settingService.parseBoardSetting(new File(boardSettingFilePath));
		PHMAlgorithmSetting phmAlgorithmSetting = settingService.parseAlgorithmSetting(algorithmSettingFile);
		settingService.storageVariableSetting();
		List<AlgorithmSetting> algorithmSettings = phmAlgorithmSetting.getAlgorithmSettings();
		for (AlgorithmSetting algorithmSetting : algorithmSettings) {
			if (algorithmSetting.getEnable()) {
				String algoName = algorithmSetting.getFilename();
				Integer algoCode = algorithmSetting.getCode();
				algoNameCodeMap.put(algoName, algoCode);
				algoFileNames.add(algoName);
				algoCode2SettingMap.put(algoCode, algorithmSetting);
				algoCode2PreAlgoResultMap.put(algoCode, new AtomicBoolean(false));
				if (StringUtils.isNotEmpty(algorithmSetting.getVideoUrl())) {
					algoCode2RtspAddrMap.put(algoCode, algorithmSetting.getVideoUrl());
				}

				for (VariableGroup variableGroup : algorithmSetting.getVariableGroups()) {
					String multicastIp = variableGroup.getMulticastIp();
					multicastIpPortMap.put(multicastIp, variableGroup.getMulticastPort());
					multicastIpAlgoCodeMap.put(multicastIp, algoCode);

					Variable variable = variableGroup.getVariables().get(0);
					if (variable instanceof MVBVariable) {
						multicastIpVariableTypeMap.put(multicastIp, VariableType.MVB);
					} else if (variable instanceof ECNVariable) {
						multicastIpVariableTypeMap.put(multicastIp, VariableType.ECN);
					} else if (variable instanceof ADVariable) {
						multicastIpVariableTypeMap.put(multicastIp, VariableType.AD);
					}
				}
			}
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoService#getAlgoCodeQueue()
	 */
	@Override
	public Queue<Integer> getAlgoCodeQueue() {
		return algoCodeQueue;
	}

	private String getExtension(String filename) {
		int lastIndexOf = filename.lastIndexOf(".");
		String extension = filename.substring(lastIndexOf);
		return extension;
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoService#sendAlgoCode(int)
	 */
	// @Override
	// public AlgoCodeResponseMessage sendAlgoCode(int algoCode) throws Exception {
	// AlgoCodeMessage algoCodeMessage = new AlgoCodeMessage();
	// algoCodeMessage.setAlgoCode(algoCode);
	// algoCodeMessage.setUdpPort(algoCode2UdpPortMap.get(algoCode));
	// TransportMessage<AlgoCodeMessage> transportMessage = new TransportMessage<>();
	// transportMessage.setSid(SidConstant.PHM_ALGOCODE_COMMAND);
	// transportMessage.setSource(PHMProgram.PHM_SERVICE.getCode());
	// transportMessage.setTarget(PHMProgram.PHM_ALGO.getCode());
	// transportMessage.setData(algoCodeMessage);
	// Message<?> response = null;
	// response = communicationImpl.send(transportMessage, 3);
	// if (response == null) {
	// throw new SynapsisException("超时：没有收到算法Code报文回复");
	// }
	// AlgoCodeResponseMessage message = (AlgoCodeResponseMessage) response.getData();
	// return message;
	// }

}
