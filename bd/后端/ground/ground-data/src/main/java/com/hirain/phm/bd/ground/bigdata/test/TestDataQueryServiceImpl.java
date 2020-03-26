/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hirain.phm.bd.data.DataQueryService;
import com.hirain.phm.bd.data.bean.DataParam;
import com.hirain.phm.bd.data.bean.DataRecord;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 6, 2020 5:48:08 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class TestDataQueryServiceImpl implements DataQueryService {

	private List<DataRecord> records;

	/**
	 * @see com.hirain.phm.bd.data.DataQueryService#select(com.hirain.phm.bd.data.bean.DataParam)
	 */
	@Override
	public List<DataRecord> select(DataParam param) {
		if (records == null) {
			getRecords(param);
		}
		return records;
	}

	/**
	 * 
	 */
	private void getRecords(DataParam param) {
		records = new ArrayList<>(10);
		for (int i = 0; i < 10; i++) {
			DataRecord record = new DataRecord();
			record.setProject(param.getProject());
			record.setTrain(param.getTrain());
			record.setCarriageId(1);
			record.setDoorId(i + 1);
			record.setState(1);
			record.setTimestamp(new Date());
			record.setData("1");
			records.add(record);
		}
	}

	/**
	 * @see com.hirain.phm.bd.data.DataQueryService#count(com.hirain.phm.bd.data.bean.DataParam)
	 */
	@Override
	public int count(DataParam param) {
		if (records == null) {
			getRecords(param);
		}
		return records.size();
	}

}
