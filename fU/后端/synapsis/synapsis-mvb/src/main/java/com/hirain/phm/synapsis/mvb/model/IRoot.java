/*******************************************************************************
 * Copyright (c) 2017, 2017 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.model;

import java.util.List;

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
public interface IRoot extends IStandardModel, IPropertyCache, IPropertyChangeSupport {

	/**
	 * @return 总线类型
	 */
	String getBusType();

	/**
	 * @param busType
	 *            总线类型
	 */
	void setBusType(final String busType);

	/**
	 * @return 此模型的ID（区分其他模型）
	 */
	String getUuid();

	/**
	 * @param uuid
	 *            此模型的ID（区分其他模型）
	 */
	void setUuid(final String uuid);

	String getName();

	void setName(final String name);

	/**
	 * @return JAXB获得模型的源xml路径
	 */
	String getXmlPath();

	/**
	 * @param xmlPath
	 *            JAXB获得模型的源xml路径
	 */
	void setXmlPath(final String xmlPath);

	String getExcelVersion();

	void setExcelVersion(String excelVersion);

	String getAppVersion();

	void setAppVersion(String appVersion);

	List<IDataType> getDataTypes();

	// List<ITrain> getTrains();
	//
	// List<ISystem> getSystems();

	List<IDevice> getDevices();

	List<IPort> getPorts();

	List<IDatagram> getDatagrams();

	List<IDeviceStructure> getDeviceStructures();

	// ------------------------------------

	/**
	 * @return 缓存的结构相关信息
	 */
	ICacheModel getCacheModel();

	/**
	 * @param cacheModel
	 *            缓存的结构相关信息
	 */
	void setCacheModel(final ICacheModel cacheModel);

	/**
	 * @return 模型错误信息（为null表示无错误信息）
	 */
	String getErrorInfo();

	/**
	 * @param errorMessage
	 *            已经有错误信息时，是换行追加错误信息；
	 */
	void setErrorInfo(String errorMessage);

}
