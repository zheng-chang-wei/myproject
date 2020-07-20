package com.hirain.phm.bd.ground.fault.param;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FaultDetailRequestParams extends FaultDetailCommonParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8532895047123250052L;

	@ApiModelProperty("开始时间")
	private Date startTime;

	@ApiModelProperty("截止时间")
	private Date endTime;
}
