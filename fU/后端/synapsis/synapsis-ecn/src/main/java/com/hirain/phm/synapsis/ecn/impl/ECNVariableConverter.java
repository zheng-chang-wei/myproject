/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.ecn.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.ecn.ECNVariableQuery;
import com.hirain.phm.synapsis.ecn.model.Device;
import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.protocol.VariableConverter;
import com.hirain.phm.synapsis.setting.ECNVariable;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 4:29:19 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component("ECN-converter")
public class ECNVariableConverter implements VariableConverter {

	private ECNVariableQuery query;

	/**
	 * @see com.hirain.phm.synapsis.protocol.VariableConverter#convert(java.lang.Object)
	 */
	@Override
	public List<ECNVariable> convert(Object object) throws SynapsisException {
		Device device = (Device) object;
		return query.getAllVariables(device);
	}

	/**
	 * @param query
	 *            the query to set
	 */
	@Autowired
	public void setQuery(ECNVariableQuery query) {
		this.query = query;
	}

}
