/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.synapsis.result.domain.ResultSegment;
import com.hirain.phm.synapsis.result.domain.ResultSegmentContainer;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 9:43:57 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class ResultSegmentWriter {

	public static void main(String[] args) throws Exception {
		ResultSegmentContainer header = createHeader();
		ResultSegmentContainer data = createData();
		List<ResultSegmentContainer> segmentList = Arrays.asList(header, data);
		String json = JSONObject.toJSONString(segmentList);
		File file = ResourceUtils.getFile("classpath:result-segment.txt");
		FileUtils.writeStringToFile(file, json);
	}

	/**
	 * @return
	 */
	public static ResultSegmentContainer createData() {
		List<ResultSegment> segments = new ArrayList<>();
		segments.add(createResultSegment("包头类型", "UINT16", 2));
		segments.add(createResultSegment("算法ID", "UINT16", 2));
		segments.add(createResultSegment("算法包头长度", "UINT8", 1));
		segments.add(createResultSegment("年", "UIN8", 1));
		segments.add(createResultSegment("月", "UIN8", 1));
		segments.add(createResultSegment("日", "UIN8", 1));
		segments.add(createResultSegment("时", "UIN8", 1));
		segments.add(createResultSegment("分", "UIN8", 1));
		segments.add(createResultSegment("秒", "UIN8", 1));
		segments.add(createResultSegment("毫秒", "UIN16", 2));
		segments.add(createResultSegment("算法供应商代码", "UIN8", 1));
		segments.add(createResultSegment("算法版本", "BCD", 2));
		segments.add(createResultSegment("生命信号", "UINT16", 2));
		segments.add(createResultSegment("车和端", "UINT8", 1));
		segments.add(createResultSegment("设备顺序编号", "UINT8", 1));
		segments.add(createResultSegment("模型子系统类型", "UINT8", 1));
		segments.add(createResultSegment("CRC校验", "UINT16", 2));
		ResultSegmentContainer data = new ResultSegmentContainer();
		data.setType(1);
		int length = 0;
		for (int i = 0; i < segments.size(); i++) {
			length += segments.get(i).getByteLen();
			segments.get(i).setIndex(i);
		}
		data.setSegments(segments);
		data.setLength(length);
		return data;
	}

	/**
	 * @return
	 */
	public static ResultSegmentContainer createHeader() {
		List<ResultSegment> segments = new ArrayList<>();
		segments.add(createResultSegment("公共包头长度", "UINT8", 1));
		segments.add(createResultSegment("年", "UIN8", 1));
		segments.add(createResultSegment("月", "UIN8", 1));
		segments.add(createResultSegment("日", "UIN8", 1));
		segments.add(createResultSegment("时", "UIN8", 1));
		segments.add(createResultSegment("分", "UIN8", 1));
		segments.add(createResultSegment("秒", "UIN8", 1));
		segments.add(createResultSegment("毫秒", "UIN16", 2));
		segments.add(createResultSegment("经度（下位）", "UIN8", 1));
		segments.add(createResultSegment("经度（中下位）", "UIN8", 1));
		segments.add(createResultSegment("经度（中上位）", "UIN8", 1));
		segments.add(createResultSegment("经度（上位）", "UIN8", 1));
		segments.add(createResultSegment("经度方向", "UIN8", 1));
		segments.add(createResultSegment("纬度（下位）", "UIN8", 1));
		segments.add(createResultSegment("纬度（中下位）", "UIN8", 1));
		segments.add(createResultSegment("纬度（中上位）", "UIN8", 1));
		segments.add(createResultSegment("纬度（上位）", "UIN8", 1));
		segments.add(createResultSegment("纬度方向", "UIN8", 1));
		segments.add(createResultSegment("协议版本号", "BCD", 2));
		segments.add(createResultSegment("CRC校验", "UINT16", 2));
		ResultSegmentContainer header = new ResultSegmentContainer();
		header.setType(0);
		int length = 0;
		for (int i = 0; i < segments.size(); i++) {
			length += segments.get(i).getByteLen();
			segments.get(i).setIndex(i);
		}
		header.setSegments(segments);
		header.setLength(length);
		return header;
	}

	/**
	 * @param byteLen
	 * @param string
	 * @param string2
	 */
	private static ResultSegment createResultSegment(String name, String type, int byteLen) {
		ResultSegment segment = new ResultSegment();
		segment.setName(name);
		segment.setDataType(type);
		segment.setByteLen(byteLen);
		return segment;
	}
}
