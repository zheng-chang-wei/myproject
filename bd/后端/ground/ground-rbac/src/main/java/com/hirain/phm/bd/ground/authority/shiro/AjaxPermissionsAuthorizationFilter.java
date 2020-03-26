package com.hirain.phm.bd.ground.authority.shiro;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.bd.ground.common.model.RespCodeEnum;

public class AjaxPermissionsAuthorizationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", RespCodeEnum.FORCE_LOGIN.getCode());
		jsonObject.put("msg", RespCodeEnum.FORCE_LOGIN.getName());
		PrintWriter out = null;
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("application/json");
			out = response.getWriter();
			out.println(jsonObject);
		} catch (Exception e) {
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		return false;
	}

	public FilterRegistrationBean<?> registration(AjaxPermissionsAuthorizationFilter filter) {
		FilterRegistrationBean<?> registration = new FilterRegistrationBean<>(filter);
		registration.setEnabled(false);
		return registration;
	}
}
