/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.mail.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.mail.dao.MailUserMapper;
import com.hirain.phm.bd.ground.mail.domain.MailUser;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 2, 2020 10:43:27 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 2, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class MailUserServiceImpl extends BaseService<MailUser> implements MailUserService {

	@Autowired
	private MailUserMapper mapper;

	/**
	 * @see com.hirain.phm.bd.ground.mail.service.MailUserService#deleteAll()
	 */
	@Override
	public void deleteAll() {
		mapper.deleteAll();
	}

	/**
	 * @see com.hirain.phm.bd.ground.mail.service.MailUserService#saveAll(java.util.List)
	 */
	@Override
	public void saveAll(List<MailUser> users) {
		mapper.insertList(users);
	}

}
