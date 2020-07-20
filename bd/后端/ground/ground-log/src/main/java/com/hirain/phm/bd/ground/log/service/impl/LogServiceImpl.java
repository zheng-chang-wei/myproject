package com.hirain.phm.bd.ground.log.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hirain.phm.bd.ground.common.annotation.Log;
import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.log.dao.LogMapper;
import com.hirain.phm.bd.ground.log.domain.SysLog;
import com.hirain.phm.bd.ground.log.service.LogService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年9月20日 下午5:13:59
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年9月20日 changwei.zheng@hirain.com 1.0 create file
 */
@Service
public class LogServiceImpl extends BaseService<SysLog> implements LogService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	LogMapper logMapper;

	@Override
	public List<SysLog> listLogs(SysLog log) {
		Example example = new Example(SysLog.class);
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
		return this.selectByExample(example);
	}

	@Override
	@Transactional
	public void deleteLogs(String logIds) {
		List<String> list = Arrays.asList(logIds.split(","));
		this.batchDelete(list, "id", SysLog.class);
	}

	@Override
	public void saveLog(ProceedingJoinPoint joinPoint, SysLog log) throws JsonProcessingException {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Log logAnnotation = method.getAnnotation(Log.class);
		if (logAnnotation != null) {
			// 注解上的描述
			log.setOperation(logAnnotation.value());
		}
		// 请求的方法参数值
		Object[] args = joinPoint.getArgs();
		// 请求的方法参数名称
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paramNames = u.getParameterNames(method);
		if (args != null && paramNames != null) {
			StringBuilder params = new StringBuilder();
			params = handleParams(params, args, Arrays.asList(paramNames));
			log.setParams(params.toString());
		}
		// 保存系统日志
		save(log);
	}

	private StringBuilder handleParams(StringBuilder params, Object[] args, List<String> paramNames) throws JsonProcessingException {
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof MultipartFile) {
				MultipartFile file = (MultipartFile) args[i];
				params.append("  ").append(paramNames.get(i)).append(": ").append(file.getOriginalFilename());
			} else if (args[i] instanceof Long[] || args[i] instanceof Integer[] || args[i] instanceof String[]) {
				params.append("  ").append(paramNames.get(i)).append(": ").append(traversalArray(args[i]));
			} else if (args[i] instanceof Date) {
				params.append("  ").append(paramNames.get(i)).append(": ").append(formatDate(args[i]));
			} else if (args[i] instanceof String) {
				params.append("  ").append(paramNames.get(i)).append(": ").append(args[i]);
			} else if (args[i] instanceof Integer) {
				params.append("  ").append(paramNames.get(i)).append(": ").append(args[i]);
			} else if (args[i] instanceof List) {
				params.append("  ").append(paramNames.get(i)).append(": ").append(listToString((List<?>) args[i]));
			} else if (args[i] instanceof Serializable) {
				params.append("  ").append(paramNames.get(i)).append(": ").append(toString(args[i]));
			} else {
				params.append("  ").append(paramNames.get(i)).append(": ").append(args[i]);
			}
		}
		return params;
	}

	/**
	 * 遍历数组
	 * 
	 * @param object
	 * @return
	 */
	private String traversalArray(Object object) {
		StringBuffer buffer = new StringBuffer();
		Object[] values = (Object[]) object;
		for (Object value : values) {
			buffer.append(value);
			buffer.append(",");
		}
		if (values.length > 0) {
			// 删除最后一个逗号
			buffer.deleteCharAt(buffer.length() - 1);
		}
		return buffer.toString();
	}

	private String formatDate(Object object) {
		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = dateFormat.format(object);
		return format;
	}

	private String listToString(List<?> listObj) {
		StringBuffer buffer = new StringBuffer();
		for (Object obj : listObj) {
			buffer.append(toString(obj));
		}
		return buffer.toString();
	}

	private String toString(Object obj) {
		StringBuffer buffer = new StringBuffer();
		// buffer.append(obj.getClass().getName());
		buffer.append("[");
		List<Field> fieldList = new ArrayList<>();
		Class<? extends Object> tempClass = obj.getClass();
		// 当父类为null的时候说明到达了最上层的父类(Object类).
		while (tempClass != null) {
			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			// 得到父类,然后赋给自己
			tempClass = tempClass.getSuperclass();
		}
		for (Field f : fieldList) {
			f.setAccessible(true);
			String name = f.getName();
			try {
				Object value = f.get(obj);
				if (value != null && StringUtils.isNotBlank(value.toString()) && !"serialVersionUID".equals(name)) {
					// 当value为集合且集合不为空时才添加
					if (value instanceof List) {
						List<?> list = (List<?>) value;
						if (list.size() != 0) {
							buffer.append(name + "=" + value);
							buffer.append(",");
						}
					} else {
						buffer.append(name + "=" + value);
						buffer.append(",");
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		// 删除最后一个逗号
		if (buffer.indexOf(",") != -1) {
			buffer.deleteCharAt(buffer.length() - 1);
		}
		buffer.append("]");
		return buffer.toString();
	}

	@Override
	public void deleteLogsByTime(Date time) {
		Example example = new Example(SysLog.class);
		Criteria criteria = example.createCriteria();
		if (time != null) {
			criteria.andCondition("operation_time<", time);
		}
		logMapper.deleteByExample(example);
	}
}
