/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.algorithm.domain.AlgorithmStatus;
import com.hirain.phm.synapsis.algorithm.domain.PHMAlgoResult;
import com.hirain.phm.synapsis.algorithm.message.AlgoControlResponseMessage;
import com.hirain.phm.synapsis.algorithm.message.AlgoResultMessage;
import com.hirain.phm.synapsis.algorithm.message.AlgoResultResponseMessage;
import com.hirain.phm.synapsis.algorithm.message.DataControlResponseMessage;
import com.hirain.phm.synapsis.algorithm.message.StatusResponseMessage;
import com.hirain.phm.synapsis.algorithm.message.VersionResponseMessage;
import com.hirain.phm.synapsis.algorithm.multicast.MulticastReceiver;
import com.hirain.phm.synapsis.algorithm.util.BeanConstants;
import com.hirain.phm.synapsis.algorithm.util.PHMProgram;
import com.hirain.phm.synapsis.algorithm.util.SidConstant;
import com.hirain.phm.synapsis.algorithm.video.VideoService;
import com.hirain.phm.synapsis.communication.Message;
import com.hirain.phm.synapsis.communication.impl.CommunicationImpl;
import com.hirain.phm.synapsis.communication.impl.TransportMessage;
import com.hirain.phm.synapsis.exception.SynapsisException;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 14, 2019 4:30:40 PM
 * @Description
 *              <p>
 *              壳子和控制管理程序交互的service
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 14, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class AlgoManagerServiceImpl implements AlgoManagerService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${synapsis.algomanage.version}")
	private String servVersion;// 算法管理程序版本号

	@Autowired
	public CommunicationImpl communicationImpl;

	@Autowired
	private AlgoService algoService;

	@Autowired
	private MulticastReceiver receiver;

	@Autowired
	private VideoService videoService;

	@Autowired
	@Qualifier(BeanConstants.ALGOID_ALGOCODE_MAP)
	private Map<Integer, Integer> algoID2AlgoCodeMap;

	@Autowired
	@Qualifier(BeanConstants.ALGOCODE_RTSPADDR_MAP)
	private Map<Integer, String> algoCode2RtspAddrMap;

	@Autowired
	@Qualifier(BeanConstants.MULTICAST_IPPORT_MAP)
	private Map<String, Integer> multicastIpPortMap;

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoManagerService#algoStatusInquire()
	 */
	@Override
	public void algoStatusInquire() throws Exception {
		Map<Integer, AlgorithmStatus> map = algoService.statusInquire();
		StatusResponseMessage message = new StatusResponseMessage();
		message.setAlgoNum(map.size());
		message.setMap(map);
		TransportMessage<StatusResponseMessage> transportMessage = new TransportMessage<>();
		transportMessage.setSid(SidConstant.PHM_ALGOSTATUS_COMMAND);
		transportMessage.setSource(PHMProgram.PHM_SERVICE.getCode());
		transportMessage.setTarget(PHMProgram.PHM_CONTROL.getCode());
		transportMessage.setData(message);
		communicationImpl.sendAsync(transportMessage);
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoManagerService#versionInquire()
	 */
	@Override
	public void versionInquire() {
		VersionResponseMessage message = new VersionResponseMessage();
		message.setVersion(servVersion);
		TransportMessage<VersionResponseMessage> transportMessage = new TransportMessage<>();
		transportMessage.setSid(SidConstant.PHM_VERSION_COMMAND);
		transportMessage.setSource(PHMProgram.PHM_SERVICE.getCode());
		transportMessage.setTarget(PHMProgram.PHM_CONTROL.getCode());
		transportMessage.setData(message);
		communicationImpl.sendAsync(transportMessage);
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoManagerService#algoStart()
	 */
	@Override
	public void algoStart() {
		int result = algoService.launch();
		AlgoControlResponseMessage message = new AlgoControlResponseMessage();
		message.setResult(result);
		TransportMessage<AlgoControlResponseMessage> transportMessage = new TransportMessage<>();
		transportMessage.setSid(SidConstant.PHM_ALGOCONTROL_COMMAND);
		transportMessage.setSource(PHMProgram.PHM_SERVICE.getCode());
		transportMessage.setTarget(PHMProgram.PHM_CONTROL.getCode());
		transportMessage.setData(message);
		communicationImpl.sendAsync(transportMessage);
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoManagerService#algoStop()
	 */
	@Override
	public void algoStop() {
		int result = algoService.shut();
		AlgoControlResponseMessage message = new AlgoControlResponseMessage();
		message.setResult(result);
		TransportMessage<AlgoControlResponseMessage> transportMessage = new TransportMessage<>();
		transportMessage.setSid(SidConstant.PHM_ALGOCONTROL_COMMAND);
		transportMessage.setSource(PHMProgram.PHM_SERVICE.getCode());
		transportMessage.setTarget(PHMProgram.PHM_CONTROL.getCode());
		transportMessage.setData(message);
		communicationImpl.sendAsync(transportMessage);
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoManagerService#dataStart()
	 */
	@Override
	public void dataStart() {
		DataControlResponseMessage message = new DataControlResponseMessage();
		try {
			algoService.init();
			if (multicastIpPortMap.size() > 0) {
				receiver.subscribe();// 启动组播监听
			}
			if (algoCode2RtspAddrMap.size() > 0) {
				videoService.startCacheFrame();// 开始接收视频数据
			}
			message.setResult(0);
		} catch (Exception e) {
			message.setResult(1);
			log.error(e.getMessage(), e);
		}
		TransportMessage<DataControlResponseMessage> transportMessage = new TransportMessage<>();
		transportMessage.setSid(SidConstant.PHM_DATACONTROL_COMMAND);
		transportMessage.setSource(PHMProgram.PHM_SERVICE.getCode());
		transportMessage.setTarget(PHMProgram.PHM_CONTROL.getCode());
		transportMessage.setData(message);
		communicationImpl.sendAsync(transportMessage);
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoManagerService#dataStop()
	 */
	@Override
	public void dataStop() {
		DataControlResponseMessage message = new DataControlResponseMessage();
		try {
			receiver.stop();// 停止组播监听
			videoService.stop();// 停止视频接收
			message.setResult(0);
		} catch (Exception e) {
			message.setResult(1);
			log.error(e.getMessage(), e);
		}
		TransportMessage<DataControlResponseMessage> transportMessage = new TransportMessage<>();
		transportMessage.setSid(SidConstant.PHM_DATACONTROL_COMMAND);
		transportMessage.setSource(PHMProgram.PHM_SERVICE.getCode());
		transportMessage.setTarget(PHMProgram.PHM_CONTROL.getCode());
		transportMessage.setData(message);
		communicationImpl.sendAsync(transportMessage);
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgoManagerService#algoResultSend(com.hirain.phm.synapsis.algorithm.domain.PHMAlgoResult)
	 */
	@Override
	public AlgoResultResponseMessage algoResultSend(PHMAlgoResult result) throws Exception {
		AlgoResultMessage resultMessage = new AlgoResultMessage();
		Integer algoCode = algoID2AlgoCodeMap.get(result.getAlgoId());
		resultMessage.setAlgoCode(algoCode);
		resultMessage.setFileType(algoCode2RtspAddrMap.get(algoCode) == null ? (byte) 0 : (byte) 1);// 0代表总线结果，1代表视频结果
		resultMessage.setPhmAlgoResult(result);
		TransportMessage<AlgoResultMessage> transportMessage = new TransportMessage<>();
		transportMessage.setSid(SidConstant.PHM_ALGORESULT_COMMAND);
		transportMessage.setSource(PHMProgram.PHM_SERVICE.getCode());
		transportMessage.setTarget(PHMProgram.PHM_CONTROL.getCode());
		transportMessage.setData(resultMessage);

		Message<?> response = getResponse(transportMessage);
		AlgoResultResponseMessage message = (AlgoResultResponseMessage) response.getData();
		return message;
	}

	private Message<?> getResponse(TransportMessage<?> transportMessage) throws Exception {
		Message<?> response = communicationImpl.send(transportMessage, 2);
		if (response == null) {
			throw new SynapsisException("No reply received after timeout");//
		}
		return response;
	}

}
