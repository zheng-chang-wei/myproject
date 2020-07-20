/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;

import lombok.Getter;
import lombok.Setter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created May 7, 2020 6:32:19 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 7, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
@Aspect
@ConfigurationProperties("synapsis")
public class ValidateAopConfig {

	@Getter
	@Setter
	private boolean validateEnable = true;

	@Pointcut("execution(public * com.hirain.phm.synapsis.setting.support.SettingValidator.*(..))")
	public void validate() {
	}

	@Around("validate()")
	public Object checkEnable(ProceedingJoinPoint jp) throws Throwable {
		// String methodName = jp.getSignature().getName();
		if (!validateEnable) {
			return new ValidateResult();
		} else {
			return jp.proceed();
		}
	}
}
