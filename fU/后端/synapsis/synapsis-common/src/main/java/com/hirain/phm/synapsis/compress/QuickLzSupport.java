/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.compress;

import org.springframework.stereotype.Component;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 6:01:39 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class QuickLzSupport implements CompressSupport {

	/**
	 * @see com.hirain.phm.synapsis.compress.CompressSupport#compress(byte[])
	 */
	@Override
	public byte[] compress(byte[] source) {
		QuickLZ lz = new QuickLZ();
		return lz.compress(source, 1);
	}

	/**
	 * @see com.hirain.phm.synapsis.compress.CompressSupport#decompress(byte[])
	 */
	@Override
	public byte[] decompress(byte[] source) {
		QuickLZ lz = new QuickLZ();
		return lz.decompress(source);
	}

}
