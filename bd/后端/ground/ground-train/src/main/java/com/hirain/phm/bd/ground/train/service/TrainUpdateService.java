package com.hirain.phm.bd.ground.train.service;

import com.hirain.phm.bd.message.train.OffsetMessage;
import com.hirain.phm.bd.message.train.TrainConfigMessage;
import com.hirain.phm.bd.message.train.WillMessage;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Aug 28, 2019 9:35:00 AM
 * @Description
 *              <p>
 *              处理车载端发送的业务报文
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Aug 28, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface TrainUpdateService {

	void updateConfig(TrainConfigMessage message);

	void upadateOnline(WillMessage message);

	void updateOffset(OffsetMessage message);
}
