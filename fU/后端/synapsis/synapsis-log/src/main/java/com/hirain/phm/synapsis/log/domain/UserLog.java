package com.hirain.phm.synapsis.log.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年9月20日 下午5:14:06
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年9月20日 changwei.zheng@hirain.com 1.0 create file
 */
@Table(name = "t_log")
@Data
public class UserLog implements Serializable {

	private static final long serialVersionUID = -8878596941954995444L;

	@Id
	private Long id;

	private String userName;

	private String operation;

	private LocalDateTime operationTime;

	// 用于搜索条件中的时间字段
	@Transient
	private String startTime;

	// 用于搜索条件中的时间字段
	@Transient
	private String endTime;

}