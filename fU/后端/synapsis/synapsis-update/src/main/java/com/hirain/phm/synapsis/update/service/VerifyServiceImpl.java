/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.service.BoardService;
import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.update.domain.VersionInfo;
import com.hirain.phm.synapsis.update.domain.VersionVerifyResult;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 8, 2020 3:48:34 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 8, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class VerifyServiceImpl implements VerifyService {

	@Autowired
	private VersionInfoService versionInfoService;

	@Autowired
	private BoardService boardService;

	/**
	 * @see com.hirain.phm.synapsis.update.service.VerifyService#verifyFile(java.io.File, java.io.File)
	 */
	@Override
	public boolean verifyFile(File versionPackage, File sha1File) {
		try {
			byte[] sha1 = getSha1Value(versionPackage);
			byte[] verify = FileUtils.readFileToByteArray(sha1File);
			if (!compareSha1Value(sha1, verify)) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * @see com.hirain.phm.synapsis.update.service.VerifyService#verifyVersion()
	 */
	@Override
	public VersionVerifyResult verifyVersion(String packageName) {
		VersionVerifyResult result = new VersionVerifyResult();
		VersionInfo versionInfo = versionInfoService.selectByPackageName(packageName);
		List<String> resultInfos = new ArrayList<>();

		Map<BoardType, String> versionMap = new HashMap<>();
		// 获取当前机箱各板卡的版本信息
		List<Board> boards = boardService.getBoards();
		for (Board board : boards) {
			versionMap.put(board.getBoardType(), board.getControlVer());
		}
		Iterator<?> entries = versionMap.entrySet().iterator();
		while (entries.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<BoardType, String> entry = (Map.Entry<BoardType, String>) entries.next();
			BoardType type = entry.getKey();
			String versionNum = entry.getValue();
			switch (type) {
			case MVB:
				resultInfos.add("MVB板卡：当前版本【" + versionNum + "】，更新版本：【：" + versionInfo.getMvbVersion() + "】");
				break;
			case ECN:
				resultInfos.add("ECN板卡：当前版本【" + versionNum + "】，更新版本：【：" + versionInfo.getMvbVersion() + "】");
				break;
			case CPU:
				resultInfos.add("CPU板卡：当前版本【" + versionNum + "】，更新版本：【：" + versionInfo.getMvbVersion() + "】");
				break;
			case AD1:
				resultInfos.add("AD1板卡：当前版本【" + versionNum + "】，更新版本：【：" + versionInfo.getMvbVersion() + "】");
				break;
			case AD2:
				resultInfos.add("AD2板卡：当前版本【" + versionNum + "】，更新版本：【：" + versionInfo.getMvbVersion() + "】");
				break;
			case AD3:
				resultInfos.add("AD3板卡：当前版本【" + versionNum + "】，更新版本：【：" + versionInfo.getMvbVersion() + "】");
				break;
			case PHM_IMX8:
				resultInfos.add("PHM_IMX8板卡：当前版本【" + versionNum + "】，更新版本：【：" + versionInfo.getMvbVersion() + "】");
				break;
			case PHM_AGX:
				resultInfos.add("PHM_AGX板卡：当前版本【" + versionNum + "】，更新版本：【：" + versionInfo.getMvbVersion() + "】");
				break;
			case PHM_TX2:
				resultInfos.add("PHM_TX2板卡：当前版本【" + versionNum + "】，更新版本：【：" + versionInfo.getMvbVersion() + "】");
				break;
			default:
				break;
			}
		}
		result.setInfos(resultInfos);
		return result;
	}

	/**
	 * 计算file的SHA1码值
	 * 
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unused")
	private byte[] getSha1Value(final File file) throws OutOfMemoryError, IOException, NoSuchAlgorithmException {
		FileInputStream in = null;
		in = new FileInputStream(file);
		final MessageDigest digest = MessageDigest.getInstance("SHA-1");
		final byte[] buffer = new byte[(int) file.length()];

		int len = 0;
		while ((len = in.read(buffer)) > 0) {
			digest.update(buffer, 0, len);
		}
		in.close();
		return digest.digest();
	}

	/**
	 * 比较两组SHA1值是否一致
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean compareSha1Value(final byte[] b1, final byte[] b2) {
		if (b1.length != b2.length) {
			return false;
		}
		for (int i = 0; i < b1.length; i++) {
			if (b1[i] != b2[i]) {
				return false;
			}
		}
		return true;
	}

}
