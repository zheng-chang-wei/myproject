/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis;

import java.io.UnsupportedEncodingException;

import org.springframework.test.web.servlet.MvcResult;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.synapsis.response.ResultBean;

/**
 * @Version 1.0   
 * @Author jianwen.xin@hirain.com  
 * @Created Jan 7, 2020 11:38:37 AM  
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Jan 7, 2020     jianwen.xin@hirain.com             1.0        create file 
 */
public class TestUtils {

	/**
	 * @param result
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static ResultBean<?> getResultBean(MvcResult result) throws UnsupportedEncodingException {
		String content = result.getResponse().getContentAsString();
		System.out.println(content);
		ResultBean<?> resultBean = JSONObject.parseObject(content, ResultBean.class);
		System.out.println(resultBean);
		return resultBean;
	}

}
