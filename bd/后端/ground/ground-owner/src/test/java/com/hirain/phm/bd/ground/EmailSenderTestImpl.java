/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground;

import java.util.Map;

import com.hirain.phm.bd.ground.common.push.EMailSender;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 7, 2020 11:33:43 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 7, 2020 jianwen.xin@hirain.com 1.0 create file
 */
// @Service
public class EmailSenderTestImpl implements EMailSender {

	/**
	 * @see com.hirain.phm.bd.ground.common.push.EMailSender#sendSimpleMail(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void sendSimpleMail(String to, String subject, String content) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.hirain.phm.bd.ground.common.push.EMailSender#sendTemplateMail(java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public void sendTemplateMail(String to, String subject, Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.hirain.phm.bd.ground.common.push.EMailSender#sendTemplateMail(java.lang.String, java.lang.String, java.util.Map, java.lang.String)
	 */
	@Override
	public void sendTemplateMail(String to, String subject, Map<String, Object> params, String template) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.hirain.phm.bd.ground.common.push.EMailSender#sendTemplateWithAttachment(java.lang.String, java.lang.String, java.util.Map,
	 *      java.lang.String, java.lang.String[])
	 */
	@Override
	public void sendTemplateWithAttachment(String to, String subject, Map<String, Object> params, String template, String... attachments)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
