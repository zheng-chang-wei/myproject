/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board;

import com.hirain.phm.synapsis.constant.BoardType;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 18, 2020 11:12:35 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 18, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface IBoard {

	int getSlotID();

	String getCardIP();

	BoardType getBoardType();
}
