/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.push.owner;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.event.FaultTopType;
import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.push.dao.PushInfoMapper;
import com.hirain.phm.bd.ground.push.domain.FaultPushMessage;
import com.hirain.phm.bd.ground.push.domain.PushInfo;
import com.hirain.phm.bd.ground.push.service.PushFaultService;
import com.hirain.phm.bd.ground.train.controller.TrainGateWay;
import com.hirain.phm.bd.ground.train.domain.Project;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Oct 18, 2019 1:32:01 PM
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
public class PushToOwnerService implements PushFaultService {

	private static final String CODE_04 = "03";

	private static final String CODE_05 = "07";

	private static final String CODE_06 = "03";

	private static final String CODE_07 = "07";

	private static final String MESSAGE_VERSION = "0200";

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private CountGenerator counter;

	@Autowired
	private TrainGateWay trainGW;

	@Autowired
	private PushInfoMapper pushMapper;

	@Value("${fault.owner.tooken}")
	private String tooken;

	@Autowired
	private FaultPushClient client;

	/**
	 * @see com.hirain.phm.bd.ground.push.service.PushFaultService#push(com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord, int)
	 */
	@Override
	public void push(UnifiedFaultRecord record, long sheetId) {
		if (FaultTopType.Fault.equals(record.getType()) || FaultTopType.SubHealth.equals(record.getType())) {
			FaultPushMessage message = generate(record);
			PushResponse response = client.pushFault(tooken, message);
			log.info(response.toString());
			if (response.getCode() != 200) {
				log.error("push fault message error:" + response);
			}
		}
	}

	private FaultPushMessage generate(UnifiedFaultRecord record) {
		String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String messageIndex = time + format(counter.getCount());
		PushInfo pushInfo = pushMapper.findByKey(record.getType().getCode(), record.getCode());
		String messageCode = messageCode(record, pushInfo);
		int lifeForecastKm = lifeForecastKm(record);
		int lifeForecastTime = lifeForecastTime(record);
		String beginTime = sdf.format(record.getTime());
		FaultPushMessage message = new FaultPushMessage();
		message.setMessageIndex(messageIndex);
		message.setMessageVersion(MESSAGE_VERSION);
		message.setMessageCode(messageCode);
		message.setLifeForecastKm(lifeForecastKm);
		message.setLifeForecastTime(lifeForecastTime);
		message.setBeginTime(beginTime);
		message.setEndTime(beginTime);
		message.setRemarks(pushInfo.getDescription());
		return message;
	}

	private String messageCode(UnifiedFaultRecord record, PushInfo pushInfo) {
		String code = "";
		Project project = trainGW.selectProjectById(record.getProjectId());
		code += format(project.getLine());
		code += format(record.getTrainId());
		code += format(record.getCarriageId() != null ? record.getCarriageId() : 0);
		code += CODE_04 + CODE_05 + CODE_06 + CODE_07;
		code += format(record.getDoorId() != null ? record.getDoorId() : 0);
		code += "00";
		code += format(pushInfo.getNum());
		code += format(pushInfo.getLevel());
		return code;
	}

	/**
	 * @param record
	 * @return
	 */
	private int lifeForecastKm(UnifiedFaultRecord record) {
		return 1;
	}

	private int lifeForecastTime(UnifiedFaultRecord record) {
		return 1;
	}

	/**
	 * @param count
	 * @return
	 */
	private String format(int count) {
		String s = Integer.toString(count);
		return format(s);
	}

	private String format(String value) {
		String s = value;
		if (s.length() < 2) {
			s = "0" + s;
		} else if (s.length() > 2) {
			s = s.substring(0, 2);
		}
		return s;
	}
}
