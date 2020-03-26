package com.hirain.phm.synapsis.ecn.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.hirain.phm.synapsis.ecn.model.BusInterface;
import com.hirain.phm.synapsis.ecn.model.BusInterfaceList;
import com.hirain.phm.synapsis.ecn.model.ComParameter;
import com.hirain.phm.synapsis.ecn.model.ComParameterList;
import com.hirain.phm.synapsis.ecn.model.DataSet;
import com.hirain.phm.synapsis.ecn.model.DataSetList;
import com.hirain.phm.synapsis.ecn.model.Destination;
import com.hirain.phm.synapsis.ecn.model.Device;
import com.hirain.phm.synapsis.ecn.model.DeviceConfiguration;
import com.hirain.phm.synapsis.ecn.model.Element;
import com.hirain.phm.synapsis.ecn.model.MdComParameter;
import com.hirain.phm.synapsis.ecn.model.MdParameter;
import com.hirain.phm.synapsis.ecn.model.PdComParameter;
import com.hirain.phm.synapsis.ecn.model.PdParameter;
import com.hirain.phm.synapsis.ecn.model.SheetAndLine;
import com.hirain.phm.synapsis.ecn.model.Source;
import com.hirain.phm.synapsis.ecn.model.Telegram;
import com.hirain.phm.synapsis.ecn.model.TrdpProcess;
import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.util.ExcelUtil;

public class ParseExcelUtil {

	// 必填
	private static final String REQUISITE = "1";

	// 选填或没有范围
	private static final String OPTIONAL = "0";

	// 没有默认值ֵ
	private static final String NO_DEFAULT = "-1";

	// UINT32的范围0-4294967295
	private static final String EXTENT_UINT32 = "uint32";

	private static final String EXTENT_INT32 = "int32";

	private static final String EXTENT_FLOAT = "float";

	private static List<SheetAndLine> all = new ArrayList<>();

	public static ParseResult parse(File file) throws Exception {
		Device device = parseExcel(file);
		List<String[]> parameScopeList = initParameScope();
		List<String> resultList = verifyValues(parameScopeList, device);
		ParseResult result = new ParseResult();
		result.setData(device);
		result.setErrors(resultList);
		all.clear();
		return result;
	}

	private static Device parseExcel(File file) throws Exception {
		Device device = null;
		if (file.exists()) {
			Workbook workbook = ExcelUtil.createWorkbook(file);
			int numberOfSheets = workbook.getNumberOfSheets();
			BusInterfaceList busInterfaceList = new BusInterfaceList();
			List<BusInterface> busInterfaces = new ArrayList<>();
			busInterfaceList.setBusInterfaces(busInterfaces);
			for (int index = 0; index < numberOfSheets; index++) {
				Sheet sheet = workbook.getSheetAt(index);
				String sheetName = sheet.getSheetName();
				int lastRowNum = sheet.getLastRowNum();
				if ("Device & Bus".equals(sheetName)) {
					for (int i = 0; i <= lastRowNum; i++) {
						Row row = sheet.getRow(i);
						for (int j = 0; j < row.getLastCellNum(); j++) {
							Cell cell = row.getCell(j);
							String contents = ExcelUtil.getCellValue(cell);
							if ("Device Parameters".equals(contents)) {
								device = new Device();
								device.setBusInterfaceList(busInterfaceList);
								setValue(sheet, device, i, sheetName);
							} else if ("Device Configuration Parameters".equals(contents)) {
								DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
								setValue(sheet, deviceConfiguration, i, sheetName);
								if (deviceConfiguration.getMemorySize() != null && !deviceConfiguration.getMemorySize().isEmpty()) {
									device.setDeviceConfiguration(deviceConfiguration);
								}
							}
						}
					}
				} else if ("Bus_Interface_1".equals(sheetName)) {
					getBusInterface(sheet, device, busInterfaceList, busInterfaces);
				} else if ("Com-parameter".equals(sheetName)) {
					List<ComParameter> comParameters = new ArrayList<>();

					for (int i = 0; i <= lastRowNum; i++) {
						Row row = sheet.getRow(i);
						for (int j = 0; j < row.getLastCellNum(); j++) {
							Cell cell = row.getCell(j);
							String contents = ExcelUtil.getCellValue(cell);
							if ("Communication Parameters".equals(contents)) {
								ComParameterList comParameterList = new ComParameterList();
								device.setComParameterList(comParameterList);
								comParameterList.setComParameter(comParameters);
								if (isAllNull(sheet, i + 2)) {
									ComParameter comParameter = new ComParameter();
									setValue(sheet, comParameter, i, sheetName);
									comParameters.add(comParameter);
								}
								for (int k = i + 2; k <= lastRowNum; k++) {
									if (isAllNull(sheet, k)) {
										break;
									} else {
										ComParameter comParameter = new ComParameter();
										setValue(sheet, comParameter, i, k - 2, sheetName);
										comParameters.add(comParameter);
									}
								}
							}
						}
					}
				} else if (sheetName.equals("Dataset_1")) {
					DataSetList dataSetList = new DataSetList();
					device.setDataSetList(dataSetList);
					List<DataSet> dataSets = new ArrayList<>();
					dataSetList.setDataSet(dataSets);
					DataSet dataSet = null;
					for (int i = 0; i <= lastRowNum; i++) {
						Row row = sheet.getRow(i);
						if (row == null) {
							continue;
						}
						for (int j = 0; j < row.getLastCellNum(); j++) {
							Cell cell = row.getCell(j);
							String contents = ExcelUtil.getCellValue(cell);
							if ("Dataset Parameters".equals(contents)) {
								dataSet = new DataSet();
								setValue(sheet, dataSet, i, sheetName);
								dataSets.add(dataSet);
							} else if ("Dataset Element".equals(contents)) {
								List<Element> elementList = new ArrayList<>();
								dataSet.setElement(elementList);
								if (isAllNull(sheet, i + 2)) {
									Element element = new Element();
									setValue(sheet, element, i, sheetName);
									elementList.add(element);
								}
								for (int k = i + 2; k <= lastRowNum; k++) {
									if (isAllNull(sheet, k)) {
										break;
									} else {
										Element element = new Element();
										setValue(sheet, element, i, k - 2, sheetName);
										elementList.add(element);
									}
								}
							}

						}
					}
				}
			}
		}
		return device;
	}

