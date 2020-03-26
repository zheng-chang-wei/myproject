/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created May 22, 2019 5:56:25 PM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 22, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.subhealth.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hirain.phm.bd.ground.train.domain.Train;

import lombok.Data;

@Data
@Table(name = "t_subhealth_detail")
public class SubhealthDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 796549508098755076L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	private Integer subhealthTypeId;

	private Date startTime;

	private Date endTime;

	private Integer trainId;

	private Integer carNo;

	private Integer doorAddr;

	private Boolean debugMode;

	private Train train;

	private SubhealthInfo subhealthInfo;

	private Boolean statistics;

}
