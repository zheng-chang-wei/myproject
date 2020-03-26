/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.common.csv.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 4:17:25 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Target(ElementType.FIELD)
@Retention(RUNTIME)
public @interface DateFormat {

	String pattern();
}
