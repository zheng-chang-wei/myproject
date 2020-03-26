/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.service;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.life.domain.LifeItem;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created May 28, 2019 2:06:41 PM  
 * @Description
 * <p>处理t_life_item表的service
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 28, 2019     zepei.tao@hirain.com             1.0        create file 
 */
public interface LifeItemService extends IService<LifeItem> {

	/**
	 * 通过id找到 t_life_item表中对应的数据
	 */
	LifeItem findLifeItemById(Integer id);
}
