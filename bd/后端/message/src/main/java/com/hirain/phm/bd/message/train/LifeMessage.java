/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message.train;

import com.hirain.phm.bd.message.header.MessageHeader;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created May 27, 2019 7:07:08 PM  
 * @Description
 * <p>  传递寿命计算所需参数的寿命报文对象
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 27, 2019     zepei.tao@hirain.com             1.0        create file 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LifeMessage extends MessageHeader {

	private int carNo;//车厢号

	private int doorAddr;

	private int doorOpenFrequency;//开门总次数

	private int doorCloseFrequency;//关门总次数

	private int doorOpenTime;//开门总时间

	private int doorCloseTime;//关门总时间

	private int communicationTime;//通信总时间

	private int emergencyUnlockFrequency;//紧急解锁开关次数

	private int doorIsolationFrequency;//门隔离激活次数

	private int lockSwitchFrenquency;//闭锁开关信号变化次数

}
