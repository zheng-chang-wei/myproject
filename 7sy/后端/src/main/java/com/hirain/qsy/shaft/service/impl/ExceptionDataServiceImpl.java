package com.hirain.qsy.shaft.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.qsy.shaft.common.util.ReadExcelUtil;
import com.hirain.qsy.shaft.dao.ExceptionDataMapper;
import com.hirain.qsy.shaft.dao.ManageMapper;
import com.hirain.qsy.shaft.model.AttributeMappingConfigurationData;
import com.hirain.qsy.shaft.model.AxleExceptionData;
import com.hirain.qsy.shaft.model.AxleExceptionStateData;
import com.hirain.qsy.shaft.model.ExceptionData;
import com.hirain.qsy.shaft.model.ThresholdData;
import com.hirain.qsy.shaft.service.ExceptionDataService;
import com.hirain.qsy.shaft.service.RedisCacheService;
import com.hirain.qsy.shaft.service.SendDataToPythonService;
import com.hirain.qsy.shaft.service.TrainInfoService;

@Service("exceptionDataService")
public class ExceptionDataServiceImpl extends BaseService<ExceptionData> implements ExceptionDataService {

	@Autowired
	private ExceptionDataMapper exceptionDataMapper;

	@Autowired
	TrainInfoService trainInfoService;

	@Autowired
	ManageMapper manageMapper;

	@Autowired
	private SendDataToPythonService sendDataToPythonService;

	@Autowired
	private RedisCacheService redisCacheService;

	public void saveData(String params, List<Date> acquisitionTimeList, List<String> primaryKeyList, Integer trainId) throws Exception {
		List<ExceptionData> exceptionDataList = sendDataToPythonService.postData(params, acquisitionTimeList, primaryKeyList);
		redisCacheService.cache(exceptionDataList, trainId);
		saveList(exceptionDataList, trainId);
	}

	/**
	 * 批量插入
	 */
	// @Async
	@Override
	public void saveList(List<ExceptionData> list, Integer trainId) {
		if (list != null) {
			if (list.size() > 800) {
				int multData = list.size() / 800;
				for (int i = 1; i <= multData; i++) {
					List<ExceptionData> subListInitial = list.subList(800 * (i - 1), 800 * (i));
					exceptionDataMapper.batExceptionlAdd(subListInitial, trainId);
				}
				List<ExceptionData> subListInitial = list.subList(800 * multData, list.size());
				exceptionDataMapper.batExceptionlAdd(subListInitial, trainId);
			} else {
				exceptionDataMapper.batExceptionlAdd(list, trainId);
			}
		}
	}

	/**
	 * 返回轴的异常状态
	 */
	@Override
	public Set<AxleExceptionStateData> analyseExceptionData(String trainType, String trainNum, String startDate, String endDate) throws Exception {
		List<ExceptionData> list = listExceptionData(trainType, trainNum, startDate, endDate);

		// 存储轴异常状态信息
		TreeSet<AxleExceptionStateData> set = new TreeSet<>();
		if (list.size() > 0) {
			// 获取exceptionData的映射关系
			Map<String, String> map = AttributeMappingConfigurationData.exceptionData;

			// 首先将所有测点状态设为正常
			for (int i = 0; i < 36; i++) {
				for (Map.Entry<String, String> entry : map.entrySet()) {
					set.add(new AxleExceptionStateData(entry.getValue(), "0"));
				}
			}
			for (ExceptionData data : list) {
				for (Map.Entry<String, String> entry : map.entrySet()) {
					String exceptionDataString = BeanUtils.getProperty((ExceptionData) data, entry.getKey());
					if (isException(exceptionDataString)) {
						// set.add(new axleExceptionData(map))
						// 将该轴设为异常
						String value = entry.getValue();
						set.remove(new AxleExceptionStateData(value, "0"));
						set.add(new AxleExceptionStateData(value, "1"));
					}
				}
			}
		}
		return set;
	}

	@Override
	public List<ExceptionData> listExceptionData(String trainType, String trainNum, String startDate, String endDate) {
		Integer trainId = trainInfoService.findTrainInfoByTypeAndNum(trainNum, trainType);
		// 查询数据
		List<ExceptionData> list = exceptionDataMapper.listExceptionData(trainId, startDate, endDate);
		return list;
	}

	@Override
	public List<ExceptionData> listExceptionDataGroupByAcquisitionTime(String trainType, String trainNum, String startDate, String endDate) {
		Integer trainId = trainInfoService.findTrainInfoByTypeAndNum(trainNum, trainType);
		// 查询数据
		List<ExceptionData> list = exceptionDataMapper.listExceptionDataGroupByAcquisitionTime(trainId, startDate, endDate);
		return list;
	}

	/**
	 * 判断是否存在异常
	 */
	@Override
	public boolean isException(String exception) {
		String data[] = exception.split(",");
		if (data.length >= 4) {
			// 1表示有异常
			if (data[3].toString().equals("1")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 通过轴名称获取轴的信息
	 */
	@Override
	public List<AxleExceptionData> findAxleExceptionData(String trainType, String trainNum, String startDate, String endDate, String axleName)
			throws Exception {
		List<ThresholdData> thresholdDatas = ReadExcelUtil.getThresholdDatas();

		// 查询信息
		List<ExceptionData> list = listExceptionDataGroupByAcquisitionTime(trainType, trainNum, startDate, endDate);

		// 存储轴异常数据
		List<AxleExceptionData> axleListDatas = new ArrayList<AxleExceptionData>();

		ThresholdData threaData = null;
		for (ThresholdData data : thresholdDatas) {
			if (data.getTestNum().toString().equals(axleName.toString())) {
				threaData = data;
			}
		}
		// 转化时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
		if (list.size() > 0) {
			for (ExceptionData data : list) {
				AxleExceptionData axleData = new AxleExceptionData();
				// 通过属性名称获取属性值
				String exceptionDataString = BeanUtils.getProperty((ExceptionData) data, "resultAxle" + axleName);

				axleData.setAcquisitionTime(sdf.parse((BeanUtils.getProperty((ExceptionData) data, "acquisitionTime"))));
				String exceptionDataStringSplit[] = exceptionDataString.split(",");
				axleData.setActualValue(exceptionDataStringSplit[0]);
				axleData.setPredicteValue(exceptionDataStringSplit[1]);
				axleData.setResiduals(exceptionDataStringSplit[2]);
				axleData.setThreshold(threaData.getParameter1());
				axleData.setThreshold1(threaData.getParameter2());
				axleData.setThreshold2(threaData.getParameter3());
				axleListDatas.add(axleData);
			}
		}
		return axleListDatas;
	}

	@Override
	@Transactional
	public void deleteByTrainNumAndTime(Integer trainId, String deadline) {
		exceptionDataMapper.deleteByTrianIdAndAcquisitionTime(trainId, deadline);
	}

	@Override
	@Transactional
	public void dropTable(Integer trainId) {
		manageMapper.dropTable("t_exception_data_" + trainId);
	}
}
