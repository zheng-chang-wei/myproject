package com.hirain.qsy.shaft.common.aspect;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirain.qsy.shaft.common.util.HttpContextUtils;
import com.hirain.qsy.shaft.common.util.IPUtils;
import com.hirain.qsy.shaft.model.SysLog;
import com.hirain.qsy.shaft.model.User;
import com.hirain.qsy.shaft.service.LogService;

import lombok.extern.slf4j.Slf4j;

/**
 * AOP 记录用户操作日志
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

	@Autowired
	LogService logService;

	// @Autowired
	// RedisCacheService redisCacheService;

	@Pointcut("@annotation(com.hirain.qsy.shaft.common.annotation.Log)")
	public void logPointcut() {

		// do nothing
	}

	// @Pointcut("@annotation(com.hirain.qsy.shaft.common.annotation.DeleteRedisCache)")
	// public void deleteRedisCachePointcut() {
	//
	// // do nothing
	// }

	@Around("logPointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long beginTime = System.currentTimeMillis();
		Object result = null;
		try {
			// 执行方法
			result = joinPoint.proceed();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		// 执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		SysLog sysLog = new SysLog();
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		if (user != null) {
			sysLog.setUsername(user.getUsername());
			sysLog.setTime(time);
			HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
			String ip = IPUtils.getIpAddr(request);
			sysLog.setIp(ip);
			logService.saveLog(joinPoint, sysLog);
		}
		return result;
	}

	// @After("deleteRedisCachePointcut()")
	// public void deleteRedisCacheAround() throws Throwable {
	// redisCacheService.deleteBypPttern("DataService*");
	// redisCacheService.deleteBypPttern("StatisticsService*");
	// }
}
