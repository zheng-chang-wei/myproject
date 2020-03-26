/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.service;

import java.util.List;

import com.hirain.phm.bd.ground.bigdata.param.DataRecordResponse;
import com.hirain.phm.bd.ground.bigdata.param.DataRecordVO;
import com.hirain.phm.bd.ground.bigdata.param.DataRequest;
import com.hirain.phm.bd.ground.bigdata.param.DataResponse;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 5, 2020 2:43:10 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 5, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface DataRecordService {

	/**
	 * @param param
	 * @return
	 */
	DataRecordResponse getDataRecords(DataRequest request);

	/**
	 * @param param
	 * @return
	 */
	int count(DataRequest request);

	/**
	 * @param record
	 * @param variables
	 * @return
	 * @throws Exception
	 */
	DataResponse getData(DataRecordVO record, List<String> variables) throws Exception;

}
