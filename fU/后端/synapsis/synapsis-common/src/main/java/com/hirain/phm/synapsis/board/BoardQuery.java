/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board;

import java.util.List;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 18, 2020 11:13:50 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 18, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface BoardQuery {

	List<IBoard> getBoards(String type);
}
