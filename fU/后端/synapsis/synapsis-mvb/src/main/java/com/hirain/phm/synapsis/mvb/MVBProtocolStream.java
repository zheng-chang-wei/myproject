package com.hirain.phm.synapsis.mvb;

import java.io.File;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.mvb.model.IRoot;
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
@Component("MVB")
public class MVBProtocolStream implements ProtocolStream {

	@Override
	public ParseResult read(String filename) throws Exception {
		final MCTExcelImportor importor = new MCTExcelImportor();
		final IRoot root = importor.loadExcel(filename);
		ParseResult result = new ParseResult();
		result.setData(root);
		return result;
	}

	@Override
	public ParseResult read(File file) throws Exception {
		return read(file.getAbsolutePath());
	}

	@Override
	public void write(String filename, Object obj) throws Exception {
		JaxbUtil.marshall(obj, filename);
	}

}
