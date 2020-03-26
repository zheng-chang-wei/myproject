/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.response.ResultBean;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 14, 2019 2:08:02 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 14, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Aspect
@Configuration
@Slf4j
public class ControllerAopConfig {

	@Pointcut("execution(public com.hirain.phm.synapsis.response.ResultBean *(..))")
	public void controller() {

	}

	@Around("controller()")
	public Object doAround(ProceedingJoinPoint jp) {
		try {
			Object result = jp.proceed();
			doAfterReturning(result);
			return result;
		} catch (SynapsisException e) {
			doAfterThrowning(e);
			return new ResultBean<>(e.getMessage(), e);
		} catch (Throwable e) {
			doAfterThrowning(e);
			return new ResultBean<>("系统异常，请联系技术支持", e);
		}
	}

	/**
	 * @param e
	 */
	private void doAfterThrowning(Throwable e) {
		log.error(e.getMessage(), e);
	}

	/**
	 * @param result
	 */
	private void doAfterReturning(Object result) {
		log.info("return:" + result);
	}
}
