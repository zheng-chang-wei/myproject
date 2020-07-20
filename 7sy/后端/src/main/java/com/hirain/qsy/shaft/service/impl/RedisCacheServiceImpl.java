package com.hirain.qsy.shaft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.qsy.shaft.common.model.CoreConstant;
import com.hirain.qsy.shaft.common.util.DateUtil;
import com.hirain.qsy.shaft.common.util.RedisService;
import com.hirain.qsy.shaft.model.AttributeMappingConfigurationData;
import com.hirain.qsy.shaft.model.ExceptionData;
import com.hirain.qsy.shaft.model.StatisticsChartDataRow;
import com.hirain.qsy.shaft.service.RedisCacheService;

@Service
public class RedisCacheServiceImpl implements RedisCacheService {

	@Autowired
	private RedisService redisService;

	@Override
	public void cache(List<ExceptionData> exceptionDataList, Integer trainId) throws Exception {
		Map<String, String> map = AttributeMappingConfigurationData.exceptionData;
		for (ExceptionData exceptionData : exceptionDataList) {
			Date acquisitionTime = exceptionData.getAcquisitionTime();
			String stringDay = DateUtil.date2StringDay(acquisitionTime);
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String exceptionDataString = BeanUtils.getProperty(exceptionData, entry.getKey());
				if (isException(exceptionDataString)) {
					saveExceptionCount(trainId, entry.getValue(), stringDay);
					saveLastestExceptionTime(acquisitionTime, trainId);
				}
			}
		}
		ExceptionData exceptionData = exceptionDataList.get(exceptionDataList.size() - 1);
		saveLastestTime(exceptionData.getAcquisitionTime(), trainId);

	}

	@Override
	public String getLastestTime(Integer trainId) {
		return redisService.getString("lastestTime_" + trainId);
	}

	@Override
	public String getLastestExceptionTime(Integer trainId) {
		return redisService.getString("lastestExceptionTime_" + trainId);
	}

	@Override
	public void deleteBypPttern(String pattern) {
		redisService.deleteBypPttern(pattern);

	}

	@Override
	public Integer getLastMonthExceptionCount(Integer trainId, String lastestTime) {
		List<String> lastMonthDays = DateUtil.getLastMonthDays(lastestTime);
		Integer count = getExceptionCount(trainId, lastMonthDays);
		return count;
	}

	@Override
	public Integer getExceptionCountByDate(Integer trainId, String startTime, String endTime) {
		List<String> dayList = DateUtil.getDaysBetweenDate(startTime, endTime);
		Integer count = getExceptionCount(trainId, dayList);
		return count;
	}

	@Override
	public int getCurrentWeekExeptionCount(List<Integer> trainIds, String monday) {
		List<String> dayList = DateUtil.getDaysCurrentWeek(monday);
		Integer count = 0;
		for (Integer trainId : trainIds) {
			count += getExceptionCount(trainId, dayList);
		}
		return count;
	}

	private Integer getExceptionCount(Integer trainId, List<String> dayList) {
		Integer count = 0;
		for (String date : dayList) {
			String pattern = trainId + "_*_" + date;
			Set<String> keys = redisService.keys(pattern);
			for (String key : keys) {
				count += redisService.getInteger(key);
			}
		}
		return count;
	}

	@Override
	public void deleteCache(Integer trainId) {
		redisService.deleteBypPttern(trainId + "*");
		redisService.deleteBypPttern("*_" + trainId);
	}

	@Override
	public void deleteCache(Integer trainId, String deadline) {
		List<String> list = new ArrayList<>();
		String pattern = trainId + "_*";
		Set<String> keys = redisService.keys(pattern);
		for (String key : keys) {
			String date = key.split("_")[2];
			if (DateUtil.compareDate(deadline, date) != -1) {
				list.add(key);
			}
		}
		redisService.delete(list);
	}

	@Override
	public Set<String> getKeysPyPttern(String pattern) {
		return redisService.keys(pattern);
	}

	@Override
	public Integer getValueByKey(String key) {
		return redisService.getInteger(key);
	}

	@Override
	public List<StatisticsChartDataRow> getAxlesExceptionCount(Integer trainId, String startTime, String endTime) {
		List<String> dayList = DateUtil.getDaysBetweenDate(startTime, endTime);
		List<StatisticsChartDataRow> rows = new ArrayList<>();

		for (int i = 1; i <= CoreConstant.axleCount; i++) {
			List<Integer> list = new LinkedList<>();
			for (int j = 1; j <= CoreConstant.pointCount; j++) {
				Integer count = 0;
				for (String date : dayList) {
					String pattern = trainId + "_" + i + j + "_" + date;
					Set<String> keys = redisService.keys(pattern);
					for (String key : keys) {
						count += redisService.getInteger(key);
					}
				}
				list.add(count);
			}
			rows.add(new StatisticsChartDataRow((i) + "号轴", list));
		}
		return rows;
	}

	/**
	 * 保存故障发生次数
	 * 
	 * @param stringDay
	 * @param trainId
	 * @param axleAndPoint
	 */
	private void saveExceptionCount(Integer trainId, String axleAndPoint, String stringDay) {
		String key = getRedisKey(trainId, axleAndPoint, stringDay);
		redisService.incrBy(key, 1);
		// String value = redisService.get(key);
		// if (value == null) {
		// redisService.set(key, "1");
		// } else {
		// redisService.set(key, String.valueOf(Integer.valueOf(value) + 1));
		// }
	}

	/**
	 * 保存最晚故障发生时间
	 * 
	 * @param acquisitionTime
	 * @param trainId
	 */
	private void saveLastestExceptionTime(Date acquisitionTime, Integer trainId) {
		String key = "lastestExceptionTime_" + trainId;
		String maxTime = redisService.getString(key);
		if (DateUtil.compareDate(acquisitionTime, DateUtil.string2Date_Second(maxTime)) == 1) {
			redisService.set(key, DateUtil.date2StringSecond(acquisitionTime));
		}
	}

	/**
	 * 保存最晚一条数据的时间
	 * 
	 * @param acquisitionTime
	 * @param trainId
	 */
	private void saveLastestTime(Date acquisitionTime, Integer trainId) {
		String key = "lastestTime_" + trainId;
		String maxTime = redisService.getString(key);
		if (DateUtil.compareDate(acquisitionTime, DateUtil.string2Date_Second(maxTime)) == 1) {
			redisService.set(key, DateUtil.date2StringSecond(acquisitionTime));
		}
	}

	/**
	 * @param trainId
	 * @param axleAndPoint
	 *            轴号和测点号，如12表示1号轴2号测点
	 * @param stringDay
	 *            日期，精确到天
	 * @return
	 */
	private String getRedisKey(Integer trainId, String axleAndPoint, String stringDay) {
		String key = trainId + "_" + axleAndPoint + "_" + stringDay;
		return key;
	}

	/**
	 * 判断是否存在异常
	 */
	private boolean isException(String exception) {
		String data[] = exception.split(",");
		if (data.length >= 4) {
			// 1表示有异常
			if (data[3].toString().equals("1")) {
				return true;
			}
		}
		return false;
	}
}
