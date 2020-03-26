
package com.hirain.phm.bd.ground.train.param;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年7月22日 下午7:03:24
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年7月22日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
public class TrainDMResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -160779818810672558L;

	private String project;

	private String train;

	private Integer trainId;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date startDate;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date endDate;

	private Integer storageRatio;

	private Integer faultRatio;

	private Boolean online;
}
