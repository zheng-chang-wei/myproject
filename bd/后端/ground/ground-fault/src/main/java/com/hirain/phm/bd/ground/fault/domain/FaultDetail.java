package com.hirain.phm.bd.ground.fault.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年5月6日 下午5:30:46
 * @Description
 *              <p>
 *              故障详情
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月6日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_fault_detail")
public class FaultDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -848316889037780338L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Long id;

	private Integer faultInfoId;

	private Integer trainId;

	private Date faultTime;

	private Integer carNo;

	private Integer doorAddr;

	private Boolean debugMode;

	private Boolean statistics;
}