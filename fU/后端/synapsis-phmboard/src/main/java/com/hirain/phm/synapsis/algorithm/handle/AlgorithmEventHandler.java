/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.handle;

import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.synapsis.algorithm.domain.PHMAlgoResult;
import com.hirain.phm.synapsis.algorithm.message.AlgoControlMessage;
import com.hirain.phm.synapsis.algorithm.message.AlgoResultMessage;
import com.hirain.phm.synapsis.algorithm.message.AlgoUdpPortMessage;
import com.hirain.phm.synapsis.algorithm.message.DataControlMessage;
import com.hirain.phm.synapsis.algorithm.message.StatusInquireMessage;
import com.hirain.phm.synapsis.algorithm.message.VersionInquireMessage;
import com.hirain.phm.synapsis.algorithm.service.AlgoManagerService;
import com.hirain.phm.synapsis.algorithm.service.AlgoService;
import com.hirain.phm.synapsis.algorithm.storage.CacheService;
import com.hirain.phm.synapsis.algorithm.storage.StorageService;
import com.hirain.phm.synapsis.algorithm.util.BeanConstants;
import com.hirain.phm.synapsis.algorithm.video.VideoService;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 18, 2019 11:15:30 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Configuration
public class AlgorithmEventHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${synapsis.video.cache.time.range}")
	private int CACHE_TIME_RANGE;

	@Autowired
	private AlgoManagerService service;

	@Autowired
	private AlgoService algoService;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private VideoService videoService;

	@Autowired // TODO 有了 @Resource，还需要@Autowired 吗？
	@Resource(name = BeanConstants.STORAGE + "MVB")
	private StorageService mvbStorageService;

	@Autowired
	@Resource(name = BeanConstants.STORAGE + "ECN")
	private StorageService ecnStorageService;

	@Autowired
	@Resource(name = BeanConstants.STORAGE + "AD")
	private StorageService adStorageService;

	@Autowired
	@Qualifier(BeanConstants.ALGOCODE_UDPPORT_MAP)
	private Map<Integer, Integer> algoCode2UdpPortMap;

	@Autowired
	@Qualifier(BeanConstants.ALGOCODE_ALOGPID_MAP)
	private Map<Integer, Integer> algoCode2AlgoPidMap;

	@Autowired
	@Qualifier(BeanConstants.ALGOID_UDPPORT_MAP)
	private Map<Integer, Integer> algoID2UdpPortMap;

	@Autowired
	@Qualifier(BeanConstants.ALGOID_ALGOCODE_MAP)
	private Map<Integer, Integer> algoID2AlgoCodeMap;

	@Autowired
	@Qualifier(BeanConstants.ALGOCODE_ALGOSETTING_MAP)
	private Map<Integer, AlgorithmSetting> algoCode2SettingMap;

	@Autowired
	@Qualifier(BeanConstants.MULTICAST_IPALOGCODE_MAP)
	private Map<String, Integer> multicastIpAlgoCodeMap;

	@Autowired
	@Qualifier(BeanConstants.MULTICAST_IPALOGPORT_MAP)
	private Map<String, Integer> multicastIpAlgoPortMap;

	@Autowired
	@Qualifier(BeanConstants.ALGOCODE_RTSPADDR_MAP)
	private Map<Integer, String> algoCode2RtspAddrMap;

	@Autowired
	@Qualifier(BeanConstants.MULTICAST_IPPORT_MAP)
	private Map<String, Integer> multicastIpPortMap;

	@Autowired
	@Qualifier(BeanConstants.MESSAGECACHEROOTPATH)
	private String cacheParentPath;

	@Autowired
	@Qualifier(BeanConstants.VIDEOCACHEROOTPATH)
	private String videoParentPath;

	@Autowired
	@Qualifier(BeanConstants.ALGOCODE_PREALGORESULT)
	private Map<Integer, AtomicBoolean> algoCode2PreAlgoResultMap;

	@Value("${synapsis.setting.folder}")
	private String settingParentPath;

	@Value("${synapsis.algorithm.folder}")
	private String algoParentPath;

	private Map<Integer, Boolean> algoCode2NowAlgoResultMap = new HashMap<>();;

	/**
	 * 收到算法发来的通信端口和算法PID信息的报文
	 */
	@EventListener
	@Async
	public void handleAlgoUdpPortPid(AlgoUdpPortMessage message) {
		int udpPort = message.getUdpPort();
		int algoPid = message.getAlgoPid();
		int algoID = message.getAlgoID();
		algoID2UdpPortMap.put(algoID, udpPort);
		algoService.sendUdpPortResponse(udpPort);
		// log.info("udpPort:" + udpPort + " ,algoPid:" + algoPid + " ,algoID:" + algoID);
		Integer algoCodeNow = algoService.getAlgoCodeQueue().poll();
		if (algoCodeNow == null) {
			return;
		}
		for (String multicastIP : multicastIpAlgoCodeMap.keySet()) {
			if (multicastIpAlgoCodeMap.get(multicastIP) == algoCodeNow) {
				multicastIpAlgoPortMap.put(multicastIP, udpPort);
			}
		}
		algoCode2UdpPortMap.put(algoCodeNow, udpPort);
		algoCode2AlgoPidMap.put(algoCodeNow, algoPid);
		algoID2AlgoCodeMap.put(algoID, algoCodeNow);
	}

	/**
	 * 收到算法发来的算法结果报文
	 */
	@EventListener
	@Async
	public void handleAlgoResult(AlgoResultMessage message) {
		PHMAlgoResult phmAlgoResult = message.getPhmAlgoResult();
		int algoID = phmAlgoResult.getAlgoId();
		Integer udpPort = algoID2UdpPortMap.get(algoID);
		Integer algoCode = algoID2AlgoCodeMap.get(algoID);
		if (udpPort != null) {
			algoService.sendAlgoResultResponse(udpPort);// 算法壳-->算法 确认收到算法结果
		}
		algoCode2NowAlgoResultMap.put(algoCode, phmAlgoResult.isSaveData());
		// log.info(phmAlgoResult.getDateTime() + " ,algoCode:" + algoCode + " ,itemName:" + phmAlgoResult.getItemName() + " ,preResult:"
		// + algoCode2PreAlgoResultMap.get(algoCode).get() + " ,nowResult:" + algoCode2NowAlgoResultMap.get(algoCode));
		if (algoCode != null && !algoCode2PreAlgoResultMap.get(algoCode).get() && algoCode2NowAlgoResultMap.get(algoCode)) {
			AlgorithmSetting setting = algoCode2SettingMap.get(algoCode);
			String timestamp = phmAlgoResult.getDateTime();
			long warningTime = converTimeStamp(timestamp);
			for (VariableGroup variableGroup : setting.getVariableGroups()) {
				switch (variableGroup.getType()) {
				case "ECN":
					String folderName_ecn = setting.getCode() + "_" + timestamp.replaceAll("_", "");
					String fileName_ecn = "ECN_" + variableGroup.getSlotId() + "_" + timestamp + ".dat";
					ecnStorageService.init(folderName_ecn, fileName_ecn);
					ecnStorageService.storage(warningTime);
					break;

				case "MVB":
					String folderName_mvb = setting.getCode() + "_" + timestamp.replaceAll("_", "");
					String fileName_mvb = "MVB_" + variableGroup.getSlotId() + "_" + timestamp + ".dat";
					mvbStorageService.init(folderName_mvb, fileName_mvb);
					mvbStorageService.storage(warningTime);
					break;

				// case "AD":
				// adStorageService.init(variableGroup.getSlotId(), timestamp);
				// adStorageService.storage();
				// break;
				}
			}
			try {
				if (StringUtils.isNotEmpty(setting.getVideoUrl())) {
					String folderName = setting.getCode() + "_" + timestamp.replaceAll("_", "");
					String videoName = timestamp + ".mp4";
					videoService.storageWarningVideo(folderName, videoName, warningTime);
				}
				if (algoCode2PreAlgoResultMap.get(algoCode).get() != algoCode2NowAlgoResultMap.get(algoCode)) {// 算法结果状态跳变，才向控制管理程序发送结果
					service.algoResultSend(message.getPhmAlgoResult());// 算法壳-->PHM控制管理程序，发送算法结果
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		algoCode2PreAlgoResultMap.get(algoCode).set(algoCode2NowAlgoResultMap.get(algoCode));
	}

	/**
	 * 收到控制管理程序发来的数据接收、停止指令
	 */
	@EventListener
	@Async
	public void handleDataControl(DataControlMessage message) {
		try {
			if (message.isStart() && !cacheService.isStart()) {
				forceDeleteCache();
				forceDeleteVideo();
				/****** 开始接收数据 ***********/
				service.dataStart();
				/****** 开始数据缓存10分钟的数据校验 *******/
				cacheService.startCheck();
				/****** 开始对缓存的视频帧时戳进行校验 *******/
				videoService.startCheck(CACHE_TIME_RANGE);
			} else {
				service.dataStop();
				/****** 停止数据缓存10分钟的数据校验 *******/
				cacheService.stop();
				/****** 停止故障数据存储 *******/
				ecnStorageService.shutdown();
				clearMap();
			}
		} catch (Exception e) {
			cacheService.stop();// 停止数据缓存10分钟的数据校验
			clearMap();
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 收到控制管理程序发来的算法启动、停止指令
	 */
	@EventListener
	@Async
	public void handleAlgoControl(AlgoControlMessage message) {
		try {
			if (message.isStart()) {
				service.algoStart();
			} else {
				service.algoStop();
				forceDeleteSettingFile();
				forceDeleteAlgoFile();
			}
		} catch (Exception e) {
			log.error("AlgorithmEventHandler.handleAlgoControl()---------->{}", e.getMessage());
		}
	}

	/**
	 * 收到控制管理程序发来的版本查询指令
	 */
	@EventListener
	@Async
	public void handleVersionInquire(VersionInquireMessage message) {
		service.versionInquire();
	}

	/**
	 * 收到控制管理程序发来的算法状态查询指令
	 */
	@EventListener
	@Async
	public void handleAlgoStatus(StatusInquireMessage message) {
		try {
			service.algoStatusInquire();
		} catch (Exception e) {
			log.error("AlgorithmEventHandler.handleAlgoStatus()---------->{}", e.getMessage());
		}
	}

	/**
	 * 将cache文件夹下的内容都删除
	 */
	private void forceDeleteCache() throws Exception {
		final File cacheParentFolder = new File(cacheParentPath);
		if (cacheParentFolder.exists()) {
			for (File file : cacheParentFolder.listFiles()) {
				FileUtils.forceDelete(file);
			}
		}
	}

	/**
	 * 将video文件夹下的内容都删除
	 */
	private void forceDeleteVideo() throws Exception {
		final File videoParentFolder = new File(videoParentPath);
		if (videoParentFolder.exists()) {
			for (File file : videoParentFolder.listFiles()) {
				FileUtils.forceDelete(file);
			}
		}
	}

	/**
	 * 将算法文件夹下的算法文件删除
	 */
	private void forceDeleteAlgoFile() throws Exception {
		File algoFolder = new File(algoParentPath);
		if (algoFolder.exists()) {
			for (File file : algoFolder.listFiles((FileFilter) (f) -> f.isFile())) {
				FileUtils.forceDelete(file);
			}
		}
	}

	/**
	 * 将配置文件夹下的配置文件删除
	 */
	private void forceDeleteSettingFile() throws Exception {
		File settingFolder = new File(settingParentPath);
		if (settingFolder.exists()) {
			for (File file : settingFolder.listFiles()) {
				FileUtils.forceDelete(file);
			}
		}

	}

	private long converTimeStamp(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
		}
		return date.getTime() / 1000;
	}

	private void clearMap() {
		algoCode2RtspAddrMap.clear();
		multicastIpPortMap.clear();
		algoCode2UdpPortMap.clear();
		algoCode2AlgoPidMap.clear();
		algoID2AlgoCodeMap.clear();
		multicastIpAlgoPortMap.clear();
		algoID2UdpPortMap.clear();
		algoCode2SettingMap.clear();
		multicastIpAlgoCodeMap.clear();
		algoCode2PreAlgoResultMap.clear();
		algoCode2NowAlgoResultMap.clear();
	}

}
