/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.flow.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月30日 上午9:56:43
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月30日 jianwen.xin@hirain.com 1.0 create file
 */
@Aspect
@Configuration
@Slf4j
public class ControllerAopConfig {

	@Pointcut("execution(public com.hirain.phm.bd.flow.domain.ResultBean *(..))")
	public void webLog() {
	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) {
		log.info("return " + ret);
	}

	@AfterThrowing(throwing = "ex", pointcut = "webLog()")
	public void doAfterThrowing(Throwable ex) {
		log.error(ex.getMessage(), ex);
	}
}
