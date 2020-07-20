/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.service.BoardService;
import com.hirain.phm.synapsis.communication.Message;
import com.hirain.phm.synapsis.communication.impl.CommunicationImpl;
import com.hirain.phm.synapsis.communication.impl.TransportMessage;
import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.constant.Program;
import com.hirain.phm.synapsis.constant.SidConstant;
import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.update.domain.BoardUpdateSchedule;
import com.hirain.phm.synapsis.update.domain.Schedule;
import com.hirain.phm.synapsis.update.domain.VersionInfo;
import com.hirain.phm.synapsis.update.message.VersionUpdateMessage;
import com.hirain.phm.synapsis.update.message.VersionUpdateResponseMessage;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 7, 2020 7:07:42 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 7, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class VersionServiceImpl implements VersionService {

	@Autowired
	private CommunicationImpl communicationImpl;

	@Autowired
	private CompressService compressService;

	@Autowired
	private VerifyService verifyService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private VersionInfoService versionInfoService;

	@Autowired
	private UpdateProperties props;

	private List<BoardUpdateSchedule> schedules = new CopyOnWriteArrayList<>();

	/**
	 * @see com.hirain.phm.synapsis.update.service.VersionService#uploadFile(
	 *      org.springframework.web.multipart.MultipartFile,org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public void uploadFile(MultipartFile versionPackage, MultipartFile sha1File) throws Exception {
		if (versionPackage.isEmpty() || sha1File.isEmpty()) {
			throw new IOException("文件为空");
		}
		File repository = new File(props.getVersionRepository());
		if (!repository.exists()) {
			repository.mkdirs();
		}
		File versionFile = new File(repository, versionPackage.getOriginalFilename());
		File verifyFile = new File(repository, sha1File.getOriginalFilename());
		versionPackage.transferTo(versionFile);
		sha1File.transferTo(verifyFile);

		if (verifyService.verifyFile(versionFile, verifyFile)) {
			// 解压到临时文件夹
			String packageName = versionFile.getName();
			File temporaryDirectory = new File(
					props.getVersionRepository() + File.separator + packageName.replaceAll(getFileSuffix(packageName), ""));// TODO
			// 注意这里交付的时候后缀名固定是.tar.gz
			if (!temporaryDirectory.exists()) {
				temporaryDirectory.mkdirs();
			}
			compressService.deCompress(versionFile, temporaryDirectory.getAbsolutePath());

			VersionInfo versionInfo = parseVersionNo(temporaryDirectory);
			VersionInfo db_versionInfo = versionInfoService.selectByPackageName(versionInfo.getPackageName());
			if (db_versionInfo == null) {// 若数据库中没有这个包名对应的信息，则插入新一条
				versionInfoService.insertVersionInfo(versionInfo);
			} else {// 若数据库中存在这个包名对应的信息，则更新该条信息
				versionInfo.setId(db_versionInfo.getId());
				versionInfoService.updateVersionInfo(versionInfo);
			}
			FileUtils.forceDelete(temporaryDirectory);// 解析、存入数据库完成后，删除临时文件夹
			FileUtils.forceDelete(verifyFile);// 将校验完成后，将sha1文件删除
		} else {
			FileUtils.forceDelete(versionFile);// 校验失败，将本次上传的更新包删除
			FileUtils.forceDelete(verifyFile);// 校验失败，将本次上传的sha1文件删除
			throw new SynapsisException("文件上传受损，请重新上传");
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.update.service.VersionService#deleteVersionPackage(java.lang.String)
	 */
	@Override
	public boolean deleteVersionPackage(String packageName) {
		boolean result = true;
		File file = new File(props.getVersionRepository() + File.separator + packageName);
		if (file.exists()) {
			result = FileUtils.deleteQuietly(file);
		} else {
			result = true;
		}
		return result;
	}

	/**
	 * @see com.hirain.phm.synapsis.update.service.VersionService#sendUpdateMessage()
	 */
	@Override
	public VersionUpdateResponseMessage sendUpdateMessage() throws Exception {
		VersionUpdateMessage message = new VersionUpdateMessage();
		TransportMessage<VersionUpdateMessage> transportMessage = new TransportMessage<>();
		transportMessage.setSid(SidConstant.ACTIVATE_COMMAND);
		transportMessage.setSource(Program.CPU_SERVICE.getCode());
		transportMessage.setTarget(Program.CPU_CONTROL.getCode());
		transportMessage.setData(message);

		Message<?> response = getResponse(transportMessage);
		VersionUpdateResponseMessage responseMessage = (VersionUpdateResponseMessage) response.getData();
		return responseMessage;
	}

	/**
	 * @see com.hirain.phm.synapsis.update.service.VersionService#download()
	 */
	@Override
	public boolean download(String packageName) throws Exception {
		File versionFile = FileUtils.getFile(props.getVersionRepository(), packageName);
		File activeRoot = new File(props.getVersionActiveRoot());
		if (!activeRoot.exists()) {
			activeRoot.mkdirs();
		}
		for (File file : activeRoot.listFiles()) {
			FileUtils.forceDelete(file);
		}
		return compressService.deCompress(versionFile, props.getVersionActiveRoot());
	}

	/**
	 * @see com.hirain.phm.synapsis.update.service.VersionService#update()
	 */
	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see com.hirain.phm.synapsis.update.service.VersionService#scanUpdateSchedule()
	 */
	@Override
	public List<BoardUpdateSchedule> scanUpdateSchedule() {
		if (schedules.size() == 0) {
			initBoardUpdateSchedule();
		}
		return new ArrayList<>(schedules);
	}

	/**
	 * @see com.hirain.phm.synapsis.update.service.VersionService#updateBoardUpdateSchedule(com.hirain.phm.synapsis.update.domain.BoardUpdateSchedule)
	 */
	@Override
	public void updateBoardUpdateSchedule(BoardUpdateSchedule now_schedule) {
		for (BoardUpdateSchedule schedule : schedules) {
			if (schedule.getSlotID() == now_schedule.getSlotID()) {
				schedule.setSchedule(now_schedule.getSchedule());
			}
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.update.service.VersionService#initBoardUpdateSchedule()
	 */
	@Override
	public void initBoardUpdateSchedule() {
		List<Board> boards = boardService.getBoards();
		for (Board board : boards) {
			BoardType type = board.getBoardType();
			if (type == BoardType.POWER || type == BoardType.SSD || type == BoardType.WIRELESS) {
				continue;
			}
			BoardUpdateSchedule schedule = new BoardUpdateSchedule();
			schedule.setSlotID(board.getSlotID());
			schedule.setBoardName(board.getCardName());
			schedule.setSchedule(Schedule.READY);
			schedules.add(schedule);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.update.service.VersionService#clearSchedules()
	 */
	@Override
	public void clearSchedules() {
		schedules.clear();
	}

	private VersionInfo parseVersionNo(File temporaryFolder) {
		VersionInfo versionInfo = new VersionInfo();
		versionInfo.setPackageName(temporaryFolder.getName() + props.getCompressionSuffix());
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = LocalDateTime.now().atZone(zoneId);
		Date date = Date.from(zdt.toInstant());
		versionInfo.setUploadTime(date);
		versionInfo.setEffectived(false);
		File[] boardFolders = temporaryFolder.listFiles((FileFilter) (f) -> f.isDirectory());
		for (File boardFolder : boardFolders) {
			String versionNum = StringUtils.EMPTY_STRING;
			File[] files = boardFolder.listFiles((FilenameFilter) (dir, name) -> name.endsWith(props.getCompressionSuffix()));
			if (files.length == 1) {
				versionNum = parsePackageName(files[0].getName().replaceAll(props.getCompressionSuffix(), ""));
			}
			switch (boardFolder.getName()) {
			case "MVB":
				versionInfo.setMvbVersion(versionNum);
				break;
			case "ECN":
				versionInfo.setEcnVersion(versionNum);
				break;
			case "CPU":
				versionInfo.setCpuVersion(versionNum);
				break;
			case "AD1":
				versionInfo.setAd1Version(versionNum);
				break;
			case "AD2":
				versionInfo.setAd2Version(versionNum);
				break;
			case "AD3":
				versionInfo.setAd3Version(versionNum);
				break;
			case "PHM_AGX":
				versionInfo.setPhmAgxVersion(versionNum);
				break;
			case "PHM_TX2":
				versionInfo.setPhmTx2Version(versionNum);
				break;
			case "PHM_IMX8":
				versionInfo.setPhmImx8Version(versionNum);
				break;
			}
		}
		return versionInfo;
	}

	private String parsePackageName(String packageName) {
		String[] split = packageName.split("_");
		return split[1];
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param filename
	 * @return
	 */
	private String getFileSuffix(String filename) {
		int lastIndexOf = filename.lastIndexOf(".");
		String extension = filename.substring(lastIndexOf);
		return extension;
	}

	private Message<?> getResponse(TransportMessage<?> transportMessage) throws Exception {
		Message<?> response = communicationImpl.send(transportMessage, 5);
		if (response == null) {
			throw new SynapsisException("No reply received after timeout");//
		}
		return response;
	}

}
