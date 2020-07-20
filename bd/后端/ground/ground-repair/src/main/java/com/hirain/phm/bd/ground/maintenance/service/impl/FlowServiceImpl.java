/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirain.phm.bd.ground.authority.controller.RBACGateWay;
import com.hirain.phm.bd.ground.authority.domain.Dept;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.common.event.StatisticsEvent;
import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.maintenance.FlowException;
import com.hirain.phm.bd.ground.maintenance.domain.ExternalFault;
import com.hirain.phm.bd.ground.maintenance.domain.RepairOption;
import com.hirain.phm.bd.ground.maintenance.domain.StepType;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;
import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;
import com.hirain.phm.bd.ground.maintenance.domain.WorkStep;
import com.hirain.phm.bd.ground.maintenance.flowable.FlowableClient;
import com.hirain.phm.bd.ground.maintenance.param.AfterSaleInput;
import com.hirain.phm.bd.ground.maintenance.param.CommonInput;
import com.hirain.phm.bd.ground.maintenance.param.FlowResult;
import com.hirain.phm.bd.ground.maintenance.param.QualityInvestInput;
import com.hirain.phm.bd.ground.maintenance.redis.WorksheetRedisMapper;
import com.hirain.phm.bd.ground.maintenance.service.ExternalFaultService;
import com.hirain.phm.bd.ground.maintenance.service.FlowService;
import com.hirain.phm.bd.ground.maintenance.service.StepTypeService;
import com.hirain.phm.bd.ground.maintenance.service.WorkDetailService;
import com.hirain.phm.bd.ground.maintenance.service.WorkSheetService;
import com.hirain.phm.bd.ground.maintenance.service.WorkStepService;
import com.hirain.phm.bd.ground.train.controller.ProjectGateWay;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.util.RedisUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月28日 下午5:27:20
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月28日 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class FlowServiceImpl implements FlowService {

	@Autowired
	private WorkSheetService sheetService;

	@Autowired
	private WorkStepService stepService;

	@Autowired
	private WorkDetailService detailService;

	@Autowired
	private ExternalFaultService externalService;

	@Autowired
	private StepTypeService typeService;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private WorksheetRedisMapper redisMapper;

	@Autowired
	private FlowableClient flowClient;

	private String uploadRoot = System.getProperty("user.dir") + "//imgs";

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private RBACGateWay rbacGW;

	@Autowired
	private ProjectGateWay trainGW;

	/**
	 * @throws IOException
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowService#upload(org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public String upload(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			throw new IOException("文件为空");
		}
		File root = new File(uploadRoot);
		if (!root.exists()) {
			root.mkdirs();
		}
		String extension = getExtension(file.getOriginalFilename());
		String filename = UUID.randomUUID().toString() + extension;
		File serverFile = new File(root, filename);
		file.transferTo(serverFile);
		return filename;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowService#delete(java.lang.String)
	 */
	@Override
	public String delete(String filepaths) {
		String[] pathArray = filepaths.split(",");
		File root = new File(uploadRoot);
		for (int i = 0; i < pathArray.length; i++) {
			String path = pathArray[i];
			File file = new File(root, path);
			if (file.exists()) {
				file.delete();
			}
		}
		return "删除成功";
	}

	/**
	 * @throws FlowException
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowService#createWorksheet(com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord)
	 */
	@Override
	public WorkSheet createWorksheet(UnifiedFaultRecord fault) {
		WorkDetail detail = detailService.createDetail(fault);
		WorkSheet sheet = create(detail);
		sheetService.updateSheetFault(sheet, fault);
		redisMapper.addSheet(sheet.getCreateTime());
		return sheet;
	}

	/**
	 * @throws FlowException
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowService#commitWorkSheet(java.lang.Long,
	 *      com.hirain.phm.bd.ground.maintenance.domain.WorkDetail)
	 */
	@Override
	public String commitWorkSheet(Long sheetId, WorkDetail detail) throws FlowException {
		WorkSheet sheet = validate(sheetId, RepairOption.Create.getDesc());
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		detail.setId(sheet.getDetailId());
		Project project = trainGW.selectProjectByName(detail.getProject());
		detail.setProjectId(project.getId());
		detailService.updateAll(detail);
		sheetService.updateSheetDetail(sheet, detail);
		sheet.setUserId(user.getUserId());
		sheetService.updateNotNull(sheet);
		commitStep(sheetId, true, "提交", detail, user.getUserId());
		redisMapper.handleSheet(sheet.getCreateTime());
		return "提交成功";
	}

	/**
	 * @throws FlowException
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowService#createAndCommit(com.hirain.phm.bd.ground.maintenance.domain.WorkDetail)
	 */
	@Override
	public String createAndCommit(WorkDetail detail) throws FlowException {
		User user = checkUser(RepairOption.Create.getDesc());
		Project project = trainGW.selectProjectByName(detail.getProject());
		detail.setProjectId(project.getId());
		detailService.save(detail);
		WorkSheet workSheet = create(detail);
		workSheet.setUserId(user.getId());
		ExternalFault fault = externalService.addFault(detail);
		sheetService.updateSheetFault(workSheet, fault);
		commit(workSheet, user.getUserId());

		redisMapper.addSheet(workSheet.getCreateTime());
		redisMapper.handleSheet(workSheet.getCreateTime());
		return "提交成功";
	}

	/**
	 * @throws FlowException
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowService#commitAfterSale(java.lang.Long, boolean,
	 *      com.hirain.phm.bd.ground.maintenance.param.AfterSaleInput)
	 */
	@Override
	public WorkSheet commitAfterSale(Long sheetId, boolean result, AfterSaleInput input) throws FlowException {
		validate(sheetId, RepairOption.AfterSale.getDesc());
		User user = getCurrentUser();
		WorkSheet workSheet = commitStep(sheetId, result, result ? "通过" : "驳回", input, user.getUserId());
		if (input != null) {
			modifyFaultStatistics(input.isStatistics(), workSheet.getFaultType(), workSheet.getFaultId());
		}
		return workSheet;
	}

	@Override
	public WorkSheet terminate(Long sheetId, boolean result, AfterSaleInput input) throws FlowException {
		validate(sheetId, RepairOption.AfterSale.getDesc());
		WorkSheet sheet = terminateStep(sheetId, "中止", input);
		return sheet;
	}

	/**
	 * @throws FlowException
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowService#commitQualityInvest(java.lang.Long, boolean,
	 *      com.hirain.phm.bd.ground.maintenance.param.QualityInvestInput)
	 */
	@Override
	public WorkSheet commitQualityInvest(Long sheetId, boolean result, QualityInvestInput object) throws FlowException {
		validate(sheetId, RepairOption.Invest.getDesc());
		User user = getCurrentUser();
		WorkSheet sheet = commitStep(sheetId, result, "提交", object, user.getUserId());
		String[] departs = object.getDepartments();
		redisMapper.initDeparts(sheetId, departs);
		return sheet;
	}

	/**
	 * @throws FlowException
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowService#commitQuality(java.lang.Long, boolean,
	 *      com.hirain.phm.bd.ground.maintenance.domain.QualityInput)
	 */
	@Override
	public WorkSheet commitQuality(Long sheetId, boolean result, CommonInput object) throws FlowException {
		validate(sheetId, RepairOption.Quality.getDesc());
		User user = getCurrentUser();
		return commitStep(sheetId, result, result ? "通过" : "驳回", object, user.getUserId());
	}

	/**
	 * @throws FlowException
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowService#resolveProblem(java.lang.Long, boolean,
	 *      com.hirain.phm.bd.ground.maintenance.domain.ResolveInput)
	 */
	@Override
	public WorkSheet resolveProblem(Long sheetId, boolean result, CommonInput object) throws FlowException {
		validate(sheetId, RepairOption.Resolve.getDesc());
		User user = getCurrentUser();
		checkDept(sheetId, user);
		WorkSheet sheet = sheetService.selectByKey(sheetId);
		if (redisMapper.incrementAndCompare(sheetId)) {
			FlowResult flowResult = flowClient.process(sheet.getProcessInstanceId(), true);
			update(sheet, flowResult.getNext(), "提交", object, user.getUserId());
		} else {
			// 创建新的step，时间和当前步骤保持一致
			stepService.updateWorkStep(sheet.getStepId(), "提交", object, user.getUserId());
			WorkStep last = stepService.selectByKey(sheet.getStepId());
			WorkStep next = WorkStep.newStep(sheetId, "问题解决");
			next.setStartTime(last.getStartTime());
			next.setSeq(last.getSeq() + 1);
			stepService.save(next);
			sheetService.updateSheetState(sheet, next.getType(), next.getId());
		}
		redisMapper.updateDepart(sheetId, rbacGW.findDeptById(user.getDeptId()).getDeptName());
		return sheet;
	}

	/**
	 * @throws FlowException
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowService#appeal(java.lang.Long, boolean,
	 *      com.hirain.phm.bd.ground.maintenance.param.CommonInput)
	 */
	@Override
	public WorkSheet appeal(Long sheetId, boolean result, CommonInput object) throws FlowException {
		validate(sheetId, RepairOption.Resolve.getDesc());
		User user = getCurrentUser();
		checkDept(sheetId, user);
		return commitStep(sheetId, result, "申诉", object, user.getUserId());
	}

	/**
	 * @throws FlowException
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowService#closeWorkSheet(java.lang.Long, boolean,
	 *      com.hirain.phm.bd.ground.maintenance.param.CommonInput)
	 */
	@Override
	public WorkSheet closeWorkSheet(Long sheetId, boolean result, CommonInput object) throws FlowException {
		validate(sheetId, RepairOption.Track.getDesc());
		WorkSheet sheet = sheetService.selectByKey(sheetId);
		flowClient.process(sheet.getProcessInstanceId(), result);
		User user = getCurrentUser();
		stepService.updateWorkStep(sheet.getStepId(), "关闭", object, user.getUserId());
		sheet.setState("关闭");
		sheetService.updateNotNull(sheet);
		redisMapper.delDeparts(sheetId);
		return sheet;
	}

	/**
	 * @throws FlowException
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowService#problemRepetition(java.lang.Long, boolean,
	 *      com.hirain.phm.bd.ground.maintenance.param.CommonInput)
	 */
	@Override
	public WorkSheet problemRepetition(Long sheetId, boolean result, CommonInput object) throws FlowException {
		validate(sheetId, RepairOption.Track.getDesc());
		User user = getCurrentUser();
		WorkSheet sheet = commitStep(sheetId, result, "问题复现", object, user.getId());
		redisUtil.set(sheet.getId() + "-count", String.valueOf(0));
		Map<Object, Object> map = redisUtil.hget(sheetId + "-department");
		for (Object key : map.keySet()) {
			map.put(key, "false");
		}
		redisMapper.resetDeparts(sheetId, sheet);
		return sheet;
	}

	private User getCurrentUser() {
		return (User) SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * @param filename
	 * @return
	 */
	private String getExtension(String filename) {
		int lastIndexOf = filename.lastIndexOf(".");
		String extension = filename.substring(lastIndexOf);
		return extension;
	}

	private WorkSheet create(WorkDetail detail) {
		WorkSheet sheet = sheetService.addWorkSheet(detail);
		WorkStep step = stepService.addCreateStep(sheet.getId());
		sheetService.updateSheetState(sheet, step.getType(), step.getId());
		FlowResult flowResult = flowClient.start();// 创建新流程
		sheet.setProcessInstanceId(flowResult.getProcessId());
		return sheet;
	}

	private WorkSheet commit(WorkSheet sheet, Long userId) {
		FlowResult flowResult = flowClient.process(sheet.getProcessInstanceId(), true);// 执行提交操作
		update(sheet, flowResult.getNext(), "提交", null, userId);
		return sheet;
	}

	private void modifyFaultStatistics(boolean statistics, int topCode, long faultId) {
		publisher.publishEvent(new StatisticsEvent(statistics, topCode, faultId));
	}

	/**
	 * @param content
	 * @throws FlowException
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowService#commitStep(java.lang.Long, boolean, String, Object)
	 */
	private WorkSheet commitStep(Long sheetId, boolean result, String option, Object content, Long userId) {
		WorkSheet sheet = sheetService.selectByKey(sheetId);
		String instanceId = sheet.getProcessInstanceId();
		FlowResult flowResult = flowClient.process(instanceId, result);
		update(sheet, flowResult.getNext(), option, content, userId);
		return sheet;
	}

	private WorkSheet terminateStep(Long sheetId, String option, Object content) {
		WorkSheet sheet = sheetService.selectByKey(sheetId);
		String instanceId = sheet.getProcessInstanceId();
		flowClient.terminate(instanceId);
		User user = getCurrentUser();
		stepService.updateWorkStep(sheet.getStepId(), option, content, user.getUserId());
		sheet.setState("关闭");
		sheetService.updateNotNull(sheet);
		return sheet;
	}

	/**
	 * @param sheet
	 * @param next
	 * @param result
	 * @param content
	 */
	private void update(WorkSheet sheet, String next, String result, Object content, Long userId) {
		stepService.updateWorkStep(sheet.getStepId(), result, content, userId);
		WorkStep last = stepService.selectByKey(sheet.getStepId());
		WorkStep step = stepService.addWorkStep(sheet.getId(), next, last.getSeq() + 1);
		sheetService.updateSheetState(sheet, next, step.getId());
	}

	private WorkSheet validate(Long sheetId, String state) throws FlowException {
		checkUser(state);
		WorkSheet sheet = sheetService.selectWithLock(sheetId);
		String current = sheet.getState();
		if (!current.equals(state)) {
			throw new FlowException("工单状态发生变化，当前操作不能执行");
		}
		return sheet;
	}

	private User checkUser(String type) throws FlowException {
		StepType stepType = typeService.selectByKey(type);
		Integer roleId = stepType.getRoleId();
		User user = getCurrentUser();
		List<Integer> roles = rbacGW.getRepairRolesByUserId(user.getUserId());
		if (roles.contains(roleId)) {
			return user;
		}
		throw new FlowException("不具备操作权限");
	}

	private void checkDept(Long sheetId, User user) throws FlowException {
		Dept dept = rbacGW.findDeptById(user.getDeptId());
		Object check = redisMapper.checkDepart(sheetId, dept.getDeptName());
		if (check == null) {
			throw new FlowException("所属部门不能处理此工单");
		} else if (check.equals("true")) {
			throw new FlowException("所属部门已提交，不能重复操作");
		}
	}
}
