package com.hirain.phm.bd.message.decode.old;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Jan 10, 2019 4:59:09 PM
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
public class DataFrameD4 {

	/**
	 * 秒
	 */
	private int second;

	/**
	 * 毫秒
	 */
	private short millisecond;

	/**
	 * 温度
	 */
	private int temperature;

	/**
	 * CRC校验
	 */
	private short verifyCRC;

}
