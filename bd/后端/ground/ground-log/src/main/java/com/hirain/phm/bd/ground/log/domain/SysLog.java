package com.hirain.phm.bd.ground.log.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class SysLog implements Serializable {

	private static final long serialVersionUID = -8878596941954995444L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Long id;

	private String userName;

	private String operation;

	private String params;

	private Date operationTime;

	// 用于搜索条件中的时间字段
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Transient
	private String startTime;

	// 用于搜索条件中的时间字段
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Transient
	private String endTime;

}