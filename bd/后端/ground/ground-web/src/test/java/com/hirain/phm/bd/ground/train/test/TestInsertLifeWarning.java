/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.test;

import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.GroundWebApplication;
import com.hirain.phm.bd.ground.life.dao.LifeDoorItemMapper;
import com.hirain.phm.bd.ground.life.dao.LifeItemMapper;
import com.hirain.phm.bd.ground.life.dao.LifeTrainInfoMapper;
import com.hirain.phm.bd.ground.life.domain.LifeDoorItem;
import com.hirain.phm.bd.ground.life.domain.LifeItem;
import com.hirain.phm.bd.ground.life.domain.LifeProjectInfo;
import com.hirain.phm.bd.ground.life.domain.LifeTrainInfo;
import com.hirain.phm.bd.ground.train.dao.ProjectMapper;
import com.hirain.phm.bd.ground.train.dao.TrainMapper;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.train.domain.Train;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年1月7日 上午9:50:46
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年1月7日 changwei.zheng@hirain.com 1.0 create file
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GroundWebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestInsertLifeWarning {


	@Autowired
	LifeItemMapper lifeItemMapper;

	@Autowired
	LifeTrainInfoMapper lifeTrainInfoMapper;


	@Autowired
	ProjectMapper projectMapper;

	@Autowired
	LifeDoorItemMapper lifeDoorItemMapper;

	@Autowired
	TrainMapper trainMapper;

	String[] items = { "到位开关", "紧急解锁开关", "隔离开关", "锁闭开关", "控制器", "电机", "导轨" };

	Integer[] totalLifes = { 500 * 10000, 500 * 10000, 500 * 10000, 500 * 10000, 6 * 10000, 3000, 160 };


	@Test
	public void insertTest() {
		int i = 0;
		for (String itemName : items) {
			Integer lifeItemId = insert_LifeItem(itemName);
			insert_LifeProjectInfo(lifeItemId);
			insert_LifeTrainInfo(i, lifeItemId);
			i++;
		}
		insert_LifeDoorItem();
	}

	private Integer insert_LifeItem(String itemName) {
		LifeItem lifeItem = new LifeItem();
		lifeItem.setItemName(itemName);
		lifeItemMapper.insertUseGeneratedKeys(lifeItem);
		Integer lifeItemId = lifeItem.getId();
		return lifeItemId;
	}

	private void insert_LifeProjectInfo(Integer lifeItemId) {
		List<Project> projects = projectMapper.selectAll();
		for (Project project : projects) {
			LifeProjectInfo lifeProjectInfo = new LifeProjectInfo();
			lifeProjectInfo.setProjectId(project.getId());
			lifeProjectInfo.setLifeitemId(lifeItemId);
		}
	}
	private void insert_LifeTrainInfo(int i, Integer lifeItemId) {
		List<Train> trains = trainMapper.selectAll();
		for (Train train : trains) {
			LifeTrainInfo lifeTrainInfo = new LifeTrainInfo();
			lifeTrainInfo.setLifeitemId(lifeItemId);
			lifeTrainInfo.setReferenceValue(totalLifes[i]);
			lifeTrainInfo.setTrainId(train.getId());
			lifeTrainInfoMapper.insert(lifeTrainInfo);
		}
	}

	private void insert_LifeDoorItem() {
		List<Train> trains = trainMapper.selectAll();
		List<LifeItem> lifeItems = lifeItemMapper.selectAll();
		for (Train train : trains) {
			for (LifeItem lifeItem : lifeItems) {
				LifeDoorItem lifeDoorItem = new LifeDoorItem();
				lifeDoorItem.setCarNo(1);
				lifeDoorItem.setDoorAddr(1);
				lifeDoorItem.setLifeitemId(lifeItem.getId());
				lifeDoorItem.setTrainId(train.getId());
				lifeDoorItem.setValue(new Random().nextInt(1000000));
				lifeDoorItemMapper.insert(lifeDoorItem);
			}
		}
	}
}