	private static void getBusInterface(Sheet sheet, Device device, BusInterfaceList busInterfaceList, List<BusInterface> busInterfaces)
			throws IllegalAccessException {
		String sheetName = sheet.getSheetName();
		List<Telegram> telegramList = null;
		BusInterface busInterface = null;
		Telegram telegram = null;
		List<Source> sourceList = null;
		List<Destination> destinationList = null;
		int lastRowNum = sheet.getLastRowNum();
		for (int i = 0; i <= lastRowNum; i++) {
			Row row = sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				String contents = ExcelUtil.getCellValue(row.getCell(j));
				if ("Bus Interface Configuration".equals(contents)) {
					busInterface = new BusInterface();
					setValue(sheet, busInterface, i, sheetName);
					busInterfaces.add(busInterface);
				} else if ("Process Configuration".equals(contents)) {
					TrdpProcess trdpProcess = new TrdpProcess();
					setValue(sheet, trdpProcess, i, sheetName);
					busInterface.setTrdpProcess(trdpProcess);

				} else if ("PD Communication Parameters".equals(contents)) {
					PdComParameter pdComParameter = new PdComParameter();
					setValue(sheet, pdComParameter, i, sheetName);
					busInterface.setPdComParameter(pdComParameter);
				} else if ("MD Communication Parameters".equals(contents)) {
					MdComParameter mdComParameter = new MdComParameter();
					setValue(sheet, mdComParameter, i, sheetName);
					busInterface.setMdComParameter(mdComParameter);
				} else if ("Telegram Configuration".equals(contents)) {
					sourceList = new ArrayList<>();
					destinationList = new ArrayList<>();
					if (telegramList == null) {
						telegramList = new ArrayList<>();
						busInterface.setTelegram(telegramList);
					}
					telegram = new Telegram();
					setValue(sheet, telegram, i, sheetName);
					telegramList.add(telegram);

					telegram.setSource(sourceList);
					telegram.setDestination(destinationList);

				} else if ("PD Parameters".equals(contents)) {
					PdParameter pdParameter = new PdParameter();
					setValue(sheet, pdParameter, i, sheetName);
					telegram.setPdParameter(pdParameter);
				} else if ("MD Parameters".equals(contents)) {
					MdParameter mdParameter = new MdParameter();
					setValue(sheet, mdParameter, i, sheetName);
					telegram.setMdParameter(mdParameter);
				} else if ("Source Parameters".equals(contents)) {
					for (int k = i + 2; k <= lastRowNum; k++) {
						if (isAllNull(sheet, k)) {
							break;
						} else {
							Source source = new Source();
							setValue(sheet, source, i, k - 2, sheetName);
							sourceList.add(source);
						}
					}
					if (isAllNull(sheet, i + 2) && isAllNull(sheet, i + 3)) {
						Source source = new Source();
						setValue(sheet, source, i, sheetName);
						sourceList.add(source);
					}
				} else if ("Destination Parameters".equals(contents)) {
					if (isAllNull(sheet, i + 2)) {
						Destination destination = new Destination();
						setValue(sheet, destination, i, sheetName);
						destinationList.add(destination);
					}
					for (int k = i + 2; k <= lastRowNum; k++) {
						if (isAllNull(sheet, k)) {
							break;
						} else {
							Destination destination = new Destination();
							setValue(sheet, destination, i, k - 2, sheetName);
							destinationList.add(destination);
						}
					}
				}
			}
		}
	}

	/**
	 * 判断一行数据是否全为空
	 * 
	 * @param sheet
	 *            页
	 * @param row
	 *            所在行
	 * @return true 表示全为空，false表示不全为空
	 */
	private static boolean isAllNull(Sheet sheet, int rowNum) {
		Row row = sheet.getRow(rowNum);
		if (row == null) {
			return true;
		}
		for (int j = 0; j < row.getLastCellNum(); j++) {
			String paramName = ExcelUtil.getCellValue(row.getCell(j));
			if (paramName != null && !"".equals(paramName)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param sheet
	 * @param obj
	 * @param row
	 * @param sheetName
	 * @throws IllegalAccessException
	 */
	private static void setValue(Sheet sheet, SheetAndLine obj, int rowNum, String sheetName) throws IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();
		obj.setSheetName(sheetName);
		obj.setLineNum(String.valueOf(rowNum + 3));
		all.add(obj);
		Row row = sheet.getRow(rowNum);
		for (int k = 0; k < row.getLastCellNum(); k++) {
			String paramName = ExcelUtil.getCellValue(sheet.getRow(rowNum + 1).getCell(k));
			String value = ExcelUtil.getCellValue(sheet.getRow(rowNum + 2).getCell(k));
			for (Field field : fields) {
				if (field.getName().equals(paramName)) {
					field.setAccessible(true);
					field.set(obj, value);
				}
			}
		}
	}

	private static void setValue(Sheet sheet, SheetAndLine obj, int i, int j, String sheetName) throws IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();
		obj.setSheetName(sheetName);
		obj.setLineNum(String.valueOf(j + 3));
		all.add(obj);
		Row row = sheet.getRow(j);
		for (int k = 0; k < row.getLastCellNum(); k++) {
			String paramName = ExcelUtil.getCellValue(sheet.getRow(i + 1).getCell(k));
			Cell cell = sheet.getRow(j + 2).getCell(k);
			if (cell != null) {
				String value = ExcelUtil.getCellValue(cell);
				for (Field field : fields) {
					if (field.getName().equals(paramName)) {
						field.setAccessible(true);
						field.set(obj, value);
					}
				}
			}
		}
	}

	/**
	 * 初始化校验值
	 * 
	 * @return
	 */
	private static List<String[]> initParameScope() {
		List<String[]> list = new ArrayList<>();
		// hostName,1,0,-1:分别表示参数名，必填（1）还是选填（0）,范围 0表示没有范围,默认值 -1表示没有默认值ֵ
		String[] device = { "Device", "hostName", REQUISITE, OPTIONAL, NO_DEFAULT, "leaderName", OPTIONAL, OPTIONAL, NO_DEFAULT, "type", OPTIONAL,
				OPTIONAL, NO_DEFAULT };
		String[] deviceConfiguration = { "DeviceConfiguration", "memorySize", OPTIONAL, EXTENT_UINT32, NO_DEFAULT };

		String[] busInterface = { "BusInterface", "networkId", REQUISITE, "1-4", NO_DEFAULT, "name", REQUISITE, OPTIONAL, NO_DEFAULT, "hostIp",
				OPTIONAL, OPTIONAL, NO_DEFAULT, "leaderIp", OPTIONAL, OPTIONAL, NO_DEFAULT };
		String[] trdpProcess = { "TrdpProcess", "cycleTime", OPTIONAL, EXTENT_UINT32, NO_DEFAULT, "blocking", OPTIONAL, OPTIONAL, "no",
				"trafficShaping", OPTIONAL, OPTIONAL, "on", "priority", OPTIONAL, "1-255", NO_DEFAULT };
		String[] pdComParameter = { "PdComParameter", "timeOutValue", OPTIONAL, EXTENT_UINT32, "100000", "validityBehaviour", OPTIONAL, OPTIONAL,
				"zero", "ttl", OPTIONAL, EXTENT_UINT32, "64", "qos", OPTIONAL, "0-7", "5", "marshall", OPTIONAL, OPTIONAL, "off", "callback",
				OPTIONAL, OPTIONAL, "off", "port", OPTIONAL, EXTENT_UINT32, NO_DEFAULT };
		String[] mdComParameter = { "MdComParameter", "confirmTimeOut", OPTIONAL, EXTENT_UINT32, "1000000", "replyTimeOut", OPTIONAL, EXTENT_UINT32,
				"5000000", "connectTimeOut", OPTIONAL, EXTENT_UINT32, "6000000", "ttl", OPTIONAL, EXTENT_UINT32, "64", "qos", OPTIONAL, "0-7", "3",
				"protocol", OPTIONAL, OPTIONAL, "UDP", "marshall", OPTIONAL, OPTIONAL, "off", "callback", OPTIONAL, OPTIONAL, "on", "udpPort",
				OPTIONAL, EXTENT_UINT32, NO_DEFAULT, "tcpPort", OPTIONAL, EXTENT_UINT32, NO_DEFAULT, "numSessions", OPTIONAL, EXTENT_UINT32, "1000" };
		String[] telegram = { "Telegram", "name", OPTIONAL, OPTIONAL, NO_DEFAULT, "comId", REQUISITE, EXTENT_UINT32, NO_DEFAULT, "datasetId",
				OPTIONAL, EXTENT_UINT32, NO_DEFAULT, "comParameterId", OPTIONAL, EXTENT_UINT32, NO_DEFAULT };
		String[] pdParameter = { "PdParameter", "timeOutValue", OPTIONAL, EXTENT_UINT32, "100000", "validityBehaviour", OPTIONAL, OPTIONAL, "zero",
				"cycle", REQUISITE, EXTENT_UINT32, NO_DEFAULT, "redundant", OPTIONAL, OPTIONAL, NO_DEFAULT, "marshall", OPTIONAL, OPTIONAL, "off",
				"callback", OPTIONAL, OPTIONAL, "off", "offsetAddress", OPTIONAL, OPTIONAL, NO_DEFAULT };
		String[] mdParameter = { "MdParameter", "confirmTimeOut", OPTIONAL, EXTENT_UINT32, "100000", "replyTimeOut", OPTIONAL, EXTENT_UINT32,
				"5000000", "marshall", OPTIONAL, OPTIONAL, "off", "callback", OPTIONAL, OPTIONAL, "on", "protocol", OPTIONAL, OPTIONAL, "UDP" };
		String[] source = { "Source", "id", REQUISITE, EXTENT_UINT32, NO_DEFAULT, "uri1", REQUISITE, OPTIONAL, NO_DEFAULT, "uri2", OPTIONAL, OPTIONAL,
				NO_DEFAULT, "name", OPTIONAL, OPTIONAL, NO_DEFAULT };
		String[] destination = { "Destination", "id", REQUISITE, EXTENT_UINT32, NO_DEFAULT, "uri", REQUISITE, OPTIONAL, NO_DEFAULT, "name", OPTIONAL,
				OPTIONAL, NO_DEFAULT };

		String[] comParameter = { "ComParameter", "id", REQUISITE, EXTENT_UINT32, NO_DEFAULT, "qos", OPTIONAL, "0-7", NO_DEFAULT, "ttl", OPTIONAL,
				EXTENT_UINT32, "64", "retries", OPTIONAL, EXTENT_UINT32, "3" };

		String[] dataSet = { "DataSet", "name", OPTIONAL, OPTIONAL, NO_DEFAULT, "id", REQUISITE, EXTENT_UINT32, NO_DEFAULT };

		String[] element = { "Element", "name", OPTIONAL, OPTIONAL, NO_DEFAULT, "type", REQUISITE, OPTIONAL, NO_DEFAULT, "arraySize", OPTIONAL,
				EXTENT_UINT32, "1", "unit", OPTIONAL, OPTIONAL, NO_DEFAULT, "scale", OPTIONAL, EXTENT_FLOAT, NO_DEFAULT, "offset", OPTIONAL,
				EXTENT_INT32, NO_DEFAULT };

		list.add(device);
		list.add(deviceConfiguration);
		list.add(busInterface);
		list.add(trdpProcess);
		list.add(pdComParameter);
		list.add(mdComParameter);
		list.add(telegram);
		list.add(pdParameter);
		list.add(mdParameter);
		list.add(source);
		list.add(destination);
		list.add(comParameter);
		list.add(dataSet);
		list.add(element);
		return list;
	}

	/**
	 * 校验属性值的合法性
	 * 
	 * @param device
	 * @return
	 * @throws Exception
	 */
	private static List<String> verifyValues(List<String[]> parameScopeList, Device device) throws Exception {
		List<String> resultList = new ArrayList<>();
		// 校验comId和dataSetId是否合法
		verifyComIdAndDataSetId(device, resultList);
		for (SheetAndLine obj : all) {
			String name = obj.getClass().getName();
			for (String[] parameScopes : parameScopeList) {
				if (name.equals("com.hirain.phm.synapsis.ecn.model." + parameScopes[0])) {
					List<String> list = test(obj, parameScopes);
					resultList.addAll(list);
				}
			}
			obj.setLineNum(null);
			obj.setSheetName(null);
		}
		return resultList;
	}

	/**
	 * 校验comId和dataSetId是否合法
	 * 
	 * @param device
	 * @param resultList
	 */
	private static void verifyComIdAndDataSetId(Device device, List<String> resultList) {
		List<BusInterface> busInterfaces = device.getBusInterfaceList().getBusInterfaces();
		List<DataSet> dataSets = device.getDataSetList().getDataSet();
		// 存放所有的DataSet中的dataSetId
		List<String> datasetIdList = new ArrayList<>();
		for (DataSet dataSet : dataSets) {
			datasetIdList.add(dataSet.getId());
		}
		// 存放所有的comId
		List<String> comIdList = new ArrayList<>();
		// 存放所有的Telegram中的dataSetId
		List<String> telegramDatasetIdList = new ArrayList<>();
		// 存放所有的Telegram
		List<Telegram> allTelegramList = new ArrayList<>();
		// 将数据添加到对应的集合中
		for (BusInterface busInterface : busInterfaces) {
			List<Telegram> telegramList = busInterface.getTelegram();
			for (Telegram telegram : telegramList) {
				String comId = telegram.getComId();
				String datasetId = telegram.getDatasetId();
				comIdList.add(comId);
				telegramDatasetIdList.add(datasetId);
				allTelegramList.add(telegram);
			}
		}
		for (int i = 0; i < telegramDatasetIdList.size(); i++) {
			String dataSetId = telegramDatasetIdList.get(i);
			if (dataSetId != null && !datasetIdList.contains(dataSetId)) {
				Telegram telegram = allTelegramList.get(i);
				resultList.add(telegram.getSheetName() + "页，第" + telegram.getLineNum() + "行dataSetId不存在");
				telegramDatasetIdList.remove(i);
				comIdList.remove(i);
				allTelegramList.remove(i);
				i--;
			}
		}
		// 将第i个comId，dataSetId组合和之前的比较,如果comId相同而dataSetId不同，说明不合法，相同的comId必须对应相同的dataSetId
		for (int i = 0; i < comIdList.size(); i++) {
			String comId = comIdList.get(i);
			String dataSetId = telegramDatasetIdList.get(i);
			for (int j = 0; j < i; j++) {
				if (comId.equals(comIdList.get(j))) {
					if (!dataSetId.equals(telegramDatasetIdList.get(j))) {
						Telegram telegram = allTelegramList.get(i);
						resultList.add(telegram.getSheetName() + "页，第" + telegram.getLineNum() + "行comId和dataSetId不合法");
						break;
					}
				}
			}
		}
	}

	private static List<String> test(SheetAndLine obj, String[] parameScopes) throws Exception {
		List<String> resultList = new ArrayList<>();
		Class<?> clazz = obj.getClass();
		// 设置Telegram的datasetId是否为必填
		if (clazz.getName().equals(Telegram.class.getName())) {
			Field mdParameterField = clazz.getDeclaredField("mdParameter");
			mdParameterField.setAccessible(true);
			MdParameter mdParameter = (MdParameter) mdParameterField.get(obj);
			if (mdParameter != null) {
				Field[] fields = mdParameter.getClass().getDeclaredFields();
				int j = 1;
				for (j = 1; j < parameScopes.length; j = j + 4) {
					if (parameScopes[j].equals("datasetId")) {
						break;
					}
				}
				if (isAllNull(fields, mdParameter)) {
					parameScopes[j + 1] = OPTIONAL;
				} else {
					parameScopes[j + 1] = REQUISITE;
				}
			}

		}

		String sheetName = obj.getSheetName();
		String lineNum = obj.getLineNum();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				for (int i = 1; i < parameScopes.length; i = i + 4) {
					// 属性名是否与数组中的相同
					if (field.getName().equals(parameScopes[i])) {
						String value = (String) field.get(obj);
						// 判断是否为空
						if ("".equals(value) || value == null) {
							// 判断是否为必填项，1表示必填
							if (parameScopes[i + 1].equals(REQUISITE)) {
								resultList.add(sheetName + "页，第" + lineNum + "行" + field.getName() + "不能为空");
							} else if (!parameScopes[i + 3].equals(NO_DEFAULT)) {
								// 判断是否有默认值，-1表示没有默认值ֵ
								field.set(obj, parameScopes[i + 3]);
							} else {
								// 非必填项，为空的话设为null，xml中不显示该属性
								field.set(obj, null);
							}
						} else {
							// 如果字段是ip或者uri要验证是否符合规则
							if (field.getName().contains("Ip") || field.getName().contains("uri")) {
								String pattan = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";
								boolean isMatch = Pattern.matches(pattan, value);
								if (!isMatch) {
									resultList.add(sheetName + "页，第" + lineNum + "行" + field.getName() + "不合法");
								}
							} else {
								String extent = parameScopes[i + 2];
								boolean b = verifyExtent(extent, value);
								if (!b) {
									resultList.add(sheetName + "页，第" + lineNum + "行" + field.getName() + "不合法");
								}
							}

						}
					}
				}

			}
		}
		return resultList;
	}

	/**
	 * 判断某个类中的属性是否全为空
	 * 
	 * @param fields
	 * @param obj
	 * @return true 表示全为空，false 表示不全为空
	 * @throws Exception
	 */
	private static boolean isAllNull(Field[] fields, SheetAndLine obj) throws Exception {
		for (Field field : fields) {
			field.setAccessible(true);
			Object anObject = field.get(obj);
			if (anObject != null && !"".equals(anObject)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 校验数据是否在范围内
	 * 
	 * @param extent
	 * @param value
	 * @return
	 */
	private static boolean verifyExtent(String extent, String value) {
		if (extent.equals("uint32")) {
			// 判断value是否为 正整数
			if (value.matches("^[0-9]*$")) {
				long parseLong = Long.parseLong(value);
				// 判断数据是否在范围内
				if (parseLong < 0L || parseLong > 4294967295L) {
					return false;
				}
			} else {
				return false;
			}
		} else if (extent.equals("int32")) {
			// 判断value是否为 整数
			if (value.matches("^-?[0-9]*$")) {
				long parseLong = Long.parseLong(value);
				// 判断数据是否在范围内
				if (parseLong < -2147483647L || parseLong > 2147483648L) {
					return false;
				}
			} else {
				return false;
			}
		} else if (extent.equals("float")) {
			// 判断value是否为 float
			if (!value.matches("^[+-]?(([0-9]+)([.]([0-9]+))?)$")) {
				return false;
			}
		} else if (extent.equals(OPTIONAL)) {

		} else {
			long parseLong = Long.parseLong(value);
			String[] split = extent.split("-");
			// 判断数据是否在范围内
			if (parseLong < Long.parseLong(split[0]) || parseLong > Long.parseLong(split[1])) {
				return false;
			}
		}
		return true;
	}
}
