/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.impala;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.data.DataDefinitionService;
import com.hirain.phm.bd.data.DataQueryService;
import com.hirain.phm.bd.data.TableNameFormatter;
import com.hirain.phm.bd.data.bean.DataParam;
import com.hirain.phm.bd.data.bean.DataRecord;
import com.hirain.phm.bd.data.impala.dao.DataMapper;

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
@Service
public class ImpalaDataService implements DataQueryService, DataDefinitionService {

	@Autowired
	private DataMapper mapper;

	@Autowired
	private TableNameFormatter tnf;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	/**
	 * @see com.hirain.phm.bd.data.DataQueryService#select(com.hirain.phm.bd.data.bean.DataParam)
	 */
	@Override
	public List<DataRecord> select(DataParam param) {
		return mapper.select(tnf.getTableName(param.getProject(), param.getTrain()), param);
	}

	/**
	 * @see com.hirain.phm.bd.data.DataQueryService#count(com.hirain.phm.bd.data.bean.DataParam)
	 */
	@Override
	public int count(DataParam param) {
		return mapper.count(tnf.getTableName(param.getProject(), param.getTrain()), param);
	}

	/**
	 * @see com.hirain.phm.bd.data.DataQueryService#selectRange(java.lang.String)
	 */
	@Override
	public String[] selectRange(DataParam param) {
		String tableName = tnf.getTableName(param.getProject(), param.getTrain());
		List<String> partitions = mapper.selectPartition(tableName);
		Collections.sort(partitions);
		return new String[] { partitions.get(0), partitions.get(partitions.size() - 1) };
	}

	/**
	 * @see com.hirain.phm.bd.data.DataDefinitionService#delete(com.hirain.phm.bd.data.bean.DataParam)
	 */
	@Override
	public void delete(DataParam param) {
		String tableName = tnf.getTableName(param.getProject(), param.getTrain());
		String date = sdf.format(param.getDate());
		mapper.delete(tableName, date);
	}

}
