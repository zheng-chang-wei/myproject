/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault;

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

import com.hirain.phm.bd.ground.fault.dao.FaultDetailMapper;
import com.hirain.phm.bd.ground.fault.param.FaultDetailRequestParams;
import com.hirain.phm.bd.ground.fault.param.FaultDetailResponseParams;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 1, 2019 4:16:08 PM
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
	private FaultDetailMapper detailMapper;

	@Test
	public void test() {
		List<FaultDetailResponseParams> details = detailMapper.findFaultDetailsByParms(new FaultDetailRequestParams());
		assertNotNull(details);
		assertFalse(details.isEmpty());
		if (details.size() > 1) {
			FaultDetailResponseParams d1 = details.get(0);
			FaultDetailResponseParams d2 = details.get(1);
			assertNotNull(d1.getFaultTime());
			assertNotNull(d2.getFaultTime());
			assertTrue(d1.getFaultTime().compareTo(d2.getFaultTime()) > 0);
		}
		details.forEach(System.out::println);

		// assertNotNull(details.get(0).getSolution());
	}

}
