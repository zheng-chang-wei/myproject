/*******************************************************************************
 * Copyright (c) 2017, 2017 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.model;

import java.io.Serializable;

/**
 * @Version 1.0
 * @Author gangjie.yang@hirain.com
 * @Created May 9, 2017 2:44:21 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 9, 2017 gangjie.yang@hirain.com 1.0 create file
 */
public interface IStandardModel extends Serializable, Cloneable {

	String getDescription();

	void setDescription(final String description);
}
