package com.hirain.qsy.shaft.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AxleExceptionData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8024585159255192651L;

	/**
	 * 实际值
	 */
	private String actualValue;

	/**
	 * 预测值
	 */
	private String predicteValue;

	/**
	 * 残差
	 */
	private String residuals;

	/**
	 * 异常检测门限
	 */
	private String threshold;

	/**
	 * 异常检测门限
	 */
	private String threshold1;

	/**
	 * 异常检测门限
	 */
	private String threshold2;

	/**
	 * 采集时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date acquisitionTime;

}
