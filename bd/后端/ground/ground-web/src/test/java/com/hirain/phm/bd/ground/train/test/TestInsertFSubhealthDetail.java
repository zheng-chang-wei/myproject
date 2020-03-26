/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.GroundWebApplication;
import com.hirain.phm.bd.ground.push.dao.PushInfoMapper;
import com.hirain.phm.bd.ground.subhealth.dao.SubhealthDetailMapper;
import com.hirain.phm.bd.ground.subhealth.dao.SubhealthInfoMapper;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthDetail;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthInfo;
import com.hirain.phm.bd.ground.train.dao.ProjectMapper;
import com.hirain.phm.bd.ground.train.dao.TrainMapper;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.ground.train.service.TrainService;

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
public class TestInsertFSubhealthDetail {

	@Autowired
	private SubhealthDetailMapper subhealthDetailMapper;

	@Autowired
	TrainMapper trainMapper;

	@Autowired
	TrainService trainService;

	@Autowired
	SubhealthInfoMapper subhealthInfoMapper;

	@Autowired
	PushInfoMapper pushInfoMapper;

	@Autowired
	ProjectMapper projectMapper;

	@Test
	public void test() {
		List<SubhealthInfo> subhealthInfos = subhealthInfoMapper.selectAll();
		List<Train> trains = trainService.selectAll();
		for (Train train : trains) {
			for (SubhealthInfo subhealthInfo : subhealthInfos) {
				Integer subhealthInfoId = subhealthInfo.getTypeCode();
				SubhealthDetail subhealthDetail = new SubhealthDetail();
				subhealthDetail.setCarNo(1);
				subhealthDetail.setDebugMode(false);
				subhealthDetail.setDoorAddr(1);
				subhealthDetail.setStartTime(new Date());
				subhealthDetail.setEndTime(new Date());
				subhealthDetail.setSubhealthTypeId(subhealthInfoId);
				subhealthDetail.setStatistics(true);
				subhealthDetail.setTrainId(train.getId());
				subhealthDetailMapper.insert(subhealthDetail);
			}
		}
	}

	@Test
	public void resetDateTest() {
		List<SubhealthDetail> subhealthDetails = subhealthDetailMapper.selectAll();
		for (SubhealthDetail subhealthDetail : subhealthDetails) {
			subhealthDetail.setStartTime(new Date());
			subhealthDetail.setEndTime(new Date());
			subhealthDetailMapper.updateByPrimaryKeySelective(subhealthDetail);
		}
	}
}
