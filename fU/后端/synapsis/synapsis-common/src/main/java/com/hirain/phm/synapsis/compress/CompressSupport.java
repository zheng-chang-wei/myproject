/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.compress;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 5:43:52 PM
 * @Description
 *              <p>
 *              压缩、解压缩工具（内容压缩）
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface CompressSupport {

	byte[] compress(byte[] source);

	byte[] decompress(byte[] source);
}
