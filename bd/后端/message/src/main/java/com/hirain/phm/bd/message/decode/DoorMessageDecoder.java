/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message.decode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.hirain.phm.bd.message.CommonMessage;
import com.hirain.phm.bd.message.decode.old.DataFrameD1;
import com.hirain.phm.bd.message.decode.old.DataFrameD2;
import com.hirain.phm.bd.message.decode.old.DataFrameD3;
import com.hirain.phm.bd.message.decode.old.DataFrameD4;
import com.hirain.phm.bd.message.decode.old.RunDataFrame;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月19日 下午1:36:48
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月19日 jianwen.xin@hirain.com 1.0 create file
 */
@Deprecated
public class DoorMessageDecoder implements IDoorMessageDecoder {

	@Override
	public synchronized RunDataFrame decode(CommonMessage message) {
		byte[] datas = message.getDatas();
		if (datas.length != 32) {
			throw new IllegalArgumentException("door message length error");
		}
		RunDataFrame dataFrame = new RunDataFrame();

		ByteBuffer buffer = ByteBuffer.wrap(datas).order(ByteOrder.LITTLE_ENDIAN);
		dataFrame.setId(buffer.get());// b0
		dataFrame.setType(buffer.get());// b1

		DataFrameD1 d1 = new DataFrameD1();
		dataFrame.setDataFrameD1(d1);
		{
			byte b2 = buffer.get();// b2
			d1.setDoor_PreventExtrusion(int2Boolean(b2));
			d1.setDoor_Isolation(int2Boolean(b2 >> 1));
			d1.setDoor_EmergencyUnlock(int2Boolean(b2 >> 2));
			d1.setDoor_Open(int2Boolean(b2 >> 3));
			d1.setDoor_Close(int2Boolean(b2 >> 4));
			d1.setDoor_Action(int2Boolean(b2 >> 5));
			d1.setDoor_NoSpeed(int2Boolean(b2 >> 6));
			d1.setDoor_Enable(int2Boolean(b2 >> 7));
		}
		{
			byte b3 = buffer.get();
			d1.setDoor_ControlChoose(int2Boolean(b3 >> 2));
			d1.setFeedback_SwitchAgain(int2Boolean(b3 >> 3));
			d1.setDetectObstacles(int2Boolean(b3 >> 4));
			d1.setFeedback_doorClose(int2Boolean(b3 >> 5));
			d1.setFeedback_doorOpen(int2Boolean(b3 >> 6));
			d1.setFault_DoorExist(int2Boolean(b3 >> 7));
		}
		{
			byte b4 = buffer.get();
			d1.setFault_OutputShortCircuit(int2Boolean(b4));
			d1.setDoor_SwitchTimeOut(int2Boolean(b4 >> 1));
			d1.setFault_Encoder(int2Boolean(b4 >> 2));
			d1.setFault_LockSwitch(int2Boolean(b4 >> 3));
			d1.setFault_GreenLoop(int2Boolean(b4 >> 4));
			d1.setFault_doorSwitch(int2Boolean(b4 >> 5));
			d1.setFault_MotorOverCurrent(int2Boolean(b4 >> 6));
			d1.setFault_MotorOpenCircuit(int2Boolean(b4 >> 7));
		}

		{
			byte b5 = buffer.get();
			d1.setSignal_ServiceButton(int2Boolean(b5));
			d1.setSignal_SwitchDoorCentralControl(int2Boolean(b5 >> 1));
			d1.setSignal_CloseDoorCentralControl(int2Boolean(b5 >> 2));
			d1.setSignal_OpenCloseAgain(int2Boolean(b5 >> 3));
			d1.setSignal_ZeroSpeed(int2Boolean(b5 >> 4));
			d1.setSignal_Isolation(int2Boolean(b5 >> 5));
			d1.setSignal_EmergencyUnlock(int2Boolean(b5 >> 6));
			d1.setSignal_UnlockSwitch(int2Boolean(b5 >> 7));
		}
		{
			byte b6 = buffer.get();
			d1.setSignal_leftDoorOpenClose(int2Boolean(b6));
			d1.setSignal_rightDoorOpenClose(int2Boolean(b6 >> 1));
			d1.setDoor_CodeAddr1(int2Boolean(b6 >> 2));
			d1.setDoor_CodeAddr2(int2Boolean(b6 >> 3));
			d1.setDoor_CodeAddr3(int2Boolean(b6 >> 4));
			d1.setDoor_CodeAddr4(int2Boolean(b6 >> 5));
			d1.setSignal_SafetyInterlockInput(int2Boolean(b6 >> 6));
			d1.setSignal_SafetyInterlockOutput(int2Boolean(b6 >> 7));
		}
		{
			byte b7 = buffer.get();
			d1.setOpenDoorLightOutput(int2Boolean(b7));
			d1.setEmergencyUnlockElectromagnetOutput(int2Boolean(b7 >> 1));
			d1.setBuzzerOutput(int2Boolean(b7 >> 2));
		}
		buffer.get();// b8

		DataFrameD2 d2 = new DataFrameD2();
		dataFrame.setDataFrameD2(d2);
		{
			byte b9 = buffer.get();
			d2.setDoor_Open(int2Boolean(b9));
			d2.setProcess_openDoor(int2Boolean(b9 >> 1));
			d2.setProcess_closeDoor(int2Boolean(b9 >> 2));
			d2.setDoor_Close(int2Boolean(b9 >> 3));
			d2.setProcess_PreventExtrusion(int2Boolean(b9 >> 4));
			d2.setPreventExtrusionStop(int2Boolean(b9 >> 5));
		}
		d2.setMotorVoltage(buffer.get());// b10
		d2.setMotorCurrent(buffer.get());// b11
		{
			byte b12 = buffer.get();
			d2.setCarNo(b12 >> 4 & 0x0F);
			d2.setDoorAddr(b12 & 0x0F);
		}
		d2.setTrainNo(Byte.toUnsignedInt(buffer.get()));// b13

		d2.setOpenDoorTime(buffer.getShort());// b14,15

		buffer.get();// b16
		DataFrameD3 d3 = new DataFrameD3();
		dataFrame.setDataFrameD3(d3);
		d3.setCloseDoorTime(buffer.getShort());// b17,18
		d3.setEncoder(buffer.getShort());// b19,20
		d3.setDate(Byte.toUnsignedInt(buffer.get()));// b21
		d3.setHour(Byte.toUnsignedInt(buffer.get()));// b22
		d3.setMinute(Byte.toUnsignedInt(buffer.get()));// b23

		buffer.get();// b24
		DataFrameD4 d4 = new DataFrameD4();
		dataFrame.setDataFrameD4(d4);
		d4.setSecond(Byte.toUnsignedInt(buffer.get()));// b25
		d4.setMillisecond(buffer.getShort());// b26,27
		d4.setTemperature(Byte.toUnsignedInt(buffer.get()));// b28
		buffer.get();// b29
		d4.setVerifyCRC(buffer.getShort());// b30,b31
		return dataFrame;
	}

	private boolean int2Boolean(int i) {
		return toBoolean(i & 0x01);
	}

	private boolean toBoolean(int i) {
		return i == 1 ? true : false;
	}
}
