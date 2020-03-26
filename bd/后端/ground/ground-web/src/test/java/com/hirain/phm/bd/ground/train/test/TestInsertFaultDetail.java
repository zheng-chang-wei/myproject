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
import com.hirain.phm.bd.ground.fault.dao.FaultDetailMapper;
import com.hirain.phm.bd.ground.fault.dao.FaultInfoMapper;
import com.hirain.phm.bd.ground.fault.domain.FaultDetail;
import com.hirain.phm.bd.ground.fault.domain.FaultInfo;
import com.hirain.phm.bd.ground.push.dao.PushInfoMapper;
import com.hirain.phm.bd.ground.push.domain.PushInfo;
import com.hirain.phm.bd.ground.train.dao.ProjectMapper;
import com.hirain.phm.bd.ground.train.dao.TrainMapper;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.ground.train.param.TrainParamHeader;
import com.hirain.phm.bd.ground.train.service.TrainService;

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
public class TestInsertFaultDetail {

	@Autowired
	private FaultDetailMapper faultDetailMapper;

	@Autowired
	TrainMapper trainMapper;

	@Autowired
	TrainService trainService;

	@Autowired
	FaultInfoMapper faultInfoMapper;

	@Autowired
	PushInfoMapper pushInfoMapper;

	@Autowired
	ProjectMapper projectMapper;

	@Test
	public void test() {
		List<PushInfo> pushInfos = pushInfoMapper.selectAll();
		List<Project> projects = projectMapper.selectAll();
		for (Project project : projects) {
			for (int i = 0; i < pushInfos.size(); i++) {
				PushInfo pushInfo = pushInfos.get(i);
				Integer code = pushInfo.getCode();
				FaultInfo faultInfo = new FaultInfo();
				faultInfo.setFaultCode(code);
				faultInfo.setFaultName("falut" + i);
				faultInfo.setProjectId(project.getId());
				faultInfoMapper.insertUseGeneratedKeys(faultInfo);
				Integer faultInfoId = faultInfo.getId();
				TrainParamHeader trainParms = new TrainParamHeader();
				trainParms.setProject(project.getName());
				List<Train> trains = trainService.findTrains(trainParms);
				for (Train train : trains) {
					FaultDetail faultDetail = new FaultDetail();
					faultDetail.setCarNo(1);
					faultDetail.setDebugMode(false);
					faultDetail.setDoorAddr(1);
					faultDetail.setFaultInfoId(faultInfoId);
					faultDetail.setFaultTime(new Date());
					faultDetail.setStatistics(true);
					faultDetail.setTrainId(train.getId());
					faultDetailMapper.insert(faultDetail);
				}
			}
		}
	}

	@Test
	public void resetDateTest() {
		List<FaultDetail> faultDetails = faultDetailMapper.selectAll();
		for (FaultDetail faultDetail : faultDetails) {
			faultDetail.setFaultTime(new Date());
			faultDetailMapper.updateByPrimaryKeySelective(faultDetail);
		}
	}

}
