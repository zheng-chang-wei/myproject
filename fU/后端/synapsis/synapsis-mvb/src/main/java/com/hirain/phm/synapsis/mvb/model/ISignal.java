/*******************************************************************************
 * Copyright (c) 2017, 2017 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.model;

import com.hirain.phm.synapsis.setting.BusDataType;

/**
 * @Version 1.0
 * @Author gangjie.yang@hirain.com
 * @Created May 9, 2017 2:49:15 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 9, 2017 gangjie.yang@hirain.com 1.0 create file
 */
public interface ISignal extends IStandardModel {

	/**
	 * @return 这个ID是UUID，对象一经创建不可修改
	 */
	String getId();

	/**
	 * @param id
	 *            这个ID是UUID，对象一经创建不可修改
	 */
	void setId(final String id);

	/**
	 * @return 模型树上的路径
	 */
	String getPath();

	/**
	 * @param path
	 *            模型树上的路径
	 */
	void setPath(final String path);

	String getVariableId();

	void setVariableId(final String variableId);

	String getSignalName();

	void setSignalName(final String signalName);

	BusDataType getDataType();

	/**
	 * @param dataType
	 *            除BITS类型外，其他类型均自动调用了setLength(final int length);
	 */
	void setDataType(final BusDataType dataType);

	/**
	 * @return bit
	 */
	int getLength();

	/**
	 * @param length
	 *            bit（只有类型是BITS时候才需要调用此方法，其他类型的设置类型时已经调了）
	 */
	void setLength(final int length);

	int getByteOffset();

	void setByteOffset(final int byteOffset);

	int getBitOffset();

	void setBitOffset(final int bitOffset);

	/**
	 * @return 是否锁定(锁定后禁止修改偏移量)
	 */
	boolean isLocked();

	/**
	 * @param locked
	 *            是否锁定(锁定后禁止修改偏移量)
	 */
	void setLocked(final boolean locked);

	String getRange();

	void setRange(final String range);

	/**
	 * @return 公式是否有效
	 */
	boolean isFomula();

	/**
	 * @param fomula
	 *            公式是否有效
	 */
	void setFomula(boolean fomula);

	/**
	 * @return 乘积因子
	 */
	double getFactor();

	/**
	 * @param factor
	 *            乘积因子
	 */
	void setFactor(final double factor);

	/**
	 * @return 乘积偏移
	 */
	double getOffset();

	/**
	 * @param offset
	 *            乘积偏移
	 */
	void setOffset(final double offset);

	String getUnit();

	void setUnit(final String unit);

	/**
	 * @return 标记
	 */
	String getKeyWord();

	/**
	 * @param keyWord
	 *            标记
	 */
	void setKeyWord(final String keyWord);

	/**
	 * @return 通用
	 */
	String getUniversal();

	/**
	 * @param universal
	 *            通用
	 */
	void setUniversal(final String universal);

	// ----------------------------------------------------------

	/**
	 * @return 信号值
	 */
	Object getValue();

	/**
	 * @param value
	 *            信号值
	 */
	void setValue(Object value);

	/**
	 * @return 信号值str
	 */
	String getValueString();
}
