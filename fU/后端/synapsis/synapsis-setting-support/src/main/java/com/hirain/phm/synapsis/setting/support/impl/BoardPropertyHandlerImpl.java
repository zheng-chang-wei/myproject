/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.protocol.impl.ProtocolStreamServiceImpl;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.property.BoardProperty;
import com.hirain.phm.synapsis.setting.property.BusBoardProperty;
import com.hirain.phm.synapsis.setting.property.CPUBoardProperty;
import com.hirain.phm.synapsis.setting.property.ECNBoardProperty;
import com.hirain.phm.synapsis.setting.property.MVBBoardProperty;
import com.hirain.phm.synapsis.setting.property.PHMBoardProperty;
import com.hirain.phm.synapsis.setting.support.BoardPropertyHandler;
import com.hirain.phm.synapsis.setting.support.param.BoardSettingVO;
import com.hirain.phm.synapsis.setting.support.utils.StringUtils;
import com.hirain.phm.synapsis.setting.support.utils.SupportUtils;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 22, 2020 11:57:22 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 22, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class BoardPropertyHandlerImpl implements BoardPropertyHandler {

	@Autowired
	private ProtocolStreamServiceImpl protocolParseService;

	/**
	 * @throws IOException
	 * @see com.hirain.phm.synapsis.setting.support.BoardPropertyHandler#handleResource(java.io.File, java.io.File,
	 *      com.hirain.phm.synapsis.setting.property.BoardProperty, String)
	 */
	@Override
	public List<String> handleResource(File source, File dest, BoardProperty property, String boardType) throws Exception {
		if (property instanceof BusBoardProperty) {
			String filename = ((BusBoardProperty) property).getFilename();
			File file = new File(dest, filename);
			if (!file.exists()) {
				File sourceFile = new File(source, filename);
				FileUtils.moveFileToDirectory(sourceFile, dest, true);
			}
			BoardType type = BoardType.valueOf(boardType);
			String xmlName = SupportUtils.getFilePrefix(filename) + ".xml";
			try {
				String xmlFilepath = dest.getAbsolutePath() + File.separator + xmlName;
				ParseResult result = protocolParseService.read(type.getType(), file.getAbsolutePath());
				if (result != null) {
					protocolParseService.write(type.getType(), xmlFilepath, result.getData());
				}
			} catch (SynapsisException e) {
				throw e;
			}
			return Arrays.asList(filename, xmlName);
		}
		return null;
	}

	/**
	 * @throws IOException
	 * @see com.hirain.phm.synapsis.setting.support.BoardPropertyHandler#copyFiles(java.io.File, java.io.File,
	 *      com.hirain.phm.synapsis.setting.property.BoardProperty)
	 */
	@Override
	public void copyFiles(File boardFolder, File destFolder, BoardProperty property) throws IOException {
		if (property instanceof BusBoardProperty) {
			File file = new File(boardFolder, ((BusBoardProperty) property).getFilename());
			FileUtils.copyFileToDirectory(file, destFolder, true);
			String xmlName = SupportUtils.getFilePrefix(((BusBoardProperty) property).getFilename()) + ".xml";
			File xmlFile = new File(boardFolder, xmlName);
			FileUtils.copyFileToDirectory(xmlFile, destFolder, true);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.BoardPropertyHandler#getProtocolFile(com.hirain.phm.synapsis.setting.property.BoardProperty)
	 */
	@Override
	public String getProtocolFile(BoardProperty property) {
		if (property instanceof BusBoardProperty) {
			return ((BusBoardProperty) property).getFilename();
		}
		return null;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.BoardPropertyHandler#generateProperty(com.hirain.phm.synapsis.setting.support.param.BoardSettingVO)
	 */
	@Override
	public BoardProperty generateProperty(BoardSettingVO vo) {
		BoardType boardType = BoardType.valueOf(vo.getType());
		switch (boardType) {
		case PHM_AGX:
		case PHM_TX2:
		case PHM_IMX8:
			return generatePHMProperty(vo);
		case MVB:
			return generateMVBProperty(vo);
		case ECN:
			return generateECNProperty(vo);
		case CPU:
			return generateCpuProperty(vo);
		default:
			break;
		}
		return null;
	}

	/**
	 * @param vo
	 * @return
	 */
	private BoardProperty generateCpuProperty(BoardSettingVO vo) {
		CPUBoardProperty property = new CPUBoardProperty();
		if (vo.getIps().length > 0) {
			property.setIp(vo.getIps()[0]);
		}
		return property;
	}

	private MVBBoardProperty generateMVBProperty(BoardSettingVO vo) {
		MVBBoardProperty property = new MVBBoardProperty();
		property.setFilename(vo.getFilename());
		property.setOriginalName(vo.getOriginalName());
		property.setTrustLine(vo.getTrustLine());
		return property;
	}

	private ECNBoardProperty generateECNProperty(BoardSettingVO vo) {
		ECNBoardProperty property = new ECNBoardProperty();
		property.setFilename(vo.getFilename());
		property.setOriginalName(vo.getOriginalName());
		if (vo.getIps().length > 0) {
			property.setIp1(vo.getIps()[0]);
		}
		if (vo.getIps().length > 1) {
			property.setIp2(vo.getIps()[1]);
		}
		return property;
	}

	/**
	 * @param vo
	 * @return
	 */
	private BoardProperty generatePHMProperty(BoardSettingVO vo) {
		PHMBoardProperty property = new PHMBoardProperty();
		if (vo.getIps().length > 0) {
			property.setIp1(vo.getIps()[0]);
		}
		if (vo.getIps().length > 1) {
			property.setIp2(vo.getIps()[1]);
		}
		if (vo.getIps().length > 2) {
			property.setTargetIp1(vo.getIps()[2]);
		}
		if (vo.getIps().length > 3) {
			property.setTargetIp2(vo.getIps()[3]);
		}
		return property;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.BoardPropertyHandler#fillVOProperty(com.hirain.phm.synapsis.setting.BoardSetting,
	 *      com.hirain.phm.synapsis.setting.support.param.BoardSettingVO)
	 */
	@Override
	public void fillVOProperty(BoardSetting boardSetting, BoardSettingVO vo) {
		BoardProperty property = boardSetting.getProperty();
		if (property instanceof CPUBoardProperty) {
			String[] ips = new String[1];
			ips[0] = ((CPUBoardProperty) property).getIp();
			vo.setIps(ips);
		} else if (property instanceof ECNBoardProperty) {
			ECNBoardProperty ecnBoardProperty = (ECNBoardProperty) property;
			String[] ips = new String[2];
			ips[0] = ecnBoardProperty.getIp1();
			ips[1] = ecnBoardProperty.getIp2();
			vo.setIps(ips);
			vo.setFilename(ecnBoardProperty.getFilename());
			vo.setOriginalName(ecnBoardProperty.getOriginalName());
		} else if (property instanceof MVBBoardProperty) {
			MVBBoardProperty mvbBoardProperty = (MVBBoardProperty) property;
			vo.setFilename(mvbBoardProperty.getFilename());
			vo.setOriginalName(mvbBoardProperty.getOriginalName());
			vo.setTrustLine(mvbBoardProperty.getTrustLine());
			vo.setMode(mvbBoardProperty.getMode());
		} else if (property instanceof PHMBoardProperty) {
			String[] ips = new String[4];
			ips[0] = ((PHMBoardProperty) property).getIp1();
			ips[1] = ((PHMBoardProperty) property).getIp2();
			ips[2] = ((PHMBoardProperty) property).getTargetIp1();
			ips[3] = ((PHMBoardProperty) property).getTargetIp2();
			vo.setIps(ips);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.BoardPropertyHandler#validate(com.hirain.phm.synapsis.setting.BoardSetting)
	 */
	@Override
	public List<String> validate(BoardSetting boardSetting) {
		List<String> errors = new ArrayList<>();
		if (!boardSetting.getEnable()) {
			return errors;
		}
		BoardProperty property = boardSetting.getProperty();
		if (property instanceof CPUBoardProperty) {
			validateCpuBoardProperty(boardSetting, errors);
		} else if (property instanceof ECNBoardProperty) {
			validateECNBoardProperty(boardSetting, errors);
		} else if (property instanceof MVBBoardProperty) {
			validateMVBBoardProperty(boardSetting, errors);
		} else {
			BoardType type = BoardType.valueOf(boardSetting.getType());
			if (type.equals(BoardType.PHM_AGX) || type.equals(BoardType.PHM_TX2)) {
				validateAgxAndTx2(boardSetting, errors);
			} else {
				validateIMAX8(boardSetting, errors);
			}
		}
		return errors;
	}

	/**
	 * @param boardSetting
	 * @param errors
	 */
	private void validateCpuBoardProperty(BoardSetting boardSetting, List<String> errors) {
		CPUBoardProperty cpuProperty = (CPUBoardProperty) boardSetting.getProperty();
		// 校验cpu_ip是否为空，是否匹配正则
		String cpu_ip = cpuProperty.getIp();
		if (StringUtils.isEmpty(cpu_ip)) {
			errors.add("CPU板卡IP为空");
		} else if (!StringUtils.isIPAddr(cpu_ip)) {
			errors.add("CPU板卡IP格式错误");
		}
	}

	/**
	 * @param boardSetting
	 * @param errors
	 */
	private void validateECNBoardProperty(BoardSetting boardSetting, List<String> errors) {
		ECNBoardProperty ecnProperty = (ECNBoardProperty) boardSetting.getProperty();
		String type = boardSetting.getType();
		// 校验 filename是否为空
		String filename = ecnProperty.getFilename();
		if (StringUtils.isEmpty(filename)) {
			errors.add(type + " 板卡数据流文件为空");
		}
		// 校验originalName是否为空
		String originalName = ecnProperty.getOriginalName();
		if (StringUtils.isEmpty(originalName)) {
			errors.add(type + " 板卡OriginalName为空");
		}
		// 校验ip1是否为空，是否匹配正则
		String ip1 = ecnProperty.getIp1();
		if (StringUtils.isEmpty(ip1)) {
			errors.add(type + " 板卡IP1为空");
		} else if (!StringUtils.isIPAddr(ip1)) {
			errors.add(type + " 板卡IP1格式错误");
		}
		// 校验ip2是否为空，是否匹配正则
		String ip2 = ecnProperty.getIp2();
		if (StringUtils.isEmpty(ip2)) {
			errors.add("总线板卡IP2为空");
		} else if (!StringUtils.isIPAddr(ip2)) {
			errors.add("总线板卡IP2格式错误");
		}
		if (StringUtils.isEquals(ip1, ip2)) {
			errors.add(type + " 板卡前面板IP冲突");
		}
	}

	/**
	 * @param boardSetting
	 * @param errors
	 */
	private void validateMVBBoardProperty(BoardSetting boardSetting, List<String> errors) {
		MVBBoardProperty mvbProperty = (MVBBoardProperty) boardSetting.getProperty();
		String type = boardSetting.getType();
		// 校验 filename是否为空
		String filename = mvbProperty.getFilename();
		if (StringUtils.isEmpty(filename)) {
			errors.add(type + " 板卡数据流文件为空");
		}
		// 校验originalName是否为空
		String originalName = mvbProperty.getOriginalName();
		if (StringUtils.isEmpty(originalName)) {
			errors.add(type + " 板卡OriginalName为空");
		}
		// 校验信任线是否为空
		Integer trustLine = mvbProperty.getTrustLine();
		if (trustLine == null) {
			errors.add(type + " 板卡信任线为空");
		}
		// 校验板卡模式是否为空
		String mode = mvbProperty.getMode();
		if (StringUtils.isEmpty(mode)) {
			errors.add(type + " 板卡模式为空");
		}
	}

	/**
	 * 校验AGX 和 TX2板卡的基本属性
	 */
	private void validateAgxAndTx2(BoardSetting boardSetting, List<String> errors) {
		PHMBoardProperty agxtx2_property = (PHMBoardProperty) boardSetting.getProperty();
		String type = boardSetting.getType();
		// 校验agxtx2_ip1是否为空，是否匹配正则
		String agxtx2_ip1 = agxtx2_property.getIp1();
		if (StringUtils.isEmpty(agxtx2_ip1)) {
			errors.add(type + " 前面板ETH1 ip为空");
		} else if (!StringUtils.isIPAddr(agxtx2_ip1)) {
			errors.add(type + " 前面板ETH1 ip格式错误");
		}
		// 校验agxtx2_ip2是否为空，是否匹配正则
		String agxtx2_ip2 = agxtx2_property.getIp2();
		if (StringUtils.isEmpty(agxtx2_ip2)) {
			errors.add(type + " 前面板ETH2 ip为空");
		} else if (!StringUtils.isIPAddr(agxtx2_ip2)) {
			errors.add(type + " 前面板ETH2 ip格式错误");
		}
		if (StringUtils.isEquals(agxtx2_ip1, agxtx2_ip2)) {
			errors.add(type + " 前面板ETH ip冲突");
		}
		if (inSameNetSegment(agxtx2_ip1, agxtx2_ip2)) {
			errors.add(type + " 前面板ETH ip不能在同一网段");
		}
		// 校验agxtx2_targetIp1是否为空，是否匹配正则
		// String agxtx2_targetIp1 = agxtx2_property.getTargetIp1();
		// if (StringUtils.isEmpty(agxtx2_targetIp1)) {
		// errors.add(type + " 板卡targetIP1为空");
		// } else if (!StringUtils.isIPAddr(agxtx2_targetIp1)) {
		// errors.add(type + " 板卡targetIP1格式错误");
		// }
		// 校验agxtx2_targetIp2是否为空，是否匹配正则
		// String agxtx2_targetIp2 = agxtx2_property.getTargetIp2();
		// if (StringUtils.isEmpty(agxtx2_targetIp2)) {
		// errors.add(type + " 板卡targetIP1为空");
		// } else if (!StringUtils.isIPAddr(agxtx2_targetIp2)) {
		// errors.add(type + " 板卡targetIP1格式错误");
		// }
		// if (StringUtils.isEquals(agxtx2_targetIp1, agxtx2_targetIp2)) {
		// errors.add(type + " 板卡目标IP冲突");
		// }
	}

	/**
	 * 校验IMAX8板卡的基本属性
	 */
	private void validateIMAX8(BoardSetting boardSetting, List<String> errors) {
		PHMBoardProperty imax8_property = (PHMBoardProperty) boardSetting.getProperty();
		// 校验imax8_ip1是否为空，是否匹配正则
		String imax8_ip1 = imax8_property.getIp1();
		if (StringUtils.isEmpty(imax8_ip1)) {
			errors.add("IMX8板卡IP1为空");
		} else if (!StringUtils.isIPAddr(imax8_ip1)) {
			errors.add("IMX8板卡IP1格式错误");
		}
		// 校验imax8_ip2是否为空，是否匹配正则
		String imax8_ip2 = imax8_property.getIp2();
		if (StringUtils.isEmpty(imax8_ip2)) {
			errors.add("IMX8板卡IP2为空");
		} else if (!StringUtils.isIPAddr(imax8_ip2)) {
			errors.add("IMX8板卡IP2格式错误");
		}
		if (StringUtils.isEquals(imax8_ip1, imax8_ip2)) {
			errors.add("IMX8板卡前面板IP冲突");
		}

	}

	/**
	 * 判断两个IP地址是否在同一网段
	 */
	private boolean inSameNetSegment(String ip1, String ip2) {
		String[] ip1s = ip1.split("\\.");
		String[] ip2s = ip2.split("\\.");
		if (ip1s.length > 3 && ip2s.length > 3) {
			if (StringUtils.isEquals(ip1s[0], ip2s[0]) && StringUtils.isEquals(ip1s[1], ip2s[1]) && StringUtils.isEquals(ip1s[2], ip2s[2])) {
				return true;
			}
		}
		return false;
	}
}
