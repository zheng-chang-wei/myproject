/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.parse;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.hirain.phm.synapsis.parse.domain.FileDataContent;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.HeaderVariableGroup;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.setting.Variable;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 6, 2020 6:50:06 PM
 * @Description
 *              <p>
 *              原始数据文件解析组件
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface DataFileParser {

	/**
	 * 解析数据头文件，获得变量信息
	 * 
	 * @param file
	 * @throws IOException
	 * @throws Exception
	 */
	FileHeader parseHeader(File file) throws JAXBException, IOException;

	FileHeader parseHeader(String filepath) throws JAXBException, IOException;

	/**
	 * 解析数据头文件，获得变量信息
	 * 
	 * @param header
	 * @param dataContent
	 * @param postProcessor
	 * @return
	 */
	List<VariableData> parseData(HeaderVariableGroup header, FileDataContent dataContent, ValuePostProcessor postProcessor);

	/**
	 * 根据数据头和所选变量解析获得变量的值
	 * 
	 * @param header
	 * @param dataContent
	 * @param params
	 * @param postProcessor
	 * @return
	 */
	List<VariableData> parseData(HeaderVariableGroup header, FileDataContent dataContent, List<? extends Variable> params,
			ValuePostProcessor postProcessor);

}
