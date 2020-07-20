/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.mail.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.mail.domain.MailUser;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 2, 2020 10:42:40 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 2, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface MailUserService extends IService<MailUser> {

	/**
	 * 
	 */
	void deleteAll();

	/**
	 * @param users
	 */
	void saveAll(List<MailUser> users);

}
