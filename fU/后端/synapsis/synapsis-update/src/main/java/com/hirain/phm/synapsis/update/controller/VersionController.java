/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.update.domain.BoardUpdateSchedule;
import com.hirain.phm.synapsis.update.domain.VersionInfo;
import com.hirain.phm.synapsis.update.domain.VersionVerifyResult;
import com.hirain.phm.synapsis.update.service.VerifyService;
import com.hirain.phm.synapsis.update.service.VersionInfoService;
import com.hirain.phm.synapsis.update.service.VersionService;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 7, 2020 7:06:27 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 7, 2020 zepei.tao@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/versionupdate")
public class VersionController {

	@Autowired
	private VersionService versionService;

	@Autowired
	private VersionInfoService versionInfoService;

	@Autowired
	private VerifyService verifyService;

	/**
	 * 上传版本更新文件
	 * 
	 * @param slotId
	 * @param versionFile
	 * @return
	 */
	@PostMapping("/upload")
	public ResultBean<String> uploadProtocol(MultipartFile versionFile, MultipartFile sha1File) {
		try {
			versionService.uploadFile(versionFile, sha1File);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<>(e.getMessage(), e);
		}
		return new ResultBean<>("上传成功");
	}

	@PostMapping("/delete")
	public ResultBean<String> deleteVersionPackage(String packageName) {
		boolean result = false;
		versionInfoService.deleteVersionInfo(packageName);
		result = versionService.deleteVersionPackage(packageName);
		if (result) {
			return new ResultBean<>("删除更新包成功！");
		}
		return new ResultBean<>("删除更新包失败，请联系网站管理员！");
	}

	@PostMapping("/listall")
	public ResultBean<List<VersionInfo>> listAll() {
		List<VersionInfo> listVersions = versionInfoService.listVersions();
		return new ResultBean<>(listVersions);
	}

	/**
	 * 校验当前要更新的版本和正在运行的版本号之间的差异
	 * 
	 * @param packageName
	 * @return
	 */
	@PostMapping("/verify")
	public ResultBean<VersionVerifyResult> verify(String packageName) {
		try {
			VersionVerifyResult versionVerifyResult = null;
			if (versionService.download(packageName)) {
				versionVerifyResult = verifyService.verifyVersion(packageName);
				return new ResultBean<>(versionVerifyResult);
			} else {
				versionVerifyResult = new VersionVerifyResult();
				versionVerifyResult.setError(true);
				return new ResultBean<>(versionVerifyResult);
			}
		} catch (Exception e) {
			return new ResultBean<>(e.getMessage(), e);
		}
	}

	@PostMapping("/update")
	public ResultBean<String> update() {
		try {
			versionService.sendUpdateMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResultBean<>(null);
	}

	@PostMapping("/schedule")
	public ResultBean<List<BoardUpdateSchedule>> schedule() {
		List<BoardUpdateSchedule> schedules = versionService.scanUpdateSchedule();
		return new ResultBean<>(schedules);
	}

}
