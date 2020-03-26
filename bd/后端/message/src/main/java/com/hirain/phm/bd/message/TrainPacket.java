/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月8日 下午5:04:22
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@NoArgsConstructor
public class TrainPacket {

	private int index;

	private String project;

	private String city;

	private String line;

	private String train;

	private List<CarriagePacket> packets;

	/**
	 * @param index
	 * @param packets
	 */
	public TrainPacket(int index, List<CarriagePacket> packets) {
		super();
		this.index = index;
		this.packets = packets;
	}

}
