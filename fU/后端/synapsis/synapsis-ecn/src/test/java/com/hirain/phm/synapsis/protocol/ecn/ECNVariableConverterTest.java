package com.hirain.phm.synapsis.protocol.ecn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import com.hirain.phm.synapsis.ecn.impl.ECNProcotolStream;
import com.hirain.phm.synapsis.ecn.impl.ECNVariableConverter;
import com.hirain.phm.synapsis.ecn.impl.ECNVariableQueryImpl;
import com.hirain.phm.synapsis.ecn.model.Device;
import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.protocol.ProtocolStream;
import com.hirain.phm.synapsis.setting.ECNVariable;

public class ECNVariableConverterTest {

	private ECNVariableConverter converter = new ECNVariableConverter();

	private ProtocolStream parser = new ECNProcotolStream();

	private Device device;

	@Before
	public void setUp() throws Exception {
		File file = ResourceUtils.getFile("classpath:trdp.xls");
		ParseResult result = parser.read(file);
		device = (Device) result.getData();
		converter.setQuery(new ECNVariableQueryImpl());
	}

	@Test
	public void converterECNTest() {
		try {
			List<ECNVariable> convert = converter.convert(device);
			ECNVariable v1 = convert.get(0);
			assertEquals(12096L, v1.getComId().longValue());
			assertEquals("10.0.1.215", v1.getSourceIp());
			assertEquals(0, v1.getDataType().byteValue());

			ECNVariable v2 = convert.get(1);
			assertEquals(12096L, v2.getComId().longValue());
			assertEquals("reserve", v2.getName());

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
