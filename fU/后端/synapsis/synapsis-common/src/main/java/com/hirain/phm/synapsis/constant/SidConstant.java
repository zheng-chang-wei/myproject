/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.constant;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 9:56:42 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class SidConstant {

	/**
	 * 板卡自检回复
	 */
	public static final int SELFCHECK_CONFIRM = 0x00;

	/**
	 * 校正时间
	 */
	public static final int TIME_COMMAND = 0x01;

	/**
	 * 启动或停止
	 */
	public static final int CONTROL_COMMAND = 0x02;

	/**
	 * 版本文件下载
	 */
	public static final int DOWNLOAD_COMMAND = 0x03;

	/**
	 * 版本文件激活
	 */
	public static final int ACTIVATE_COMMAND = 0x04;

	/**
	 * 板卡状态查询
	 */
	public static final int STATUS_COMMAND = 0x05;

	public static final int ALGORITHM_RESULT_RESPONSE = 0x06;

	/**
	 * 发送异常信息
	 */
	public static final int ERROR_COMMAND = 0x07;

	/**
	 * 算法状态查询
	 */
	public static final int ALGORITHM_STATUS_COMMAND = 0x08;

	/**
	 * 启动步骤
	 */
	public static final int LAUNCH_STEP_COMMAND = 0x09;

}
