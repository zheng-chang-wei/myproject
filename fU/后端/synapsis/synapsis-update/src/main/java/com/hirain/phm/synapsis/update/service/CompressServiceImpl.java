/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.service;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.update.compression.Compressor;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 8, 2020 3:47:08 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 8, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class CompressServiceImpl implements CompressService {

	@Autowired
	private Map<String, Compressor> factories;

	@Autowired
	private UpdateProperties props;

	/**
	 * @see com.hirain.phm.synapsis.update.service.CompressService#deCompress(java.io.File, java.lang.String)
	 */
	@Override
	public boolean deCompress(File sourceFile, String destPath) throws Exception {
		Compressor factory = factories.get(props.getCompressionSuffix());
		try {
			factory.deCompress(sourceFile, destPath);
		} catch (Exception e) {
			throw e;
		}
		return true;
	}

}
