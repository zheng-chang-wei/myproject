/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.dictionary.domain.DoorType;
import com.hirain.phm.bd.ground.dictionary.domain.FirstComponent;
import com.hirain.phm.bd.ground.dictionary.service.DoorTypeService;
import com.hirain.phm.bd.ground.dictionary.service.FileParser;
import com.hirain.phm.bd.ground.dictionary.service.FirstComponentService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 9, 2020 5:47:37 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class DoorTypeServiceImpl extends BaseService<DoorType> implements DoorTypeService {

	@Autowired
	private FileParser fileParser;

	@Autowired
	private FirstComponentService firstService;

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.service.DoorTypeService#checkDuplicate(com.hirain.phm.bd.ground.dictionary.domain.DoorType)
	 */
	@Override
	public boolean checkDuplicate(DoorType doorType) {
		Example example = new Example(DoorType.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("name", doorType.getName());
		criteria.andEqualTo("active", true);
		List<DoorType> list = selectByExample(example);
		if (list != null && !list.isEmpty()) {
			DoorType exist = list.get(0);
			if (exist.getId() != doorType.getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.service.DoorTypeService#selectAllActive()
	 */
	@Override
	public List<DoorType> selectAllActive() {
		Example example = new Example(DoorType.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("active", true);
		return selectByExample(example);
	}

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.service.DoorTypeService#deactive(com.hirain.phm.bd.ground.dictionary.domain.DoorType)
	 */
	@Override
	public void deactive(DoorType doorType) {
		doorType.setActive(false);
		updateNotNull(doorType);
	}

	/**
	 * @throws IOException
	 * @see com.hirain.phm.bd.ground.dictionary.service.DoorTypeService#batchImport(com.hirain.phm.bd.ground.dictionary.domain.DoorType,
	 *      org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public void batchImport(DoorType doorType, MultipartFile file) throws IOException {
		List<FirstComponent> list = fileParser.parse(file.getInputStream());
		list.forEach(t -> {
			t.setDoorTypeId(doorType.getId());
		});
		firstService.batchInsert(list);
	}

}
