/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.parse.impl;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.parse.DataFileParser;
import com.hirain.phm.synapsis.parse.ValuePostProcessor;
import com.hirain.phm.synapsis.parse.domain.FileDataContent;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.HeaderVariableGroup;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.parse.domain.VariableValue;
import com.hirain.phm.synapsis.setting.BusDataType;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.util.JaxbUtil;

import lombok.ToString;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 6, 2020 6:51:16 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class DataFileParserImpl implements DataFileParser {

	private final static ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;

	/**
	 * @throws IOException
	 * @throws Exception
	 * @see com.hirain.phm.synapsis.parse.DataFileParser#parseHeader(com.hirain.phm.synapsis.data.parse.domain.FileHeaderContent)
	 */
	@Override
	public FileHeader parseHeader(File file) throws JAXBException, IOException {
		String version = getVersion(file);
		String xml = FileUtils.readFileToString(file);
		FileHeader headerList = (FileHeader) JaxbUtil.xmlToObject(FileHeader.class, xml);
		headerList.setVersion(version);
		return headerList;
	}

	/**
	 * @see com.hirain.phm.synapsis.parse.DataFileParser#parseHeader(java.lang.String)
	 */
	@Override
	public FileHeader parseHeader(String filepath) throws JAXBException, IOException {
		File file = new File(filepath);
		return parseHeader(file);
	}

	/**
	 * @param file
	 * @return
	 */
	private String getVersion(File file) {
		int dotIndex = file.getName().lastIndexOf(".");
		String filename = file.getName().substring(0, dotIndex);
		String[] split = filename.split("_");
		return split[split.length - 1];
	}

	/**
	 * @see com.hirain.phm.synapsis.parse.DataFileParser#parseData(com.hirain.phm.synapsis.parse.domain.HeaderVariableGroup,
	 *      com.hirain.phm.synapsis.parse.domain.FileDataContent, ValuePostProcessor)
	 */
	@Override
	public List<VariableData> parseData(HeaderVariableGroup header, FileDataContent dataContent, ValuePostProcessor postProcessor) {
		ArrayList<VariableData> variableDatas = new ArrayList<>();
		for (Variable variable : header.getVariables()) {
			VariableData data = new VariableData();
			data.setVariable(variable);
			data.setValues(new ArrayList<>());
			variableDatas.add(data);
		}
		ByteBuffer buffer = ByteBuffer.wrap(dataContent.getDatas()).order(BYTE_ORDER);
		while (buffer.hasRemaining()) {
			Frame frame = parseFrame(buffer);
			LocalDateTime timestamp = timestamp(frame);
			ByteBuffer dataBuffer = ByteBuffer.wrap(frame.data).order(BYTE_ORDER);
			for (VariableData data : variableDatas) {
				VariableValue variableValue = new VariableValue();
				variableValue.setFrameIndex(frame.index);
				variableValue.setTimestamp(timestamp);
				double value = parseValue(data.getVariable(), dataBuffer);
				variableValue.setValue(postProcessor.process(data.getVariable(), value));
				data.getValues().add(variableValue);
			}
		}
		return variableDatas;
	}

	/**
	 * @see com.hirain.phm.synapsis.parse.DataFileParser#parseData(com.hirain.phm.synapsis.parse.domain.HeaderVariableGroup,
	 *      com.hirain.phm.synapsis.parse.domain.FileDataContent, java.util.List,
	 *      com.hirain.phm.synapsis.data.parse.impl.DefaultValuePostProcessor)
	 */
	@Override
	public List<VariableData> parseData(HeaderVariableGroup header, FileDataContent dataContent, List<? extends Variable> params,
			ValuePostProcessor postProcessor) {
		ArrayList<VariableData> variableDatas = new ArrayList<>();
		for (Variable variable : params) {
			VariableData data = new VariableData();
			data.setVariable(variable);
			data.setValues(new ArrayList<>());
			variableDatas.add(data);
		}
		ByteBuffer buffer = ByteBuffer.wrap(dataContent.getDatas()).order(BYTE_ORDER);
		while (buffer.hasRemaining()) {
			Frame frame = parseFrame(buffer);
			System.out.println(frame);
			LocalDateTime timestamp = timestamp(frame);
			System.out.println(timestamp);
			ByteBuffer dataBuffer = ByteBuffer.wrap(frame.data).order(BYTE_ORDER);
			for (int i = 0, j = 0; i < header.getVariables().size() && j < variableDatas.size(); i++) {
				Variable v1 = header.getVariables().get(i);
				VariableData data = variableDatas.get(j);
				Variable v2 = data.getVariable();
				if (isEqual(v1, v2)) {
					VariableValue variableValue = new VariableValue();
					variableValue.setFrameIndex(frame.index);
					variableValue.setTimestamp(timestamp);
					double value = parseValue(data.getVariable(), dataBuffer);
					variableValue.setValue(postProcessor.process(data.getVariable(), value));
					data.getValues().add(variableValue);
					j++;
				} else {
					int offset = getOffset(v1.getDataType());
					dataBuffer.position(dataBuffer.position() + offset);
				}
			}
		}
		return variableDatas;
	}

	/**
	 * @param v1
	 * @param v2
	 * @return
	 */
	public boolean isEqual(Variable v1, Variable v2) {
		return v1.getName().equals(v2.getName());
	}

	/**
	 * @param dataType
	 * @return
	 */
	private int getOffset(Integer dataType) {
		BusDataType type = BusDataType.values()[dataType];
		return type.getByteLen();
	}

	/**
	 * @param variable
	 * @param dataBuffer
	 * @return
	 */
	private double parseValue(Variable variable, ByteBuffer dataBuffer) {
		Integer typeIndex = variable.getDataType();
		BusDataType type = BusDataType.values()[typeIndex];
		switch (type) {
		case BOOLEAN:
		case BITS:
		case CHAR8:
		case INT8:
			return dataBuffer.get();
		case DOUBLE:
			return dataBuffer.getDouble();
		case FLOAT:
			return dataBuffer.getFloat();
		case INT16:
			return dataBuffer.getShort();
		case INT32:
			return dataBuffer.getInt();
		case LONG:
			return dataBuffer.getLong();
		case UINT16:
			return Short.toUnsignedInt(dataBuffer.getShort());
		case UINT32:
			return Integer.toUnsignedLong(dataBuffer.getInt());
		case UINT8:
			return Byte.toUnsignedInt(dataBuffer.get());
		default:
			break;
		}
		return 0;
	}

	/**
	 * @param frame
	 * @return
	 */
	private LocalDateTime timestamp(Frame frame) {
		LocalDateTime timestamp = LocalDateTime.ofEpochSecond(frame.second, (int) (frame.micro * 1000), ZoneOffset.ofHours(8));
		System.out.println(timestamp);
		return timestamp;
	}

	/**
	 * @param buffer
	 * @return
	 */
	private Frame parseFrame(ByteBuffer buffer) {
		Frame frame = new Frame();
		frame.index = buffer.getInt();
		frame.second = Integer.toUnsignedLong(buffer.getInt());
		frame.micro = Integer.toUnsignedLong(buffer.getInt());
		int length = Short.toUnsignedInt(buffer.getShort());
		byte[] data = new byte[length];
		buffer.get(data);
		frame.data = data;
		return frame;
	}

	@ToString
	private class Frame {

		int index;

		long second;

		long micro;

		byte[] data;
	}
}
