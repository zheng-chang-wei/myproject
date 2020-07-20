/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hirain.phm.synapsis.update.domain.BoardUpdateSchedule;
import com.hirain.phm.synapsis.update.message.VersionUpdateResponseMessage;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 7, 2020 7:07:20 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 7, 2020 zepei.tao@hirain.com 1.0 create file
 */
public interface VersionService {

	/**
	 * 将更新文件上传到相应路径下
	 */
	void uploadFile(MultipartFile versionPackage, MultipartFile sha1File) throws Exception;

	/**
	 * 删除版本库中的更新包
	 * 
	 * @param packageName
	 */
	boolean deleteVersionPackage(String packageName);

	/**
	 * 版本更新
	 * 
	 * @return
	 */
	boolean update();

	/**
	 * 版本下载
	 * 
	 * @return
	 */
	boolean download(String packageName) throws Exception;

	/**
	 * 发送版本更新指令
	 */
	VersionUpdateResponseMessage sendUpdateMessage() throws Exception;

	/**
	 * 获取更新进度
	 * 
	 * @return
	 */
	List<BoardUpdateSchedule> scanUpdateSchedule();

	/**
	 * 更新板卡升级进度
	 */
	void updateBoardUpdateSchedule(BoardUpdateSchedule schedule);

	void initBoardUpdateSchedule();

	void clearSchedules();

}
