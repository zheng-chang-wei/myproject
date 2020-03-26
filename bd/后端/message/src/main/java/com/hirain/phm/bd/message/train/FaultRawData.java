/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message.train;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 26, 2019 10:52:26 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 26, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class FaultRawData {

	private Date timestamp;

	private List<String> values;
}
