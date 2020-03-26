package com.hirain.phm.bd.ground.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.GroundWebApplication;
import com.hirain.phm.bd.ground.maintenance.param.CommonStatisticsResult;
import com.hirain.phm.bd.ground.maintenance.param.FaultStatisticsRequestParm;
import com.hirain.phm.bd.ground.maintenance.service.WorkDetailService;
import com.hirain.phm.bd.ground.statistics.param.ProjectFaultNameStatisticsResponse;
import com.hirain.phm.bd.ground.statistics.service.BDStatisticsService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GroundWebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BDStatisticsServiceTest {

	@Autowired
	BDStatisticsService bdStatisticsService;

	@Autowired
	WorkDetailService workDetailService;

	@Test
	public void countFaultByParmsTest() throws Exception {
		FaultStatisticsRequestParm parm = new FaultStatisticsRequestParm();
		parm.setDoorTypeId(1);
		parm.setEffectId(1);
		parm.setModeId(1);
		parm.setStageId(1);
		List<List<CommonStatisticsResult>> list = bdStatisticsService.countProjectFaultByParms(parm);
		System.out.println(list);
	}

	@Test
	public void countPartsFaultByParms() throws Exception {
		FaultStatisticsRequestParm parm = new FaultStatisticsRequestParm();
		parm.setProjectId(2);
		parm.setEffectId(1);
		parm.setModeId(1);
		parm.setStageId(1);
		List<CommonStatisticsResult> list = bdStatisticsService.countPartsFaultByParms(parm);
		System.out.println(list);
	}

	@Test
	public void getTopFiveProjectFaultByParms() throws Exception {
		FaultStatisticsRequestParm parm = new FaultStatisticsRequestParm();
		parm.setEffectId(1);
		parm.setModeId(1);
		parm.setStageId(1);
		List<CommonStatisticsResult> list = workDetailService.getTopFiveProjectFaultByParms(parm);
		System.out.println(list);
	}

	@Test
	public void countFaultNameByParms() throws Exception {
		FaultStatisticsRequestParm parm = new FaultStatisticsRequestParm();
		parm.setEffectId(1);
		parm.setModeId(1);
		parm.setStageId(1);
		List<ProjectFaultNameStatisticsResponse> list = bdStatisticsService.countFaultNameByParms(parm);
		System.out.println(list);
	}
}
