/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.hirain.phm.synapsis.compress.QuickLzSupport;
import com.hirain.phm.synapsis.data.param.FileTreeNode;
import com.hirain.phm.synapsis.data.service.DataFileLoader;
import com.hirain.phm.synapsis.data.service.impl.DataFileLoaderImpl;
import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.HeaderVariableGroup;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.parse.impl.DataFileParserImpl;
import com.hirain.phm.synapsis.parse.impl.DefaultVariableTypeHelper;
import com.hirain.phm.synapsis.parse.impl.InMemoryDataFileCache;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 16, 2020 9:24:49 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 16, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class DataFileParseManagerTest {

	private DataFileLoader manager;

	private InMemoryDataFileCache cache;

	private static String FILE_ROOT = System.getProperty("user.dir") + File.separator + "test-data";

	@Before
	public void setUp() {
		cache = new InMemoryDataFileCache();
		manager = new DataFileLoaderImpl(new DataFileParserImpl(), cache);
	}

	@Test
	public void test() throws FileNotFoundException, SynapsisException {
		FileTreeNode node1 = new FileTreeNode();
		node1.setPath(FILE_ROOT + "\\2019年12月\\18日\\20191218150000");
		FileHeader headerList = manager.getVariables(Arrays.asList(node1));
		assertEquals(2, headerList.getHeaders().size());
		assertEquals("MVB", headerList.getHeaders().get(0).getType());
		System.out.println(headerList);

		Object object = cache.get(node1.getPath());
		assertNotNull(object);
	}

	@Test
	public void test2() throws Exception {
		FileTreeNode node1 = new FileTreeNode();
		node1.setPath(FILE_ROOT + "\\2019年12月\\18日\\20191218150000");
		FileTreeNode node2 = new FileTreeNode();
		node2.setPath(FILE_ROOT + "\\2019年12月\\18日\\20191218160000");
		FileHeader headerList = manager.getVariables(Arrays.asList(node1, node2));
		assertEquals(2, headerList.getHeaders().size());
		assertEquals("MVB", headerList.getHeaders().get(0).getType());
		System.out.println(headerList);
	}

	@Test(expected = SynapsisException.class)
	public void testException() throws SynapsisException {
		FileTreeNode node1 = new FileTreeNode();
		node1.setPath(FILE_ROOT + "\\2019年12月\\18日\\20191218160000");
		FileTreeNode node2 = new FileTreeNode();
		node2.setPath(FILE_ROOT + "\\2019年12月\\18日\\20191218170000");
		FileHeader headerList = manager.getVariables(Arrays.asList(node1, node2));
		assertEquals(2, headerList.getHeaders().size());
		assertEquals("MVB", headerList.getHeaders().get(0).getType());
		System.out.println(headerList);
	}

	@Test
	public void testDatas() throws SynapsisException, IOException {
		FileTreeNode node1 = new FileTreeNode();
		node1.setPath(FILE_ROOT + "\\2019年12月\\18日\\20191218150000");
		FileHeader headerList = manager.getVariables(Arrays.asList(node1));
		List<VariableGroup> selectedVariables = prepareSelected(headerList);
		injectHelper();
		FileTreeNode node2 = new FileTreeNode();
		node2.setPath(FILE_ROOT + "\\2019年12月\\18日\\20191218160000");
		parseDatasAndAssert(Arrays.asList(node1, node2), 1, selectedVariables,
				FILE_ROOT + "\\2019年12月\\18日\\20191218150000\\MVB_01_20191218150000.txt");
		parseDatasAndAssert(Arrays.asList(node1, node2), 2, selectedVariables,
				FILE_ROOT + "\\2019年12月\\18日\\20191218150000\\MVB_01_20191218150500.txt");
		parseDatasAndAssert(Arrays.asList(node1, node2), 3, selectedVariables,
				FILE_ROOT + "\\2019年12月\\18日\\20191218160000\\MVB_01_20191218160000.txt");
	}

	/**
	 * 
	 */
	private void injectHelper() {
		((DataFileLoaderImpl) manager).setHelper(new DefaultVariableTypeHelper());
		((DataFileLoaderImpl) manager).setCompress(new QuickLzSupport());
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
	 * @param nodes
	 * @param offset
	 * @param selectedVariables
	 * @param expectedPath
	 * @throws IOException
	 * @throws SynapsisException
	 */
	private void parseDatasAndAssert(List<FileTreeNode> nodes, int offset, List<VariableGroup> selectedVariables, String expectedPath)
			throws IOException, SynapsisException {
		long time1 = System.currentTimeMillis();
		List<VariableData> variableDatas = manager.getVariableDatas(nodes, offset, selectedVariables);
		long time2 = System.currentTimeMillis();
		System.out.println(time2 - time1);
		assertNotNull(variableDatas);
		assertEquals(1, variableDatas.size());

		File expectedFile = new File(expectedPath);
		byte[] bs = FileUtils.readFileToByteArray(expectedFile);
		QuickLzSupport support = new QuickLzSupport();
		byte[] datas = support.decompress(bs);
		ByteBuffer buffer = ByteBuffer.wrap(datas).order(ByteOrder.LITTLE_ENDIAN);
		buffer.position(14);
		double expected = buffer.getInt();
		double value = variableDatas.get(0).getValues().get(0).getValue();
		assertEquals(expected, value, 0.01);
	}

}
