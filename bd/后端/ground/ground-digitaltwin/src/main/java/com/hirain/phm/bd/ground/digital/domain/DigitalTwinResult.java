/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.digital.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Sep 25, 2019 10:48:53 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Sep 25, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Table(name = "t_digital_twins")
@Data
public class DigitalTwinResult {

	@Id
	private Long id;

	private Integer trainId;

	private Integer carId;

	private Integer doorId;

	private Date timestamp;

	private Integer paramId;

	private Double paramValue;
}
