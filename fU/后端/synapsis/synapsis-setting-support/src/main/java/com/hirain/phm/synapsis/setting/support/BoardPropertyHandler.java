/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.property.BoardProperty;
import com.hirain.phm.synapsis.setting.support.param.BoardSettingVO;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 22, 2020 11:54:12 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 22, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface BoardPropertyHandler {

	/**
	 * @param source
	 * @param dest
	 * @param property
	 * @param boardType
	 * @return
	 * @throws Exception
	 */
	List<String> handleResource(File source, File dest, BoardProperty property, String boardType) throws Exception;

	/**
	 * @param boardFolder
	 * @param destFolder
	 * @param property
	 * @throws IOException
	 */
	void copyFiles(File boardFolder, File destFolder, BoardProperty property) throws IOException;

	/**
	 * @param property
	 */
	String getProtocolFile(BoardProperty property);

	/**
	 * @param vo
	 */
	BoardProperty generateProperty(BoardSettingVO vo);

	/**
	 * @param boardSetting
	 * @param vo
	 */
	void fillVOProperty(BoardSetting boardSetting, BoardSettingVO vo);

	/**
	 * @param boardSetting
	 */
	List<String> validate(BoardSetting boardSetting);

}
