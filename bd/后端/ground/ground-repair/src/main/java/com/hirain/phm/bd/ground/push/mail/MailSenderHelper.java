/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.push.mail;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.hirain.phm.bd.ground.common.push.EMailSender;

import freemarker.template.Configuration;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Oct 16, 2019 5:33:39 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Oct 16, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class MailSenderHelper implements EMailSender {

	@Autowired
	private GroundMailProperties props;

	@Autowired
	private JavaMailSender sender;

	@Override
	public void sendSimpleMail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		message.setFrom(props.getFrom());
		sender.send(message);
	}

	@Override
	public void sendTemplateMail(String to, String subject, Map<String, Object> params) throws Exception {
		String template = "mail.ftl";
		sendTemplateMail(to, subject, params, template);
	}

	@Override
	public void sendTemplateMail(String to, String subject, Map<String, Object> params, String template) throws Exception {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(props.getFrom());
		helper.setTo(to);

		Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
		configuration.setClassForTemplateLoading(this.getClass(), "/");
		String html = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(template, "UTF-8"), params);

		helper.setSubject(subject);
		helper.setText(html, true);// 重点，默认为false，显示原始html代码，无效果

		sender.send(message);
	}

	@Override
	public void sendTemplateWithAttachment(String to, String subject, Map<String, Object> params, String template, String... attachments)
			throws Exception {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(props.getFrom());
		helper.addTo(to);
		helper.setSubject(subject);
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
		configuration.setClassForTemplateLoading(this.getClass(), "/");
		String html = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(template, "UTF-8"), params);
		helper.setText(html, true);

		for (String filepath : attachments) {
			FileSystemResource file = new FileSystemResource(filepath);
			helper.addAttachment(file.getFilename(), file);
		}

		sender.send(message);
	}
}
