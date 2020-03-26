package com.hirain.phm.bd.ground.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.bd.ground.GroundWebApplication;
import com.hirain.phm.bd.ground.common.model.ResultBean;
import com.hirain.phm.bd.ground.statistics.domain.MonthStatisticsResult;
import com.hirain.phm.bd.ground.statistics.domain.TypeStatisticsResult;
import com.hirain.phm.bd.ground.statistics.domain.YearStatisticsResult;
import com.hirain.phm.bd.ground.statistics.param.StatisticsParm;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GroundWebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FaultStatisticsControllerTest {

	@Autowired
	public WebApplicationContext context;

	public MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void test() throws Exception {
		MvcResult result = mockMvc.perform(get("/statistics/countFaultByProjectName").param("projectName", "上海地铁01号线"))

				.andExpect(status().isOk()).andDo(print()).andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println(content);
	}

	@Test
	public void listTrains() throws Exception {
		MvcResult result = mockMvc.perform(get("/statistics/trains")).andExpect(status().isOk())

				.andDo(print()).andReturn();
		String content = result.getResponse().getContentAsString();
		ResultBean<?> resultBean = JSONObject.parseObject(content, ResultBean.class);
		List<String> trains = JSONObject.parseArray(resultBean.getData().toString(), String.class);
		trains.forEach(System.out::println);
	}

	@Test
	public void testByMonth() throws Exception {
		StatisticsParm param = new StatisticsParm();
		param.setTrains(new String[] { "341", "1" });
		param.setStart_month(0);
		param.setEnd_month(0);
		param.setYear(0);
		ResultBean<?> resultBean = perform("/statistics/month", param);
		List<MonthStatisticsResult> monthResults = JSONObject.parseArray(resultBean.getData().toString(), MonthStatisticsResult.class);
		monthResults.forEach(System.out::println);
	}

	@Test
	public void testByYear() throws Exception {
		StatisticsParm param = new StatisticsParm();
		param.setStart_month(1);
		param.setEnd_month(12);
		param.setYear(2019);
		ResultBean<?> resultBean = perform("/statistics/year", param);
		YearStatisticsResult result = JSONObject.parseObject(resultBean.getData().toString(), YearStatisticsResult.class);
		System.out.println(result.getYear());
		result.getResults().forEach(System.out::println);
	}

	@Test
	public void testByFaultType() throws Exception {
		StatisticsParm param = new StatisticsParm();
		param.setStart_month(1);
		param.setEnd_month(12);
		param.setYear(2019);
		ResultBean<?> resultBean = perform("/statistics/type/fault", param);
		TypeStatisticsResult result = JSONObject.parseObject(resultBean.getData().toString(), TypeStatisticsResult.class);
		System.out.println(result.getTopType());
		result.getTypes().forEach(System.out::println);
	}

	@Test
	public void testBySubHealth() throws Exception {
		StatisticsParm param = new StatisticsParm();
		param.setStart_month(1);
		param.setEnd_month(12);
		param.setYear(2019);
		ResultBean<?> resultBean = perform("/statistics/type/subhealth", param);
		TypeStatisticsResult result = JSONObject.parseObject(resultBean.getData().toString(), TypeStatisticsResult.class);
		System.out.println(result.getTopType());
		result.getTypes().forEach(System.out::println);
	}

	@Test
	public void testByTop() throws Exception {
		StatisticsParm param = new StatisticsParm();
		param.setStart_month(1);
		param.setEnd_month(12);
		param.setYear(2019);
		ResultBean<?> resultBean = perform("/statistics/type/top", param);
		TypeStatisticsResult result = JSONObject.parseObject(resultBean.getData().toString(), TypeStatisticsResult.class);
		System.out.println(result.getTopType());
		result.getTypes().forEach(System.out::println);
	}

	private ResultBean<?> perform(String url, StatisticsParm param) throws Exception {
		MvcResult result = mockMvc
				.perform(get(url).param("trains", "341,1").param("start_month", param.getStart_month().toString())
						.param("end_month", param.getEnd_month().toString()).param("year", param.getYear().toString()))

				.andExpect(status().isOk()).andDo(print()).andReturn();
		String content = result.getResponse().getContentAsString();
		ResultBean<?> resultBean = JSONObject.parseObject(content, ResultBean.class);
		return resultBean;
	}
}
