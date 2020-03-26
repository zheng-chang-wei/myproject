package com.hirain.phm.synapsis.mvb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.protocol.ProtocolService;
import com.hirain.phm.synapsis.setting.MVBVariable;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class MVBVariableConverterTest {

	@Autowired
	private MVBVariableConverter mvbVariableConverter;

	@Autowired
	private ProtocolService parseService;

	@Test
	public void converterMVBTest() {
		try {
			File file = ResourceUtils.getFile("classpath:mvb.xlsx");
			ParseResult result = parseService.parse("MVB", file.getAbsolutePath());
			List<MVBVariable> convert = mvbVariableConverter.convert(result.getData());
			List<MVBVariable> list = new ArrayList<>();
			MVBVariable mvbVariable1 = new MVBVariable();
			mvbVariable1.setDevice(2);
			mvbVariable1.setDeviceName("test");
			mvbVariable1.setCarriage("11");
			mvbVariable1.setSystem("Windows");
			mvbVariable1.setPort(1);
			mvbVariable1.setFcode(3);
			mvbVariable1.setDataType(8);
			mvbVariable1.setByteOffset(11);
			mvbVariable1.setBitOffset(2);
			mvbVariable1.setBitLen(8);
			mvbVariable1.setName("v1");
			mvbVariable1.setUnit("");
			list.add(mvbVariable1);
			MVBVariable mvbVariable2 = new MVBVariable();
			mvbVariable2.setDevice(3);
			mvbVariable2.setDeviceName("test2");
			mvbVariable2.setCarriage("12");
			mvbVariable2.setSystem("Windows2");
			mvbVariable2.setPort(2);
			mvbVariable2.setFcode(4);
			mvbVariable2.setDataType(16);
			mvbVariable2.setByteOffset(12);
			mvbVariable2.setBitOffset(3);
			mvbVariable2.setBitLen(16);
			mvbVariable2.setName("v2");
			mvbVariable2.setUnit("");
			list.add(mvbVariable2);
			assertEquals(convert.toString(), list.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
