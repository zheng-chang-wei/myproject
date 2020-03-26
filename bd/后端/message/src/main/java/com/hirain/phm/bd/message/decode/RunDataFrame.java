/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message.decode;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月17日 上午9:10:46
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月17日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class RunDataFrame {

	private int id;

	private int type;

	private List<String> values = new ArrayList<>();

	private boolean debug;
}
