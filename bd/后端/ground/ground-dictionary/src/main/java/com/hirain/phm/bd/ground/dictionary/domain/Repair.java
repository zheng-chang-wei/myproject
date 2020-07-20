/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 8, 2020 4:57:18 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 8, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_repair")
public class Repair {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	private String content;

}
