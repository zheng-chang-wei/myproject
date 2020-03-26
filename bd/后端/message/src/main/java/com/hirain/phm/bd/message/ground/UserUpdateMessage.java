package com.hirain.phm.bd.message.ground;

import java.util.List;

import com.hirain.phm.bd.message.header.MessageHeader;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserUpdateMessage extends MessageHeader {

	private List<TrainUser> users;
}
