package com.hirain.phm.bd.ground.statistics.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.maintenance.param.CommonStatisticsResult;
import com.hirain.phm.bd.ground.maintenance.param.FaultStatisticsRequestParm;
import com.hirain.phm.bd.ground.maintenance.service.WorkDetailService;
import com.hirain.phm.bd.ground.statistics.dao.FaultStatisticsMapper;
import com.hirain.phm.bd.ground.statistics.dao.SubhealthStatisticsMapper;
import com.hirain.phm.bd.ground.statistics.param.ProjectFaultNameStatisticsResponse;
import com.hirain.phm.bd.ground.statistics.service.BDStatisticsService;
import com.hirain.phm.bd.ground.statistics.service.CommonCountStatisticsMapper;
import com.hirain.phm.bd.ground.train.service.TrainService;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年2月13日 下午2:40:55
 * @Description
 *              <p>
 *              博得统计
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年2月13日 changwei.zheng@hirain.com 1.0 create file
 */
@Service
public class BDStatisticsServiceImpl implements BDStatisticsService {

	@Autowired
	FaultStatisticsMapper faultStatisticsMapper;

	@Autowired
	SubhealthStatisticsMapper subhealthStatisticsMapper;

	@Autowired
	WorkDetailService workDetailService;

	@Autowired
	TrainService trainService;

	/**
	 * 统计故障数量
	 */
	@Override
	public List<List<CommonStatisticsResult>> statisticsFaultCountByProjectName(String projectName) {
		List<List<CommonStatisticsResult>> list = statistics(projectName, faultStatisticsMapper);
		return list;
	}

	/**
	 * 统计亚健康数量
	 */
	@Override
	public List<List<CommonStatisticsResult>> statisticsSubhealthCountByProjectName(String projectName) {
		List<List<CommonStatisticsResult>> list = statistics(projectName, subhealthStatisticsMapper);
		return list;
	}

	private List<List<CommonStatisticsResult>> statistics(String projectName, CommonCountStatisticsMapper mapper) {
		List<List<CommonStatisticsResult>> list = new ArrayList<>();
		Calendar date = Calendar.getInstance();
		Integer year = date.get(Calendar.YEAR);
		for (int i = 0; i < 2; i++) {
			List<CommonStatisticsResult> results = null;
			results = mapper.statisticsCountByProjectName(projectName, String.valueOf(year - i));
			list.add(results);
		}
		return list;
	}

	@Override
	public List<List<CommonStatisticsResult>> countProjectFaultByParms(FaultStatisticsRequestParm parm) throws Exception {
		List<List<CommonStatisticsResult>> list = new ArrayList<>();
		// X为项目名称，Y为故障数
		List<CommonStatisticsResult> projectNameAndFaultCountList = workDetailService.countProjectFaultByParms(parm);
		list.add(projectNameAndFaultCountList);
		List<CommonStatisticsResult> projectNameAndFaultRateList = getProjectFaultRate(projectNameAndFaultCountList);
		list.add(projectNameAndFaultRateList);
		return list;
	}

	/**
	 * 获取项目故障率
	 * 
	 * @param projectNameAndFaultCountList
	 * @return
	 * @throws Exception
	 */
	private List<CommonStatisticsResult> getProjectFaultRate(List<CommonStatisticsResult> projectNameAndFaultCountList) throws Exception {
		// X为项目名称，Y为故障率
		List<CommonStatisticsResult> projectNameAndFaultRateList = new ArrayList<>();
		for (CommonStatisticsResult projectNameAndFaultCount : projectNameAndFaultCountList) {
			String projectName = projectNameAndFaultCount.getX();
			// 获取门个数
			Integer doorCount = trainService.getTrainDoorCount(projectName);
			CommonStatisticsResult projectNameAndFaultRate = new CommonStatisticsResult();
			projectNameAndFaultRate.setX(projectName);
			projectNameAndFaultRate.setY(String.valueOf(Float.valueOf(projectNameAndFaultCount.getY()) / doorCount));
			projectNameAndFaultRateList.add(projectNameAndFaultRate);
		}
		return projectNameAndFaultRateList;
	}

	@Override
	public List<CommonStatisticsResult> countPartsFaultByParms(FaultStatisticsRequestParm parm) {
		return workDetailService.countPartsFaultByParms(parm);
	}

	@Override
	public List<ProjectFaultNameStatisticsResponse> countFaultNameByParms(FaultStatisticsRequestParm parm) throws Exception {
		List<ProjectFaultNameStatisticsResponse> list = new ArrayList<>();
		// 获取前故障最多的5个项目，x为项目id,y为项目名称
		List<CommonStatisticsResult> topFiveProjectList = workDetailService.getTopFiveProjectFaultByParms(parm);
		for (CommonStatisticsResult commonStatisticsResult : topFiveProjectList) {
			parm.setProjectId(Integer.valueOf(commonStatisticsResult.getX()));
			String projectName = commonStatisticsResult.getY();
			// 获取门个数
			Integer doorCount = trainService.getTrainDoorCount(projectName);
			// 获取项目id对应的 故障名x和故障名对应的故障数y
			List<CommonStatisticsResult> faultNameStatisticsResultList = workDetailService.countFaultNameByParms(parm);
			ProjectFaultNameStatisticsResponse statisticsResponse = new ProjectFaultNameStatisticsResponse();
			statisticsResponse.setProjectName(projectName);
			statisticsResponse.setDoorCount(doorCount);
			List<CommonStatisticsResult> faultNameAndCountList = new ArrayList<>();
			for (CommonStatisticsResult faultNameStatisticsResult : faultNameStatisticsResultList) {
				faultNameAndCountList.add(new CommonStatisticsResult(faultNameStatisticsResult.getX(), faultNameStatisticsResult.getY()));
			}
			statisticsResponse.setCommonStatisticsResultList(faultNameStatisticsResultList);
			list.add(statisticsResponse);
		}
		return list;
	}

}
