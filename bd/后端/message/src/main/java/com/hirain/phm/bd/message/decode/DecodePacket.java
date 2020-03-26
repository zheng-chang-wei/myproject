/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message.decode;

import java.util.List;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月17日 上午9:24:01
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月17日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class DecodePacket {

	private int sid;

	private String project;

	private String train;

	private List<RunDataFrame> frames;

	private List<String> keys;

	public DecodePacket(int sid, String project, String train) {
		this.sid = sid;
		this.project = project;
		this.train = train;
	}
}
