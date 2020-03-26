/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import com.hirain.phm.bd.ground.common.model.PageResultBean;
import com.hirain.phm.bd.ground.common.model.ResultBean;
import com.hirain.phm.bd.ground.maintenance.FlowException;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月30日 上午10:57:54
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

	@Pointcut("execution(public com.hirain.phm.bd.ground.common.model.ResultBean *(..))")
	public void controller() {
	}

	@Pointcut("execution(public com.hirain.phm.bd.ground.common.model.PageResultBean *(..))")
	public void page() {

	}

	@Around("controller()")
	public Object doAround(ProceedingJoinPoint jp) {
		try {
			Object result = jp.proceed();
			doAfterReturning(result);
			return result;
		} catch (FlowException e) {
			return new ResultBean<>(e.getMessage(), e);
		} catch (Throwable e) {
			doAfterThrowing(e);
			return new ResultBean<>(e);
		}
	}

	@Around("page()")
	public Object doPageAround(ProceedingJoinPoint jp) {
		try {
			Object result = jp.proceed();
			doAfterReturning(result);
			return result;
		} catch (FlowException e) {
			return new PageResultBean<>(e);
		} catch (Throwable e) {
			doAfterThrowing(e);
			return new PageResultBean<>(e);
		}
	}

	public void doAfterReturning(Object ret) {
		log.info("return " + ret);
	}

	public void doAfterThrowing(Throwable ex) {
		log.error(ex.getMessage(), ex);
	}
}
