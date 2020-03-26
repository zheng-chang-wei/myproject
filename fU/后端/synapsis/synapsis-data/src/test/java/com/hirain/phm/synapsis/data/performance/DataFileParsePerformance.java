/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.data.performance;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import com.hirain.phm.synapsis.compress.QuickLzSupport;
import com.hirain.phm.synapsis.parse.DataFileParser;
import com.hirain.phm.synapsis.parse.domain.FileDataContent;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.HeaderVariableGroup;
import com.hirain.phm.synapsis.parse.impl.DataFileParserImpl;
import com.hirain.phm.synapsis.parse.impl.DefaultValuePostProcessor;
import com.hirain.phm.synapsis.setting.Variable;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 17, 2020 10:28:57 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 17, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class DataFileParsePerformance {

	@Before
	public void setUp() {
		System.out.println("-----------------------------");
	}

	@Test
	public void testDecompress() throws IOException {
		ByteBuffer buffer = FilePrepare.getDataBuffer();
		QuickLzSupport support = new QuickLzSupport();
		byte[] source = buffer.array();
		byte[] compress = support.compress(source);
		TimeCounter.run("decompress", () -> {
			QuickLzSupport s = new QuickLzSupport();
			s.decompress(compress);
		}, 100);
	}

	@Test
	public void testParseData() throws JAXBException, IOException {
		DataFileParser parser = new DataFileParserImpl();
		File file = ResourceUtils.getFile("classpath:data_header_1.0.txt");
		FileHeader header = parser.parseHeader(file);
		HeaderVariableGroup headerGroup = header.getHeaders().get(0);
		ByteBuffer buffer = FilePrepare.getDataBuffer();
		FileDataContent content = new FileDataContent(buffer.array());
		List<Variable> selected = new ArrayList<>();
		selected.add(headerGroup.getVariables().get(0));
		DefaultValuePostProcessor postProcessor = new DefaultValuePostProcessor();
		TimeCounter.run("Parse Data", () -> {
			parser.parseData(headerGroup, content, selected, postProcessor);
		}, 100);
	}

}
