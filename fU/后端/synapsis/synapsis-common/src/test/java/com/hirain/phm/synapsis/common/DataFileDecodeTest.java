/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import com.hirain.phm.synapsis.parse.DataFileParser;
import com.hirain.phm.synapsis.parse.domain.FileDataContent;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.HeaderVariableGroup;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.parse.domain.VariableValue;
import com.hirain.phm.synapsis.parse.impl.DataFileParserImpl;
import com.hirain.phm.synapsis.parse.impl.DefaultValuePostProcessor;
import com.hirain.phm.synapsis.setting.BusDataType;
import com.hirain.phm.synapsis.setting.ECNVariable;
import com.hirain.phm.synapsis.setting.MVBVariable;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.util.JaxbUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 6, 2020 6:48:43 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */

public class DataFileDecodeTest {

	@Test
	public void test() throws Exception {
		File file = ResourceUtils.getFile("classpath:data_header_1.0.txt");
		System.out.println(file.getAbsolutePath());
		DataFileParser parser = new DataFileParserImpl();
		FileHeader header = parser.parseHeader(file);
		assertNotNull(header);
		System.out.println(header);
		assertEquals("1.0", header.getVersion());
	}

	@Test
	public void testData() throws IOException, JAXBException {
		File file = ResourceUtils.getFile("classpath:data_header_1.0.txt");
		System.out.println(file.getAbsolutePath());
		DataFileParser parser = new DataFileParserImpl();
		FileHeader headers = parser.parseHeader(file);
		File dataFile = ResourceUtils.getFile("classpath:datafile.txt");
		System.out.println(dataFile.getAbsolutePath());
		byte[] bs = FileUtils.readFileToByteArray(dataFile);
		FileDataContent dataContent = new FileDataContent();
		dataContent.setDatas(bs);

		HeaderVariableGroup header = headers.getHeaders().get(0);
		List<VariableData> values = parser.parseData(header, dataContent, new DefaultValuePostProcessor());
		assertEquals(2, values.size());
		assertEquals(10, values.get(0).getValues().size());
		System.out.println(values.get(0));
		System.out.println(values.get(1));

		List<VariableData> values2 = parser.parseData(header, dataContent, (variable, value) -> value * 2 + 3);
		VariableValue v1 = values.get(0).getValues().get(0);
		VariableValue v2 = values2.get(0).getValues().get(0);
		assertEquals(v1.getValue() * 2 + 3, v2.getValue(), 0.0001);
		System.out.println(v1.getValue() + " " + v2.getValue());
	}

	@Test
	public void testData2() throws JAXBException, IOException {
		File file = ResourceUtils.getFile("classpath:data_header_1.0.txt");
		DataFileParser parser = new DataFileParserImpl();
		FileHeader headers = parser.parseHeader(file);
		File dataFile = ResourceUtils.getFile("classpath:datafile.txt");
		System.out.println(dataFile.getAbsolutePath());
		byte[] bs = FileUtils.readFileToByteArray(dataFile);
		FileDataContent dataContent = new FileDataContent();
		dataContent.setDatas(bs);
		HeaderVariableGroup header = headers.getHeaders().get(0);
		List<Variable> params = new ArrayList<>();
		params.add(header.getVariables().get(1));
		List<VariableData> values = parser.parseData(header, dataContent, params, new DefaultValuePostProcessor());
		assertEquals(1, values.size());
		System.out.println(values.get(0));
	}

	@BeforeClass
	public static void prepareFile() throws JAXBException, IOException {
		File file = ResourceUtils.getFile("classpath:data_header_1.0.txt");
		System.out.println(file.getAbsolutePath());
		if (!file.exists()) {
			file.createNewFile();
		}
		ByteBuffer header = getHeadBuffer();
		byte[] bs = header.array();

		FileUtils.writeByteArrayToFile(file, bs);

		writeDataFile();
	}

	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void writeDataFile() throws FileNotFoundException, IOException {
		File dataFile = ResourceUtils.getFile("classpath:datafile.txt");
		System.out.println(dataFile.getAbsolutePath());
		ByteBuffer data = getDataBuffer();
		FileUtils.writeByteArrayToFile(dataFile, data.array());
	}

