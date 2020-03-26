package com.hirain.phm.bd.ground.fault.param;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FaultDetailResponseParms extends FaultDetailCommonParms implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8532895047123250052L;

	/**
	 * 故障详情id
	 */
	private Long id;

	/**
	 * 故障发生时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date faultTime;
}
