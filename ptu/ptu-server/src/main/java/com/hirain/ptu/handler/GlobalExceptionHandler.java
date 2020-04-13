package com.hirain.ptu.handler;

import javax.servlet.http.HttpServletRequest;

import com.hirain.ptu.common.exception.ExcelFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hirain.ptu.common.model.ResponseBo;

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(value = ExcelFormatException.class)
	public Object handleExcelFormatException(HttpServletRequest request, ExcelFormatException ex) {
		log.error(ex.getMessage(), ex);
		return ResponseBo.error(ex.getMessage());
	}
	@ExceptionHandler(value = Exception.class)
	public Object handleException(HttpServletRequest request, Exception ex) {
		log.error(ex.getMessage(), ex);
		return ResponseBo.error("系统异常");
	}

}
