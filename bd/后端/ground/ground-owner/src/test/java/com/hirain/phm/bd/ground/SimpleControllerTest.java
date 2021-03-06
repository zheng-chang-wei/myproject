package com.hirain.phm.bd.ground;

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
import com.hirain.phm.bd.ground.common.model.ResultBean;
import com.hirain.phm.bd.ground.statistics.domain.MonthStatisticsResult;
import com.hirain.phm.bd.ground.statistics.domain.StatisticsParam;
import com.hirain.phm.bd.ground.statistics.domain.TypeStatisticsResult;
import com.hirain.phm.bd.ground.statistics.domain.YearStatisticsResult;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class SimpleControllerTest {

	@Autowired
	public WebApplicationContext context;

	public MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
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
		StatisticsParam param = new StatisticsParam();
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
		StatisticsParam param = new StatisticsParam();
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
		StatisticsParam param = new StatisticsParam();
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
		StatisticsParam param = new StatisticsParam();
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
		StatisticsParam param = new StatisticsParam();
		param.setStart_month(1);
		param.setEnd_month(12);
		param.setYear(2019);
		ResultBean<?> resultBean = perform("/statistics/type/top", param);
		TypeStatisticsResult result = JSONObject.parseObject(resultBean.getData().toString(), TypeStatisticsResult.class);
		System.out.println(result.getTopType());
		result.getTypes().forEach(System.out::println);
	}

	private ResultBean<?> perform(String url, StatisticsParam param) throws Exception {
		MvcResult result = mockMvc
				.perform(get(url).param("trains", "341,1").param("start_month", param.getStart_month().toString())
						.param("end_month", param.getEnd_month().toString()).param("year", param.getYear().toString()))

				.andExpect(status().isOk()).andDo(print()).andReturn();
		String content = result.getResponse().getContentAsString();
		ResultBean<?> resultBean = JSONObject.parseObject(content, ResultBean.class);
		return resultBean;
	}
}
