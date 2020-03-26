/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store.service;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 4, 2020 6:58:46 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 4, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface MessageStoragerFactory {

	IMessageStorager create(String train);
}
