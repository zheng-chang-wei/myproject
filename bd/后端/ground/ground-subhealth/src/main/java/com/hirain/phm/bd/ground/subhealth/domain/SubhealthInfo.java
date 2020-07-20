/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created May 23, 2019 11:23:57 AM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 23, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.subhealth.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hirain.phm.bd.ground.dictionary.domain.Repair;
import com.hirain.phm.bd.ground.dictionary.domain.Solution;

import lombok.Data;

@Data
@Table(name = "t_subhealth_type")
public class SubhealthInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3932673619204586421L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	private Integer projectId;

	private String subhealthName;

	private Integer subhealthCode;

	@Transient
	private List<Repair> repairList;

	@Transient
	private List<Solution> solutionList;
}
