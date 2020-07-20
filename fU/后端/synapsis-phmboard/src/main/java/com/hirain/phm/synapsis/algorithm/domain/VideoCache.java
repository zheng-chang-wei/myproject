/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.domain;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec May 22, 2020 10:50:26 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 22, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class VideoCache {

	private Queue<VideoFrame> videoFrameCache = new LinkedBlockingQueue<>(200);

	private AtomicBoolean start = new AtomicBoolean(false);

	public void init(Queue<VideoFrame> cache) {
		videoFrameCache.addAll(cache);
	}

	public VideoFrame read() {
		VideoFrame videoFrame = videoFrameCache.poll();
		return videoFrame;
	}

}
