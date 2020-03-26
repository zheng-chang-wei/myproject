/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.digital.response;

import java.util.List;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Sep 25, 2019 1:33:23 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Sep 25, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class ResultQueryResponse {

	private List<ParamResult> results;
}
