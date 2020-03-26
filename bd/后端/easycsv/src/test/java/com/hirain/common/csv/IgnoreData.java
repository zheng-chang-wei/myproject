/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.common.csv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hirain.common.csv.annotation.CsvIgnore;
import com.hirain.common.csv.annotation.CsvProperty;
import com.hirain.common.csv.annotation.DateFormat;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 28, 2020 9:41:22 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class IgnoreData {

	@CsvProperty("序号")
	private String value;

	@DateFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒")
	@CsvProperty("时间")
	private Date timestamp;

	@CsvIgnore
	private String ignore;

	/**
	 * @return
	 */
	public static List<IgnoreData> data() {
		List<IgnoreData> datas = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			IgnoreData data = new IgnoreData();
			data.setValue("序号" + (i + 1));
			data.setTimestamp(new Date());
			data.setIgnore("ignore_" + i);
			datas.add(data);
		}
		return datas;
	}
}
