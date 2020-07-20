/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.timed;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 6, 2020 3:11:24 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class CarFaultItem {

	private Integer carriage;

	private List<DoorItem> items;

	public CarFaultItem() {
		items = new ArrayList<>(10);
		for (int i = 0; i < 10; i++) {
			items.add(new DoorItem(i + 1));
		}
	}

	public CarFaultItem(Integer carriage) {
		this();
		this.carriage = carriage;
	}

}
