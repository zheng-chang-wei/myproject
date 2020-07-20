/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.video;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec May 6, 2020 3:52:06 PM
 * @Description
 *              <p>
 *              处理视频流的服务层
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 6, 2020 zepei.tao@hirain.com 1.0 create file
 */
public interface VideoService {

	/**
	 * 校验视频帧缓存对象的时戳是否在定长之内
	 * 
	 * @param range
	 *            单位：s
	 */
	void startCheck(int range);

	/**
	 * 存储报警视频
	 */
	void storageWarningVideo(String folderName, String videoName, long warningTime) throws Exception, org.bytedeco.javacv.FrameRecorder.Exception;

	/**
	 * 开始缓存Frame
	 */
	void startCacheFrame() throws Exception, org.bytedeco.javacv.FrameRecorder.Exception;

	/**
	 * 停止接收rtsp视频流数据
	 */
	void stop() throws Exception, org.bytedeco.javacv.FrameRecorder.Exception;

}
