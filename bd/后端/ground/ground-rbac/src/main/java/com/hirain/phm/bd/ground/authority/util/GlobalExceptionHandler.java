package com.hirain.phm.bd.ground.authority.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hirain.phm.bd.ground.common.model.ResponseBo;

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(value = UnauthenticatedException.class)
	public Object handleException(HttpServletRequest request, UnauthenticatedException ex) {
		return ResponseBo.error("NotLogin");
	}

	@ExceptionHandler(value = Exception.class)
	public Object handleException(HttpServletRequest request, Exception ex) {
		log.error(ex.getMessage(), ex);
		return ResponseBo.error("系统异常");
	}

	// private static boolean isAjaxRequest(HttpServletRequest request) {
	//
	// return (request.getHeader("X-Requested-With") != null &&
	// "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
	// }

}
