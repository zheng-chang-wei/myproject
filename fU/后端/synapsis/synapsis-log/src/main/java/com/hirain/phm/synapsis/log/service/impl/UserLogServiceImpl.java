/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.log.service.impl;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hirain.phm.synapsis.annotation.Log;
import com.hirain.phm.synapsis.log.BaseService;
import com.hirain.phm.synapsis.log.dao.UserLogMapper;
import com.hirain.phm.synapsis.log.domain.UserLog;
import com.hirain.phm.synapsis.log.service.UserLogService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Apr 16, 2020 7:24:47 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 16, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class UserLogServiceImpl extends BaseService<UserLog> implements UserLogService {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	UserLogMapper userLogMapper;

	/**
	 * @see com.hirain.phm.synapsis.log.service.UserLogService#listLogs(com.hirain.phm.synapsis.log.domain.UserLog)
	 */
	@Override
	public List<UserLog> listLogs(UserLog log) {
		Example example = new Example(UserLog.class);
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(log.getUserName())) {
			criteria.andCondition("username like", "%" + log.getUserName().toLowerCase() + "%");
		}
		if (StringUtils.isNotBlank(log.getOperation())) {
			criteria.andCondition("operation like", "%" + log.getOperation() + "%");
		}
		if (StringUtils.isNotBlank(log.getStartTime())) {
			criteria.andCondition("date_format(operation_time,'%Y-%m-%d %H:%i:%s') >=", log.getStartTime());
		}
		if (StringUtils.isNotBlank(log.getEndTime())) {
			criteria.andCondition("date_format(operation_time,'%Y-%m-%d %T') <=", log.getEndTime());
		}
		example.setOrderByClause("operation_time desc");
		return selectByExample(example);
	}

	/**
	 * @see com.hirain.phm.synapsis.log.service.UserLogService#deleteLogs(java.lang.String)
	 */
	@Override
	@Transactional
	public void deleteLogs(String logIds) {
		List<String> list = Arrays.asList(logIds.split(","));
		batchDelete(list, "id", UserLog.class);
	}

	/**
	 * @see com.hirain.phm.synapsis.log.service.UserLogService#saveLog(org.aspectj.lang.ProceedingJoinPoint,
	 *      com.hirain.phm.synapsis.log.domain.UserLog)
	 */
	@Override
	public void saveLog(ProceedingJoinPoint point, UserLog log) throws JsonProcessingException {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Log logAnnotation = method.getAnnotation(Log.class);
		if (logAnnotation != null) {
			// 注解上的描述
			log.setOperation(logAnnotation.value());
		}
		// 请求的方法参数值
		// Object[] args = point.getArgs();
		// 请求的方法参数名称
		// LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		// String[] paramNames = u.getParameterNames(method);
		// if (args != null && paramNames != null) {
		// StringBuilder params = new StringBuilder();
		// params = handleParams(params, args, Arrays.asList(paramNames));
		// log.setParams(params.toString());
		// }
		// 保存系统日志
		save(log);
	}

	/**
	 * @see com.hirain.phm.synapsis.log.service.UserLogService#deleteLogsByTime(java.util.Date)
	 */
	@Override
	public void deleteLogsByTime(LocalDateTime time) {
		Example example = new Example(UserLog.class);
		Criteria criteria = example.createCriteria();
		if (time != null) {
			criteria.andCondition("operation_time<", time);
		}
		userLogMapper.deleteByExample(example);
	}
}
