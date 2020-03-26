/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.hirain.phm.synapsis.compress.QuickLzSupport;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.HeaderVariableGroup;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.parse.impl.DataFileParserImpl;
import com.hirain.phm.synapsis.parse.impl.DefaultVariableTypeHelper;
import com.hirain.phm.synapsis.parse.impl.InMemoryDataFileCache;
import com.hirain.phm.synapsis.result.domain.AlgorithmResult;
import com.hirain.phm.synapsis.result.message.AlgorithmResultMessage;
import com.hirain.phm.synapsis.result.param.ResultQueryParam;
import com.hirain.phm.synapsis.result.service.ResultService;
import com.hirain.phm.synapsis.result.service.impl.InMemoryAlgorithmResultService;
import com.hirain.phm.synapsis.result.service.impl.ResultDataLoaderImpl;
import com.hirain.phm.synapsis.result.service.impl.ResultServiceImpl;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 20, 2020 9:56:51 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 20, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class ResultServiceTest {

	private ResultService service;

	@Before
	public void setUp() {
		service = new ResultServiceImpl();
		((ResultServiceImpl) service).setService(new InMemoryAlgorithmResultService());
		((ResultServiceImpl) service).setQuery(new TestAlgorithmQuerySetting());
		ResultDataLoaderImpl loader = new ResultDataLoaderImpl();
		loader.setParser(new DataFileParserImpl());
		loader.setFileCache(new InMemoryDataFileCache());
		loader.setCompress(new QuickLzSupport());
		loader.setHelper(new DefaultVariableTypeHelper());
		loader.setResultFolder(System.getProperty("user.dir") + File.separator + "test-data");
		((ResultServiceImpl) service).setDataLoader(loader);
	}

	@Test
	public void test() {
		AlgorithmResultMessage message = create();
		service.parseAndSave(message);

		List<AlgorithmResult> results = service.listResults();
		assertEquals(1, results.size());

		AlgorithmResult result = results.get(0);
		assertEquals(message.getCode(), result.getCode().intValue());
		assertEquals(message.getFileType(), result.getFileType().intValue());
	}

	@Test
	public void testValue0() {
		AlgorithmResultMessage message = create();
		message.setValue(0);
		service.parseAndSave(message);

		List<AlgorithmResult> results = service.listResults();
		assertEquals(0, results.size());
	}

	@Test
	public void testList() {
		for (int i = 0; i < 5; i++) {
			service.parseAndSave(create());
		}

		List<AlgorithmResult> results = service.listResults();
		assertEquals(5, results.size());

		ResultQueryParam param = new ResultQueryParam();
		long current = System.currentTimeMillis();
		param.setEnd(new Date(current));
		param.setStart(new Date(current - 1000 * 60));
		List<AlgorithmResult> presults = service.listResults(param);
		assertEquals(5, presults.size());
	}

	@Test
	public void testGetData() throws Exception {
		AlgorithmResultMessage message = create();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = sdf.parse("20200211110000");
		message.setTimestamp(date);
		service.parseAndSave(message);
		List<AlgorithmResult> results = service.listResults();
		Long resultId = results.get(results.size() - 1).getId();
		FileHeader header = service.getFileHeader(resultId);
		assertNotNull(header);
		System.err.println(header);

		List<VariableGroup> selected = prepareSelected(header);
		List<VariableData> datas = service.getVariableDatas(resultId, selected);
		assertNotNull(datas);
		assertFalse(datas.isEmpty());
		System.out.println(datas.size());
	}

	/**
	 * @param headerList
	 * @return
	 */
	private List<VariableGroup> prepareSelected(FileHeader headerList) {
		List<HeaderVariableGroup> groups = headerList.getHeaders();
		List<VariableGroup> selectedVariables = new ArrayList<>();
		HeaderVariableGroup headerGroup = groups.get(0);
		VariableGroup variableGroup = new VariableGroup();
		variableGroup.setSlotId(headerGroup.getSlotId());
		variableGroup.setType(headerGroup.getType());
		List<Variable> variables = new ArrayList<>();
		variables.add(headerGroup.getVariables().get(0));
		variableGroup.setVariables(variables);
		selectedVariables.add(variableGroup);
		return selectedVariables;
	}

	/**
	 * @return
	 */
	public AlgorithmResultMessage create() {
		AlgorithmResultMessage message = new AlgorithmResultMessage();
		message.setCode(1);
		message.setFileType(AlgorithmResult.FILE_DATA);
		message.setTimestamp(new Date());
		message.setValue(1);
		return message;
	}

}
