package com.hirain.common.csv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hirain.common.csv.annotation.CsvProperty;

import lombok.Data;

@Data
public class SimpleData {

	/**
	 * @return
	 */
	public static List<SimpleData> data() {
		List<SimpleData> datas = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			SimpleData data = new SimpleData();
			data.setValue("序号" + (i + 1));
			data.setTimestamp(new Date());
			datas.add(data);
		}
		return datas;
	}

	@CsvProperty("序号")
	private String value;

	@CsvProperty("时间")
	private Date timestamp;

}