package com.hirain.phm.bd.ground;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.statistics.domain.MonthStatisticsResult;
import com.hirain.phm.bd.ground.statistics.domain.StatisticsParam;
import com.hirain.phm.bd.ground.statistics.domain.TypeStatisticsResult;
import com.hirain.phm.bd.ground.statistics.domain.YearStatisticsResult;
import com.hirain.phm.bd.ground.statistics.service.SimpleStatisticsService;

@ActiveProfiles("test")
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class SimpleServiceTest {

	@Autowired
	private SimpleStatisticsService service;

	@Test
	public void testListTrainsOfShenzhen() {
		List<String> trains = service.listTrains();
		assertNotNull(trains);
		assertFalse(trains.isEmpty());
		assertEquals("717", trains.get(0));
	}

	@Test
	public void testTrainIdByTrainNo() {
		List<String> trains = service.getTrainId("717");
		System.out.println(trains.stream().collect(Collectors.joining(",")));
	}

	@Test
	public void testCountByMonth() {
		StatisticsParam param = new StatisticsParam();
		param.setTrains(new String[] { "717", "718" });
		List<MonthStatisticsResult> results = service.countByMonth(param);
		results.forEach(r -> System.out.println(r));
	}

	@Test
	public void testCountByYear() {
		StatisticsParam param = new StatisticsParam();
		param.setTrains(new String[] { "717" });
		param.setYear(2019);
		YearStatisticsResult result = service.countByYear(param);
		System.out.println(result);
		result.getResults().forEach(r -> System.out.println(r));
	}

	@Test
	public void testCountByFaultType() {
		StatisticsParam param = new StatisticsParam();
		param.setTrains(new String[] { "717" });
		param.setYear(2019);
		param.setStart_month(1);
		param.setEnd_month(9);
		TypeStatisticsResult result = service.countByFaultType(param);
		System.out.println(result.getTopType());
		result.getTypes().forEach(System.out::println);
	}

	@Test
	public void testCountBySubHealthType() {
		StatisticsParam param = new StatisticsParam();
		param.setTrains(new String[] { "717" });
		param.setYear(2019);
		param.setStart_month(1);
		param.setEnd_month(9);
		TypeStatisticsResult result = service.countBySubhealthType(param);
		System.out.println(result.getTopType());
		result.getTypes().forEach(System.out::println);
	}

	@Test
	public void testCountByTopType() {
		StatisticsParam param = new StatisticsParam();
		param.setTrains(new String[] { "717" });
		param.setStart_month(1);
		param.setEnd_month(9);
		param.setYear(2019);
		TypeStatisticsResult result = service.countByTopType(param);
		System.out.println(result.getTopType());
		result.getTypes().forEach(System.out::println);
	}

}
