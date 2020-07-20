/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 23, 2019 4:51:44 PM
 * @Description
 *              <p>  算法输入数据——总线数据 ，包括 MVB 和 ECN
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 23, 2019     zepei.tao@hirain.com    1.0   create file   
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusRawData extends AlgoRawData {

	private short dataCrc;

}
