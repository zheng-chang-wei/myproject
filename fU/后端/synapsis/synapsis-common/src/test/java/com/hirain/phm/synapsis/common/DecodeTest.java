/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.common;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;

import com.hirain.phm.synapsis.compress.QuickLzSupport;
import com.hirain.phm.synapsis.parse.DataFileParser;
import com.hirain.phm.synapsis.parse.domain.FileDataContent;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.parse.impl.DataFileParserImpl;
import com.hirain.phm.synapsis.parse.impl.DefaultValuePostProcessor;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 24, 2020 1:35:18 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 24, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class DecodeTest {

	public static String headerPath = "D:\\workIde\\workspace-4u\\synapsis\\synapsis-common\\data_header_1.0.txt";

	public static String dataPath = "D:\\workIde\\workspace-4u\\synapsis\\synapsis-common\\ECN_15_20010203_040700.dat";

	/**
	 * @param args
	 * @throws IOException
	 * @throws JAXBException
	 */
	public static void main(String[] args) throws JAXBException, IOException {
		DataFileParser parser = new DataFileParserImpl();
		FileHeader header = parser.parseHeader(headerPath);
		byte[] bs = FileUtils.readFileToByteArray(new File(dataPath));
		QuickLzSupport support = new QuickLzSupport();
		byte[] compress = support.decompress(bs);

		FileDataContent content = new FileDataContent(compress);
		// System.out.println(content);

		List<VariableData> datas = parser.parseData(header.getHeaders().get(0), content, new DefaultValuePostProcessor());
		System.out.println(datas.size());
	}

}
