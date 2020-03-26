package com.hirain.phm.bd.ground.subhealth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthDetail;
import com.hirain.phm.bd.ground.subhealth.param.SubhealthDetailParams;
import com.hirain.phm.bd.ground.subhealth.param.SubhealthWithSuggestionParams;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthAnalyseService;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthQueryService;
import com.hirain.phm.bd.message.train.SubhealthPacket;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class TestSubhealth {

	static String json = "{\"startTime\": \"2019-09-23 09:34:10:000\", \"endTime\": \"2019-09-23 09:34:14:000\", \"debug\": \"False\", \"subhealthItems\": [\"0\"], \"city\": \"\\u6df1\\u5733\", \"line\": \"7\", \"carID\": \"6\", \"doorID\": \"8\", \"train\": \"717\"}";

	@Autowired
	private SubhealthAnalyseService analyseService;

	@Autowired
	private SubhealthQueryService queryService;

	@Test
	public void test() {
		SubhealthPacket packet = JsonUtil.fromString(json, SubhealthPacket.class);
		packet.getSubhealthItems().add(1);
		packet.setProject("深圳地铁7号线");
		List<SubhealthDetail> list = analyseService.analyse(packet);
		list.forEach(System.out::println);

		SubhealthDetailParams param = new SubhealthDetailParams();
		param.setProject("深圳地铁7号线");
		param.setTrainNo("717");
		List<SubhealthWithSuggestionParams> result = queryService.selectByParams(param);
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(param.getProject(), result.get(0).getProject());
	}
}
