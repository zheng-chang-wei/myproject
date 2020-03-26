/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created May 24, 2019 11:37:22 AM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 24, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.message.train;

import java.util.Date;
import java.util.List;

import com.hirain.phm.bd.message.header.MessageHeader;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubhealthPacket extends MessageHeader {

	private int carID;

	private int doorID;

	private boolean debug;

	private Date startTime;

	private Date endTime;

	private List<Integer> subhealthItems;

	private int state;// 0:开门，1:关门，2:开门中，3:关门中。

	private List<String> keys;

	private List<FaultRawData> datas;
}
