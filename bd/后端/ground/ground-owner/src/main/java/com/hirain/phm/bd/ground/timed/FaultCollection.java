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
 * @Created Jul 6, 2020 3:12:49 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class FaultCollection {

	private String sheetName;

	private String project;

	private String train;

	private List<CarFaultItem> carItems;

	public FaultCollection() {
		carItems = new ArrayList<>(6);
		for (int i = 0; i < 6; i++) {
			carItems.add(new CarFaultItem(i + 1));
		}
	}
}
