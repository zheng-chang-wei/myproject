/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.impala;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.bd.data.DataQueryService;
import com.hirain.phm.bd.data.bean.DataParam;
import com.hirain.phm.bd.data.bean.DataRecord;
import com.hirain.phm.bd.data.impala.dao.DataQueryMapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 6, 2020 1:55:24 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class ImpalaQueryService implements DataQueryService {

	@Autowired
	private DataQueryMapper mapper;

	/**
	 * @see com.hirain.phm.bd.data.DataQueryService#select(com.hirain.phm.bd.data.bean.DataParam)
	 */
	@Override
	public List<DataRecord> select(DataParam param) {
		return mapper.select(getTableName(param), param);
	}

	/**
	 * @see com.hirain.phm.bd.data.DataQueryService#count(com.hirain.phm.bd.data.bean.DataParam)
	 */
	@Override
	public int count(DataParam param) {
		return mapper.count(getTableName(param), param);
	}

	/**
	 * @param param
	 * @return
	 */
	private String getTableName(DataParam param) {
		// String project = PinyinUtil.getFullSpell(param.getProject());
		// String train = PinyinUtil.getFullSpell(param.getTrain());
		return "shenzhen_717";
	}

}
