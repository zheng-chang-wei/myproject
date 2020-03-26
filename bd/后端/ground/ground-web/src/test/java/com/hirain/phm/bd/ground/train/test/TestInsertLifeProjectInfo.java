/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.GroundWebApplication;
import com.hirain.phm.bd.ground.life.dao.LifeItemMapper;
import com.hirain.phm.bd.ground.life.dao.LifeProjectInfoMapper;
import com.hirain.phm.bd.ground.life.domain.LifeItem;
import com.hirain.phm.bd.ground.life.domain.LifeProjectInfo;
import com.hirain.phm.bd.ground.train.dao.ProjectMapper;
import com.hirain.phm.bd.ground.train.domain.Project;

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
public class TestInsertLifeProjectInfo {

	@Autowired
	LifeProjectInfoMapper lifeProjectInfoMapper;

	@Autowired
	ProjectMapper projectMapper;

	@Autowired
	LifeItemMapper lifeItemMapper;

	@Test
	public void test() {
		List<Project> projects = projectMapper.selectAll();
		List<LifeItem> lifeItems = lifeItemMapper.selectAll();
		for (Project project : projects) {
			for (LifeItem lifeItem : lifeItems) {
				LifeProjectInfo projectInfo = new LifeProjectInfo();
				projectInfo.setLifeitemId(lifeItem.getId());
				projectInfo.setProjectId(project.getId());
				lifeProjectInfoMapper.insert(projectInfo);
			}
		}

	}

}
