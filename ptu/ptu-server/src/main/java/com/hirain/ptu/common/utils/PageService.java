/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.ptu.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hirain.ptu.common.model.QueryRequest;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年5月14日 下午6:53:39
 * @Description
 *              <p>
 *              分页查询
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月14日 changwei.zheng@hirain.com 1.0 create file
 */
@Component
public class PageService {

	public Map<String, Object> selectByPageNumSize(QueryRequest request, Supplier<?> s) {
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		PageInfo<?> pageInfo = new PageInfo<>((List<?>) s.get());
		PageHelper.clearPage();
		return getDataTable(pageInfo);
	}

	private Map<String, Object> getDataTable(PageInfo<?> pageInfo) {
		Map<String, Object> rspData = new HashMap<>();
		rspData.put("rows", pageInfo.getList());
		rspData.put("total", pageInfo.getTotal());
		return rspData;
	}

}
