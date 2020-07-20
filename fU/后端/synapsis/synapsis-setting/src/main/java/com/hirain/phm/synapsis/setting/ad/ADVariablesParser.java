/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.ad;

import java.io.File;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.protocol.ProtocolStream;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 10, 2020 4:06:17 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 10, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component("AD")
public class ADVariablesParser implements ProtocolStream {

	/**
	 * @see com.hirain.phm.synapsis.protocol.ProtocolStream#read(java.io.File)
	 */
	@Override
	public ParseResult read(File file) throws Exception {
		return read(file.getAbsolutePath());
	}

	/**
	 * @see com.hirain.phm.synapsis.protocol.ProtocolStream#read(java.lang.String)
	 */
	@Override
	public ParseResult read(String filename) throws Exception {
		ParseResult result = new ParseResult();
		// File file = new File(filename);
		// String name = file.getName();
		// try {
		// Integer groupId = Integer.parseInt(name);
		// VariableGroup group = service.select(groupId);
		// result.setData(group);
		// } catch (NumberFormatException e) {
		// result.setCode(ParseResult.FAIL);
		// } catch (Exception e) {
		// throw new SynapsisException("ad变量查询失败", e);
		// }
		return result;
	}
}
