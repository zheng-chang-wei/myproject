package com.hirain.phm.synapsis.ecn.impl;

import java.io.File;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.ecn.utils.ParseExcelUtil;
import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.protocol.ProtocolStream;
import com.hirain.phm.synapsis.util.JaxbUtil;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月3日 上午11:06:59
 * @Description
 *              <p>
 *              MVB数据解析
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月3日 changwei.zheng@hirain.com 1.0 create file
 */
@Component("ECN")
public class ECNProcotolStream implements ProtocolStream {

	@Override
	public ParseResult read(String filename) throws Exception {
		return read(new File(filename));
	}

	@Override
	public ParseResult read(File file) throws Exception {
		if (file.exists()) {
			return ParseExcelUtil.parse(file);
		}
		return null;
	}

	@Override
	public void write(String filename, Object obj) throws Exception {
		JaxbUtil.marshall(obj, filename);
	}

}
