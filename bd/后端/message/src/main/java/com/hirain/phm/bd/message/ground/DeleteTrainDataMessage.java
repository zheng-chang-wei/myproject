/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created Jun 6, 2019 5:56:18 PM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Jun 6, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.message.ground;

import java.util.Date;

import com.hirain.phm.bd.message.header.MessageHeader;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeleteTrainDataMessage extends MessageHeader {

	private Date deleteDate;

}
