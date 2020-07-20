/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.domain;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 10, 2020 11:53:07 AM
 * @Description
 *              <p>
 *              板卡更新进度
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 10, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class BoardUpdateSchedule {

	/**
	 * 槽位号
	 */
	private int slotID;

	private String boardName;

	private Schedule schedule;

}
