/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train;

import com.hirain.phm.bd.message.train.RegisterMessage;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Apr 25, 2019 6:42:25 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 25, 2019 zepei.tao@hirain.com 1.0 create file
 */
public interface IRegisterHandler {

	void handle(RegisterMessage message);

}
