/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.message;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.hirain.phm.synapsis.board.domain.BoardStartResult;
import com.hirain.phm.synapsis.board.domain.BoardStartStructure;
import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.SynapsisResponse;

import lombok.Data;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created Dec 4, 2019 5:41:01 PM  
 * @Description
 * <p>   控制管理——>服务管理   板卡启动/停止回复
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 4, 2019     zepei.tao@hirain.com             1.0        create file 
 */
@Data
public class BoardControlResponseMessage implements SynapsisResponse {

	/**
	 * 是否启动、停止成功
	 */
	private boolean successed;

	/**
	 * 失败记录
	 */
	private List<BoardStartStructure> boardStartStructures = new ArrayList<>();

	/** 
	 * @see com.hirain.phm.synapsis.message.SynapsisResponse#parse(byte[])
	 */
	@Override
	public void parse(byte[] datas) {
		final ByteBuffer buffer = ByteBuffer.wrap(datas).order(MessageConstant.MESSAGE_ORDER);
		successed = buffer.get() == 0 ? true : false;
		int faultNum = buffer.get();//失败记录数
		for (int i = 0; i < faultNum; i++) {
			BoardStartStructure structure = new BoardStartStructure();

			byte[] cardIPBytes = new byte[4];
			buffer.get(cardIPBytes);
			StringBuilder cardIP = new StringBuilder();
			cardIP.append(String.valueOf(cardIPBytes[0] & 0XFF));
			cardIP.append(".");
			cardIP.append(String.valueOf(cardIPBytes[1] & 0XFF));
			cardIP.append(".");
			cardIP.append(String.valueOf(cardIPBytes[2] & 0XFF));
			cardIP.append(".");
			cardIP.append(String.valueOf(cardIPBytes[3] & 0XFF));
			structure.setCardIP(cardIP.toString());

			structure.setSlotID(buffer.get() & 0xFF);

			int resultCode = buffer.get();
			structure.setResult(BoardStartResult.values()[resultCode]);
			boardStartStructures.add(structure);
		}

	}

}
