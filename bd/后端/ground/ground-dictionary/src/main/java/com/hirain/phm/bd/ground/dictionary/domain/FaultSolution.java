/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.domain;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 8, 2020 6:48:45 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 8, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_fault_solution")
public class FaultSolution {

	@Id
	private Integer faultInfoId;

	@Id
	private Integer solutionId;
}
