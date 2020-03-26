package com.hirain.phm.bd.message.train;

import com.hirain.phm.bd.message.header.MessageHeader;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OffsetMessage extends MessageHeader {

	private int offset;
}
