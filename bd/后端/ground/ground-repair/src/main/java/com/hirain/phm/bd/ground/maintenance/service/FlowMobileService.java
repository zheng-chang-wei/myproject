/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service;

import java.util.List;

import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetRecord;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Oct 14, 2019 2:29:48 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Oct 14, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface FlowMobileService {

	/**
	 * @param user
	 *            TODO
	 * @return
	 */
	List<WorkSheetRecord> listEditSheets(User user);

	/**
	 * @param user TODO
	 * @return
	 */
	List<WorkSheetRecord> listHistorySheets(User user);

}
