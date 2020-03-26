/*******************************************************************************
 * Copyright (c) 2017, 2017 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.model;

import java.util.Map;

/**
 * @Version 1.0
 * @Author gangjie.yang@hirain.com
 * @Created May 19, 2017 1:50:42 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 19, 2017 gangjie.yang@hirain.com 1.0 create file
 */
public interface IPropertyCache {

	void setProperty(String key, Object value);

	Object getProperty(String key);

	Map<String, Object> getPropertyMap();
}
