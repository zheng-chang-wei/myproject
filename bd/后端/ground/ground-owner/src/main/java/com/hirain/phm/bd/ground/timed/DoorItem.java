/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.timed;

import java.lang.reflect.Field;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 6, 2020 3:09:29 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class DoorItem {

	private Integer door;

	private Integer item1;

	private Integer item2;

	private Integer item3;

	private Integer item4;

	private Integer item5;

	private Integer item6;

	private Integer item7;

	private Integer item8;

	private Integer item9;

	private Integer item10;

	private Integer item11;

	private Integer item12;

	private Integer item13;

	public DoorItem() {
		for (int i = 0; i < 13; i++) {
			setItem(i, 0);
		}
	}

	public DoorItem(Integer door) {
		this();
		this.door = door;
	}

	public void setItem(Integer index, Integer count) {
		try {
			Field field = getClass().getDeclaredField("item" + (index + 1));
			field.setAccessible(true);
			field.set(this, count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
