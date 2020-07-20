/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.push.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.event.FaultTopType;
import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.dictionary.dao.RepairMapper;
import com.hirain.phm.bd.ground.dictionary.dao.SolutionMapper;
import com.hirain.phm.bd.ground.dictionary.domain.Repair;
import com.hirain.phm.bd.ground.dictionary.domain.Solution;
import com.hirain.phm.bd.ground.push.dao.FaultDescMapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 1, 2020 4:30:12 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 1, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class SolutionContentService {

	@Autowired
	private FaultDescMapper descMapper;

	@Autowired
	private RepairMapper repairMapper;

	@Autowired
	private SolutionMapper solutionMapper;

	private static final String lineSeparator = System.lineSeparator();

	public Map<String, Object> solutionContent(UnifiedFaultRecord record) {
		Map<String, Object> content = new HashMap<>();
		FaultDesc faultDesc = descMapper.findByTypeAndCode(record.getType().getCode(), record.getCode());
		if (faultDesc != null) {
			content.put("level", faultDesc.getLevel() != null ? faultDesc.getLevel() : "");
			content.put("description", faultDesc.getDescription() != null ? faultDesc.getDescription() : "");
		} else {
			content.put("level", "");
			content.put("description", "");
		}
		List<Repair> repairs = findRepair(record.getType().getCode(), record.getCode());
		String treatment = repairs.stream().map(r -> r.getContent()).collect(Collectors.joining(lineSeparator));
		content.put("treatment", treatment);
		List<Solution> solutions = findSolution(record.getType().getCode(), record.getCode());
		String repair = solutions.stream().map(r -> r.getContent()).collect(Collectors.joining(lineSeparator));
		content.put("repair", repair);
		return content;
	}

	/**
	 * @param code
	 * @param code2
	 */
	private List<Solution> findSolution(int type, int code) {
		if (type == FaultTopType.Fault.getCode()) {
			return solutionMapper.selectByFaultInfoId(code);
		} else if (type == FaultTopType.SubHealth.getCode()) {
			return solutionMapper.selectBySubhealthInfoId(code);
		}
		return new ArrayList<>();
	}

	private List<Repair> findRepair(int type, int code) {
		if (type == FaultTopType.Fault.getCode()) {
			return repairMapper.selectByFaultInfoId(code);
		} else if (type == FaultTopType.SubHealth.getCode()) {
			return repairMapper.selectBySubhealthInfoId(code);
		}
		return new ArrayList<>();
	}

}
