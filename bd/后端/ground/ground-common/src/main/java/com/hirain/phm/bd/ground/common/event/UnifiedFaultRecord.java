/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.common.event;

import java.util.Date;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年7月17日 上午11:16:29
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年7月17日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class UnifiedFaultRecord {

	private FaultTopType type;

	private Integer code;

	private Long id;

	private Date time;

	private String faultName;

	private String trainId;

	private Integer carriageId;

	private Integer doorId;

	private Integer projectId;
}
