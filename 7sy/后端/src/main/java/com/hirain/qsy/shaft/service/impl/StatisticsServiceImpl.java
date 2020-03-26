package com.hirain.qsy.shaft.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.DetailDataRequest;
import com.hirain.qsy.shaft.common.model.StatisticsRequest;
import com.hirain.qsy.shaft.common.util.DateUtil;
import com.hirain.qsy.shaft.common.util.ReadExcelUtil;
import com.hirain.qsy.shaft.common.util.StatisticsUtil;
import com.hirain.qsy.shaft.model.AttributeMappingConfigurationData;
import com.hirain.qsy.shaft.model.ExceptionData;
import com.hirain.qsy.shaft.model.StatisticsChartDataRow;
import com.hirain.qsy.shaft.model.StatisticsExceptionTableDatas;
import com.hirain.qsy.shaft.model.ThresholdData;
import com.hirain.qsy.shaft.model.TrainInfo;
import com.hirain.qsy.shaft.service.ExceptionDataService;
import com.hirain.qsy.shaft.service.RedisCacheService;
import com.hirain.qsy.shaft.service.StatisticsService;
import com.hirain.qsy.shaft.service.TrainInfoService;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private ExceptionDataService exceptionDataService;

	@Autowired
	private TrainInfoService trainInfoService;

	@Autowired
	private RedisCacheService redisCacheService;

	@Override
	public List<StatisticsChartDataRow> findStatisticsResultByCar(StatisticsRequest statisticsRequest) throws Exception {
		List<StatisticsChartDataRow> statisticsByCarRows = new ArrayList<>();
		switch (statisticsRequest.getDimensionality()) {
		// 按车号统计，按车号统计只关注时间
		case 0:
			List<TrainInfo> trainInfos = trainInfoService.selectAll();
			Collections.sort(trainInfos, new Comparator<TrainInfo>() {

				@Override
				public int compare(TrainInfo o1, TrainInfo o2) {
					return Integer.valueOf(o1.getTrainNum()) - Integer.valueOf(o2.getTrainNum());
				}
			});
			for (TrainInfo trainInfo : trainInfos) {
				Integer count = redisCacheService.getExceptionCountByDate(trainInfo.getId(), statisticsRequest.getStartTime(),
						statisticsRequest.getEndTime());
				List<Integer> list = new ArrayList<>();
				list.add(count);
				statisticsByCarRows.add(new StatisticsChartDataRow(trainInfo.getTrainType() + ":" + trainInfo.getTrainNum(), list));
			}
			break;
		// 按时间统计,按时间统计只关注车号
		case 1:
			List<Integer> trainIds = trainInfoService.findIdByTrainNum(statisticsRequest.getNum());
			statisticsByTime(statisticsByCarRows, trainIds, statisticsRequest.getGranularityTime());
			break;
		default:
			break;
		}
		return statisticsByCarRows;
	}

	/**
	 * 按时间统计
	 * 
	 * @param statisticsByCarRows
	 * @param trainIds
	 * @param granularityTime
	 */
	private void statisticsByTime(List<StatisticsChartDataRow> statisticsByCarRows, List<Integer> trainIds, Integer granularityTime) {
		// 匹配trainId的所有key
		Set<String> daySet = getDaySet(trainIds);
		switch (granularityTime) {
		case 0:// 按天统计
			for (String day : daySet) {
				List<Integer> list = getCount(trainIds, day, granularityTime);
				statisticsByCarRows.add(new StatisticsChartDataRow(day, list));
			}
			break;
		case 1:// 按周统计
			Set<String> weekSet = getMondayByDays(daySet);
			for (String monday : weekSet) {
				int count = redisCacheService.getCurrentWeekExeptionCount(trainIds, monday);
				List<Integer> list = new ArrayList<>();
				list.add(count);
				statisticsByCarRows.add(new StatisticsChartDataRow(monday, list));
			}
			break;
		case 2:// 按月统计
			Set<String> monthSet = getSameMonthFromDays(daySet);
			for (String month : monthSet) {
				List<Integer> list = getCount(trainIds, month, granularityTime);
				statisticsByCarRows.add(new StatisticsChartDataRow(month, list));
			}
			break;
		case 3:// 按年统计
			Set<String> yearSet = getSameYearFromDays(daySet);
			for (String year : yearSet) {
				List<Integer> list = getCount(trainIds, year, granularityTime);
				statisticsByCarRows.add(new StatisticsChartDataRow(year, list));
			}
			break;
		default:
			break;
		}
	}

	private List<Integer> getCount(List<Integer> trainIds, String date, Integer granularityTime) {
		List<Integer> list = new ArrayList<>();
		Integer count = 0;
		// 匹配trainId和Day的所有key
		Set<String> ptternTrainIdAndMonthKeys = new HashSet<>();
		for (Integer trainId : trainIds) {
			String pattern = "";
			switch (granularityTime) {
			case 0:
				pattern = trainId + "_*_" + date;
				break;
			case 2:
				pattern = trainId + "_*_" + date + "-*";
				break;
			case 3:
				pattern = trainId + "_*_" + date + "-*";
				break;
			default:
				break;
			}
			ptternTrainIdAndMonthKeys.addAll(redisCacheService.getKeysPyPttern(pattern));
		}
		for (String key : ptternTrainIdAndMonthKeys) {
			count += redisCacheService.getValueByKey(key);
		}
		list.add(count);
		return list;
	}

	public Set<String> getMondayByDays(Set<String> daySet) {
		Set<String> weekSet = new TreeSet<>();
		for (String day : daySet) {
			String monday = DateUtil.getFirstDayOfWeek(day);
			weekSet.add(monday);
		}
		return weekSet;
	}

	public Set<String> getDaySet(List<Integer> trainIds) {
		Set<String> ptternTrainIdKeys = new HashSet<>();
		for (Integer trainId : trainIds) {
			ptternTrainIdKeys.addAll(redisCacheService.getKeysPyPttern(trainId + "_*"));
		}
		Set<String> daySet = new TreeSet<>();
		for (String key : ptternTrainIdKeys) {
			String day = getDay(key);
			daySet.add(day);
		}
		return daySet;
	}

	public Set<String> getSameMonthFromDays(Set<String> daySet) {
		Set<String> monthsSet = new TreeSet<>();
		for (String day : daySet) {
			int index = day.lastIndexOf("-");
			monthsSet.add(day.substring(0, index));
		}
		return monthsSet;
	}

	private Set<String> getSameYearFromDays(Set<String> daySet) {
		Set<String> yearsSet = new TreeSet<>();
		for (String day : daySet) {
			int index = day.indexOf("-");
			yearsSet.add(day.substring(0, index));
		}
		return yearsSet;
	}

	private String getDay(String key) {
		return key.split("_")[2];
	}

	@Override
	public List<StatisticsChartDataRow> findStatisticsResultByPoint(DataRequest dataRequest) throws Exception {
		Integer trainId = trainInfoService.findTrainInfoByTypeAndNum(dataRequest.getTrainNum(), dataRequest.getTrainType());
		return redisCacheService.getAxlesExceptionCount(trainId, dataRequest.getStartTime(), dataRequest.getEndTime());
	}

	@Override
	public List<StatisticsExceptionTableDatas> findTableData(DataRequest dataRequest, String axleNum) throws Exception {
		List<ExceptionData> exceptionDatas = exceptionDataService.listExceptionData(dataRequest.getTrainType(), dataRequest.getTrainNum(),
				dataRequest.getStartTime(), dataRequest.getEndTime());
		return getTable(exceptionDatas, axleNum);
	}

	@Override
	public List<Map<String, Object>> findDetailData(DetailDataRequest detailDataRequest) throws Exception {
		List<ExceptionData> exceptionDatas = getDetailExceptionData(detailDataRequest);
		return getDetailData(exceptionDatas, detailDataRequest);
	}

	private List<ExceptionData> getDetailExceptionData(DetailDataRequest detailDataRequest) {
		Date faultTime = detailDataRequest.getFaultTime();
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(faultTime);
		// 加8个小时，从当前时间的8个小时后开始算
		rightNow.add(Calendar.HOUR_OF_DAY, 8);
		faultTime = rightNow.getTime();
		switch (detailDataRequest.getTimeRange()) {
		case 0:
			rightNow.add(Calendar.DAY_OF_YEAR, -1);
			break;
		case 1:
			rightNow.add(Calendar.DAY_OF_YEAR, -2);
			break;
		case 2:
			rightNow.add(Calendar.DAY_OF_YEAR, -7);
			break;
		case 3:
			rightNow.add(Calendar.DAY_OF_YEAR, -14);
			break;
		default:
			break;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<ExceptionData> exceptionDatas = exceptionDataService.listExceptionDataGroupByAcquisitionTime(detailDataRequest.getTrainType(),
				detailDataRequest.getTrainNum(), dateFormat.format(rightNow.getTime()), dateFormat.format(faultTime));
		return exceptionDatas;
	}

	private List<Map<String, Object>> getDetailData(List<ExceptionData> exceptionDatas, DetailDataRequest detailDataRequest) throws Exception {
		List<ThresholdData> thresholdDatas = ReadExcelUtil.getThresholdDatas();
		List<Map<String, Object>> list = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 获取exceptionData的映射关系
		Map<String, String> exceptionDataParameterMap = AttributeMappingConfigurationData.exceptionData;
		for (ExceptionData exceptionData : exceptionDatas) {
			for (Map.Entry<String, String> entry : exceptionDataParameterMap.entrySet()) {
				String num = entry.getValue();
				if (String.valueOf(num.charAt(0)).equals(detailDataRequest.getAxleNum())
						&& String.valueOf(num.charAt(1)).equals(detailDataRequest.getPointNum())) {
					String value = BeanUtils.getProperty((ExceptionData) exceptionData, entry.getKey());
					String[] split = value.split(",");
					if (split.length == 4) {
						Map<String, Object> map = new HashMap<>();
						map.put("x", format.format(exceptionData.getAcquisitionTime()));
						// 预测值
						map.put("t1", split[1]);
						// 实测值
						map.put("t2", split[0]);
						// 残差
						map.put("t3", split[2]);
						ThresholdData thresholdData = getThresholdData(thresholdDatas, num);
						// 门限
						map.put("t4", thresholdData);
						list.add(map);
					}
				}

			}
		}
		return list;
	}

	private ThresholdData getThresholdData(List<ThresholdData> thresholdDatas, String num) {
		for (ThresholdData thresholdData : thresholdDatas) {
			if (thresholdData.getTestNum().equals(num)) {
				return thresholdData;
			}
		}
		return null;
	}

	private List<StatisticsExceptionTableDatas> getTable(List<ExceptionData> exceptionDatas, String axleNum) throws Exception {
		List<StatisticsExceptionTableDatas> rows = new ArrayList<>();
		// 获取exceptionData的映射关系
		Map<String, String> exceptionDataParameterMap = AttributeMappingConfigurationData.exceptionData;
		for (ExceptionData exceptionData : exceptionDatas) {
			for (Map.Entry<String, String> entry : exceptionDataParameterMap.entrySet()) {
				String num = entry.getValue();
				if (String.valueOf(num.charAt(0)).equals(axleNum)) {
					String value = BeanUtils.getProperty((ExceptionData) exceptionData, entry.getKey());
					if (exceptionDataService.isException(value)) {
						rows.add(new StatisticsExceptionTableDatas(axleNum, Integer.valueOf(String.valueOf(num.charAt(1))),
								exceptionData.getAcquisitionTime()));
					}
				}
			}
		}
		StatisticsUtil.sort(rows);
		return rows;
	}
}
