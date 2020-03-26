package com.hirain.phm.synapsis.constant;

import lombok.Getter;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月5日 上午9:40:40
 * @Description
 *              <p>
 *              运行状态
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月5日 changwei.zheng@hirain.com 1.0 create file
 */
public enum RunState {
	/**
	 * 软件启动后的初始状态，表明控制管理程序正在进行自检
	 */
	SELFCHECK("自检"),
	/**
	 * 收到自检结果后，进入初始化状态，软件开始检查历史配置
	 */
	INIT("初始化"),
	/**
	 * 系统处于停止状态
	 */
	IDLE("停止"),
	/**
	 * 加载状态，表明系统正在加载配置
	 */
	LOADING("加载中"),
	/**
	 * 当前系统没有正确配置来运行，需要等待用户操作
	 */
	WAITING("等待"),
	/**
	 * 系统根据配置正常运行
	 */
	RUNNING("运行");

	@Getter
	private String desciption;

	private RunState(String description) {
		desciption = description;
	}

}
