/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive.file;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import com.hirain.phm.bd.data.bean.DataRecord;
import com.hirain.phm.bd.data.hive.event.LoadFileEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 11, 2020 11:03:19 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 11, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Slf4j
public class FileWriterImpl implements FileWriter {

	@Autowired
	private FileProperties properties;

	@Autowired
	private ApplicationEventPublisher publisher;

	private String tableName;

	private String folder;

	private String path;

	private File file;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	private SimpleDateFormat fileSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	public FileWriterImpl(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public void init(String tableName) {
		folder = properties.getRoot() + File.separator + "packet" + File.separator + tableName;
	}

	/**
	 * @see com.hirain.phm.bd.data.hive.file.FileWriter#write(java.util.List)
	 */
	@Override
	public void write(List<DataRecord> records) {
		System.out.println("write");
		String filename = getFilename(records.get(0).getTimestamp());
		System.out.println("getFileName:" + filename);
		File current = getFile(filename);
		List<String> lines = getLines(records);
		try {
			System.out.println("file writer write lines");
			FileUtils.writeLines(current, "utf-8", lines, "\n", true);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @param records
	 */
	private List<String> getLines(List<DataRecord> records) {
		List<String> lines = new ArrayList<>();
		String sep = properties.getSeperator();
		for (DataRecord record : records) {
			StringBuilder sb = new StringBuilder();
			sb.append(record.getProject()).append(sep);
			sb.append(record.getTrain()).append(sep);
			sb.append(record.getCarriageId()).append(sep);
			sb.append(record.getDoorId()).append(sep);
			sb.append(fileSdf.format(record.getTimestamp())).append(sep);
			sb.append(record.getData()).append(sep);
			sb.append(record.getState());
			lines.add(sb.toString());
		}
		return lines;
	}

	/**
	 * @param filename
	 */
	private File getFile(String filename) {
		String newPath = folder + File.separator + filename + ".txt";
		if (newPath.equals(path)) {
			return file;
		}
		try {
			String oldPath = path;
			closeAndLoadToHive(oldPath);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		try {
			path = newPath;
			file = new File(path);
			System.out.println(file.getAbsolutePath());
			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return file;
	}

	/**
	 * @throws IOException
	 */
	private void closeAndLoadToHive(String path) {
		if (path != null) {
			publisher.publishEvent(new LoadFileEvent(tableName, path));
		}
	}

	/**
	 * @param timestamp
	 */
	private String getFilename(Date timestamp) {
		System.out.println(sdf.format(timestamp));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(timestamp);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int interval = properties.getInterval();
		System.err.println(interval);
		int integral = hour / interval * interval;
		System.err.println(integral);
		calendar.set(Calendar.HOUR_OF_DAY, integral);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime();
		return sdf.format(date);
	}

}
