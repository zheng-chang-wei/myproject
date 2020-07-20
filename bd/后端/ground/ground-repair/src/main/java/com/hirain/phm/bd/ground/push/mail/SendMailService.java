/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.push.mail;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.authority.controller.RBACGateWay;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.common.event.FaultTopType;
import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.common.push.EMailSender;
import com.hirain.phm.bd.ground.common.push.PushFaultService;
import com.hirain.phm.bd.ground.train.controller.TrainGateWay;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.util.Utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 1, 2020 4:14:01 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 1, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Profile("local")
@Slf4j
public class SendMailService implements PushFaultService {

	@Autowired
	private EMailSender helper;

	@Autowired
	private TrainGateWay trainGW;

	@Autowired
	private GroundMailProperties props;

	@Autowired
	private RBACGateWay rbacGW;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");

	@Autowired
	private SolutionContentService contentService;

	/**
	 * @see com.hirain.phm.bd.ground.common.push.PushFaultService#push(com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord, long)
	 */
	@Override
	public void push(UnifiedFaultRecord record, long sheetId) {
		if (record.getType().equals(FaultTopType.Fault)) {
			List<User> users = rbacGW.getUsersByProjectID(record.getProjectId());
			for (User user : users) {
				Map<String, Object> params = new HashMap<>();
				params.putAll(mailContent(record));
				params.putAll(userContent(record, sheetId, user));
				String subject = getSubject(params);
				String to = getToMail(params);
				log.info("TO " + to);
				try {
					helper.sendTemplateMail(to, subject, params);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * @param record
	 * @return
	 */
	private Map<String, Object> mailContent(UnifiedFaultRecord record) {
		Map<String, Object> content = new HashMap<>();
		Project project = trainGW.selectProjectById(record.getProjectId());
		content.put("project", project.getName());
		content.put("train", record.getTrainId());
		content.put("door", Utils.getDoor(record));
		content.put("type", record.getType().getName());
		content.put("fault", record.getFaultName());
		content.put("timestamp", sdf.format(record.getTime()));
		content.putAll(contentService.solutionContent(record));
		return content;
	}

	/**
	 * @param record
	 * @param sheetId
	 * @param user
	 * @return
	 */
	private Map<String, Object> userContent(UnifiedFaultRecord record, long sheetId, User user) {
		Map<String, Object> content = new HashMap<>();
		content.put("name", user.getName());
		content.put("to", user.getEmail());
		String param = user.getUserName() + "&" + user.getPassword() + "&" + sheetId;
		Encoder encoder = Base64.getEncoder();
		String enUrl = encoder.encodeToString(param.getBytes());
		content.put("url", props.getUrlPrefix() + "?param=" + enUrl);
		content.put("mobileUrl", props.getMobilePrefix() + "?param=" + enUrl);
		System.err.println(props.getUrlPrefix() + "?param=" + enUrl);
		System.err.println(props.getMobilePrefix() + enUrl);
		return content;
	}

	/**
	 * @param params
	 * @return
	 */
	private String getSubject(Map<String, Object> params) {
		String subject = "通知：";
		subject += params.get("project");
		subject += params.get("train") + "号车辆";
		subject += "故障报警";
		return subject;
	}

	/**
	 * @param params
	 * @return
	 */
	private String getToMail(Map<String, Object> params) {
		return (String) params.get("to");
	}

}
