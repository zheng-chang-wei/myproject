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

import com.hirain.phm.bd.ground.fault.dao.OwnerFaultMapper;
import com.hirain.phm.bd.ground.fault.param.FaultCount;
import com.hirain.phm.bd.ground.fault.param.FaultExportObject;
import com.hirain.phm.bd.ground.fault.param.FaultRequest;
import com.hirain.phm.bd.ground.fault.param.FaultResponse;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 24, 2020 5:10:28 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 24, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class OwnerFaultServiceImpl implements OwnerFaultService {

	@Autowired
	private OwnerFaultMapper mapper;

	@Override
	public List<FaultResponse> selectTop20(String project, String train) {
		return mapper.selectTop20(project, train);
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.OwnerFaultService#exportFault(com.hirain.phm.bd.ground.fault.param.FaultRequest)
	 */
	@Override
	public void exportFault(FaultRequest request, OutputStream stream) {
		List<FaultResponse> list = selectByParam(request);
		List<FaultExportObject> faults = list.stream().map(f -> {
			FaultExportObject target = new FaultExportObject();
			BeanUtils.copyProperties(f, target);
			target.setSystem("车门系统");
			target.setSource("北京博得");
			return target;
		}).collect(Collectors.toList());

		ExcelUtils.write(stream, "故障", faults, FaultExportObject.class);
	}

	@Override
	public List<FaultResponse> selectByParam(FaultRequest request) {
		return mapper.selectByParam(request);
	}

	public List<FaultCount> countLastMonth(String project, String train, List<String> faults) {
		LocalDate now = LocalDate.now();
		LocalDate lastMonth = now.minusMonths(1);
		return mapper.countByMonth(project, train, lastMonth.getMonthValue(), faults);
	}
}
