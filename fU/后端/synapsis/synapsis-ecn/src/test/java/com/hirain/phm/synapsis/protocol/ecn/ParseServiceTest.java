package com.hirain.phm.synapsis.protocol.ecn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

import com.hirain.phm.synapsis.ecn.impl.ECNProcotolStream;
import com.hirain.phm.synapsis.ecn.model.Device;
import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.util.JaxbUtil;

public class ParseServiceTest {

	private ECNProcotolStream parser = new ECNProcotolStream();

	@Test
	public void parseEcnTest() {
		try {
			File file = ResourceUtils.getFile("classpath:trdp.xls");
			ParseResult result = parser.read(file);
			File xmlFile = ResourceUtils.getFile("classpath:trdp_result.xml");
			Object object = JaxbUtil.unmarshaller(Device.class, xmlFile.getAbsolutePath());
			assertEquals(result.getData().toString(), object.toString());
			parser.write(file.getParent() + File.separator + "result.xml", result.getData());
			File resultFile = new File(file.getParentFile(), "result.xml");
			assertTrue(resultFile.exists());
			resultFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
