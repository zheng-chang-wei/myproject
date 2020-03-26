/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message.data;

import java.util.List;

import com.hirain.phm.bd.message.decode.RunDataFrame;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 5, 2020 11:50:54 AM
 * @Description
 *              <p>
 *              com.hirain.phm.bd.data.DataRecord中data的实际结构
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 5, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class RecordData {

	private List<String> keys;

	private List<RunDataFrame> frames;
}
