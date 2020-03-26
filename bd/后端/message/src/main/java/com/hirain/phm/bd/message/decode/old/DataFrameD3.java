package com.hirain.phm.bd.message.decode.old;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Jan 10, 2019 4:59:00 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 10, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
@NoArgsConstructor
@Deprecated
public class DataFrameD3 {

	/**
	 * 关门时间
	 */
	private short closeDoorTime;

	/**
	 * 编码器
	 */
	private short Encoder;

	/**
	 * 日
	 */
	private int date;

	/**
	 * 时
	 */
	private int hour;

	/**
	 * 分
	 */
	private int minute;

}
