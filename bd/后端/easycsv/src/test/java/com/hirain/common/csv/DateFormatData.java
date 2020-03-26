/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.common.csv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hirain.common.csv.annotation.CsvProperty;
import com.hirain.common.csv.annotation.DateFormat;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 28, 2020 9:38:59 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class DateFormatData {

	@CsvProperty("序号")
	private String value;

	@DateFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒")
	@CsvProperty("时间")
	private Date timestamp;

	/**
	 * @return
	 */
	public static List<DateFormatData> data() {
		List<DateFormatData> datas = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			DateFormatData data = new DateFormatData();
			data.setValue("序号" + (i + 1));
			data.setTimestamp(new Date());
			datas.add(data);
		}
		return datas;
	}
}
