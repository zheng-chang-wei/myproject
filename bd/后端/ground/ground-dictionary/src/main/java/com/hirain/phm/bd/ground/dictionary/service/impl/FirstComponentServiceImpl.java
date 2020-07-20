/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.dictionary.dao.FirstComponentMapper;
import com.hirain.phm.bd.ground.dictionary.domain.FirstComponent;
import com.hirain.phm.bd.ground.dictionary.service.FirstComponentService;
import com.hirain.phm.bd.ground.dictionary.service.SecondComponentService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 30, 2020 11:58:33 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 30, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class FirstComponentServiceImpl extends BaseService<FirstComponent> implements FirstComponentService {

	@Autowired
	private FirstComponentMapper mapper;

	@Autowired
	private SecondComponentService secondService;

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.service.FirstComponentService#getAllActive(java.lang.Integer)
	 */
	@Override
	public List<FirstComponent> getAllActive(Integer doorTypeId) {
		Example example = new Example(FirstComponent.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("doorTypeId", doorTypeId);
		criteria.andEqualTo("active", true);
		return selectByExample(example);
	}

	@Override
	public boolean checkDuplicate(FirstComponent component) {
		Example example = new Example(FirstComponent.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("doorTypeId", component.getDoorTypeId());
		criteria.andEqualTo("active", true);
		criteria.andEqualTo("name", component.getName());
		List<FirstComponent> list = selectByExample(example);
		if (list != null && !list.isEmpty()) {
			FirstComponent exist = list.get(0);
			if (exist.getId() != component.getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.service.FirstComponentService#deactive(com.hirain.phm.bd.ground.dictionary.domain.FirstComponent)
	 */
	@Override
	public void deactive(FirstComponent component) {
		component.setActive(false);
		updateNotNull(component);
	}

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.service.FirstComponentService#batchInsert(java.util.List)
	 */
	@Override
	public void batchInsert(List<FirstComponent> list) {
		mapper.insertList(list);
		list.forEach(f -> {
			f.getChildren().forEach(s -> {
				s.setParentId(f.getId());
			});
			secondService.batchInsert(f.getChildren());
		});
	}

}
