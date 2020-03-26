package com.hirain.qsy.shaft.common.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirain.qsy.shaft.service.RedisCacheService;

/**
 * AOP 记录用户操作日志
 */
@Aspect
@Component
public class DeleteRedisCacheAspect {

	@Autowired
	RedisCacheService redisCacheService;

	@Pointcut("@annotation(com.hirain.qsy.shaft.common.annotation.DeleteRedisCache)")
	public void deleteRedisCachePointcut() {
		// do nothing
	}

	@After("deleteRedisCachePointcut()")
	public void deleteRedisCacheAround() throws Throwable {
		redisCacheService.deleteBypPttern("DataService*");
		redisCacheService.deleteBypPttern("StatisticsService*");
	}
}
