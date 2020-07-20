/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.dictionary.domain.BaseValue;
import com.hirain.phm.bd.ground.dictionary.service.BaseValueService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 15, 2020 10:25:07 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 15, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class BaseValueServiceImpl extends BaseService<BaseValue> implements BaseValueService {

	private static List<String> variables = Arrays.asList("电机电压", "电机电流", "编码器", "开门时间", "关门时间");

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.service.BaseValueService#list(java.lang.Integer)
	 */
	@Override
	public List<BaseValue> list(Integer projectId) {
		List<BaseValue> list = selectBy(projectId);
		if (list == null) {
			list = new ArrayList<>();
		}
		if (list.isEmpty()) {
			for (String variable : variables) {
				BaseValue value = new BaseValue();
				value.setProjectId(projectId);
				value.setVariable(variable);
				value.setBaseValues("");
				save(value);
				list.add(value);
			}
		}
		return list;
	}

	/**
	 * @param projectId
	 */
	private List<BaseValue> selectBy(Integer projectId) {
		Example example = new Example(BaseValue.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("projectId", projectId);
		return selectByExample(example);
	}

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.service.BaseValueService#getBaseValue(int, java.lang.String)
	 */
	@Override
	public List<String> getBaseValue(int projectId, String variable) {
		Example example = new Example(BaseValue.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("projectId", projectId);
		criteria.andEqualTo("variable", variable);
		List<BaseValue> list = selectByExample(example);
		if (list == null || list.isEmpty()) {
			return null;
		}
		String values = list.get(0).getBaseValues();
		String[] baseValues = values.split(",");
		return Arrays.asList(baseValues);
	}

}
