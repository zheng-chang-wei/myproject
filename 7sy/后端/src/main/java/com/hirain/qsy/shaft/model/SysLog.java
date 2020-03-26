package com.hirain.qsy.shaft.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Table(name = "t_log")
@Data
public class SysLog implements Serializable {

	private static final long serialVersionUID = -8878596941954995444L;

	@Id
	@GeneratedValue(generator = "JDBC")
	@Column(name = "ID")
	private Long id;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "OPERATION")
	private String operation;

	@Column(name = "TIME")
	private Long time;

	@Column(name = "METHOD")
	private String method;

	@Column(name = "PARAMS")
	private String params;

	@Column(name = "IP")
	private String ip;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "LOCATION")
	private String location;

	// 用于搜索条件中的时间字段
	@Transient
	private String startTime;

	// 用于搜索条件中的时间字段
	@Transient
	private String endTime;

}