/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.message.BoardControlResponseMessage;
import com.hirain.phm.synapsis.board.service.BoardService;
import com.hirain.phm.synapsis.communication.Communication;
import com.hirain.phm.synapsis.communication.Message;
import com.hirain.phm.synapsis.communication.impl.TransportMessage;
import com.hirain.phm.synapsis.constant.ErrorCode;
import com.hirain.phm.synapsis.constant.Program;
import com.hirain.phm.synapsis.constant.RunState;
import com.hirain.phm.synapsis.constant.SidConstant;
import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.runtime.message.ErrorNoticeRequest;
import com.hirain.phm.synapsis.runtime.param.ActivateResponse;
import com.hirain.phm.synapsis.runtime.param.ControlResponse;
import com.hirain.phm.synapsis.runtime.param.SettingResponse;
import com.hirain.phm.synapsis.runtime.service.LaunchProgressManager;
import com.hirain.phm.synapsis.runtime.service.RuntimeService;
import com.hirain.phm.synapsis.runtime.service.StateService;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.Subsystem;
import com.hirain.phm.synapsis.setting.db.SettingDbService;
import com.hirain.phm.synapsis.setting.support.SettingActivateSupporter;
import com.hirain.phm.synapsis.setting.support.SettingUpdateSupporter;
import com.hirain.phm.synapsis.setting.support.SettingValidator;
import com.hirain.phm.synapsis.setting.support.SettingViewSupporter;
import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;
import com.hirain.phm.synapsis.setting.support.extend.SupportExtenderManager;
import com.hirain.phm.synapsis.setting.support.param.AlgorithmSettingVO;
import com.hirain.phm.synapsis.setting.support.param.BoardSettingVO;
import com.hirain.phm.synapsis.setting.support.param.SettingVO;
import com.hirain.phm.synapsis.util.SettingFileConfig;

import cn.hutool.core.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 2:29:30 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Slf4j
public class RuntimeServiceImpl implements RuntimeService {

	@Autowired
	private SettingDbService settingService;

	@Autowired
	private SettingValidator validator;

	@Autowired
	private Communication communication;

	@Autowired
	private StateService stateService;

	@Autowired
	private SettingActivateSupporter activateSupporter;

	@Autowired
	private BoardService boardService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private LaunchProgressManager launchManager;

	@Autowired
	private SettingUpdateSupporter updateSupporter;

	@Autowired
	private SupportExtenderManager extender;

	@Autowired
	private SettingViewSupporter settingParser;

	@Autowired
	private SettingFileConfig config;

	/**
	 * 要导出的文件所在目录
	 */
	private String exportRoot = System.getProperty("user.dir") + "//files";

	private String zipRoot = System.getProperty("user.dir") + "//files.zip";

	/**
	 * @see com.hirain.phm.synapsis.runtime.service.RuntimeService#init(java.util.List)
	 */
	@Override
	public void init(List<Board> boards) {
		stateService.init();
		try {
			Setting current = settingService.selectCurrent();
			ValidateResult result = validator.validateWithBoard(current, boards);
			if (result.isError()) {
				stateService.waiting(result.getOutline());
				stateService.validateError(result);
				sendValidateResult(result);
			} else {
				stateService.loading();
				generateConfigFiles(current);
				BoardControlResponseMessage message = startBoards(current);
				if (!message.isSuccessed()) {
					stateService.waiting("启动失败");
					stateService.lauchError(new ControlResponse(message.getBoardStartStructures()));
				} else {
					publisher.publishEvent(current);
					stateService.running();
				}
			}
		} catch (SynapsisException e) {
			log.error(e.getMessage(), e);
			stateService.waiting(e.getMessage() != null ? e.getMessage() : "系统异常");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			stateService.waiting("系统异常");
		}
	}

	/**
	 * @param result
	 * @throws SynapsisException
	 */
	private void sendValidateResult(ValidateResult result) throws SynapsisException {
		ErrorNoticeRequest request = new ErrorNoticeRequest();
		request.setStatus(ErrorCode.ERROR_SETTING);
		TransportMessage<ErrorNoticeRequest> requestMessage = new TransportMessage<>(SidConstant.ERROR_COMMAND, Program.CPU_SERVICE.getCode(),
				Program.CPU_CONTROL.getCode(), request);
		send(requestMessage);
	}