	@Test
	public void testTimestamp() {
		long millis = System.currentTimeMillis();
		long second = millis / 1000;
		long micro = (millis - second * 1000) * 1000;
		System.out.println("second micro" + second + " " + micro);

		LocalDateTime timestamp = LocalDateTime.ofEpochSecond(second, (int) (micro * 1000), ZoneOffset.ofHours(8));
		System.out.println(timestamp);
	}

	/**
	 * 
	 */
	private static ByteBuffer getDataBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(26 * 10).order(ByteOrder.LITTLE_ENDIAN);
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			buffer.putInt(i + 1);
			long millis = System.currentTimeMillis();
			long second = millis / 1000;
			long micro = (millis - second * 1000) * 1000;
			buffer.putInt((int) second);
			buffer.putInt((int) micro);
			buffer.putShort((short) 12);
			buffer.putInt(random.nextInt(10) + 10);
			buffer.putDouble(random.nextDouble());
		}
		return buffer;
	}

	/**
	 * @return
	 * @throws JAXBException
	 */
	private static ByteBuffer getHeadBuffer() throws JAXBException {
		VariableGroup group1 = createGroup1();
		VariableGroup group2 = createGroup2();
		StoreVariables storeVariables = new StoreVariables();
		storeVariables.setStoreVariables(Arrays.asList(group1, group2));
		String xml = JaxbUtil.marshall(storeVariables);
		int start = xml.indexOf("<storeVariables>");
		xml = xml.substring(start);
		System.out.println(xml);
		byte[] bs = xml.getBytes(Charset.forName("utf-8"));
		System.out.println(bs.length);
		ByteBuffer buffer = ByteBuffer.allocate(bs.length).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(bs);
		return buffer;
	}

	/**
	 * @return
	 */
	private static VariableGroup createGroup2() {
		VariableGroup group = new VariableGroup();
		group.setType("ECN");
		group.setSlotId(2);
		group.setConsumptionId(2L);
		group.setMulticastIp("127.0.0.1");
		group.setMulticastPort(65535);
		ECNVariable variable = new ECNVariable();
		variable.setName("电流");
		variable.setComId(12344L);
		variable.setSourceIp("192.168.4.13");
		variable.setBitLen(1);
		variable.setBitOffset(1);
		variable.setByteOffset(1);
		variable.setDataType(BusDataType.INT32.ordinal());
		variable.setSignalName("电流");
		variable.setUnit("A");
		group.setVariables(Arrays.asList(variable));
		return group;
	}

	/**
	 * @return
	 */
	private static VariableGroup createGroup1() {
		VariableGroup group = new VariableGroup();
		group.setType("MVB");
		group.setSlotId(1);
		group.setConsumptionId(1l);
		group.setMulticastIp("127.0.0.1");
		group.setMulticastPort(65535);
		MVBVariable variable1 = createMVBVariable1();
		MVBVariable variable2 = createMVBVariable2();
		group.setVariables(Arrays.asList(variable1, variable2));
		return group;
	}

	/**
	 * @return
	 */
	private static MVBVariable createMVBVariable1() {
		MVBVariable variable = new MVBVariable();
		variable.setName("电压");
		variable.setDevice(1);
		variable.setDeviceName("受电弓");
		variable.setCarriage("1");
		variable.setSystem("受电弓");
		variable.setBitLen(1);
		variable.setBitOffset(1);
		variable.setByteOffset(1);
		variable.setDataType(BusDataType.INT32.ordinal());
		variable.setFcode(1);
		variable.setPort(1);
		variable.setSignalName("电压");
		variable.setUnit("V");
		return variable;
	}

	/**
	 * @return
	 */
	private static MVBVariable createMVBVariable2() {
		MVBVariable variable = new MVBVariable();
		variable.setName("电流");
		variable.setDevice(1);
		variable.setDeviceName("受电弓");
		variable.setCarriage("1");
		variable.setSystem("受电弓");
		variable.setBitLen(1);
		variable.setBitOffset(1);
		variable.setByteOffset(1);
		variable.setDataType(BusDataType.DOUBLE.ordinal());
		variable.setFcode(1);
		variable.setPort(1);
		variable.setSignalName("电流");
		variable.setUnit("V");
		return variable;
	}

}