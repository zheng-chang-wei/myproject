/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth.param;

import java.util.List;

import com.hirain.phm.bd.ground.dictionary.domain.Repair;
import com.hirain.phm.bd.ground.dictionary.domain.Solution;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthInfo;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 9, 2020 2:49:49 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class SubhealthInfoDTO {

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

	public static SubhealthInfoDTO valueOf(SubhealthInfo info) {
		SubhealthInfoDTO vo = new SubhealthInfoDTO();
		vo.setId(info.getId());
		vo.setProjectId(info.getProjectId());
		vo.setCode(info.getSubhealthCode());
		vo.setName(info.getSubhealthName());
		vo.setRepairList(info.getRepairList());
		vo.setSolutionList(info.getSolutionList());
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

	public static SubhealthInfo parse(SubhealthInfoDTO vo) {
		SubhealthInfo info = new SubhealthInfo();
		info.setId(vo.getId());
		info.setProjectId(vo.getProjectId());
		info.setSubhealthCode(vo.getCode());
		info.setSubhealthName(vo.getName());
		info.setRepairList(vo.getRepairList());
		info.setSolutionList(vo.getSolutionList());
		return info;
	}
}
