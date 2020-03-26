/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message.decode.old;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Jan 10, 2019 4:58:25 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 10, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
@NoArgsConstructor
@Deprecated
public class DataFrameD1 {

	/**
	 * 软件版本
	 */
	private int version;

	/**
	 * 门防挤压 1 关门障碍物检测停
	 */
	private boolean door_PreventExtrusion;

	/**
	 * 门隔离 1 隔离开关激活 0 未激活
	 */
	private boolean door_Isolation;

	/**
	 * 门紧急解锁 1 紧急解锁未激活 0 紧急解锁激活
	 */
	private boolean door_EmergencyUnlock;

	/**
	 * 门开好 1 开门到位 0 开门未到位
	 */
	private boolean door_Open;

	/**
	 * 门关好 1 关闭 0 未关闭
	 */
	private boolean door_Close;

	/**
	 * 门动作中 1 开关门过程中 0 未在开关门过程中
	 */
	private boolean door_Action;

	/**
	 * 门零速 1 零速信号激活 0 零速信号未激活
	 */
	private boolean door_NoSpeed;

	/**
	 * 门使能 1 车门使能激活 0 车门使能未激活
	 */
	private boolean door_Enable;

	/**
	 * 门控制选择信号 1=网络位 0=硬线
	 */
	private boolean door_ControlChoose;

	/**
	 * 再开闭信号反馈 1=再开闭信号 0=无效
	 */
	private boolean feedback_SwitchAgain;//

	/**
	 * 检测到障碍物 1=检测到障碍物 0=无效
	 */
	private boolean detectObstacles;//

	/**
	 * 关门信号反馈 1=关门 0=无效
	 */
	private boolean feedback_doorClose;

	/**
	 * 开门信号反馈 1=开门 0=无效
	 */
	private boolean feedback_doorOpen;//

	/**
	 * 门存在故障 1=门存在故障 0=无效
	 */
	private boolean fault_DoorExist;//

	/**
	 * 输出短路 1=输出短路 0=没有输出短路
	 */
	private boolean fault_OutputShortCircuit;//

	/**
	 * 开关门超时 1=超时 0=未超时
	 */
	private boolean door_SwitchTimeOut;//

	/**
	 * 编码器故障 1=故障 0=正常
	 */
	private boolean fault_Encoder;//

	/**
	 * 锁闭开关故障 1=电磁铁故障 0=正常
	 */
	private boolean fault_LockSwitch;//

	/**
	 * 绿色环线故障 1=绿色环线断开 0=正常
	 */
	private boolean fault_GreenLoop;//

	/**
	 * 门板开关故障 1=开关故障 0=正常
	 */
	private boolean fault_doorSwitch;//

	/**
	 * 电机过流故障 1=电机过流 0=电动机电路OK
	 */
	private boolean fault_MotorOverCurrent;//

	/**
	 * 电机开路故障 1=电机开路 0=电动机电路OK
	 */
	private boolean fault_MotorOpenCircuit;//

	/**
	 * 服务按钮信号
	 */
	private boolean signal_ServiceButton;//

	/**
	 * 集控开关门信号
	 */
	private boolean signal_SwitchDoorCentralControl;//

	/**
	 * 集控关门信号
	 */
	private boolean signal_CloseDoorCentralControl;//

	/**
	 * 再开闭信号
	 */
	private boolean signal_OpenCloseAgain;//

	/**
	 * 零速信号
	 */
	private boolean signal_ZeroSpeed;//

	/**
	 * 隔离信号
	 */
	private boolean signal_Isolation;//

	/**
	 * 紧急解锁信号
	 */
	private boolean signal_EmergencyUnlock;//

	/**
	 * 闭锁开关信号
	 */
	private boolean signal_UnlockSwitch;//

	/**
	 * 左门板开关信号
	 */
	private boolean signal_leftDoorOpenClose;//

	/**
	 * 右门板开关信号
	 */
	private boolean signal_rightDoorOpenClose;//

	/**
	 * 门地址编码1
	 */
	private boolean door_CodeAddr1;//

	/**
	 * 门地址编码2
	 */
	private boolean door_CodeAddr2;//

	/**
	 * 门地址编码3
	 */
	private boolean door_CodeAddr3;//

	/**
	 * 门地址编码4
	 */
	private boolean door_CodeAddr4;//

	/**
	 * 安全互锁输入信号
	 */
	private boolean signal_SafetyInterlockInput;//

	/**
	 * 安全互锁输出信号
	 */
	private boolean signal_SafetyInterlockOutput;//

	/**
	 * 开关门指示灯输出
	 */
	private boolean openDoorLightOutput;//

	/**
	 * 紧急解锁电磁铁输出
	 */
	private boolean EmergencyUnlockElectromagnetOutput;//

	/**
	 * 蜂鸣器输出
	 */
	private boolean buzzerOutput;//

}
