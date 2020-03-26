/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.runtime.param.ActivateResponse;
import com.hirain.phm.synapsis.runtime.param.SettingResponse;
import com.hirain.phm.synapsis.runtime.param.UpdateResponse;
import com.hirain.phm.synapsis.setting.Subsystem;
import com.hirain.phm.synapsis.setting.support.param.SettingVO;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 2:28:12 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface RuntimeService {

	/**
	 * 初始化
	 * 
	 * @param boards
	 */
	void init(List<Board> boards);

	/**
	 * 保存或更新配置
	 * 
	 * @param settingRequest
	 * @return
	 */
	UpdateResponse saveSetting(SettingVO settingRequest) throws Exception;

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	String upload(MultipartFile file) throws Exception;

	/**
	 * 激活配置
	 * 
	 * @param settingId
	 * @return
	 */
	ActivateResponse validateAndActivate(int settingId) throws Exception;

	/**
	 * @param settingId
	 * @return
	 * @throws Exception
	 */
	String deleteSetting(int settingId) throws Exception;

	List<Subsystem> selectAllSubsystems();

	/**
	 * @return
	 * @throws Exception
	 */
	ActivateResponse launchCurrent() throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	ActivateResponse terminate() throws Exception;

	void export(HttpServletResponse response, Integer settingId) throws Exception;

	SettingResponse selectWithDetail(Integer id);

	SettingResponse importFile(MultipartFile file) throws Exception;

}
