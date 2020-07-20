/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.storage;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.algorithm.domain.ECNStorageMessage;
import com.hirain.phm.synapsis.algorithm.ftp.FTPService;
import com.hirain.phm.synapsis.algorithm.ftp.FTPServicePool;
import com.hirain.phm.synapsis.algorithm.setting.SettingService;
import com.hirain.phm.synapsis.algorithm.util.BeanConstants;
import com.hirain.phm.synapsis.compress.QuickLzSupport;
import com.hirain.phm.synapsis.message.MessageConstant;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Feb 19, 2020 4:21:06 PM
 * @Description
 *              <p>
 *              存储ECN原始数据，其结果文件上传至CPU板卡
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 19, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Service(BeanConstants.STORAGE + "ECN")
public class ECNStorageServiceImpl implements StorageService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier(BeanConstants.MESSAGECACHEROOTPATH)
	private String messageCacheRoot;

	@Autowired
	private SettingService settingService;

	@Autowired
	private FTPServicePool ftpPool;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private QuickLzSupport quickLz;

	private List<ECNStorageMessage> storageMessages = new ArrayList<>();

	protected final ExecutorService writeExecutor = Executors.newFixedThreadPool(10,
			r -> new Thread(r, ECNStorageServiceImpl.class.getName() + "-write"));

	@Override
	public void init(String folderName, String fileName) {
		try {
			ECNStorageMessage storageMessage = new ECNStorageMessage(cacheService.getCurrentPisition());
			String folderPath = messageCacheRoot + "ECN" + File.separator + folderName;
			storageMessage.init(folderPath, fileName);
			storageMessage.initOutChannel();
			storageMessage.getStoraging().set(true);
			FileUtils.copyFileToDirectory(settingService.getDataHeaderFile(), storageMessage.getFolder());
			storageMessages.add(storageMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.storage.StorageService#storage(java.lang.String)
	 */
	@Override
	public void storage(long warningTime) {
		writeExecutor.submit(() -> {
			// log.info("warnintTime:" + warningTime + " ,ECN-storageMessageSize():" + storageMessages.size());
			ECNStorageMessage ecnStorageMessage = storageMessages.get(storageMessages.size() - 1);
			byte[] sourceData = new byte[] {};
			while (ecnStorageMessage.getStoraging().get()) {
				byte[] datas = ecnStorageMessage.getCache().read();
				if (datas == null) {
					continue;
				}
				sourceData = ArrayUtils.addAll(sourceData, datas);
				final ByteBuffer bf = ByteBuffer.wrap(datas).order(MessageConstant.MESSAGE_ORDER);
				bf.getInt();// frameIndex
				int unix_time = bf.getInt();
				// log.info(ecnStorageMessage.hashCode() + " unix_time-- " + unix_time + " startStorageTimeStamp-- " +
				// startStorageTimeStamp);
				if (Math.abs(unix_time - warningTime) > 60) {
					ecnStorageMessage.getStoraging().set(false);
				}
			}

			byte[] compressData = quickLz.compress(sourceData);
			final ByteBuffer buffer = ByteBuffer.allocate(compressData.length);
			buffer.put(compressData);
			buffer.flip();
			try {
				ecnStorageMessage.getOut().write(buffer);
				ecnStorageMessage.close();
				FTPService ftpService = ftpPool.borrowObject();
				ftpService.connectFtpServer();
				ftpService.upload(ecnStorageMessage.getFolder());
				ftpService.closeFTP();
				ftpPool.returnObject(ftpService);
				sourceData = null;
				compressData = null;
			} catch (Exception e) {
				log.error("ecnStorageService exception--------->{}" + e.getMessage());
			}
		});
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.storage.StorageService#shutdown()
	 */
	@Override
	public void shutdown() {
		try {
			for (ECNStorageMessage storageMessage : storageMessages) {
				storageMessage.getStoraging().set(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			storageMessages.clear();
		}
	}

}
