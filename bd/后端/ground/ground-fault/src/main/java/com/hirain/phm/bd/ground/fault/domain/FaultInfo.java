package com.hirain.phm.bd.ground.fault.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年5月6日 下午5:30:56
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月6日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_fault_info")
public class FaultInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5369350174390932163L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	private Integer projectId;

	private String faultName;

	private Integer faultCode;

}