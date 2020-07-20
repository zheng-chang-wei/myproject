package com.hirain.phm.synapsis.log.aspect;

import java.time.LocalDateTime;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.log.domain.UserLog;
import com.hirain.phm.synapsis.log.service.UserLogService;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年9月20日 下午5:13:35
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年9月20日 changwei.zheng@hirain.com 1.0 create file
 */
@Aspect
@Component
public class LogAspect {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserLogService userLogService;

	@Pointcut("@annotation(com.hirain.phm.synapsis.annotation.Log)")
	public void pointcut() {
	}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		try {
			// 执行方法
			result = joinPoint.proceed();
		} catch (Throwable e) {
			log.error(e.getMessage());
			throw e;
		}
		UserLog userLog = new UserLog();
		String userName = (String) SecurityUtils.getSubject().getPrincipal();
		if (userName != null) {
			userLog.setUserName(userName);
			userLog.setOperationTime(LocalDateTime.now());
			userLogService.saveLog(joinPoint, userLog);
		}
		return result;
	}
}
