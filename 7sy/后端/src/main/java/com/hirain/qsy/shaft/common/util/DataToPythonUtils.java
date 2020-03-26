package com.hirain.qsy.shaft.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.hirain.qsy.shaft.common.config.GlobVariableConfig;
import com.hirain.qsy.shaft.model.PythonData;

public class DataToPythonUtils {

	List<Date> acquisitionTime = new ArrayList<>();// 采集时间

	List<String> originalPrimaryKeyList = new ArrayList<>();// 采集时间

	/**
	 * 返回json数据
	 * 
	 * @param excelData
	 *            传入excel数据
	 * @return
	 * @throws Exception
	 */
	public String objectToPythonJson(List<List<String>> excelData) throws Exception {

		return JSON.toJSONString(excelDataToPythonData(excelData));

	}

	/**
	 * 获取所有温度的数据
	 * 
	 * @param excelData
	 * @return
	 * @throws Exception
	 */
	private PythonData excelDataToPythonData(List<List<String>> excelData) throws Exception {
		/*
		 * String title[]= {"1轴测试点1温度-℃" ,"1轴测试点2温度-℃" ,"1轴测试点3温度-℃" ,"1轴测试点4温度-℃"
		 * ,"1轴测试点5温度-℃" , "2轴测试点1温度-℃" ,"2轴测试点2温度-℃" ,"2轴测试点3温度-℃" ,"2轴测试点4温度-℃"
		 * ,"2轴测试点5温度-℃" , "3轴测试点1温度-℃" ,"3轴测试点2温度-℃" ,"3轴测试点3温度-℃" ,"3轴测试点4温度-℃"
		 * ,"3轴测试点5温度-℃" , "4轴测试点1温度-℃" ,"4轴测试点2温度-℃" ,"4轴测试点3温度-℃" ,"4轴测试点4温度-℃"
		 * ,"4轴测试点5温度-℃" , "5轴测试点1温度-℃" ,"5轴测试点2温度-℃" ,"5轴测试点3温度-℃" ,"5轴测试点4温度-℃"
		 * ,"5轴测试点5温度-℃" , "6轴测试点1温度-℃" ,"6轴测试点2温度-℃" ,"6轴测试点3温度-℃" ,"6轴测试点4温度-℃"
		 * ,"6轴测试点5温度-℃"};
		 */
		SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PythonData pData = null;
		List<Integer> tempratureOnPoint;// 测点温度

		List<Date> acquistDates = new ArrayList<>();
		List<Integer> gpsSpeed = new ArrayList<>();// gps速度

		List<Integer> ambientTemperatures_1 = new ArrayList<>();
		List<Integer> ambientTemperatures_2 = new ArrayList<>();
		List<String> originalPrimaryKey = new ArrayList<>();
		if (excelData.size() > 0) {
			List<String> list = excelData.get(excelData.size() - 1);
			// 获取gps,环境温度1和环境温度2的index.
			int gpsTitleIndex = list.indexOf("GPS速度");
			int firTempIndex = list.indexOf("环温1-℃");
			int secTempIndex = list.indexOf("环温2-℃");
			int trainIdIndex = list.indexOf("机车车号");
			int acquisitionTimeIndex = list.indexOf("采集时间");
			int orignalPrimaryKeyIndex = list.indexOf("原始表主键");
			pData = new PythonData();
			for (int eIndex = 0; eIndex < excelData.size() - 1; eIndex++) {
				List<String> lists = excelData.get(eIndex);
				acquistDates.add(sdfDateFormat.parse(lists.get(acquisitionTimeIndex)));
				gpsSpeed.add(Integer.valueOf(lists.get(gpsTitleIndex)));
				ambientTemperatures_1.add(Integer.valueOf(lists.get(firTempIndex)));
				ambientTemperatures_2.add(Integer.valueOf(lists.get(secTempIndex)));
				originalPrimaryKey.add(lists.get(orignalPrimaryKeyIndex));
			}
			pData.setTrain_No(Integer.valueOf(excelData.get(1).get(trainIdIndex)));
			pData.setGPSSpeed(gpsSpeed);
			pData.setAmbientTemperature_1(ambientTemperatures_1);
			pData.setAmbientTemperature_2(ambientTemperatures_2);

			if (excelData != null) {
				for (int index = 0; index < GlobVariableConfig.title.length; index++) {
					tempratureOnPoint = new ArrayList<>();
					int titleIndex = list.indexOf(GlobVariableConfig.title[index]);
					for (int rIndex = 0; rIndex < excelData.size() - 1; rIndex++) {
						tempratureOnPoint.add(Integer.valueOf(excelData.get(rIndex).get(titleIndex)));
					}
					String proName = "temperatureOnPoint_" + (index / 6 + 1) + (index - (index / 6) * 6 + 1);
					BeanUtils.setProperty(pData, proName, tempratureOnPoint);
				}
			}
		}
		setAcquisitionTime(acquistDates);
		setPrimaryKey(originalPrimaryKey);
		return pData;
	}

	private void setAcquisitionTime(List<Date> data) {
		this.acquisitionTime = data;
	}

	public List<Date> getAcquisitionTime() {
		return acquisitionTime;
	}

	private void setPrimaryKey(List<String> data) {
		this.originalPrimaryKeyList = data;
	}

	public List<String> getPrimaryKey() {
		return originalPrimaryKeyList;
	}
}
