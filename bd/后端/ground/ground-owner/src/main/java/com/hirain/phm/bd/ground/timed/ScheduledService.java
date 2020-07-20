/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.timed;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.hirain.phm.bd.ground.common.push.EMailSender;
import com.hirain.phm.bd.ground.fault.param.FaultCount;
import com.hirain.phm.bd.ground.fault.service.OwnerFaultService;
import com.hirain.phm.bd.ground.fault.service.OwnerSubhealthService;
import com.hirain.phm.bd.ground.life.LifeExportObject;
import com.hirain.phm.bd.ground.life.OwnerLifeService;
import com.hirain.phm.bd.ground.mail.domain.MailUser;
import com.hirain.phm.bd.ground.mail.service.MailUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 6, 2020 9:45:17 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Slf4j
public class ScheduledService {

	@Autowired
	private OwnerFaultService faultService;

	@Autowired
	private OwnerSubhealthService subhealthService;

	@Autowired
	private OwnerLifeService lifeService;

	@Autowired
	private TimedProperties props;

	private String project = "深圳7号线一期";

	private String train = "717";

	@Autowired
	private ExcelService excelService;

	@Autowired
	private MailUserService userService;

	@Autowired
	private EMailSender sender;

	/**
	 * 每月第一天统计前一个月的亚健康和故障记录，形成表格，发送到指定邮箱
	 */
	@Scheduled(cron = "0 0 0 1 * ? *")
	public void collectRecord() {
		List<FaultCount> faults = faultService.countLastMonth(project, train, props.getFaults());
		FaultCollection faultCollection = convert(faults, "故障", props.getFaults());
		List<FaultCount> subhealths = subhealthService.countLastMonth(project, train, props.getSubhealths());
		FaultCollection subhealthCollection = convert(subhealths, "亚健康预警", props.getSubhealths());
		List<FaultCollection> datas = Arrays.asList(faultCollection, subhealthCollection);
		List<LifeExportObject> items = lifeService.getExportItems(project, train);
		String attachment = getExcelFilepath(props.getTempDir());
		generateExcelFile(datas, items, attachment);
		File file = new File(attachment);
		if (file.exists()) {
			sendEmail(attachment);
		}
	}

	/**
	 * @param list
	 * @param items
	 */
	private FaultCollection convert(List<FaultCount> list, String sheetName, List<String> items) {
		FaultCollection collection = new FaultCollection();
		collection.setProject(project);
		collection.setTrain(train);
		collection.setSheetName(sheetName);

		for (FaultCount count : list) {
			CarFaultItem carItem = collection.getCarItems().get(count.getCar() - 1);
			DoorItem doorItem = carItem.getItems().get(count.getDoor() - 1);
			int index = items.indexOf(count.getItem());
			doorItem.setItem(index, count.getTotal());
		}
		return collection;
	}

	/**
	 * @param tempDir
	 * @return
	 */
	private String getExcelFilepath(String parentPath) {
		String filename = "汇总表格.xlsx";
		return parentPath + File.separator + filename;
	}

	private void generateExcelFile(List<FaultCollection> datas, List<LifeExportObject> items, String dest) {
		try {
			File file = ResourceUtils.getFile("classpath:template.xlsx");
			String template = file.getAbsolutePath();
			excelService.write(template, dest, datas, items);
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		}
	}

	private void sendEmail(String attachment) {
		String lastMonth = getLastMonth();
		String subject = project + train + "车" + lastMonth + "故障汇总表格";
		Map<String, Object> params = new HashMap<>();
		params.put("project", project);
		params.put("train", train);
		params.put("date", lastMonth);
		List<MailUser> users = userService.selectAll();
		for (MailUser user : users) {
			params.put("name", user.getName());
			String to = user.getEmail();
			try {
				sender.sendTemplateWithAttachment(to, subject, params, "month.ftl", attachment);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	private String getLastMonth() {
		LocalDate date = LocalDate.now();
		LocalDate lastMonth = date.minusMonths(1);
		String lastMonthStr = lastMonth.format(DateTimeFormatter.ofPattern("yyyy年MM月"));
		return lastMonthStr;
	}

}
