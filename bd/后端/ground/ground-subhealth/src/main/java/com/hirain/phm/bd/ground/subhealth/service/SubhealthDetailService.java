/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/

package com.hirain.phm.bd.ground.subhealth.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthDetail;

/**
 * @Version 1.0
 * @Author weijia.kong@hirain.com
 * @Created May 24, 2019 3:35:28 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 24, 2019 weijia.kong@hirain.com 1.0 create file
 */
public interface SubhealthDetailService extends IService<SubhealthDetail> {

	public void insertList(List<SubhealthDetail> details);

}
