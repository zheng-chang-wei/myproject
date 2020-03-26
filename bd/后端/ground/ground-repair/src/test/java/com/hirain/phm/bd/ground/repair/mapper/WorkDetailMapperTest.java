/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.repair.mapper;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.maintenance.dao.WorkDetailMapper;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;
import com.hirain.phm.bd.ground.repair.TestApplication;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 28, 2020 10:49:07 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("dev")
public class WorkDetailMapperTest {

	@Autowired
	private WorkDetailMapper mapper;

	@Test
	public void testSelect() {
		WorkDetail detail = mapper.selectDetail(1l);
		assertNotNull(detail);
		assertNotNull(detail.getFaultMode());
		assertNotNull(detail.getDoorType());
		assertNotNull(detail.getEffect());
		assertNotNull(detail.getStage());
	}

	@Test
	@Ignore
	public void testInsert() {
		Field[] fields = WorkDetail.class.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
		}
		WorkDetail detail = new WorkDetail();
		try {
			for (Field field : fields) {
				Class<?> type = field.getType();
				if (type.equals(String.class)) {
					field.set(detail, "1");
				} else if (type.equals(Integer.class)) {
					field.set(detail, 1);
				} else if (type.equals(Date.class)) {
					field.set(detail, new Date());
				} else if (type.equals(Boolean.class)) {
					field.set(detail, true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		detail.setProjectId(2);
		mapper.insertUseGeneratedKeys(detail);
	}
}
