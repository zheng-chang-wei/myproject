/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hirain.phm.bd.ground.authority.controller.RBACGateWay;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.common.event.FaultTopType;
import com.hirain.phm.bd.ground.maintenance.domain.RepairOption;
import com.hirain.phm.bd.ground.maintenance.domain.StepType;
import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;
import com.hirain.phm.bd.ground.maintenance.domain.WorkStep;
import com.hirain.phm.bd.ground.maintenance.param.FaultType;
import com.hirain.phm.bd.ground.maintenance.param.SheetCountResponse;
import com.hirain.phm.bd.ground.maintenance.param.Suggestion;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetRecord;
import com.hirain.phm.bd.ground.maintenance.param.WorksheetPacket;
import com.hirain.phm.bd.ground.maintenance.redis.WorksheetRedisMapper;
import com.hirain.phm.bd.ground.maintenance.service.FlowReadService;
import com.hirain.phm.bd.ground.maintenance.service.RbacService;
import com.hirain.phm.bd.ground.maintenance.service.StepTypeService;
import com.hirain.phm.bd.ground.maintenance.service.WorkSheetService;
import com.hirain.phm.bd.ground.maintenance.service.WorkStepService;
import com.hirain.phm.bd.ground.push.domain.PushInfo;
import com.hirain.phm.bd.ground.push.service.SuggestionService;
import com.hirain.phm.bd.ground.train.controller.TrainGateWay;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.util.RedisUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年6月3日 下午2:57:05
 * @Description
 *              <p>
 * @TODO 修改数据查询
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年6月3日 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class FlowReadServiceImpl implements FlowReadService {

	@Autowired
	private WorkSheetService sheetService;

	@Autowired
	private RBACGateWay rbacGW;

	@Autowired
	private RbacService rbacService;

	@Autowired
	private WorkStepService stepService;

	@Autowired
	private StepTypeService typeService;

	@Autowired
	private TrainGateWay trainGW;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private SuggestionService suggestionService;

	@Autowired
	private WorksheetRedisMapper redisMapper;

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.FlowReadService#listWorkSheetsWithDetail(com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam)
	 */
	@Override
	public List<WorkSheet> listWorkSheetsWithDetail(WorkSheetQueryParam param) {
		User user = rbacService.getCurrentUser();
		List<Integer> roles = rbacService.getRepairRoles(user);
		List<StepType> types = filter(roles, typeService.selectAll());
		List<WorkSheet> sheets = listSheets(user, isOnlyAfterSales(types), param);
		return sheets;
	}

	@Override
	public List<WorkSheetRecord> listWorkSheets(WorkSheetQueryParam param) {
		User user = rbacService.getCurrentUser();
		List<Integer> roles = rbacService.getRepairRoles(user);
		List<StepType> types = filter(roles, typeService.selectAll());
		List<WorkSheet> sheets = listSheets(user, isOnlyAfterSales(types), param);

		// 工单类型和维修权限ID的Map映射
		Map<String, Integer> typeMap = types.stream().collect(Collectors.toMap(t -> t.getType(), t -> t.getRoleId()));
		List<WorkSheetRecord> records = new ArrayList<>();
		// 判断每个工单可操作范围
		sheets.stream().forEach(sheet -> {
			sheet.setFaultTypeName(getFaultTopTypeByCode(sheet.getFaultType()));
			WorkSheetRecord record = new WorkSheetRecord();
			record.setSheet(sheet);
			boolean option = false;
			if (typeMap.containsKey(sheet.getState())) {
				if (sheet.getState().equals(RepairOption.Resolve.getDesc())) {
					// 工单处于问题解决状态，需要判断用户所处的部门是否有权限，以及所处部门是否已有员工提交解决
					Long deptId = user.getDeptId();
					Object result = redisUtil.hmget(sheet.getId() + "-department", String.valueOf(deptId));
					option = "true".equals(result);
				} else if (sheet.getState().equals(RepairOption.Create.getDesc())) {
					if (sheet.getUserId() == null || user.getUserId().equals(sheet.getUserId())) {
						option = true;
					} else {
						return;
					}
				} else {
					option = true;
				}
			}
			record.setOption(option);
			records.add(record);
		});
		return records;
	}

	private List<WorkSheet> listSheets(User user, boolean onlyAfterSales, WorkSheetQueryParam param) {
		if (onlyAfterSales) {
			List<Long> projects = rbacService.getProjects(user);
			if (param.getProject() != null) {
				Project project = trainGW.selectProjectByName(param.getProject());
				if (projects.contains(Long.valueOf(project.getId()))) {
					projects.clear();
					projects.add(Long.valueOf(project.getId()));
				} else {
					return null;
				}
			}
			return sheetService.listWorkSheetOfProjects(projects, param);
		} else {
			return sheetService.listWorkSheetWithDetail(param);
		}
	}

	/**
	 * @param types
	 * @return
	 */
	private boolean isOnlyAfterSales(List<StepType> types) {
		if (types.size() == 1) {
			StepType stepType = types.get(0);
			if (stepType.getType().equals(RepairOption.Create.getDesc())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 过滤出和工单步骤相关的权限
	 * 
	 * @param groundRoleIds
	 * @param stepTypes
	 * @return
	 */
	private List<StepType> filter(List<Integer> roles, List<StepType> stepTypes) {
		List<StepType> types = new ArrayList<>();
		for (StepType type : stepTypes) {
			if (roles.contains(type.getRoleId())) {
				types.add(type);
			}
		}
		return types;
	}

	/**
	 * 获取工单，工单详情以及工单步骤
	 * 
	 * @see com.hirain.phm.bd.ground.maintenance.service.DFlowReadService#getWorksheet(java.lang.Long)
	 */
	@Override
	public WorksheetPacket getWorksheet(Long sheetId) {
		WorkSheet sheet = sheetService.selectBySheetId(sheetId);
		List<WorkStep> steps = findAllSteps(sheetId);
		WorksheetPacket packet = new WorksheetPacket();
		packet.setSheet(sheet);
		packet.setSteps(steps);
		if (sheet.getFaultCode() != null) {
			packet.setSuggestion(findSuggestion(sheet.getFaultType(), sheet.getFaultCode()));
		}
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		packet.setOption(getRepairOption(sheet, user));
		return packet;
	}

	private List<WorkStep> findAllSteps(Long sheetId) {
		List<WorkStep> steps = stepService.findAllSteps(sheetId);
		steps.forEach(step -> {
			Long auditorId = step.getAuditorId();
			if (auditorId != null) {
				User user = rbacGW.findUserByIdWithRole(auditorId);
				step.setAuditor(user.getName());
			}
		});
		return steps;
	}

	private Suggestion findSuggestion(int type, int code) {
		PushInfo pushInfo = suggestionService.findPushInfo(type, code);
		String treatment = suggestionService.findTreatmentSuggestion(pushInfo.getTreatmentId());
		String repair = suggestionService.findRepairSuggestion(pushInfo.getRepairId());
		Suggestion suggestion = new Suggestion();
		suggestion.setTreatment(treatment);
		suggestion.setRepair(repair);
		return suggestion;
	}

	/**
	 * 根据用户权限判断工单的可操作状态
	 * 
	 * @param sheet
	 * @param user
	 * @return
	 */
	private RepairOption getRepairOption(WorkSheet sheet, User user) {
		StepType type = typeService.selectByKey(sheet.getState());
		List<Integer> roles = rbacGW.getRepairRolesByUserId(user.getUserId());
		boolean contains = roles.contains(type.getRoleId());
		if (contains) {
			if (type.getType().equals(RepairOption.Resolve.getDesc())) {
				Object result = redisUtil.hmget(sheet.getId() + "-department", String.valueOf(user.getDeptId()));
				return "true".equals(result) ? RepairOption.Resolve : RepairOption.None;
			} else {
				return Arrays.asList(RepairOption.values()).stream().filter(t -> t.getDesc().equals(type.getType())).findFirst().get();
			}
		}
		return RepairOption.None;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.DFlowReadService#findLastSameStep(java.lang.Long, java.lang.String)
	 */
	@Override
	public WorkStep findLastSameStep(Long sheetId, String state) {
		WorkStep step = stepService.findLastSameType(sheetId, state);
		ObjectMapper mapper = new ObjectMapper();
		try {
			// 由于前端接收的content字符串中包含转义符号，故JSON ----> JsonNode
			if (step != null) {
				JsonNode rootNode = mapper.readTree(step.getContent());
				step.setData(rootNode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return step;
	}

	@Override
	public SheetCountResponse countSheets() {
		Date time = new Date();
		int countNew = redisMapper.countNewSheet(time);
		int countHandled = redisMapper.countHandledSheet(time);
		int countUnHandled = redisMapper.countUnHandled();
		SheetCountResponse response = new SheetCountResponse();
		response.setNewSheets(countNew);
		response.setHandled(countHandled);
		response.setUnHandled(countNew - countHandled);
		response.setUnHandledBeforeMonth(countUnHandled);
		return response;
	}

	@Override
	public List<FaultType> getFaultTypes() {
		List<FaultType> list = new ArrayList<>();
		List<Integer> faultTypes = sheetService.getFaultTypes();
		for (Integer code : faultTypes) {
			String faultTypeName = getFaultTopTypeByCode(code);
			if (faultTypeName != null) {
				list.add(new FaultType(code, faultTypeName));
			}
		}
		return list;
	}

	private String getFaultTopTypeByCode(Integer code) {
		FaultTopType[] values = FaultTopType.values();
		for (FaultTopType faultTopType : values) {
			if (faultTopType.getCode() == code) {
				return faultTopType.getName();
			}
		}
		return null;
	}
}
