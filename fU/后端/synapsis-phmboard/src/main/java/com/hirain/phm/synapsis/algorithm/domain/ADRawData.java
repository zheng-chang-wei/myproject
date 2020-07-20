/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 23, 2019 5:12:21 PM
 * @Description
 *              <p>  算法输入数据——AD数据
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 23, 2019     zepei.tao@hirain.com    1.0   create file   
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ADRawData extends AlgoRawData {

	private byte slotID;

	private byte chlID;

	private int sampleRate;

	private long startIndex;

	private int sampleNum;

}
