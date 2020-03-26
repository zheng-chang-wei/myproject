/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.domain;

import com.hirain.phm.synapsis.board.IBoard;
import com.hirain.phm.synapsis.constant.BoardType;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 3, 2019 11:32:49 AM
 * @Description
 *              <p>
 *              所有板卡的父类
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class Board implements IBoard {

	private BoardType boardType;

	private String type;

	private String cardName;

	private String cardSN;

	private String deviceID;

	/**
	 * 背板IP
	 */
	private String cardIP;

	private int slotID;

	private String controlVer;

	private CardStatus cardStatus;

	/**
	 * 板卡外观属性
	 */
	private BoardOutside outside;

	/**
	 * 内核温度
	 */
	private int coreTemp;
}
