/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.converter;

import com.hirain.phm.synapsis.algorithm.domain.AlgoRawData;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 24, 2019 9:57:43 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 24, 2019     zepei.tao@hirain.com    1.0   create file   
 */
public interface RawDataConverter {

	AlgoRawData convert(byte[] datas);
}
