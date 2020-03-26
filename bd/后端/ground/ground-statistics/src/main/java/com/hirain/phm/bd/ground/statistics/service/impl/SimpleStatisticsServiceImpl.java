package com.hirain.phm.bd.ground.statistics.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.statistics.dao.SimpleStatisticsMapper;
import com.hirain.phm.bd.ground.statistics.domain.MonthStatisticsResult;
import com.hirain.phm.bd.ground.statistics.domain.TypeResult;
import com.hirain.phm.bd.ground.statistics.domain.TypeStatisticsResult;
import com.hirain.phm.bd.ground.statistics.domain.YearStatisticsResult;
import com.hirain.phm.bd.ground.statistics.param.StatisticsParm;
import com.hirain.phm.bd.ground.statistics.service.SimpleStatisticsService;
import com.hirain.phm.bd.ground.train.controller.TrainGateWay;
import com.hirain.phm.bd.ground.train.domain.Train;

@Service
public class SimpleStatisticsServiceImpl implements SimpleStatisticsService {

	@Autowired
	private TrainGateWay trainGW;

	@Autowired
	private SimpleStatisticsMapper mapper;

	@Override
	public List<String> listTrains() {
		List<Train> trains = trainGW.selectTrainsByCityAndLine("深圳", "7");
		List<String> result = new ArrayList<>();
		trains.forEach(t -> {
			if (t != null) {
				result.add(t.getTrainNo());
			}
		});
		return result;
	}

	@Override
	public List<String> getTrainId(String trainNo) {
		return mapper.getTrainIdByTrainNo(trainNo);
	}

	@Override
	public List<MonthStatisticsResult> countByMonth(StatisticsParm param) {
		List<String> ids = getTrainId(getTrainString(param));
		param.setTrains(ids.toArray(new String[] {}));
		return mapper.countByMonth(param);
	}

	private String getTrainString(StatisticsParm param) {
		List<String> trainList = Arrays.asList(param.getTrains());
		String trains = trainList.stream().collect(Collectors.joining(","));
		return trains;
	}

	@Override
	public YearStatisticsResult countByYear(StatisticsParm param) {
		List<String> ids = getTrainId(getTrainString(param));
		param.setTrains(ids.toArray(new String[] {}));
		List<MonthStatisticsResult> fullResults = new ArrayList<>();
		List<MonthStatisticsResult> monthResults = mapper.countByYear(param);
		for (int i = 1, index = 0; i <= 12; i++) {
			if (index < monthResults.size() && monthResults.get(index).getMonth() == i) {
				fullResults.add(monthResults.get(index));
				index++;
			} else {
				MonthStatisticsResult mresult = new MonthStatisticsResult();
				mresult.setYear(param.getYear());
				mresult.setMonth(i);
				mresult.setFault(0);
				mresult.setSubhealth(0);
				mresult.setTotal(0);
				fullResults.add(mresult);
			}
		}
		YearStatisticsResult result = new YearStatisticsResult();
		result.setYear(param.getYear());
		result.setResults(fullResults);
		return result;
	}

	@Override
	public TypeStatisticsResult countByFaultType(StatisticsParm param) {
		List<String> ids = getTrainId(getTrainString(param));
		param.setTrains(ids.toArray(new String[] {}));
		List<TypeResult> typeResults = mapper.countByFaultType(param);
		TypeStatisticsResult result = new TypeStatisticsResult();
		result.setTopType("故障");
		result.setTypes(typeResults);
		return result;
	}

	@Override
	public TypeStatisticsResult countBySubhealthType(StatisticsParm param) {
		List<String> ids = getTrainId(getTrainString(param));
		param.setTrains(ids.toArray(new String[] {}));
		List<TypeResult> typeResults = mapper.countBySubhealthType(param);
		TypeStatisticsResult result = new TypeStatisticsResult();
		result.setTopType("亚健康");
		result.setTypes(typeResults);
		return result;
	}

	@Override
	public TypeStatisticsResult countByTopType(StatisticsParm param) {
		List<String> ids = getTrainId(getTrainString(param));
		param.setTrains(ids.toArray(new String[] {}));
		Double fault = mapper.countFault(param);
		System.out.println("fault:" + fault);
		Double subhealth = mapper.countSubhealth(param);
		System.out.println("subhealth:" + subhealth);
		TypeStatisticsResult topResult = new TypeStatisticsResult();
		List<TypeResult> results = new ArrayList<>();
		if (fault > 0) {
			TypeResult result = new TypeResult();
			result.setType("故障");
			result.setPercent(fault.intValue());
			results.add(result);
		}
		if (subhealth > 0) {
			TypeResult result = new TypeResult();
			result.setType("亚健康");
			result.setPercent(subhealth.intValue());
			results.add(result);
		}
		topResult.setTypes(results);
		topResult.setTopType("总体分布");
		return topResult;
	}

}
