package com.hirain.dome;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.qsy.shaft.common.model.CoreConstant;
import com.hirain.qsy.shaft.common.util.DateUtil;
import com.hirain.qsy.shaft.dao.InitialDataMapper;
import com.hirain.qsy.shaft.dao.TrainInfoMapper;
import com.hirain.qsy.shaft.model.ExceptionData;
import com.hirain.qsy.shaft.model.StatisticsChartDataRow;
import com.hirain.qsy.shaft.service.ExceptionDataService;
import com.hirain.qsy.shaft.service.RedisCacheService;
import com.hirain.qsy.shaft.service.impl.StatisticsServiceImpl;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatisticsServiceTest {

	@Autowired
	RedisCacheService redisCacheService;

	@Autowired
	ExceptionDataService exceptionDataService;

	@Autowired
	InitialDataMapper initialDataMapper;

	@Autowired
	TrainInfoMapper trainInfoMapper;

	@Autowired
	StatisticsServiceImpl satisticsService;

	private Integer trainId = 517;

	private List<Integer> trainIds = new ArrayList<>();

	// @Before
	public void setUp() {
		trainIds.add(trainId);
	}

	// @Test
	public void getSameMonthTest() {
		Set<String> daySet = satisticsService.getDaySet(trainIds);
		for (String string : daySet) {
			System.out.println(string);
		}
		Set<String> sameMonthFromDays = satisticsService.getSameMonthFromDays(daySet);
		for (String string : sameMonthFromDays) {
			System.err.println(string);
		}
	}

	// // @Test
	// public void test() throws Exception {
	// List<StatisticsChartDataRow> statisticsByCarRows = new ArrayList<>();
	// List<ExceptionData> exceptionDatas = exceptionDataService.findExceptionDataByIds(trainIds);
	// List<ExceptionData> tempExceptionDatas = new ArrayList<>();
	// Date oldDate = null;
	// int a = 2;
	// for (ExceptionData exceptionData : exceptionDatas) {
	// Date acquisitionTime = exceptionData.getAcquisitionTime();
	// if (oldDate != null) {
	// boolean isSame = DateUtil.isSame(oldDate, acquisitionTime, a);
	// if (!isSame) {
	// addStatisticsData(a, statisticsByCarRows, tempExceptionDatas);
	// }
	// }
	// tempExceptionDatas.add(exceptionData);
	// oldDate = acquisitionTime;
	// }
	// if (tempExceptionDatas.size() > 0) {
	// addStatisticsData(a, statisticsByCarRows, tempExceptionDatas);
	// }
	// for (StatisticsChartDataRow statisticsChartDataRow : statisticsByCarRows) {
	//
	// System.out.println(statisticsChartDataRow.getY());
	// }
	// }

	/**
	 * 添加统计数据
	 * 
	 * @param statisticsRequest
	 * @param statisticsByCarRows
	 * @param tempExceptionDatas
	 * @throws Exception
	 */
	private void addStatisticsData(int granularityTime, List<StatisticsChartDataRow> statisticsByCarRows, List<ExceptionData> tempExceptionDatas)
			throws Exception {
		Date time = tempExceptionDatas.get(0).getAcquisitionTime();
		List<Integer> list = new ArrayList<>();
		list.add(getCountAllType(tempExceptionDatas));
		switch (granularityTime) {
		case 0:
			SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
			statisticsByCarRows.add(new StatisticsChartDataRow(format.format(time), list));
			break;
		case 1:
			statisticsByCarRows.add(new StatisticsChartDataRow(DateUtil.getFirstDayOfWeek(time), list));
			break;
		case 2:
			statisticsByCarRows.add(new StatisticsChartDataRow(DateUtil.getCurrentMonth(time), list));
			break;
		case 3:
			statisticsByCarRows.add(new StatisticsChartDataRow(DateUtil.getCurrentYear(time), list));
			break;
		default:
			break;
		}
		tempExceptionDatas.clear();
	}

	/**
	 * 计算每个车型的异常触发次数
	 * 
	 * @param exceptionDatas
	 * @return
	 */
	private Integer getCountAllType(List<ExceptionData> exceptionDatas) throws Exception {
		Integer count = 0;
		for (ExceptionData exceptionData : exceptionDatas) {
			Field[] fields = exceptionData.getClass().getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				String name = f.getName();
				if (name.startsWith("result")) {
					Object value = f.get(exceptionData);
					String vString = (String) value;
					if (vString.contains(",")) {
						String[] split = vString.split(",");
						// 1表示异常
						if (split[3].equals("1")) {
							count++;
							// break;
						}
					}
				}
			}
		}
		return count;
	}

	/**
	 * 计算每个轴每个测点的异常触发次数
	 * 
	 * @param exceptionDatas
	 * @param rows
	 * @return
	 */
	private List<StatisticsChartDataRow> getCount(List<ExceptionData> exceptionDatas) throws Exception {
		List<StatisticsChartDataRow> rows = new ArrayList<>();
		if (exceptionDatas == null || exceptionDatas.size() == 0) {
			return rows;
		}
		for (int i = 0; i < CoreConstant.axleCount; i++) {
			List<Integer> list = new LinkedList<>();
			for (int j = 0; j < CoreConstant.pointCount; j++) {
				list.add(0);
			}
			rows.add(new StatisticsChartDataRow((i + 1) + "号轴", list));
		}
		for (ExceptionData exceptionData : exceptionDatas) {
			Field[] fields = exceptionData.getClass().getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				String name = f.getName();
				if (name.startsWith("result")) {
					Object value = f.get(exceptionData);
					String[] split = ((String) value).split(",");
					// 1表示异常
					if (split[3].equals("1")) {
						// 获取到后两位数字，个位为测点，十位为轴号
						String num = name.substring(name.length() - 2, name.length());
						// 获取对应轴的数据
						StatisticsChartDataRow statisticsByPointRow = rows.get(Integer.valueOf(String.valueOf(num.charAt(0))) - 1);
						List<Integer> y = statisticsByPointRow.getY();
						Integer valueOf = Integer.valueOf(String.valueOf(num.charAt(1))) - 1;
						y.set(valueOf, y.get(valueOf) + 1);
					}
				}
			}
		}
		return rows;
	}
}
