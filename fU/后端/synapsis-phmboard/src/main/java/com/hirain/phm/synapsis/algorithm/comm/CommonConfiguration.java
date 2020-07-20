/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.comm;

import java.io.File;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hirain.phm.synapsis.algorithm.domain.VideoFrame;
import com.hirain.phm.synapsis.algorithm.storage.ADMessageFileCache;
import com.hirain.phm.synapsis.algorithm.util.BeanConstants;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.PHMAlgorithmSetting;
import com.hirain.phm.synapsis.setting.Variable.VariableType;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 21, 2019 2:56:11 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 21, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Configuration
public class CommonConfiguration {

	/**
	 * 每个算法名称和其对应的算法Code之间的映射关系
	 * key: algoName
	 * value:algoCode
	 */
	@Bean(BeanConstants.ALGONAME_ALGOCODE_MAP)
	public Map<String, Integer> algoNameCodeMap() {
		return new HashMap<>();
	}

	/**
	 * 算法Code和其对应通信端口的映射关系
	 * key: algoCode
	 * value: updPort
	 */
	@Bean(BeanConstants.ALGOCODE_UDPPORT_MAP)
	public Map<Integer, Integer> algoCode2AlgoCodeMap() {
		return new HashMap<>();
	}

	/**
	 * 算法Code和其对应算法进程PID号的映射关系
	 * key: algoCode
	 * value: algoPid
	 */
	@Bean(BeanConstants.ALGOCODE_ALOGPID_MAP)
	public Map<Integer, Integer> algoCode2AlgoPidMap() {
		return new HashMap<>();
	}

	/**
	 * 算法Code和其对应视频源IP地址的映射关系
	 * key: algoCode
	 * value: rtspAddr
	 */
	@Bean(BeanConstants.ALGOCODE_RTSPADDR_MAP)
	public Map<Integer, String> algoCode2RtspAddrMap() {
		return new HashMap<>();
	}

	/**
	 * 算法ID和其对udp通信端口的映射关系
	 * key: algoID
	 * value: udpPort
	 */
	@Bean(BeanConstants.ALGOID_UDPPORT_MAP)
	public Map<Integer, Integer> algoID2UdpPortMap() {
		return new HashMap<>();
	}

	/**
	 * 算法ID和算法Code的映射关系
	 * key: algoID
	 * value: algoCode
	 */
	@Bean(BeanConstants.ALGOID_ALGOCODE_MAP)
	public Map<Integer, Integer> algoID2AlgoCodeMap() {
		return new HashMap<>();
	}

	/**
	 * 算法文件名称的集合
	 */
	@Bean(BeanConstants.ALGOFILE_NAMES)
	public List<String> algoFileNameList() {
		return new ArrayList<>();
	}

	/**
	 * 组播地址和组播端口的映射关系
	 */
	@Bean(BeanConstants.MULTICAST_IPPORT_MAP)
	public Map<String, Integer> multicastIpPortMap() {
		return new HashMap<>();
	}

	/**
	 * 组播地址和算法端口的映射关系
	 */
	@Bean(BeanConstants.MULTICAST_IPALOGPORT_MAP)
	public Map<String, Integer> multicastIpAlgoPortMap() {
		return new HashMap<>();
	}

	/**
	 * 组播地址和算法Code的映射关系
	 */
	@Bean(BeanConstants.MULTICAST_IPALOGCODE_MAP)
	public Map<String, Integer> multicastIpAlgoCodeMap() {
		return new HashMap<>();
	}

	/**
	 * 组播地址和变量类型的映射关系
	 */
	@Bean(BeanConstants.MULTICAST_IPTYPE_MAP)
	public Map<String, VariableType> multicastIpVariableTypeMap() {
		return new HashMap<>();
	}

	/**
	 * 组播socket和组播地址的映射关系
	 */
	@Bean(BeanConstants.MULTICAST_SOCKET_MAP)
	public Map<MulticastSocket, InetAddress> multicastSocketMap() {
		return new HashMap<>();
	}

	/**
	 * 算法code和算法对象的映射关系
	 */
	@Bean(BeanConstants.ALGOCODE_ALGOSETTING_MAP)
	public Map<Integer, AlgorithmSetting> algoCodeAlgoSettingMap() {
		return new HashMap<>();
	}

	/**
	 * 本次实验对应的算法配置文件
	 */
	@Bean(BeanConstants.PHMALGORITHMSETTING)
	public PHMAlgorithmSetting initSetting() {
		PHMAlgorithmSetting phmAlgorithmSetting = new PHMAlgorithmSetting();
		return phmAlgorithmSetting;
	}

	/**
	 * 本次实验对应的板卡配置文件
	 */
	@Bean(BeanConstants.BOARDSETTING)
	public BoardSetting initBoardSetting() {
		BoardSetting boardSetting = new BoardSetting();
		return boardSetting;
	}

	/**
	 * AD数据缓存对象 的集合
	 */
	@Bean(BeanConstants.ADCACHES)
	public List<ADMessageFileCache> adCacheList() {
		return new ArrayList<>();
	}

	/**
	 * 存放算法原始数据以及数据头文件的父路径
	 */
	@Bean(BeanConstants.MESSAGECACHEROOTPATH)
	public String cacheMessageParentPath() {
		// final File messageCacheFolder = new File(System.getProperty("user.dir"), "cache");
		final File messageCacheFolder = new File("/home/root/Hirain/Hi_opt/app", "cache");
		if (!messageCacheFolder.exists()) {
			messageCacheFolder.mkdirs();
		}
		return messageCacheFolder.getAbsolutePath() + File.separator;
	}

	/**
	 * 存放报警视频文件的父路径
	 */
	@Bean(BeanConstants.VIDEOCACHEROOTPATH)
	public String cacheVideoParentPath() {
		// final File videoCacheFolder = new File(System.getProperty("user.dir"), "video");
		final File videoCacheFolder = new File("/home/root/Hirain/Hi_opt/app", "video");
		if (!videoCacheFolder.exists()) {
			videoCacheFolder.mkdirs();
		}
		return videoCacheFolder.getAbsolutePath() + File.separator;
	}

	/**
	 * 存放视频数据帧的缓存
	 */
	@Bean(BeanConstants.VIDEOFRAMECACHE)
	public LinkedBlockingQueue<VideoFrame> videoFrameQueue() {
		LinkedBlockingQueue<VideoFrame> queue = new LinkedBlockingQueue<>(200);// 缓存10s的视频数据，VideoFrame会控制在200个以内，所以queue大小设定为200
		return queue;
	}

	/**
	 * 算法code和算法结果映射关系表
	 */
	@Bean(BeanConstants.ALGOCODE_PREALGORESULT)
	public Map<Integer, AtomicBoolean> algoCode2PreAlgoResultMap() {
		return new HashMap<>();
	}

}
