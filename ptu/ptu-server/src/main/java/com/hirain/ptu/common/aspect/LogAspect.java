package com.hirain.ptu.common.aspect;

import com.alibaba.druid.filter.AutoLoad;
import com.hirain.ptu.common.annotation.Log;
import com.hirain.ptu.common.model.ResponseBo;
import com.hirain.ptu.service.ManageService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/** AOP 记录用户操作日志 */
@Aspect
@Component
@Slf4j
public class LogAspect {

  @Autowired ManageService manageService;

  @Pointcut("@annotation(com.hirain.ptu.common.annotation.Log)")
  public void logPointcut() {}

  @Around("logPointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    Log logAnnotation = method.getAnnotation(Log.class);
    boolean isExistTable = true;
    if (logAnnotation != null) {
      String tableName = logAnnotation.value();
      isExistTable = manageService.isExistTable(tableName);
    }
    if (isExistTable) {
      return joinPoint.proceed();
    } else {
      return ResponseBo.error("查询无数据，请先导入数据");
    }
  }
}
