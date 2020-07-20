/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.impl;

import com.hirain.phm.bd.common.pinyin.PinyinUtil;
import com.hirain.phm.bd.data.TableNameFormatter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 23, 2020 11:02:06 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 23, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class TableNameFormatterImpl implements TableNameFormatter {

	/**
	 * @see com.hirain.phm.bd.data.TableNameFormatter#getTableName(String, String)
	 */
	@Override
	public String getTableName(String project, String train) {
		return PinyinUtil.getPingYin(project) + "_" + train;
	}

}
