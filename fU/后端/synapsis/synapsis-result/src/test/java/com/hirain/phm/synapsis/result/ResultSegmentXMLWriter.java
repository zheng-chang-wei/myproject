/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result;

import java.util.Arrays;

import org.junit.Test;

import com.hirain.phm.synapsis.file.XmlFileGenerator;
import com.hirain.phm.synapsis.result.domain.ResultSegment;
import com.hirain.phm.synapsis.result.domain.ResultSegmentContainer;
import com.hirain.phm.synapsis.result.service.ResultSegmentService;
import com.hirain.phm.synapsis.result.service.impl.ResultSegmentServiceImpl;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created May 14, 2020 5:18:51 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 14, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class ResultSegmentXMLWriter {

	private ResultSegmentService service = new ResultSegmentServiceImpl();

	@Test
	public void test() {
		ResultSegmentContainer header = service.getHeaderSegments();
		ResultSegment reserved = createResultSegment("保留字", "BYTE[]", 26, 19);
		header.getSegments().add(header.getSegments().size() - 1, reserved);
		header.getSegments().get(header.getSegments().size() - 1).setIndex(20);
		ResultSegmentContainer data = service.getDataSegments();
		data.getSegments().add(createResultSegment("结果包长度", "UINT8", 1, 17));
		data.getSegments().add(createResultSegment("项点名称", "CHAR[]", 16, 18));
		data.getSegments().add(createResultSegment("项点判定结果", "BOOL8", 1, 19));

		XMLRoot root = new XMLRoot();
		root.setContainers(Arrays.asList(header, data));
		XmlFileGenerator generator = new XmlFileGenerator();
		try {
			generator.generate("D:\\test\\protocol", root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ResultSegment createResultSegment(String name, String type, int byteLen, int index) {
		ResultSegment segment = new ResultSegment();
		segment.setName(name);
		segment.setDataType(type);
		segment.setByteLen(byteLen);
		segment.setIndex(index);
		return segment;
	}

}
