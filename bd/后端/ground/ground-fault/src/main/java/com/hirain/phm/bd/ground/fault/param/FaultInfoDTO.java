/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.param;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.hirain.phm.bd.ground.dictionary.domain.Repair;
import com.hirain.phm.bd.ground.dictionary.domain.Solution;
import com.hirain.phm.bd.ground.fault.domain.FaultInfo;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 8, 2020 6:01:14 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 8, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class FaultInfoDTO {

	private Integer id;

	private Integer projectId;

	private String project;

	private Integer code;

	private String name;

	private String repair;

	private String solution;

	private List<Repair> repairList;

	private List<Solution> solutionList;

	private static final String lineSeparator = System.lineSeparator();

	public static FaultInfoDTO valueOf(FaultInfo info) {
		FaultInfoDTO vo = new FaultInfoDTO();
		BeanUtils.copyProperties(info, vo);
		vo.setCode(info.getFaultCode());
		vo.setName(info.getFaultName());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < info.getRepairList().size(); i++) {
			sb.append(i + 1).append(".");
			sb.append(info.getRepairList().get(i).getContent());
			sb.append(lineSeparator);
		}
		vo.setRepair(sb.toString());
		sb = new StringBuilder();
		for (int i = 0; i < info.getSolutionList().size(); i++) {
			sb.append(i + 1).append(".");
			sb.append(info.getSolutionList().get(i).getContent());
			sb.append(lineSeparator);
		}
		vo.setSolution(sb.toString());
		return vo;
	}

	public static FaultInfo parse(FaultInfoDTO vo) {
		FaultInfo info = new FaultInfo();
		BeanUtils.copyProperties(vo, info);
		info.setFaultCode(vo.getCode());
		info.setFaultName(vo.getName());
		return info;
	}
}
