/*******************************************************************************
 * Copyright (c) 2015, 2015 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.rows;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 上午11:07:37
 * @Description
 *              <p>
 *              读excel接口
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
public abstract class AbstractExcelReader {

	/**
	 * 配置的扩展点，子类实现的ID
	 */
	private String id;

	/**
	 * 接口方法内调用ExcelReadUtil.loadExcelSheet(excelPath,sheetName,header,sheetModel,rowLoader)
	 * 
	 * @param excelPath
	 *            excel文件路径
	 * @return excel的数据模型对象excelModel(N个sheetModel组成)
	 * @throws Exception
	 */
	public abstract Object getExcelModel(String excelPath) throws Exception;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

}
