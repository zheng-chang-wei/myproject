/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.param;

import java.util.List;

import com.hirain.phm.synapsis.board.domain.BoardStartStructure;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 10:52:33 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@AllArgsConstructor
public class ControlResponse {

	private List<BoardStartStructure> controlResults;
}
