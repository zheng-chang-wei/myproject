package com.hirain.phm.bd.ground.log.aspect;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.log.domain.SysLog;
import com.hirain.phm.bd.ground.log.service.LogService;

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
	LogService logService;

	@Pointcut("@annotation(com.hirain.phm.bd.ground.common.annotation.Log)")
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
		SysLog sysLog = new SysLog();
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		if (user != null) {
			sysLog.setUserName(user.getUserName());
			logService.saveLog(joinPoint, sysLog);
		}
		return result;
	}
}
