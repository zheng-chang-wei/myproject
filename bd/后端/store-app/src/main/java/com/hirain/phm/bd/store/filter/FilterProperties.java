/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store.filter;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 2, 2020 3:11:03 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 2, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Component
@ConfigurationProperties("store.filter")
public class FilterProperties {

	private List<String> projects = Arrays.asList("all");
}
