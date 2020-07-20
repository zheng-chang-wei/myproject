/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.algorithm.util.BeanConstants;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Feb 19, 2020 4:21:06 PM
 * @Description
 *              <p>
 *              存储AD原始数据，其结果文件上传至CPU板卡
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 19, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Service(BeanConstants.STORAGE + "AD")
public class ADStorageServiceImpl implements StorageService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier(BeanConstants.MESSAGECACHEROOTPATH)
	private String messageCacheRoot;

	@Autowired
	@Qualifier(BeanConstants.ADCACHES)
	private List<ADMessageFileCache> adCaches;

	private Map<Integer, File> chnIDFileMap = new HashMap<>();

	private Map<Integer, FileChannel> chnIDOutMap = new HashMap<>();

	protected List<FileOutputStream> foss = new ArrayList<>();

	private final ExecutorService writeExecutor = Executors.newFixedThreadPool(5, r -> new Thread(r, CacheServiceImpl.class.getName() + "-write"));

	/**
	 * @see com.hirain.phm.synapsis.algorithm.storage.StorageService#init(java.lang.String, java.lang.String)
	 */
	@Override
	public void init(String folderName, String fileName) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.storage.StorageService#storage(java.lang.String)
	 */
	@Override
	public void storage(long warningTime) {
		for (ADMessageFileCache adCache : adCaches) {
			writeExecutor.submit(() -> {
				while (true) {// TODO 这里的while true 会导致只能写一个文件，需要调整
					byte[] datas = adCache.read();
					adCache.getCurrentPosition().addAndGet(datas.length);
					final ByteBuffer buffer = ByteBuffer.allocate(datas.length);
					buffer.put(datas);
					buffer.flip();
					try {
						chnIDOutMap.get(adCache.getChannelID()).write(buffer);
					} catch (final IOException e) {
						log.error(e.getMessage(), e);
					}
				}
			});
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.storage.StorageService#shutdown()
	 */
	@Override
	public void shutdown() {
		writeExecutor.shutdown();
		try {
			for (FileOutputStream fos : foss) {
				fos.close();
			}
			for (FileChannel out : chnIDOutMap.values()) {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建存储文件
	 */
	private void createFile(int slotID, String timestamp) {
		for (ADMessageFileCache adCache : adCaches) {
			String filepath = messageCacheRoot + "AD" + File.separator;
			filepath += "AD_" + String.valueOf(slotID) + "_" + String.valueOf(adCache.getChannelID()) + "_" + timestamp + ".dat";
			File file = new File(filepath);
			try {
				file.createNewFile();
				chnIDFileMap.put(adCache.getChannelID(), file);
			} catch (final IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 初始化可写通道
	 */
	private void initOutChannel() {
		try {
			Iterator entries = chnIDFileMap.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				Integer chnID = (Integer) entry.getKey();
				File file = (File) entry.getValue();
				FileOutputStream fos = new FileOutputStream(file.getAbsolutePath(), true);
				FileChannel out = fos.getChannel();
				foss.add(fos);
				chnIDOutMap.put(chnID, out);
			}
		} catch (final FileNotFoundException e) {
			log.error(e.getMessage(), e);
		}
	}

}
