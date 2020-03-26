/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.message;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.hirain.phm.synapsis.board.domain.ADBoard;
import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.domain.CPUBoard;
import com.hirain.phm.synapsis.board.domain.ECNBoard;
import com.hirain.phm.synapsis.board.domain.MVBBoard;
import com.hirain.phm.synapsis.board.domain.PHMBoard;
import com.hirain.phm.synapsis.board.domain.SSDBoard;
import com.hirain.phm.synapsis.board.util.BoardDecodeHelper;
import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.SynapsisResponse;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 4, 2019 2:35:47 PM
 * @Description
 *              <p>
 *              控制管理——>服务管理 板卡状态上报
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class BoardStatusResponseMessage implements SynapsisResponse {

	/**
	 * 板卡数量
	 */
	private int boardNum;

	private List<Board> boards = new ArrayList<>();

	/**
	 * @see com.hirain.phm.synapsis.message.SynapsisResponse#parse(byte[])
	 */
	@Override
	public void parse(byte[] datas) {
		ByteBuffer buffer = ByteBuffer.wrap(datas).order(MessageConstant.MESSAGE_ORDER);
		boardNum = buffer.get();
		for (int i = 0; i < boardNum; i++) {
			byte[] boardBytes = new byte[128];
			buffer.get(boardBytes, 0, 128);
			int boardType = boardBytes[0];
			switch (BoardType.values()[boardType]) {
			case CPU:
				CPUBoard cpuBoard = BoardDecodeHelper.getCPUBoard(boardBytes);
				boards.add(cpuBoard);
				break;
			case PHM_IMX8:
			case PHM_TX2:
			case PHM_AGX:
			case PHM_MVB:
				PHMBoard phmBoard = BoardDecodeHelper.getPHMBoard(boardBytes);
				boards.add(phmBoard);
				break;
			case ECN:
				ECNBoard ecnBoard = BoardDecodeHelper.getECNBoard(boardBytes);
				boards.add(ecnBoard);
				break;
			case MVB:
				MVBBoard mvbBoard = BoardDecodeHelper.getMVBBoard(boardBytes);
				boards.add(mvbBoard);
				break;
			case AD1:
			case AD2:
			case AD3:
				ADBoard adBoard = BoardDecodeHelper.getADBoard(boardBytes);
				boards.add(adBoard);
				break;
			case WIRELESS:
				break;
			case SSD:
				SSDBoard ssdBoard = BoardDecodeHelper.getSSDBoard(boardBytes);
				boards.add(ssdBoard);
				break;
			default:
				break;
			}
		}
	}

}
