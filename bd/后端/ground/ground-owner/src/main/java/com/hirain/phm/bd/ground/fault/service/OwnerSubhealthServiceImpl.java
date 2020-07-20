/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.service;

import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.fault.dao.OwnerSubhealthMapper;
import com.hirain.phm.bd.ground.fault.param.FaultCount;
import com.hirain.phm.bd.ground.fault.param.FaultRequest;
import com.hirain.phm.bd.ground.fault.param.SubhealthExportObject;
import com.hirain.phm.bd.ground.fault.param.SubhealthResponse;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 28, 2020 5:38:56 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class OwnerSubhealthServiceImpl implements OwnerSubhealthService {

	@Autowired
	private OwnerSubhealthMapper mapper;

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.OwnerSubhealthService#selectTop20(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SubhealthResponse> selectTop20(String project, String train) {
		return mapper.selectTop20(project, train);
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.OwnerSubhealthService#exportSubhealth(com.hirain.phm.bd.ground.fault.param.FaultRequest,
	 *      javax.servlet.ServletOutputStream)
	 */
	@Override
	public void exportSubhealth(FaultRequest request, OutputStream outputStream) {
		List<SubhealthResponse> list = mapper.selectByParam(request);
		List<SubhealthExportObject> subhealths = list.stream().map(s -> {
			SubhealthExportObject object = new SubhealthExportObject();
			BeanUtils.copyProperties(s, object);
			object.setSystem("车门系统");
			object.setSource("北京博得");
			return object;
		}).collect(Collectors.toList());

		ExcelUtils.write(outputStream, "亚健康", subhealths, SubhealthExportObject.class);
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.OwnerSubhealthService#countLastMonth(java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public List<FaultCount> countLastMonth(String project, String train, List<String> subhealths) {
		LocalDate now = LocalDate.now();
		LocalDate lastMonth = now.minusMonths(1);
		return mapper.countByMonth(project, train, lastMonth.getMonthValue(), subhealths);
	}

}
