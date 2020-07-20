package com.hirain.phm.bd.ground.fault.param;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FaultDetailResponseParams extends FaultDetailCommonParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8532895047123250052L;

	/**
	 * 故障详情id
	 */
	private Long id;

	private Integer faultInfoId;

	/**
	 * 故障发生时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date faultTime;

	private String repair;

	private String solution;
}
