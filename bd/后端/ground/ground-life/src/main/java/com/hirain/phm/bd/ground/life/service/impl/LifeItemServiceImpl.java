/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.life.dao.LifeItemMapper;
import com.hirain.phm.bd.ground.life.domain.LifeItem;
import com.hirain.phm.bd.ground.life.service.LifeItemService;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created May 28, 2019 2:08:45 PM  
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 28, 2019     zepei.tao@hirain.com             1.0        create file 
 */
@Service
public class LifeItemServiceImpl extends BaseService<LifeItem> implements LifeItemService {

	@Autowired
	private LifeItemMapper mapper;

	/** 
	 * @see com.hirain.phm.bd.ground.life.service.LifeItemService#findLifeItemById(java.lang.Integer)
	 */
	@Override
	public LifeItem findLifeItemById(Integer id) {
		return mapper.findLifeItemByID(id);
	}

}
