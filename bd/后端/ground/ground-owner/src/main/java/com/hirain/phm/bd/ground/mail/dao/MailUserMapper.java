/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.mail.dao;

import org.apache.ibatis.annotations.Delete;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.mail.domain.MailUser;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 2, 2020 10:42:16 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 2, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface MailUserMapper extends CommonMapper<MailUser> {

	/**
	 * 
	 */
	@Delete("delete from t_mail_user")
	void deleteAll();

}
