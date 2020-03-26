package com.hirain.phm.bd.message.decode.old;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Jan 10, 2019 4:58:52 PM
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
public class DataFrameD2 {

	/**
	 * 门开
	 */
	private boolean door_Open;

	/**
	 * 开门过程
	 */
	private boolean process_openDoor;

	/**
	 * 关门过程
	 */
	private boolean process_closeDoor;

	/**
	 * 门关
	 */
	private boolean door_Close;

	/**
	 * 防挤压过程中
	 */
	private boolean process_PreventExtrusion;

	/**
	 * 防挤压停
	 */
	private boolean PreventExtrusionStop;

	/**
	 * 电机电压
	 */
	private byte motorVoltage;

	/**
	 * 电机电流
	 */
	private byte motorCurrent;

	/**
	 * 车厢号
	 */
	private int carNo;

	/**
	 * 门地址
	 */
	private int doorAddr;

	/**
	 * 车号
	 */
	private int trainNo;

	/**
	 * 开门时间
	 */
	private short openDoorTime;

}
