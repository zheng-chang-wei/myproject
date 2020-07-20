/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.subhealth.dao.SubhealthQueryMapper;
import com.hirain.phm.bd.ground.subhealth.param.SubhealthDetailParams;
import com.hirain.phm.bd.ground.subhealth.param.SubhealthDetailResponseParams;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 1, 2019 4:21:32 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 1, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class TestMapper {

	@Autowired
	private SubhealthQueryMapper mapper;

	@Test
	public void test() {
		List<SubhealthDetailResponseParams> details = mapper.selectByExample(new SubhealthDetailParams());
		assertNotNull(details);
		assertFalse(details.isEmpty());
		if (details.size() > 1) {
			SubhealthDetailResponseParams d1 = details.get(0);
			SubhealthDetailResponseParams d2 = details.get(1);
			assertNotNull(d1.getStartTime());
			assertNotNull(d2.getStartTime());

			assertTrue(d1.getStartTime().compareTo(d2.getStartTime()) > 0);
		}

		details.forEach(System.out::println);
	}

}
