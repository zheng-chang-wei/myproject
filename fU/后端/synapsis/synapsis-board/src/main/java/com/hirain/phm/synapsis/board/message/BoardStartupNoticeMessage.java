/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.message;

import java.util.List;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.board.util.BoardDecodeHelper;
import com.hirain.phm.synapsis.message.SynapsisResponse;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 4, 2019 11:17:24 AM
 * @Description
 *              <p>
 *              控制管理——>服务管理 启动完成通知报文
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class BoardStartupNoticeMessage implements SynapsisResponse {

	private List<Board> boards;

	@Override
	public void parse(byte[] datas) {
		boards = BoardDecodeHelper.parseBoardInfos(datas);
	}
}
