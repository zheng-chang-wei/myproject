/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.dictionary.dao.SecondComponentMapper;
import com.hirain.phm.bd.ground.dictionary.domain.SecondComponent;
import com.hirain.phm.bd.ground.dictionary.service.SecondComponentService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 30, 2020 1:28:32 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 30, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class SecondComponentServiceImpl extends BaseService<SecondComponent> implements SecondComponentService {

	@Autowired
	private SecondComponentMapper mapper;

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.service.SecondComponentService#getAllActive(java.lang.Integer)
	 */
	@Override
	public List<SecondComponent> getAllActive(Integer parentId) {
		Example example = new Example(SecondComponent.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("parentId", parentId);
		criteria.andEqualTo("active", true);
		return selectByExample(example);
	}

	@Override
	public boolean checkDuplicate(SecondComponent component) {
		Example example = new Example(SecondComponent.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("parentId", component.getParentId());
		criteria.andEqualTo("active", true);
		criteria.andEqualTo("name", component.getName());
		List<SecondComponent> list = selectByExample(example);
		if (list != null && !list.isEmpty()) {
			SecondComponent exist = list.get(0);
			if (exist.getId() != component.getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.service.SecondComponentService#deactive(com.hirain.phm.bd.ground.dictionary.domain.SecondComponent)
	 */
	@Override
	public void deactive(SecondComponent component) {
		component.setActive(false);
		updateNotNull(component);
	}

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.service.SecondComponentService#batchInsert(java.util.List)
	 */
	@Override
	public void batchInsert(List<SecondComponent> list) {
		mapper.insertList(list);
	}
}
