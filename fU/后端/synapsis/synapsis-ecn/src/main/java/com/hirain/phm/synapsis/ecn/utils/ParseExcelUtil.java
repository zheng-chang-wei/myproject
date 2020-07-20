package com.hirain.phm.synapsis.ecn.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.StringUtils;

import com.hirain.phm.synapsis.ecn.model.BusInterface;
import com.hirain.phm.synapsis.ecn.model.BusInterfaceList;
import com.hirain.phm.synapsis.ecn.model.ComParameter;
import com.hirain.phm.synapsis.ecn.model.ComParameterList;
import com.hirain.phm.synapsis.ecn.model.DataSet;
import com.hirain.phm.synapsis.ecn.model.DataSetList;
import com.hirain.phm.synapsis.ecn.model.Destination;
import com.hirain.phm.synapsis.ecn.model.Device;
import com.hirain.phm.synapsis.ecn.model.Element;
import com.hirain.phm.synapsis.ecn.model.SheetAndLine;
import com.hirain.phm.synapsis.ecn.model.Source;
import com.hirain.phm.synapsis.ecn.model.Telegram;
import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.util.ExcelUtil;

public class ParseExcelUtil {

	// 必填
	private static final String REQUISITE = "1";

	// 选填
	private static final String OPTIONAL = "0";

