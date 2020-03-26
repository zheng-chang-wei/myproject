package com.hirain.phm.bd.ground.common.page;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hirain.phm.bd.ground.common.model.PageResultBean;

@Component
public class RepairPageService {

	public <T> PageResultBean<T> selectByPage(QueryRequest request, Supplier<List<T>> s) {
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		PageInfo<T> pageInfo = new PageInfo<>(s.get());
		PageHelper.clearPage();
		return new PageResultBean<T>(pageInfo.getList(), pageInfo.getTotal());
	}
}
