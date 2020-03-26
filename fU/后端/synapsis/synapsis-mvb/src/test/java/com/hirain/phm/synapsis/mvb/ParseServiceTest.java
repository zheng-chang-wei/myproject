package com.hirain.phm.synapsis.mvb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import com.hirain.phm.synapsis.mvb.model.impl.Root;
import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.protocol.ProtocolService;
import com.hirain.phm.synapsis.util.JaxbUtil;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class ParseServiceTest {

	@Autowired
	private ProtocolService parseService;

	@Test
	public void parseMVBTest() {
		try {
			File file = ResourceUtils.getFile("classpath:mvb.xlsx");
			ParseResult result = parseService.parse("MVB", file.getAbsolutePath());
			parseService.toFile("MVB", file.getParent() + File.separator + "result.xml", result.getData());
			File resultFile = new File(file.getParentFile(), "result.xml");
			File xmlFile = ResourceUtils.getFile("classpath:mvb_result.xml");
			Object object1 = JaxbUtil.unmarshaller(Root.class, xmlFile.getAbsolutePath());
			Object object2 = JaxbUtil.unmarshaller(Root.class, resultFile.getAbsolutePath());
			assertEquals(object2.toString(), object1.toString());
			assertTrue(resultFile.exists());
			resultFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