	/**
	 * @param request
	 */
	private void send(Message<?> request) throws SynapsisException {
		for (int i = 0; i < 3; i++) {
			try {
				Message<?> message = communication.send(request, 2);
				if (message != null) {
					return;
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.runtime.service.RuntimeService#upload(org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public String upload(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			throw new IOException("文件为空");
		}
		File temporary = new File(config.getTemp());
		if (!temporary.exists()) {
			temporary.mkdirs();
		}
		String extension = getExtension(file.getOriginalFilename());
		String fileName = UUID.randomUUID().toString() + extension;
		File serverFile = new File(temporary, fileName);
		file.transferTo(serverFile);
		return fileName;
	}

	private String getExtension(String filename) {
		int lastIndexOf = filename.lastIndexOf(".");
		String extension = filename.substring(lastIndexOf);
		return extension;
	}

	@Override
	public ActivateResponse terminate() throws Exception {
		ActivateResponse response = new ActivateResponse();
		BoardControlResponseMessage stopMessage;
		try {
			stopMessage = boardService.stop();
			if (!stopMessage.isSuccessed()) {
				response.stopFail();
				response.setControlResponse(new ControlResponse(stopMessage.getBoardStartStructures()));
				return response;
			}
			stateService.idle();
		} catch (Exception e) {
			throw new SynapsisException("系统停止失败! [" + e.getMessage() + "]", e);
		}
		return response;
	}

	@Override
	public ActivateResponse launchCurrent() throws Exception {
		Setting current = settingService.selectCurrent();
		if (current == null) {
			throw new SynapsisException("当前没有可使用的配置");
		}
		return validateAndLaunch(current);
	}

	/**
	 * @see com.hirain.phm.synapsis.runtime.service.RuntimeService#activeSetting()
	 */
	@Override
	public ActivateResponse launchSetting(int settingId) throws Exception {
		RunState state = stateService.getCurrentState();
		if (state.equals(RunState.RUNNING)) {
			ActivateResponse response = terminate();
			if (!response.isSuccess()) {
				return response;
			}
			stateService.idle();
		}
		Setting setting = settingService.selectWithDetail(settingId);
		return validateAndLaunch(setting);
	}

	/**
	 * @param setting
	 * @return
	 * @throws SynapsisException
	 * @throws Exception
	 */
	private ActivateResponse validateAndLaunch(Setting setting) throws SynapsisException, Exception {
		ActivateResponse response;
		stateService.loading();
		response = validate(setting);
		if (!response.isSuccess()) {
			ValidateResult validateResult = response.getValidateResult();
			sendValidateResult(validateResult);
			stateService.waiting(validateResult.getOutline());
			return response;
		}
		response = launch(setting);
		if (response.isSuccess()) {
			launchManager.start();
		}
		return response;
	}

	/**
	 * @param setting
	 * @throws SynapsisException
	 */
	private ActivateResponse validate(Setting setting) throws SynapsisException {
		ActivateResponse response = new ActivateResponse();
		ValidateResult validateResult = validator.validateWithBoard(setting, boardService.getBoards());
		if (validateResult.isError()) {
			response.validateError();
			response.setValidateResult(validateResult);
		}
		return response;
	}

	/**
	 * @param setting
	 * @return
	 * @throws Exception
	 */
	private ActivateResponse launch(Setting setting) throws Exception {
		ActivateResponse response = new ActivateResponse();
		try {
			generateConfigFiles(setting);
			settingService.changeCurrent(setting.getId());
			BoardControlResponseMessage message = startBoards(setting);
			if (!message.isSuccessed()) {
				response.lauchFail();
				response.setControlResponse(new ControlResponse(message.getBoardStartStructures()));
				stateService.waiting("启动失败");
				stateService.lauchError(response.getControlResponse());
			} else {
				publisher.publishEvent(setting);
				stateService.running();
			}
		} catch (Exception e) {
			stateService.waiting(e.getMessage() != null ? e.getMessage() : "系统异常");
			throw e;
		}
		return response;
	}

	/**
	 * @param setting
	 * @throws SynapsisException
	 */
	private void generateConfigFiles(Setting setting) throws SynapsisException {
		try {
			activateSupporter.acivate(setting);
		} catch (Exception e) {
			throw new SynapsisException("系统出错：生成配置文件失败", e);
		}
	}

	/**
	 * @param current
	 * @return
	 * @throws Exception
	 */
	private BoardControlResponseMessage startBoards(Setting setting) throws Exception {
		BoardControlResponseMessage message = boardService.start();
		return message;
	}

	/**
	 * @see com.hirain.phm.synapsis.runtime.service.RuntimeService#deleteSetting(int)
	 */
	@Override
	public String deleteSetting(int settingId) throws Exception {
		Setting current = settingService.selectCurrent();
		if (current != null && current.getId().equals(settingId)) {
			throw new SynapsisException("不能删除已激活的配置");
		}
		Setting setting = settingService.selectById(settingId);
		settingService.delete(settingId);
		extender.delete(settingId);
		updateSupporter.deleteResources(setting);
		return "删除成功";
	}

	@Override
	public List<Subsystem> selectAllSubsystems() {
		return settingService.selectAllSubsystems();
	}

	@Override
	public SettingResponse selectWithDetail(Integer settingId) {
		Setting setting = settingService.selectWithDetail(settingId);
		SettingResponse settingResponse = new SettingResponse();
		if (setting != null) {
			SettingVO settingVO = settingParser.settingToVO(setting);
			settingResponse.setSetting(settingVO);
		}
		return settingResponse;
	}

	@Override
	public void export(HttpServletResponse response, Integer settingId) throws Exception {
		Setting setting = settingService.selectWithDetail(settingId);
		File uploadRootDir = new File(exportRoot);
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		writeJson(setting);
		copyFileToExportFile(setting);
		ZipUtil.zip(exportRoot);
		exportFile(response, setting.getName());
		FileUtils.deleteDirectory(uploadRootDir);
	}

	/**
	 * 导入配置文件
	 */
	@Override
	public SettingResponse importFile(MultipartFile file) throws Exception {
		// 带zip后缀的文件名称，如aa.zip
		String uploadFileName = upload(file);
		String temporaryDirectory = config.getTemp();
		// zip文件绝对路径
		String zipPath = temporaryDirectory + File.separator + uploadFileName;
		if (zipPath.contains(".zip")) {

			ZipUtil.unzip(zipPath);
			// zip文件解压后的文件夹的绝对路径
			String zipFolderPath = temporaryDirectory + File.separator + uploadFileName.substring(0, uploadFileName.lastIndexOf("."));
			try {
				// result.json中json字符串
				String resultJson = FileUtils.readFileToString(new File(zipFolderPath + File.separator + "result.json"));
				Setting setting = JSONObject.parseObject(resultJson, Setting.class);
				SettingResponse settingResponse = selectWithDetail(setting.getId());
				SettingVO settingVO = settingResponse.getSetting();
				List<BoardSettingVO> boardSettings = settingVO.getBoardSettings();
				for (BoardSettingVO boardSettingVO : boardSettings) {
					String extension = getExtension(boardSettingVO.getOriginalName());
					String fileName = UUID.randomUUID().toString() + extension;
					String srcFilePath = zipFolderPath + File.separator + "board_" + boardSettingVO.getSlotId() + extension;
					String destFilePath = temporaryDirectory + File.separator + fileName;
					copyFile(srcFilePath, destFilePath);
					boardSettingVO.setFilename(fileName);
				}
				List<AlgorithmSettingVO> algorithmSettings = settingVO.getAlgorithmSettings();
				for (AlgorithmSettingVO algorithmSettingVO : algorithmSettings) {
					String extension = getExtension(algorithmSettingVO.getFileOriginalName());
					String fileName = UUID.randomUUID().toString() + extension;
					String srcFilePath = zipFolderPath + File.separator + "algorithm_" + algorithmSettingVO.getName() + extension;
					String destFilePath = temporaryDirectory + File.separator + fileName;
					copyFile(srcFilePath, destFilePath);
					algorithmSettingVO.setFilename(fileName);
				}
				return settingResponse;
			} catch (Exception e) {
				throw e;
			} finally {
				FileUtils.deleteQuietly(new File(zipPath));
				FileUtils.deleteDirectory(new File(zipFolderPath));
			}
		}
		return null;
	}

	private void writeJson(Object obj) throws IOException {
		Object json = JSONObject.toJSON(obj);
		FileUtils.writeStringToFile(new File(exportRoot + File.separator + "result.json"), json.toString());
	}

	/**
	 * 复制文件到要导出的目录
	 * 
	 * @param setting
	 * @throws IOException
	 */
	private void copyFileToExportFile(Setting setting) throws IOException {
		// 设置对应的文件夹所在目录
		String folderPath = config.getResource() + File.separator + setting.getName();
		List<BoardSetting> boardSettings = setting.getBoardSettings();
		for (BoardSetting boardSetting : boardSettings) {
			// 板卡对应的数据流文件名称
			String fileName = updateSupporter.getFilename(boardSetting);
			if (!StringUtils.isEmpty(fileName)) {
				// 将文件移动到files目录下
				copyFile(folderPath + File.separator + fileName,
						exportRoot + File.separator + "board_" + boardSetting.getSlotId() + getExtension(fileName));
			}
		}
		List<AlgorithmSetting> algorithmSettings = setting.getAlgorithmSettings();
		for (AlgorithmSetting algorithmSetting : algorithmSettings) {
			// 算法对相应的数据流文件名称
			String fileName = algorithmSetting.getFilename();
			if (!StringUtils.isEmpty(fileName)) {
				// 将文件移动到files目录下
				copyFile(folderPath + File.separator + fileName,
						exportRoot + File.separator + "algorithm_" + algorithmSetting.getName() + getExtension(fileName));
			}
		}
	}

	private void copyFile(String srcFilePath, String destFilePath) throws IOException {
		File srcFile = new File(srcFilePath);
		if (srcFile.exists()) {
			File destFile = new File(destFilePath);
			FileUtils.copyFile(srcFile, destFile);
		}
	}

	private void exportFile(HttpServletResponse response, String settingName)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		File file = new File(zipRoot);
		String extension = getExtension(file.getName());
		response.reset();
		response.setCharacterEncoding("UTF-8");
		// response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(settingName + extension, "UTF-8"));
		InputStream input = new FileInputStream(file);
		OutputStream out = response.getOutputStream();
		byte[] buff = new byte[1024];
		int index = 0;
		while ((index = input.read(buff)) != -1) {
			out.write(buff, 0, index);
			out.flush();
		}
		out.close();
		input.close();
	}

}
