/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.dictionary.domain.SecondComponent;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 30, 2020 1:27:18 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 30, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface SecondComponentService extends IService<SecondComponent> {

	/**
	 * @param doorTypeId
	 * @return
	 */
	List<SecondComponent> getAllActive(Integer doorTypeId);

	/**
	 * @param component
	 * @return
	 */
	boolean checkDuplicate(SecondComponent component);

	/**
	 * @param component
	 */
	void deactive(SecondComponent component);

	/**
	 * @param list
	 */
	void batchInsert(List<SecondComponent> list);

}
