package com.hirain.dome;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.qsy.shaft.Application;
import com.hirain.qsy.shaft.common.util.DateUtil;
import com.hirain.qsy.shaft.dao.InitialDataMapper;
import com.hirain.qsy.shaft.dao.TrainInfoMapper;
import com.hirain.qsy.shaft.service.ExceptionDataService;
import com.hirain.qsy.shaft.service.RedisCacheService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisCacheServiceTest {

	@Autowired
	RedisCacheService redisCacheService;

	@Autowired
	ExceptionDataService exceptionDataService;

	@Autowired
	InitialDataMapper initialDataMapper;

	@Autowired
	TrainInfoMapper trainInfoMapper;

	private Integer trainId = 522;

	//
	private Integer[] trainIds = { trainId };

	// @Test
	public void getLastestTimeTest() {
		String lastestTime2 = redisCacheService.getLastestTime(trainId);
		String maxTime = initialDataMapper.findMaxTime(trainId);
		assertEquals(maxTime, lastestTime2);
	}

	// @Test
	public void getLastestExceptionTimeTest() {
		redisCacheService.getLastestExceptionTime(trainId);
	}

	@Test
	public void deleteCacheTest() {
		// redisCacheService.deleteCache(trainIds, "2018-04-04 06:24:37");
		// redisCacheService.deleteBypPttern("TrainInfoService*");
		redisCacheService.deleteBypPttern("*session*");
	}

	// @Test
	public void getKeysPyPtternTest() {
		Integer valueByKey = redisCacheService.getValueByKey("617_21_2019-02-21");
		Integer valueByKey2 = redisCacheService.getValueByKey("617_52_2019-02-21");
		System.out.println(valueByKey + valueByKey2);
		// Set<String> keys = redisCacheService.getKeysPyPttern(trainId + "_*");
		// Set<String> treeSet = new TreeSet<>();
		// for (String key : keys) {
		// treeSet.add(key);
		// }
		// for (String key : treeSet) {
		// System.out.println(key);
		// }
	}

	// @Test
	// public void getLastMonthExceptionCountTest() throws Exception {
	// List<TrainInfo> allTrainInfos = trainInfoMapper.selectAll();
	// for (TrainInfo trainInfo : allTrainInfos) {
	// Integer trainId = trainInfo.getId();
	// String lastestExceptionTime = redisCacheService.getLastestExceptionTime(trainId);
	// String maxTime = initialDataMapper.findMaxTime(trainId);
	// Integer count = redisCacheService.getLastMonthExceptionCount(trainId, maxTime);
	// if (maxTime != null) {
	// DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	// LocalDateTime parse = LocalDateTime.parse(maxTime, dateTimeFormatter);
	// // 上个月时间
	// LocalDateTime lastMonth = parse.minusMonths(1);
	// List<ExceptionData> listExceptionData = exceptionDataService.listExceptionData(trainId, dateTimeFormatter.format(lastMonth), null);
	// if (listExceptionData != null && listExceptionData.size() > 0) {
	// // 获取exceptionData的映射关系
	// Map<String, String> map = AttributeMappingConfigurationData.exceptionData;
	//
	// Integer exceptionCount = 0;
	// Date lastexceptionDate = null;
	// for (ExceptionData data : listExceptionData) {
	// for (String key : map.keySet()) {
	// String exceptionDataString = BeanUtils.getProperty((ExceptionData) data, key);
	// if (exceptionDataService.isException(exceptionDataString)) {
	// lastexceptionDate = (data.getAcquisitionTime());
	// exceptionCount++;
	// }
	// }
	// }
	// System.out.println(trainId);
	// assertEquals(count, exceptionCount);
	// if (lastestExceptionTime != null && lastexceptionDate != null) {
	// assertEquals(lastestExceptionTime, DateUtil.date2StringSecond(lastexceptionDate));
	// }
	// }
	// }
	// }
	// }

	// @Test
	public void getDaysCurrentWeekTest() {
		List<String> week = DateUtil.getDaysCurrentWeek("2019-10-22");
		for (String string : week) {
			System.out.println(string);
		}
	}
}
