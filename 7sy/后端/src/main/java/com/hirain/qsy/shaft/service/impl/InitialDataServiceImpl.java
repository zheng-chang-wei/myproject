package com.hirain.qsy.shaft.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import com.hirain.qsy.shaft.common.config.GlobVariableConfig;
import com.hirain.qsy.shaft.common.exception.ExcelFormatException;
import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.common.util.DataToPythonUtils;
import com.hirain.qsy.shaft.common.util.DateUtil;
import com.hirain.qsy.shaft.dao.InitialDataMapper;
import com.hirain.qsy.shaft.handler.ExcelFileHandler;
import com.hirain.qsy.shaft.model.HomeVO;
import com.hirain.qsy.shaft.model.InitialAxleData;
import com.hirain.qsy.shaft.model.InitialData;
import com.hirain.qsy.shaft.model.MultisetObjectData;
import com.hirain.qsy.shaft.model.TrainInfo;
import com.hirain.qsy.shaft.service.ExceptionDataService;
import com.hirain.qsy.shaft.service.InitialDataService;
import com.hirain.qsy.shaft.service.LogService;
import com.hirain.qsy.shaft.service.ManageService;
import com.hirain.qsy.shaft.service.RedisCacheService;
import com.hirain.qsy.shaft.service.TrainInfoService;
import com.hirain.qsy.shaft.websocket.WebSocketServer;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InitialDataServiceImpl extends BaseService<InitialData> implements InitialDataService {

	@Autowired
	private InitialDataMapper initialDataMapper;

	@Autowired
	private ManageService manageService;

	@Autowired
	private ExceptionDataService exceptionDataService;

	@Autowired
	private TrainInfoService trainInfoService;

	@Autowired
	private RedisCacheService redisCacheService;

	@Autowired
	private LogService logService;

	@Autowired
	private ExcelFileHandler excelFileHandler;

	/**
	 * 根据机车号，时间查询数据
	 */
	@Override
	public List<InitialAxleData> findByTrainInfoAndTime(String trainType, String trainNum, String startDate, String endDate, int axleNum)
			throws Exception {
		Integer trainId = trainInfoService.findTrainInfoByTypeAndNum(trainNum, trainType);
		List<InitialData> avgOfAxleData = initialDataMapper.avgOfAxleData(trainId, startDate, endDate, axleNum);
		return retrieveEveryAxleData(avgOfAxleData, axleNum - 1);
	}

	/**
	 * 获取每个轴的数据
	 * 
	 * @param listdata
	 * @return
	 */
	public List<InitialAxleData> retrieveEveryAxleData(List<InitialData> listdata, int axleIndex) throws Exception {

		// 获取提前定义好的全局变量
		String[] axleName = GlobVariableConfig.axleName;

		List<InitialAxleData> axleDataList = new ArrayList<>();
		// 存储每个轴的数据
		if (listdata != null && listdata.size() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
			for (InitialData data : listdata) {
				InitialAxleData axleData = new InitialAxleData();
				axleData.setAxle1(Integer.valueOf(BeanUtils.getProperty(data, axleName[10 * axleIndex])));
				axleData.setAxle2(Integer.valueOf(BeanUtils.getProperty(data, axleName[10 * axleIndex + 1])));
				axleData.setAxle3(Integer.valueOf(BeanUtils.getProperty(data, axleName[10 * axleIndex + 2])));
				axleData.setAxle4(Integer.valueOf(BeanUtils.getProperty(data, axleName[10 * axleIndex + 3])));
				axleData.setAxle5(Integer.valueOf(BeanUtils.getProperty(data, axleName[10 * axleIndex + 4])));
				axleData.setAxle6(Integer.valueOf(BeanUtils.getProperty(data, axleName[10 * axleIndex + 5])));
				axleData.setAmbientTemperature1(Integer.valueOf(BeanUtils.getProperty(data, axleName[10 * axleIndex + 6])));
				axleData.setAmbientTemperature2(Integer.valueOf(BeanUtils.getProperty(data, axleName[10 * axleIndex + 7])));
				axleData.setGpsSpeed(Integer.valueOf(BeanUtils.getProperty(data, axleName[10 * axleIndex + 8])));
				axleData.setAcquisitionTime(sdf.parse(BeanUtils.getProperty(data, axleName[10 * axleIndex + 9])));
				axleDataList.add(axleData);
			}
		}
		return axleDataList;

	}

	@Override
	public Map<String, Object> findMaxAndMinTime(Integer trainId) {
		if (manageService.existInitialDataTable(trainId) == 1) {
			return initialDataMapper.findMaxAndMinTime(trainId);
		} else {
			return null;
		}
	}

	@Override
	public String findMaxTime(Integer trainId) {
		return initialDataMapper.findMaxTime(trainId);
	}

	@Override
	@Transactional
	public void deleteByTrainNumAndTime(Integer trainId, String deadline) {
		if (trainId != null && deadline != null) {
			initialDataMapper.deleteByTrianIdAndAcquisitionTime(trainId, deadline);
		}

	}

	@Async("asyncServiceExecutor")
	@Override
	// 设置事务隔离级别为读未提交，主要是为了抛出异常后回滚
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class })
	public void importFile(String realPath, String originalFilename, String userName, String ip) throws Exception {
		try {
			importFile(realPath + File.separator + originalFilename);
			WebSocketServer.sendMessage(userName, ResponseBo.ok(originalFilename + " 上传成功！"));
		} catch (IndexOutOfBoundsException e) {
			redisCacheService.deleteBypPttern("TrainInfoService*");
			WebSocketServer.sendMessage(userName, ResponseBo.error(originalFilename + " 数据格式错误！"));
			throw new Exception(e);
		} catch (NumberFormatException e) {
			redisCacheService.deleteBypPttern("TrainInfoService*");
			WebSocketServer.sendMessage(userName, ResponseBo.error(originalFilename + " 数据格式错误！"));
			throw new Exception(e);
		} catch (ExcelFormatException e) {
			redisCacheService.deleteBypPttern("TrainInfoService*");
			WebSocketServer.sendMessage(userName, ResponseBo.error(originalFilename + " 数据格式错误！"));
			throw new Exception(e);
		} catch (ResourceAccessException e) {
			redisCacheService.deleteBypPttern("TrainInfoService*");
			WebSocketServer.sendMessage(userName, ResponseBo.error(originalFilename + " 数据上传失败，算法服务未启动！"));
			throw new Exception(e);
		} catch (IllegalArgumentException e) {
			redisCacheService.deleteBypPttern("TrainInfoService*");
			WebSocketServer.sendMessage(userName, ResponseBo.error(originalFilename + " 数据格式错误！"));
			throw new Exception(e);
		} catch (Exception e) {
			log.error("数据导入失败", e);
			redisCacheService.deleteBypPttern("TrainInfoService*");
			WebSocketServer.sendMessage(userName, ResponseBo.error(originalFilename + " 数据上传失败，请检查数据内容！"));
			throw new Exception(e);
		} finally {
			deleteFile(realPath, originalFilename);
			logService.saveUploadFileLog(originalFilename, userName, ip);
		}
	}

	private void deleteFile(String realPath, String originalFilename) {
		File file = new File(realPath + File.separator + originalFilename);
		if (file.exists()) {
			file.delete();
		}
	}

	private void importFile(String filePath) throws Exception {
		// 获取温度信息,excel数据和机车信息
		MultisetObjectData multisetObjectData = excelFileHandler.initialDatas(filePath);
		List<InitialData> initialdataList = multisetObjectData.getInitialdatas();
		if (initialdataList.size() == 0) {
			throw new ExcelFormatException("数据格式错误！");
		}
		TrainInfo trainInfor = multisetObjectData.getTrainInfor();
		Integer trainId = trainInfoService.findTrainIdByTypeAndNum(trainInfor.getTrainNum(), trainInfor.getTrainType());
		createTable(initialdataList, trainId);
		// 插入实时数据
		int save = save(initialdataList, trainId);
		if (save != 0) {
			// 测试python接口数据
			DataToPythonUtils pythonUtils = new DataToPythonUtils();
			String objectToPythonJson = pythonUtils.objectToPythonJson(multisetObjectData.getExcelDataList());
			exceptionDataService.saveData(objectToPythonJson, pythonUtils.getAcquisitionTime(), pythonUtils.getPrimaryKey(), trainId);
		}

	}

	@Override
	public int save(List<InitialData> list, Integer trainId) {
		Integer addCount = 0;
		if (list.size() > 100) {
			if (list.size() > 800) {
				int multData = list.size() / 800;
				for (int i = 1; i <= multData; i++) {
					List<InitialData> subListInitial = list.subList(800 * (i - 1), 800 * (i));
					addCount += initialDataMapper.batInitialAdd(subListInitial, trainId);
				}
				List<InitialData> subListInitial = list.subList(800 * multData, list.size());
				addCount += initialDataMapper.batInitialAdd(subListInitial, trainId);
			} else {
				addCount += initialDataMapper.batInitialAdd(list, trainId);
			}
		}
		return addCount;
	}

	@Override
	public Map<String, Object> listLastMonthExceptionData(QueryRequest request, String trainType, String trainNum) throws Exception {
		List<TrainInfo> trainInfoList = trainInfoService.findList(trainType, trainNum);
		List<HomeVO> dataResponses = new ArrayList<>();
		int pageNum = request.getPageNum();
		int pageSize = request.getPageSize();
		for (int i = (pageNum - 1) * pageSize; i < (pageNum - 1) * pageSize + pageSize; i++) {
			if (i >= trainInfoList.size()) {
				break;
			}
			TrainInfo trainInfo = trainInfoList.get(i);
			String lastestTime = redisCacheService.getLastestTime(trainInfo.getId());
			String lastestExceptionTime = redisCacheService.getLastestExceptionTime(trainInfo.getId());
			if (lastestTime != null) {
				HomeVO homeVO = new HomeVO();
				homeVO.setTrainType(trainInfo.getTrainType());
				homeVO.setTrainNum(trainInfo.getTrainNum());
				homeVO.setEndTime(lastestTime);
				homeVO.setLastExceptionDate(lastestExceptionTime);
				homeVO.setExceptionCount(redisCacheService.getLastMonthExceptionCount(trainInfo.getId(), lastestTime));
				dataResponses.add(homeVO);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("rows", dataResponses);
		map.put("total", trainInfoList.size());
		return map;
	}

	@Override
	@Transactional
	public void dropTable(Integer trainId) {
		manageService.dropTable("t_initial_data_" + trainId);
	}

	@Override
	public synchronized void createTable(List<InitialData> initialdatas, Integer trainId) {
		int existInitialDataTable = manageService.existInitialDataTable(trainId);
		if (existInitialDataTable == 0) {
			// 如果表不存在，创建表
			manageService.createInitialDataTable(trainId, partitions());
			manageService.createExceptionDataTable(trainId, partitions());
			redisCacheService.deleteBypPttern("ManageService*");
		} else {
			Date acquisitionTime = initialdatas.get(initialdatas.size() - 1).getAcquisitionTime();
			// 如果表存在，查看分区是否够用，不够用添加分区
			addPartition(trainId, acquisitionTime);
		}
	}

	private void addPartition(Integer trainId, Date acquisitionTime) {
		String partition = manageService.lastPartition("t_initial_data_" + trainId);
		if (partition.startsWith("p")) {
			partition = partition.substring(1);
		}
		// 最后一个分区时间
		final LocalDateTime lastPartitionDate = LocalDateTime.parse(partition + "000000", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		// 插入数据的最大时间
		LocalDateTime maxInsertDate = DateUtil.date2LocalDateTime(acquisitionTime);
		final Duration duration = Duration.between(lastPartitionDate, maxInsertDate);
		final long days = duration.toDays();
		if (days >= 0) {
			manageService.addPartitions("t_initial_data_" + trainId, partitions(lastPartitionDate, days));
			manageService.addPartitions("t_exception_data_" + trainId, partitions(lastPartitionDate, days));
		}
	}

	/**
	 * 获取下月1号到前24个月1号的日期
	 * 
	 * @return
	 */
	private List<String> partitions() {
		final List<String> partitions = new ArrayList<>();
		final LocalDateTime date = LocalDateTime.now();
		LocalDateTime nextMonth = date.plusMonths(1);
		for (int i = 24; i >= 0; i--) {
			LocalDateTime firstDayOfNextMouth = nextMonth.plusMonths(-i).with(TemporalAdjusters.firstDayOfMonth());
			final String time = firstDayOfNextMouth.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			partitions.add(time);
		}
		return partitions;
	}

	/**
	 * @return
	 */
	private List<String> partitions(LocalDateTime lastPartitionDate, long days) {
		final List<String> partitions = new ArrayList<>();
		for (int i = 0; i <= days; i++) {
			LocalDateTime firstDayOfNextMouth = lastPartitionDate.plusMonths(i + 1).with(TemporalAdjusters.firstDayOfMonth());
			final String time = firstDayOfNextMouth.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			partitions.add(time);
		}
		return partitions;
	}

}
