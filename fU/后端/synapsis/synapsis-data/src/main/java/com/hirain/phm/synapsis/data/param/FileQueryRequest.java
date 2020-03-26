/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.data.param;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 5:09:24 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class FileQueryRequest {

	private FileTreeNode node;

	@JSONField(format = "yyyyMMdd")
	private Date startDate;

	@JSONField(format = "yyyyMMdd")
	private Date endDate;
}
