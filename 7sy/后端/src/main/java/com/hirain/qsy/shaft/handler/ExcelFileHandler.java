package com.hirain.qsy.shaft.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.stereotype.Service;

import com.hirain.qsy.shaft.common.config.GlobVariableConfig;
import com.hirain.qsy.shaft.common.util.OperateExcelUtils;
import com.hirain.qsy.shaft.common.util.VerifyUtil;
import com.hirain.qsy.shaft.model.AttributeMappingConfigurationData;
import com.hirain.qsy.shaft.model.InitialData;
import com.hirain.qsy.shaft.model.MultisetObjectData;
import com.hirain.qsy.shaft.model.TrainInfo;

@Service
public class ExcelFileHandler {

	public MultisetObjectData initialDatas(String path) throws Exception {
		MultisetObjectData initialAndExcelData = null;
		List<List<String>> excelDataList = OperateExcelUtils.getExcelFileData(path);
		sortExcelDataByAcquisitionTime(excelDataList);
		if (excelDataList.size() > 0) {
			initialAndExcelData = getInitialData(excelDataList);
		}
		return initialAndExcelData;
	}

	private void sortExcelDataByAcquisitionTime(List<List<String>> excelDataList) {
		int acquisitionTimeIndex = excelDataList.get(0).indexOf("采集时间");
		Collections.sort(excelDataList, new Comparator<List<String>>() {

			@Override
			public int compare(List<String> o1, List<String> o2) {

				return o1.get(acquisitionTimeIndex).toString().compareTo(o2.get(acquisitionTimeIndex).toString());
			}
		});
	}

	/**
	 * 获取excel中的有效数据
	 * 
	 * @param excelData
	 * @return
	 */
	// @Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public MultisetObjectData getInitialData(List<List<String>> excelData) throws Exception {
		MultisetObjectData initialAndExcelData = new MultisetObjectData();
		List<List<String>> excelDataList = new ArrayList<>();
		List<InitialData> initialDataList = new ArrayList<>();// 存储初始化object
		Map<String, String> mapData = AttributeMappingConfigurationData.mapData;
		SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 处理时间格式
		DateConverter dateConverter = new DateConverter();
		// 设置日期格式
		dateConverter.setPatterns(new String[] { "yyyy-MM-dd HH:mm:ss" });
		// 注册格式
		ConvertUtils.register(dateConverter, Date.class);
		TrainInfo trainInfo = null;
		for (int rIndex = 0; rIndex < excelData.size(); rIndex++) {
			InitialData initialData = new InitialData();
			// InitialData t = (InitialData) Class.forName("com.hirain.qsy.shaft.model.InitialData").newInstance();
			// 取得第rIndex行的所有列Excel数据
			List<String> rowList = excelData.get(rIndex);
			List<String> rowData = new ArrayList<>();// 存储行信息
			// 第1行的Excel表头
			List<String> firstRow = excelData.get(excelData.size() - 1);
			if (rIndex == excelData.size() - 1) {
				for (int cIndex = 0; cIndex < rowList.size(); cIndex++) {
					if (mapData.containsKey(firstRow.get(cIndex))) {
						rowData.add(rowList.get(cIndex));
					}
				}
				excelDataList.add(rowData);
			} else {
				if (!isNull(rowList, firstRow, mapData)) {
					if (trainInfo == null) {
						String trainType = rowList.get(0);
						String trainNum = rowList.get(1);
						trainInfo = new TrainInfo();
						trainInfo.setTrainNum(trainNum);
						trainInfo.setTrainType(trainType);
					}
					for (int cIndex = 0; cIndex < rowList.size(); cIndex++) {
						// 表头的第cIndex列值
						String head = firstRow.get(cIndex);
						if (mapData.containsKey(head)) {
							String cellValue = rowList.get(cIndex);
							rowData.add(cellValue);
							String proName = mapData.get(head);
							if (VerifyUtil.isDate(cellValue)) {
								Object dateObject = sdfDateFormat.parse(cellValue);
								BeanUtils.setProperty(initialData, proName, dateObject);
							} else {
								BeanUtils.setProperty(initialData, proName, cellValue);
							}
						}
					}
					excelDataList.add(rowData);
					initialDataList.add(initialData);
				}
			}
		}
		initialAndExcelData.setExcelDataList(excelDataList);
		initialAndExcelData.setInitialdatas(initialDataList);
		initialAndExcelData.setTrainInfor(trainInfo);
		return initialAndExcelData;
	}

	/**
	 * 判断该行温度是否存在空值
	 * 
	 * @param rows
	 * @param rowhead
	 * @param map
	 * @return
	 */
	public boolean isNull(List<String> rows, List<String> rowhead, Map<String, String> map) {
		List<String> nullList = Arrays.asList(GlobVariableConfig.title);// 实例化温度表头
		for (int i = 0; i < rows.size(); i++) {
			if (map.containsKey(rowhead.get(i)) && rows.get(i).equals("-")) {
				if ((nullList.contains(rowhead.get(i))))
					return true;
			}

			if (nullList.contains(rowhead.get(i)) && !VerifyUtil.isNumber(rows.get(i))) {
				return true;
			}
		}

		return false;
	}




}
