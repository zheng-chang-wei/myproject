/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created Jun 6, 2019 2:30:25 PM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Jun 6, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.train.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "t_train_dm")
public class TrainStorageInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1462481895082141910L;

	@Id
	private Integer trainId;

	private Integer storageRatio;

	private Integer faultStorageRatio;

	private Date startDate;

	private Date endDate;

	private Date deleteDate;

}