	/**
	 * 没有范围
	 */
	public static final String NO_SCOPE = "0";

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
			for (int index = 0; index < numberOfSheets; index++) {
				Sheet sheet = workbook.getSheetAt(index);
				String sheetName = sheet.getSheetName();
				if (sheetName.equals("User_config")) {
					device = setUserConfigSheet(device, sheet);
				} else if (sheetName.equals("Dataset_config")) {
					setDatasetConfigSheet(device, sheet);
				}
			}
		}
		return device;
	}

	private static Device setUserConfigSheet(Device device, Sheet sheet) throws IllegalAccessException {
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			for (int j = 0; j <= row.getLastCellNum(); j++) {
				String contents = ExcelUtil.getCellValue(row.getCell(j));
				if (contents.equals("Device and Bus")) {
					device = new Device();
					BusInterfaceList busInterfaceList = new BusInterfaceList();
					List<BusInterface> busInterfaces = new ArrayList<BusInterface>();
					busInterfaceList.setBusInterfaces(busInterfaces);
					device.setBusInterfaceList(busInterfaceList);
					// 设置device属性值
					setValue(sheet, device, i);
					// 设置busInterfaces值
					getBusInterface(sheet, device, busInterfaces);
					// 设置ComParameterList
					setComParameterList(device);
					return device;
				}
			}
		}
		return null;
	}

	/**
	 * @param sheet
	 * @param obj
	 * @param row
	 * @param sheetName
	 * @throws IllegalAccessException
	 */
	private static void setValue(Sheet sheet, SheetAndLine obj, int rowNum) throws IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();
		obj.setSheetName(sheet.getSheetName());
		obj.setLineNum(String.valueOf(rowNum + 3));
		all.add(obj);
		Row row = sheet.getRow(rowNum);
		for (int k = 0; k <= row.getLastCellNum(); k++) {
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

	private static void getBusInterface(Sheet sheet, Device device, List<BusInterface> busInterfaces) throws IllegalAccessException {
		List<Telegram> telegramList = null;
		BusInterface busInterface = null;
		Telegram telegram = null;
		List<Source> sourceList = null;
		List<Destination> destinationList = null;
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			for (int j = 0; j <= row.getLastCellNum(); j++) {
				String contents = ExcelUtil.getCellValue(row.getCell(j));
				if ("Device and Bus".equals(contents)) {
					busInterface = new BusInterface();
					setValue(sheet, busInterface, i + 2);
					busInterfaces.add(busInterface);
				} else if ("Telegram Configuration".equals(contents)) {
					sourceList = new ArrayList<Source>();
					destinationList = new ArrayList<Destination>();
					if (telegramList == null) {
						telegramList = new ArrayList<Telegram>();
						busInterface.setTelegram(telegramList);
					}
					telegram = new Telegram();
					setValue(sheet, telegram, i);
					setValue(sheet, telegram.getPdParameter(), i);
					telegramList.add(telegram);

					telegram.setSource(sourceList);
					telegram.setDestination(destinationList);
					if (isAllNull(sheet, i + 4, 0, 2)) {
						Source source = new Source();
						setValue(sheet, source, i + 2);
						setSourceId(sourceList, source);
						sourceList.add(source);
					} else {
						for (int k = i + 4; k <= sheet.getLastRowNum(); k++) {
							if (isAllNull(sheet, k, 0, 2)) {
								break;
							} else {
								Source source = new Source();
								setValue(sheet, source, i + 3, k);
								setSourceId(sourceList, source);
								sourceList.add(source);
							}
						}
					}

					if (isAllNull(sheet, i + 4, 3, 5)) {
						Destination destination = new Destination();
						setValue(sheet, destination, i + 2);
						setDestinationId(destinationList, destination);
						destinationList.add(destination);
					} else {
						for (int k = i + 4; k <= sheet.getLastRowNum(); k++) {
							if (isAllNull(sheet, k, 3, 5)) {
								break;
							} else {
								Destination destination = new Destination();
								setValue(sheet, destination, i + 3, k);
								setDestinationId(destinationList, destination);
								destinationList.add(destination);
							}
						}
					}
				}
			}
		}
	}

	private static void setSourceId(List<Source> sourceList, Source source) {
		if (sourceList.size() != 0) {
			// 最后一个source
			Source lastSource = sourceList.get(sourceList.size() - 1);
			source.setId(String.valueOf(Integer.valueOf(lastSource.getId()) + 1));
		} else {
			source.setId("1");
		}
	}

	private static void setDestinationId(List<Destination> destinationList, Destination destination) {
		if (destinationList.size() != 0) {
			// 最后一个source
			Destination lastDestination = destinationList.get(destinationList.size() - 1);
			destination.setId(String.valueOf(Integer.valueOf(lastDestination.getId()) + 1));
		} else {
			destination.setId("1");
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

	private static boolean isAllNull(Sheet sheet, int rowNum, int startCol, int endCol) {
		Row row = sheet.getRow(rowNum);
		if (row == null) {
			return true;
		}
		for (int j = startCol; j < endCol; j++) {
			String paramName = ExcelUtil.getCellValue(row.getCell(j));
			if (!StringUtils.isEmpty(paramName)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isAllNull(Sheet sheet, int rowNum) {
		Row row = sheet.getRow(rowNum);
		if (row == null) {
			return true;
		}
		for (int m = 0; m <= row.getLastCellNum(); m++) {
			String paramName = ExcelUtil.getCellValue(row.getCell(m));
			if (!StringUtils.isEmpty(paramName)) {
				return false;
			}
		}
		return true;
	}

	private static void setComParameterList(Device device) {
		ComParameterList comParameterList = new ComParameterList();
		List<ComParameter> comParameters = new ArrayList<ComParameter>();
		comParameterList.setComParameter(comParameters);
		comParameters.add(new ComParameter("1", "5", "64", "3"));
		comParameters.add(new ComParameter("2", "3", "64", "3"));
		comParameters.add(new ComParameter("4", "4", "2", "3"));
		device.setComParameterList(comParameterList);
	}

	private static void setDatasetConfigSheet(Device device, Sheet sheet) throws IllegalAccessException {
		DataSetList dataSetList = new DataSetList();
		device.setDataSetList(dataSetList);

		List<DataSet> dataSets = new ArrayList<DataSet>();
		dataSetList.setDataSet(dataSets);

		DataSet dataSet = null;
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			for (int j = 0; j <= row.getLastCellNum(); j++) {
				String contents = ExcelUtil.getCellValue(row.getCell(j));
				if (("Dataset Parameters").equals(contents)) {
					dataSet = new DataSet();
					setValue(sheet, dataSet, i);
					dataSets.add(dataSet);
				} else if (("Dataset Element").equals(contents)) {

					List<Element> elementList = new ArrayList<Element>();
					dataSet.setElement(elementList);
					if (isAllNull(sheet, i + 2)) {
						Element element = new Element();
						setValue(sheet, element, i);
						elementList.add(element);
					} else {
						for (int k = i + 2; k <= sheet.getLastRowNum(); k++) {
							if (isAllNull(sheet, k)) {
								break;
							} else {
								Element element = new Element();
								setValue(sheet, element, i + 1, k);
								elementList.add(element);
							}
						}
					}
				}

			}
		}
	}

	private static void setValue(Sheet sheet, SheetAndLine obj, int rowParamNameNum, int rowValueNum) throws IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();
		obj.setSheetName(sheet.getSheetName());
		obj.setLineNum(String.valueOf(rowValueNum + 1));
		all.add(obj);
		Row rowParamName = sheet.getRow(rowParamNameNum);
		Row rowValue = sheet.getRow(rowValueNum);
		for (int k = 0; k <= rowParamName.getLastCellNum(); k++) {
			String paramName = ExcelUtil.getCellValue(rowParamName.getCell(k));
			String value = ExcelUtil.getCellValue(rowValue.getCell(k));
			for (Field field : fields) {
				if (field.getName().equals(paramName)) {
					field.setAccessible(true);
					field.set(obj, value);
					break;
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
		List<String[]> list = new ArrayList<String[]>();
		// hostName,1,0,-1:分别表示参数名，必填（1）还是选填（0）,范围 0表示没有范围,默认值 -1表示没有默认值
		String[] device = { "Device", "hostName", REQUISITE, NO_SCOPE, NO_DEFAULT };

		String[] busInterface = { "BusInterface", "networkId", REQUISITE, "1-4", NO_DEFAULT, "name", REQUISITE, NO_SCOPE, NO_DEFAULT, "hostIp",
				REQUISITE, NO_SCOPE, NO_DEFAULT };
		String[] telegram = { "Telegram", "name", REQUISITE, NO_SCOPE, NO_DEFAULT, "comId", REQUISITE, EXTENT_UINT32, NO_DEFAULT, "datasetId",
				REQUISITE, EXTENT_UINT32, NO_DEFAULT };
		String[] pdParameter = { "PdParameter", "PD_cycle", REQUISITE, EXTENT_UINT32, NO_DEFAULT };
		String[] source = { "Source", "Source_uri", REQUISITE, NO_SCOPE, NO_DEFAULT, "Source_name", REQUISITE, NO_SCOPE, NO_DEFAULT };
		String[] destination = { "Destination", "Destination_uri", REQUISITE, NO_SCOPE, NO_DEFAULT, "Destination_name", REQUISITE, NO_SCOPE,
				NO_DEFAULT };

		String[] dataSet = { "DataSet", "name", OPTIONAL, NO_SCOPE, NO_DEFAULT, "id", REQUISITE, EXTENT_UINT32, NO_DEFAULT };

		String[] element = { "Element", "name", OPTIONAL, NO_SCOPE, NO_DEFAULT, "type", REQUISITE, NO_SCOPE, NO_DEFAULT, "arraySize", OPTIONAL,
				EXTENT_UINT32, "1", "unit", OPTIONAL, NO_SCOPE, NO_DEFAULT, "scale", OPTIONAL, EXTENT_FLOAT, NO_DEFAULT, "offset", OPTIONAL,
				EXTENT_INT32, NO_DEFAULT };

		list.add(device);
		list.add(busInterface);
		list.add(telegram);
		list.add(pdParameter);
		list.add(source);
		list.add(destination);
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
					List<String> list = verifyValues(obj, parameScopes);
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
				resultList.add(telegram.getSheetName() + "页，第" + telegram.getLineNum() + "行的datasetId无法在dataset_config页中找到");
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
			if (StringUtils.isEmpty(dataSetId)) {
				break;
			}
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

	private static List<String> verifyValues(SheetAndLine obj, String[] parameScopes) throws Exception {
		List<String> resultList = new ArrayList<>();
		Class<?> clazz = obj.getClass();
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
						if (StringUtils.isEmpty(value)) {
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
	 * 校验数据是否在范围内
	 * 
	 * @param extent
	 * @param value
	 * @return
	 */
	private static boolean verifyExtent(String extent, String value) {
		if (("uint32").equals(extent)) {
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
		} else if (("int32").equals(extent)) {
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
		} else if (("float").equals(extent)) {
			// 判断value是否为 float
			if (!value.matches("^[+-]?(([0-9]+)([.]([0-9]+))?)$")) {
				return false;
			}
		} else if ((NO_SCOPE).equals(extent)) {

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
