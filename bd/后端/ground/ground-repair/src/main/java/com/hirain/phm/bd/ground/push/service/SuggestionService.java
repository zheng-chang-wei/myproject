/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.push.service;

import com.hirain.phm.bd.ground.push.domain.PushInfo;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Oct 21, 2019 10:22:54 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Oct 21, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface SuggestionService {

	PushInfo findPushInfo(int type, int code);

	String findTreatmentSuggestion(Integer id);

	String findRepairSuggestion(Integer id);

}
