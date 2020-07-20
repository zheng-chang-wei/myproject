/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.dictionary.domain.FirstComponent;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 30, 2020 11:53:06 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 30, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface FirstComponentService extends IService<FirstComponent> {

	/**
	 * @param doorTypeId
	 * @return
	 */
	List<FirstComponent> getAllActive(Integer doorTypeId);

	/**
	 * @param component
	 */
	boolean checkDuplicate(FirstComponent component);

	/**
	 * @param component
	 */
	void deactive(FirstComponent component);

	/**
	 * @param list
	 */
	void batchInsert(List<FirstComponent> list);

}
