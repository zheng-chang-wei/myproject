/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.push.mail;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.authority.controller.RBACGateWay;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.push.dao.PushInfoMapper;
import com.hirain.phm.bd.ground.push.dao.RepairSuggestionMapper;
import com.hirain.phm.bd.ground.push.dao.TreatmentSuggestionMapper;
import com.hirain.phm.bd.ground.push.domain.PushInfo;
import com.hirain.phm.bd.ground.push.domain.RepairSuggestion;
import com.hirain.phm.bd.ground.push.service.PushFaultService;
import com.hirain.phm.bd.ground.train.controller.TrainGateWay;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.util.Utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Oct 18, 2019 1:33:08 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Oct 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Profile({ "test", "local" })
@Slf4j
public class SendMailService implements PushFaultService {

	@Autowired
	private MailSenderHelper helper;

	@Autowired
	private TrainGateWay trainGW;

	@Autowired
	private PushInfoMapper pushMapper;

	@Autowired
	private TreatmentSuggestionMapper treatmentMapper;

	@Autowired
	private RepairSuggestionMapper repairMapper;

	@Value("${spring.mail.url.prefix}")
	private String urlPrefix;

	@Value("${spring.mail.mobile.prefix}")
	private String mobilePrefix;

	@Autowired
	private RBACGateWay rbacGW;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");

	/**
	 * @see com.hirain.phm.bd.ground.push.service.PushFaultService#push(com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord, int)
	 */
	@Override
	public void push(UnifiedFaultRecord record, long sheetId) {
		Map<String, Object> params = new HashMap<>();
		params.putAll(mailContent(record));
		params.putAll(userContent(record, sheetId));
		String subject = getSubject(params);
		String to = getToMail(params);
		log.info("TO " + to);
		try {
			helper.sendTemplateMail(to, subject, params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
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

		PushInfo info = pushMapper.findByKey(record.getType().getCode(), record.getCode());
		if (info != null) {
			content.put("level", info.getLevel());
			content.put("description", info.getDescription());
			content.put("treatment", treatmentMapper.selectByPrimaryKey(info.getTreatmentId()).getSuggestion());
			RepairSuggestion suggestion = repairMapper.selectByPrimaryKey(info.getRepairId());
			if (suggestion == null) {
				content.put("repair", "无");
			} else {
				content.put("repair", suggestion.getSuggestion());
			}
		}
		return content;
	}

	/**
	 * @param record
	 * @param sheetId
	 * @return
	 */
	private Map<String, Object> userContent(UnifiedFaultRecord record, long sheetId) {
		Map<String, Object> content = new HashMap<>();
		List<User> users = rbacGW.getUsersByProjectID(record.getProjectId());
		for (User user : users) {
			content.put("name", user.getName());
			content.put("to", user.getEmail());
			String param = user.getUserName() + "&" + user.getPassword() + "&" + sheetId;
			Encoder encoder = Base64.getEncoder();
			String enUrl = encoder.encodeToString(param.getBytes());
			content.put("url", urlPrefix + "?param=" + enUrl);
			content.put("mobileUrl", mobilePrefix + "?param=" + enUrl);
			System.err.println(urlPrefix + "?param=" + enUrl);
			System.err.println(mobilePrefix + enUrl);
		}
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
