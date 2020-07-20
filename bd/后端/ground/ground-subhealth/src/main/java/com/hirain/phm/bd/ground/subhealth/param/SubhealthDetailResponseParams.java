/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 9, 2020 3:51:33 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubhealthDetailResponseParams extends SubhealthDetailParams {

	private static final long serialVersionUID = 7485638712884168428L;

	private Integer subhealthInfoId;

	private String repair;

	private String solution;
}
