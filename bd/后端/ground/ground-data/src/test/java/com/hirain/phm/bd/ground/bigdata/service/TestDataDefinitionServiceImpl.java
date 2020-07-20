/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.service;

import org.springframework.stereotype.Service;

import com.hirain.phm.bd.data.DataDefinitionService;
import com.hirain.phm.bd.data.bean.DataParam;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 7, 2020 4:00:59 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 7, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class TestDataDefinitionServiceImpl implements DataDefinitionService {

	/**
	 * @see com.hirain.phm.bd.data.DataDefinitionService#selectRange(com.hirain.phm.bd.data.bean.DataParam)
	 */
	@Override
	public String[] selectRange(DataParam param) {
		return null;
	}

	/**
	 * @see com.hirain.phm.bd.data.DataDefinitionService#delete(com.hirain.phm.bd.data.bean.DataParam)
	 */
	@Override
	public void delete(DataParam param) {

	}

}
