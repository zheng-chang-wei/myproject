/*******************************************************************************
 * Copyright (c) 2016, 2016 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import com.hirain.phm.synapsis.mvb.model.IDataType;
import com.hirain.phm.synapsis.mvb.model.IDatagram;
import com.hirain.phm.synapsis.mvb.model.IDeviceStructure;
import com.hirain.phm.synapsis.mvb.model.IPortStructure;
import com.hirain.phm.synapsis.mvb.model.IRoot;
import com.hirain.phm.synapsis.mvb.model.ISignal;
import com.hirain.phm.synapsis.mvb.model.impl.CacheModel;
import com.hirain.phm.synapsis.mvb.model.impl.Root;
import com.hirain.phm.synapsis.mvb.rows.AbstractExcelReader;
import com.hirain.phm.synapsis.mvb.rows.DevicesRowLoader;
import com.hirain.phm.synapsis.mvb.rows.PortsRowLoader;
import com.hirain.phm.synapsis.mvb.rows.SignalTypesRowLoader;
import com.hirain.phm.synapsis.mvb.rows.SignalsRowLoader;
import com.hirain.phm.synapsis.mvb.util.ExcelConstants;
import com.hirain.phm.synapsis.mvb.util.MVBExcelUtil;
import com.hirain.phm.synapsis.mvb.util.StringUtil;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 上午10:51:55
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
public class MCTExcelImportor extends AbstractExcelReader {

	/**
	 * @see com.hirain.common.excel.read.api.IReadExcel#getExcelModel(java.lang.String)
	 */
	@Override
	public Object getExcelModel(final String excelPath) throws Exception {
		// final long currentTimeMillis = System.currentTimeMillis();
		final IRoot data = loadExcel(excelPath);
		// System.out.println("解析Excel耗时：" + (System.currentTimeMillis() - currentTimeMillis) + " -->" + excelPath);
		return data;
	}

	/**
	 * 加载Excel数据到excel数据模型(根据业务需求自定义)，并返回模型
	 * 
	 * @throws Exception
	 */
	public IRoot loadExcel(final String excelPath) throws Exception {
		// final long currentTimeMillis = System.currentTimeMillis();
		final IRoot root = new Root();
		root.setCacheModel(new CacheModel());

		final Map<String, IDatagram> datagrams = new HashMap<>();
		final Map<String, Short> deviceAddrMap = new HashMap<>();

		final Map<Integer, String> mapIndexValue = MVBExcelUtil.readCellValue(excelPath, ExcelConstants.SHEET_NAME_COVER,
				ExcelConstants.SHEET_COVER_VERSION_ROW_INDEX);
		if (mapIndexValue.containsKey(0)) {
			final String excelVersion = mapIndexValue.get(0);
			if (excelVersion == null) {
				throw new Exception("no version");
			}
			root.setAppVersion(excelVersion.replace(StringUtil.APP_VERSION, StringUtil.EMPTY));
		} else {
			root.setAppVersion(StringUtil.EMPTY);
		}

		/* 类型声明 */
		final SignalTypesRowLoader signalTypeRowLoader = new SignalTypesRowLoader(ExcelConstants.SHEET_NAME_TYPES, ExcelConstants.SHEET_HEADER_TYPES);
		final List<IDataType> createSignalTypes = new ArrayList<>();
		MVBExcelUtil.loadExcelSheet(excelPath, createSignalTypes, signalTypeRowLoader);
		// System.out.println("=============类型=================" + (System.currentTimeMillis() - currentTimeMillis));

		for (final IDataType dataType : createSignalTypes) {
			root.getDataTypes().add(dataType);
		}

		/* 读取类型声明表的时候要将 自定义数据类型 和 标准数据类型 的相关关系映射到map中 */
		for (final IDataType dataType : createSignalTypes) {
			if (!root.getCacheModel().getDataTypesKeyName().containsKey(dataType.getName())) {
				root.getCacheModel().getDataTypesKeyName().put(dataType.getName(), dataType);
			}
		}

		/* 设备信息 */
		final DevicesRowLoader devicesRowLoader = new DevicesRowLoader(ExcelConstants.SHEET_NAME_DEVICES, ExcelConstants.SHEET_HEADER_DEVICES);
		devicesRowLoader.setDeviceAddrMap(deviceAddrMap);
		final List<IDeviceStructure> deviceStructures = new ArrayList<>();

		// currentTimeMillis = System.currentTimeMillis();
		MVBExcelUtil.loadExcelSheet(excelPath, deviceStructures, devicesRowLoader);
		// System.out.println("=============设备=================" + (System.currentTimeMillis() - currentTimeMillis));

		/* 端口信息 */
		final PortsRowLoader portsRowLoader = new PortsRowLoader(ExcelConstants.SHEET_NAME_PORTS, ExcelConstants.SHEET_HEADER_PORTS);
		portsRowLoader.setDatagrams(datagrams);
		portsRowLoader.setDeviceAddrMap(deviceAddrMap);
		final List<IPortStructure> portStructures = new ArrayList<>();

		// currentTimeMillis = System.currentTimeMillis();
		MVBExcelUtil.loadExcelSheet(excelPath, portStructures, portsRowLoader);
		// System.out.println("=============端口=================" + (System.currentTimeMillis() - currentTimeMillis));

		for (final IPortStructure iPortStructure : portStructures) {
			root.getPorts().add(iPortStructure.getPort());
		}

		/* 信号信息 */
		final List<String> sheetNames = Arrays.asList(ExcelConstants.SHEET_NAMES);
		final List<Sheet> sheets = MVBExcelUtil.getSheets(excelPath);
		for (final Sheet sheet : sheets) {
			final String sheetName = sheet.getSheetName();
			if (sheetNames.contains(sheetName)) {
				continue;
			}
			final SignalsRowLoader signalsRowLoader = new SignalsRowLoader(sheetName, ExcelConstants.SHEET_HEADER_SIGNALS);
			signalsRowLoader.setExcelModel(root);
			signalsRowLoader.setDatagrams(datagrams);
			final List<ISignal> signals = new ArrayList<>();
			// currentTimeMillis = System.currentTimeMillis();
			MVBExcelUtil.loadExcelSheet(excelPath, signals, signalsRowLoader);
			// System.out.println("=============信号=================" + (System.currentTimeMillis() - currentTimeMillis));
		}
		// createDefaultDevice(createRoot);
		// verifySequence(excelModel);

		for (final IDatagram datagram : datagrams.values()) {
			root.getDatagrams().add(datagram);
		}

		for (final IDeviceStructure deviceStructure : deviceStructures) {
			for (final IPortStructure portStructure : portStructures) {
				final List<IPortStructure> children = deviceStructure.getChildren();
				if (portStructure.getDeviceAddrSource() == deviceStructure.getAddress()) {
					final List<IPortStructure> portStructureSource = deviceStructure.getPortStructureSource();
					if (!portStructureSource.contains(portStructure)) {
						portStructureSource.add(portStructure);
					}
					if (!children.contains(portStructure)) {
						children.add(portStructure);
					}
				}
				if (portStructure.getDeviceAddrSinks().contains(String.valueOf(deviceStructure.getAddress()))) {
					final List<IPortStructure> portStructureSink = deviceStructure.getPortStructureSink();
					if (!portStructureSink.contains(portStructure)) {
						portStructureSink.add(portStructure);
					}
					if (!children.contains(portStructure)) {
						children.add(portStructure);
					}
				}
			}

			root.getDeviceStructures().add(deviceStructure);
			root.getDevices().add(deviceStructure.getDevice());
		}
		root.setName(ExcelConstants.ROOT_NAME_DEFAULT);
		root.setBusType(ExcelConstants.ROOT_TYPE_DEFAULT);
		verifySignalOffsetInDatagram(root);
		return root;
	}

	/**
	 * @param root
	 * @throws Exception
	 */
	private void verifySignalOffsetInDatagram(final IRoot root) throws Exception {
		final Map<String, IDatagram> idDatagrams = new HashMap<>();
		final Map<String, Integer> idMaxPostions = new HashMap<>();
		final Map<String, ISignal> idMaxPostionSignals = new HashMap<>();
		// 缓存报文的最大偏移信号位置
		final List<IDatagram> datagrams = root.getDatagrams();
		for (final IDatagram iDatagram : datagrams) {
			idDatagrams.put(iDatagram.getId(), iDatagram);
			final List<ISignal> signals = iDatagram.getSignals();
			int positionMax = 0;
			for (final ISignal iSignal : signals) {
				final int position = iSignal.getByteOffset() * 8 + iSignal.getBitOffset() + iSignal.getLength();
				if (position > positionMax) {
					positionMax = position;
					idMaxPostionSignals.put(iDatagram.getId(), iSignal);
				}
			}
			idMaxPostions.put(iDatagram.getId(), positionMax);
		}
		// 比较
		final List<IDeviceStructure> deviceStructures = root.getDeviceStructures();
		for (final IDeviceStructure iDeviceStructure : deviceStructures) {
			final List<IPortStructure> children = iDeviceStructure.getChildren();
			for (final IPortStructure iPortStructure : children) {
				final String datagramId = iPortStructure.getDatagramId();
				final Integer positionMax = idMaxPostions.get(datagramId);
				if (positionMax == null) {
					continue;
				}
				final int size = iPortStructure.getPort().getSize();
				if (positionMax > size) {
					final IDatagram iDatagram = idDatagrams.get(datagramId);
					final String name = iDatagram.getName();
					final short address = iPortStructure.getPort().getAddress();
					final String hexString = "0x" + Integer.toHexString(address).toUpperCase();
					final ISignal iSignal = idMaxPostionSignals.get(datagramId);
					throw new Exception("信号的偏移量超过了端口的宽度。报文" + name + ", 端口" + hexString + ", 变量" + iSignal.getSignalName());
				}
				idMaxPostions.remove(datagramId);
			}
		}
	}

	public static void main(final String[] args) {
		final MCTExcelImportor importer = new MCTExcelImportor();
		try {
			final Object excelModel = importer.getExcelModel("G:\\desktop\\新MCT\\MCT数据流模板_20161221.xlsx");
			System.out.println(excelModel);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
