/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Feb 20, 2020 3:26:11 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 20, 2020     zepei.tao@hirain.com    1.0   create file   
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ADStorageData extends StorageData {

	private int chnID;
}
