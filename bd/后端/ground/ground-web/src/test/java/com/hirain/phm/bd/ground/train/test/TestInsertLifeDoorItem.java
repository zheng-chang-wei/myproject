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
import com.hirain.phm.bd.ground.life.domain.LifeDoorItem;
import com.hirain.phm.bd.ground.life.domain.LifeItem;
import com.hirain.phm.bd.ground.train.dao.TrainMapper;
import com.hirain.phm.bd.ground.train.domain.Train;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年1月7日 上午9:50:35
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
public class TestInsertLifeDoorItem {

	@Autowired
	LifeDoorItemMapper lifeDoorItemMapper;

	@Autowired
	TrainMapper trainMapper;

	@Autowired
	LifeItemMapper lifeItemMapper;


	@Test
	public void test() {
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
