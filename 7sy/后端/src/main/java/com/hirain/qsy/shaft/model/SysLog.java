package com.hirain.qsy.shaft.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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