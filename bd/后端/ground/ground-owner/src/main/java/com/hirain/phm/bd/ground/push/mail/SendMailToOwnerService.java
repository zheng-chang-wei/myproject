/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.push.mail;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.event.FaultTopType;
import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.common.push.EMailSender;
import com.hirain.phm.bd.ground.common.push.PushFaultService;
import com.hirain.phm.bd.ground.mail.domain.MailUser;
import com.hirain.phm.bd.ground.mail.service.MailUserService;
import com.hirain.phm.bd.ground.push.dao.PushInfoMapper;
import com.hirain.phm.bd.ground.push.dao.RepairSuggestionMapper;
import com.hirain.phm.bd.ground.push.dao.TreatmentSuggestionMapper;
import com.hirain.phm.bd.ground.push.domain.PushInfo;
import com.hirain.phm.bd.ground.push.domain.RepairSuggestion;
import com.hirain.phm.bd.ground.push.domain.TreatmentSuggestion;
import com.hirain.phm.bd.ground.train.controller.TrainGateWay;
import com.hirain.phm.bd.ground.train.domain.Project;

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
@Profile({ "dev" })
@Slf4j
public class SendMailToOwnerService implements PushFaultService {

	@Autowired
	private EMailSender helper;

	@Autowired
	private TrainGateWay trainGW;

	@Autowired
	private PushInfoMapper pushMapper;

	@Autowired
	private TreatmentSuggestionMapper treatmentMapper;

	@Autowired
	private RepairSuggestionMapper repairMapper;

	@Autowired
	private MailUserService userService;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");

	/**
	 * @see com.hirain.phm.bd.ground.common.push.service.PushFaultService#push(com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord, int)
	 */
	@Override
	public void push(UnifiedFaultRecord record, long sheetId) {
		if (record.getType().equals(FaultTopType.Fault)) {
			List<MailUser> users = userService.selectAll();
			Map<String, Object> params = new HashMap<>();
			params.putAll(mailContent(record));
			String subject = getSubject(params);
			for (MailUser user : users) {
				params.putAll(userContent(record, sheetId, user));
				String to = getToMail(params);
				log.info("TO " + to);
				try {
					helper.sendTemplateMail(to, subject, params, "mail-owner.ftl");
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
		content.put("door", getDoor(record));
		content.put("type", record.getType().getName());
		content.put("fault", record.getFaultName());
		content.put("timestamp", sdf.format(record.getTime()));

		PushInfo info = pushMapper.findByKey(record.getType().getCode(), record.getCode());
		if (info != null) {
			content.put("level", info.getLevel() == null ? "无" : info.getLevel());
			content.put("description", info.getDescription() == null ? " " : info.getDescription());
			TreatmentSuggestion treatment = treatmentMapper.selectByPrimaryKey(info.getTreatmentId());
			content.put("treatment", treatment != null ? treatment.getSuggestion() : "无");
			RepairSuggestion suggestion = repairMapper.selectByPrimaryKey(info.getRepairId());
			if (suggestion == null) {
				content.put("repair", "无");
			} else {
				content.put("repair", suggestion.getSuggestion());
			}
		} else {
			content.put("level", "");
			content.put("description", "");
			content.put("treatment", "无");
			content.put("repair", "无");
		}
		return content;
	}

	private String getDoor(UnifiedFaultRecord fault) {
		String door = "";
		if (fault.getCarriageId() != null) {
			door += fault.getCarriageId() + "车";
		}
		if (fault.getDoorId() != null) {
			door += fault.getDoorId() + "门";
		}
		return door.isEmpty() ? "--" : door;
	}

	/**
	 * @param record
	 * @param sheetId
	 * @param user
	 * @return
	 */
	private Map<String, Object> userContent(UnifiedFaultRecord record, long sheetId, MailUser user) {
		Map<String, Object> content = new HashMap<>();
		content.put("name", user.getName());
		content.put("to", user.getEmail());
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
