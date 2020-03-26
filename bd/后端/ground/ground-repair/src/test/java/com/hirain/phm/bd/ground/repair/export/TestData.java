/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.repair.export;

import java.util.Date;

import com.hirain.common.csv.annotation.CsvIgnore;
import com.hirain.common.csv.annotation.CsvProperty;
import com.hirain.common.csv.annotation.DateFormat;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 2:29:24 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class TestData {

	@CsvProperty("序号")
	private String value;

	@DateFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒")
	@CsvProperty("时间")
	private Date timestamp;

	@CsvProperty("时间2")
	private Date date;

	private String noproperty;

	@CsvIgnore
	private String other;

	@CsvProperty(value = "bool值", converter = TestBooleanValueConverter.class)
	private boolean b;
}
