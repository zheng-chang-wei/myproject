/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life;

import java.io.OutputStream;
import java.util.List;

import com.hirain.phm.bd.ground.fault.param.FaultRequest;
import com.hirain.phm.bd.ground.life.param.LifeDoorItemAVG;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 9, 2020 6:49:27 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface OwnerLifeService {

	/**
	 * @param request
	 * @param stream
	 */
	void exportLifeItems(FaultRequest request, OutputStream stream);

	/**
	 * @param project
	 * @param train
	 * @return
	 */
	List<LifeDoorItemAVG> findDoorItemAVGValue(String project, String train);

	/**
	 * @param project
	 * @param train
	 * @return
	 */
	List<LifeExportObject> getExportItems(String project, String train);

}
