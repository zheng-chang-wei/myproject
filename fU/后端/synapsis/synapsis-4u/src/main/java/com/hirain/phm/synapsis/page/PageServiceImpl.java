/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.page;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hirain.phm.synapsis.response.PageResultBean;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 24, 2020 5:26:45 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 24, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class PageServiceImpl implements PageService {

	@Override
	public <T> PageResultBean<List<T>> selectByPage(QueryRequest request, Supplier<List<T>> s) {
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		PageInfo<T> pageInfo = new PageInfo<>(s.get());
		PageHelper.clearPage();
		return new PageResultBean<>(pageInfo.getList(), pageInfo.getTotal());
	}

}
