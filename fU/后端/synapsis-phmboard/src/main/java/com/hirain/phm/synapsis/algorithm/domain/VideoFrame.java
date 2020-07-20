/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.domain;

import org.bytedeco.javacv.Frame;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec May 21, 2020 7:21:14 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 21, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Data
@AllArgsConstructor
public class VideoFrame {

	private long timestamp;

	private Frame frame;
}
