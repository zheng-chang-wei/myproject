package com.hirain.qsy.shaft.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class AxleExceptionData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8024585159255192651L;

	private String actualValue;

	private String predicteValue;

	private String residuals;

	private String threshold;

	private String threshold1;

	private String threshold2;

	private Date acquisitionTime;

}
