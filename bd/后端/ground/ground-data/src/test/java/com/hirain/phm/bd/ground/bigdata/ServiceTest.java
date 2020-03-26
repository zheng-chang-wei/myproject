package com.hirain.phm.bd.ground.bigdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.bigdata.param.DeleteDataParam;
import com.hirain.phm.bd.ground.bigdata.param.GroundData;
import com.hirain.phm.bd.ground.bigdata.param.GroundDataParam;
import com.hirain.phm.bd.ground.bigdata.service.BigDataService;
import com.hirain.phm.bd.ground.bigdata.service.DataManageService;
import com.hirain.phm.bd.ground.util.GroundAccessHelper;
import com.hirain.phm.bd.message.big.DataRecord;
import com.hirain.phm.bd.message.big.DataRecordList;

@SpringBootTest(classes = BigDataTestApplication.class)
@RunWith(SpringRunner.class)
public class ServiceTest {

	@Autowired
	private DataManageService service;

	@Autowired
	private BigDataService bigService;

	@Test
	public void listTrainData() {
		GroundDataParam param = new GroundDataParam();
		List<GroundData> trainData = service.selectTrainData(param);
		trainData.forEach(System.out::println);
		assertNotNull(trainData);
		assertFalse(trainData.isEmpty());
		assertEquals("深圳地铁7号线", trainData.get(0).getProject());
	}

	@Test
	public void testInsert() {
		DataRecordList list = new DataRecordList();
		DataRecord record = new DataRecord();
		record.setProject("深圳地铁7号线");
		record.setTrain("717");
		record.setDate("20191112");
		record.setSid(GroundAccessHelper.GG_DATA_RECORD);
		list.setRecords(Arrays.asList(record));
		bigService.insertDataRecord(list.getRecords());

		GroundDataParam param = new GroundDataParam();
		param.setProject(record.getProject());
		param.setTrain(record.getTrain());
		List<GroundData> trainDatas = service.selectTrainData(param);
		assertFalse(trainDatas.isEmpty());

		GroundData data = trainDatas.get(0);
		assertEquals(record.getProject(), data.getProject());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String endDay = sdf.format(data.getEndDay());
		assertEquals(record.getDate(), endDay);
	}

	@Test
	@Ignore
	public void testDelete() {
		DeleteDataParam param = new DeleteDataParam();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2019);
		calendar.set(Calendar.MONTH, Calendar.MAY);
		calendar.set(Calendar.DAY_OF_MONTH, 23);
		param.setDate(calendar.getTime());
		GroundDataParam dataParam = new GroundDataParam();
		dataParam.setProject("北京1号线");
		dataParam.setTrain("02");
		List<GroundDataParam> list = new ArrayList<>();
		list.add(dataParam);
		GroundDataParam[] arr = new GroundDataParam[1];
		arr[0] = dataParam;
		param.setParams(arr);
		try {
			service.delete(param);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
