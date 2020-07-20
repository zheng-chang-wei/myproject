/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.storage;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.algorithm.domain.ADStorageData;
import com.hirain.phm.synapsis.algorithm.domain.StorageData;
import com.hirain.phm.synapsis.algorithm.util.BeanConstants;
import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.setting.ADVariable;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.PHMAlgorithmSetting;
import com.hirain.phm.synapsis.setting.Variable.VariableType;
import com.hirain.phm.synapsis.setting.VariableGroup;

import lombok.Setter;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Feb 11, 2020 2:20:23 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 11, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class CacheServiceImpl implements CacheService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private MVBMessageFileCache mvbCache;

	private ECNMessageFileCache ecnCache;

	@Autowired
	@Qualifier(BeanConstants.PHMALGORITHMSETTING)
	private PHMAlgorithmSetting phmAlgorithmSetting;

	@Autowired
	@Qualifier(BeanConstants.ADCACHES)
	private List<ADMessageFileCache> adCaches;

	private Map<Integer, ADMessageFileCache> channelCacheMap = new HashMap<>();

	private final ExecutorService executor = Executors.newFixedThreadPool(3, r -> new Thread(r, CacheServiceImpl.class.getName() + "-read"));

	private final static int POLL_PERIOD = 500;

	private AtomicBoolean startCache = new AtomicBoolean(false);

	@Setter
	private long currentPosition;

	/**
	 * @see com.hirain.phm.synapsis.algorithm.storage.CacheService#cache(StorageData)
	 */
	@Override
	public void cache(StorageData storageData) throws Exception {
		byte[] bytes = storageData.toBytes();
		switch (storageData.getType()) {
		case MVB:
			mvbCache.write(bytes);
			break;
		case ECN:
			ecnCache.write(bytes);
			break;
		case AD:
			int chnID = ((ADStorageData) storageData).getChnID();
			channelCacheMap.get(chnID).write(bytes);
			break;
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.storage.CacheService#startCheck()
	 */
	@Override
	public void startCheck() throws Exception {
		startCache.set(true);
		{
			ecnCache = new ECNMessageFileCache(false, 0);
			ecnCache.init();
			// mvbCache = new MVBMessageFileCache(false, 0);
			// mvbCache.init();
			// adCachesInit();
		}

		executor.submit(() -> {
			while (startCache.get()) {
				byte[] payload = ecnCache.read();
				FileChannel inChannel = ecnCache.getInChannel();
				try {
					if (payload != null) {
						final ByteBuffer buffer = ByteBuffer.wrap(payload).order(MessageConstant.MESSAGE_ORDER);
						buffer.getInt();// frameIndex
						int unix_time = buffer.getInt();
						buffer.getInt();// us_time
						int dataLen = Short.toUnsignedInt(buffer.getShort());

						if (!isWithinValidity(unix_time)) {// 和当前时间板卡系统时间进行对比，若超出1分钟，则移动读取指针
							ecnCache.getCurrentPosition().addAndGet(14 + dataLen);
						} else if (inChannel != null) {
							inChannel.position(ecnCache.getCurrentPosition().get());
						}
						setCurrentPosition(ecnCache.getCurrentPosition().get());
					} else {
						TimeUnit.MILLISECONDS.sleep(POLL_PERIOD);
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		});

		// executor.submit(() -> {
		// while (startCache.get()) {
		// byte[] payload = mvbCache.read();
		// FileChannel inChannel = mvbCache.getInChannel();
		// try {
		// if (payload != null) {
		// final ByteBuffer buffer = ByteBuffer.wrap(payload).order(MessageConstant.MESSAGE_ORDER);
		// buffer.getInt();// frameIndex
		// int unix_time = buffer.getInt();
		// buffer.getInt();// us_time
		// int dataLen = Short.toUnsignedInt(buffer.getShort());
		// if (!isWithinValidity(unix_time)) {// 和当前时间板卡系统时间进行对比，若超出1分钟，则移动读取指针
		// mvbCache.getCurrentPosition().addAndGet(14 + dataLen);
		// } else if (inChannel != null) {
		// inChannel.position(mvbCache.getCurrentPosition().get());
		// }
		// setCurrentPosition(mvbCache.getCurrentPosition().get());
		// } else {
		// TimeUnit.MILLISECONDS.sleep(POLL_PERIOD);
		// }
		// } catch (Exception e) {
		// log.error(e.getMessage(), e);
		// }
		// }
		// });

		// executor.submit(() -> {
		// while (startCache.get()) {
		// for (ADMessageFileCache adCache : adCaches) {
		// byte[] payload = adCache.read();
		// if (payload != null) {
		// // TODO 解析这段报文的时间戳，和当前时间板卡系统时间进行对比，若超出1分钟，则移动读取指针
		// } else {
		// try {
		// TimeUnit.MILLISECONDS.sleep(POLL_PERIOD);
		// } catch (InterruptedException e) {
		// log.error(e.getMessage(), e);
		// }
		// }
		// }
		// }
		// });
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.storage.CacheService#stop()
	 */
	@Override
	public void stop() {
		startCache.set(false);
		ecnCache.close();
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.storage.CacheService#isStart()
	 */
	@Override
	public boolean isStart() {
		return startCache.get();
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.storage.CacheService#getCurrentPisition()
	 */
	@Override
	public long getCurrentPisition() {
		return currentPosition;
	}

	private void adCachesInit() {
		for (AlgorithmSetting setting : phmAlgorithmSetting.getAlgorithmSettings()) {
			List<VariableGroup> variableGroups = setting.getVariableGroups();
			for (VariableGroup variableGroup : variableGroups) {
				if (variableGroup.getType().equals(VariableType.AD.name())) {
					List<ADVariable> adVariables = (List<ADVariable>) variableGroup.getVariables();
					for (ADVariable adVariable : adVariables) {
						ADMessageFileCache adCache = new ADMessageFileCache(false, 0);
						Integer chnId = adVariable.getChnId();
						adCache.init(chnId);
						adCaches.add(adCache);
						channelCacheMap.put(chnId, adCache);
					}
				}
			}
		}
	}

	/**
	 * 判断该时戳是否在1分钟以内
	 * 
	 * @param timestamp
	 */
	private boolean isWithinValidity(int timestamp) {
		Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));// 获取秒数
		if (Math.abs(second - timestamp) < 60) {
			return true;
		}
		return false;
	}

}
