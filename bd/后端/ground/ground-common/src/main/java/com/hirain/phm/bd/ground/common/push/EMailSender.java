/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.common.push;

import java.util.Map;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 1, 2020 4:05:50 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 1, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface EMailSender {

	/**
	 * @param to
	 * @param subject
	 * @param content
	 */
	void sendSimpleMail(String to, String subject, String content);

	/**
	 * @param to
	 * @param subject
	 * @param params
	 * @throws Exception
	 */
	void sendTemplateMail(String to, String subject, Map<String, Object> params) throws Exception;

	/**
	 * @param to
	 * @param subject
	 * @param params
	 * @param template
	 * @throws Exception
	 */
	void sendTemplateMail(String to, String subject, Map<String, Object> params, String template) throws Exception;

	/**
	 * @param to
	 * @param subject
	 * @param params
	 * @param template
	 * @param attachments
	 * @throws Exception
	 */
	void sendTemplateWithAttachment(String to, String subject, Map<String, Object> params, String template, String... attachments) throws Exception;

}
