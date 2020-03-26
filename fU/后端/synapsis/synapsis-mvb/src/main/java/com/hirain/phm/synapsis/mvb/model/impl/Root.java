package com.hirain.phm.synapsis.mvb.model.impl;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.hirain.phm.synapsis.mvb.model.ICacheModel;
import com.hirain.phm.synapsis.mvb.model.IDataType;
import com.hirain.phm.synapsis.mvb.model.IDatagram;
import com.hirain.phm.synapsis.mvb.model.IDevice;
import com.hirain.phm.synapsis.mvb.model.IDeviceStructure;
import com.hirain.phm.synapsis.mvb.model.IPort;
import com.hirain.phm.synapsis.mvb.model.IRoot;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 下午2:41:40
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
@XmlRootElement(name = "Root")
@XmlType(propOrder = { "busType", "name", "uuid", "excelVersion", "appVersion", "description", "dataTypes", "devices", "ports", "datagrams",
		"deviceStructures" })
@XmlAccessorType(XmlAccessType.FIELD)
public final class Root implements IRoot {

	private static final long serialVersionUID = -1719094361417028407L;

	@XmlAttribute(name = "excelVersion", required = true)
	private String excelVersion;

	@XmlAttribute(name = "appVersion", required = true)
	private String appVersion;

	@XmlAttribute(name = "uuid", required = true)
	private String uuid;

	@XmlAttribute(name = "description", required = true)
	private String description;

	@XmlElementWrapper(name = "dataTypes")
	@XmlElement(name = "dataType", required = true, type = DataType.class)
	private final List<IDataType> dataTypes = new ArrayList<IDataType>();

	@XmlElementWrapper(name = "devices")
	@XmlElement(name = "device", required = true, type = Device.class)
	private final List<IDevice> devices = new ArrayList<IDevice>();

	@XmlElementWrapper(name = "ports")
	@XmlElement(name = "port", required = true, type = Port.class)
	private final List<IPort> ports = new ArrayList<IPort>();

	@XmlElementWrapper(name = "datagrams")
	@XmlElement(name = "datagram", required = true, type = Datagram.class)
	private final List<IDatagram> datagrams = new ArrayList<IDatagram>();

	@XmlElementWrapper(name = "DeviceStructures")
	@XmlElement(name = "DeviceStructure", required = true, type = DeviceStructure.class)
	private final List<IDeviceStructure> deviceStructures = new ArrayList<IDeviceStructure>();

	@XmlAttribute(name = "name", required = true)
	private String name;

	@XmlAttribute(name = "busType", required = true)
	private String busType;

	@XmlTransient
	private final Map<String, Object> propertyCache = new HashMap<String, Object>();

	@XmlTransient
	private String xmlPath;

	@XmlTransient
	private ICacheModel cacheModel;

	@XmlTransient
	private String errorInfo;

	@XmlTransient
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	/**
	 * @return the description
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the excelVersion
	 */
	@Override
	public String getExcelVersion() {
		return excelVersion;
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#setExcelVersion(java.lang.String)
	 */
	@Override
	public void setExcelVersion(final String excelVersion) {
		this.excelVersion = excelVersion;
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#setAppVersion(java.lang.String)
	 */
	@Override
	public void setAppVersion(final String appVersion) {
		this.appVersion = appVersion;
	}

	/**
	 * @return the appVersion
	 */
	@Override
	public String getAppVersion() {
		return appVersion;
	}

	/**
	 * @return the dataTypes
	 */
	@Override
	public List<IDataType> getDataTypes() {
		return dataTypes;
	}

	// /**
	// * @return the trains
	// */
	// @Override
	// public List<ITrain> getTrains() {
	// return trains;
	// }
	//
	// /**
	// * @return the systems
	// */
	// @Override
	// public List<ISystem> getSystems() {
	// return systems;
	// }

	/**
	 * @return the devices
	 */
	@Override
	public List<IDevice> getDevices() {
		return devices;
	}

	/**
	 * @return the ports
	 */
	@Override
	public List<IPort> getPorts() {
		return ports;
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#getUuid()
	 */
	@Override
	public String getUuid() {
		return uuid;
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#setUuid(java.lang.String)
	 */
	@Override
	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#getCacheModel()
	 */
	@Override
	public ICacheModel getCacheModel() {
		return cacheModel;
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#setCacheModel(com.hirain.mct.model.standard.ICacheModel)
	 */
	@Override
	public void setCacheModel(final ICacheModel cacheModel) {
		this.cacheModel = cacheModel;
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#setName(java.lang.String)
	 */
	@Override
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#getBusType()
	 */
	@Override
	public String getBusType() {
		return busType;
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#setBusType(java.lang.String)
	 */
	@Override
	public void setBusType(final String busType) {
		this.busType = busType;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPropertyCache#setProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setProperty(final String key, final Object value) {
		propertyCache.put(key, value);
	}

	/**
	 * @see com.hirain.mct.model.standard.IPropertyCache#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty(final String key) {
		return propertyCache.get(key);
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#getXmlPath()
	 */
	@Override
	public String getXmlPath() {
		return xmlPath;
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#setXmlPath(java.lang.String)
	 */
	@Override
	public void setXmlPath(final String xmlPath) {
		this.xmlPath = xmlPath;
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#getDatagrams()
	 */
	@Override
	public List<IDatagram> getDatagrams() {
		return datagrams;
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#getErrorInfo()
	 */
	@Override
	public String getErrorInfo() {
		return errorInfo;
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#setErrorInfo(java.lang.String)
	 */
	@Override
	public void setErrorInfo(final String errorMessage) {
		if (errorMessage == null) {
			errorInfo = errorMessage;
			return;
		}
		if (errorInfo == null) {
			errorInfo = errorMessage;
		} else {
			errorInfo = errorInfo + System.getProperty("line.separator") + errorMessage;
		}
	}

	/**
	 * @see com.hirain.mct.model.standard.IRoot#getDeviceStructures()
	 */
	@Override
	public List<IDeviceStructure> getDeviceStructures() {
		return deviceStructures;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPropertyChangeSupport#getPropertyChangeSupport()
	 */
	@Override
	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	@Override
	public Map<String, Object> getPropertyMap() {
		return propertyCache;
	}

	@Override
	public String toString() {
		return "Root [excelVersion=" + excelVersion + ", appVersion=" + appVersion + ", uuid=" + uuid + ", description=" + description
				+ ", dataTypes=" + dataTypes + ", devices=" + devices + ", ports=" + ports + ", datagrams=" + datagrams + ", deviceStructures="
				+ deviceStructures + ", name=" + name + ", busType=" + busType + ", propertyCache=" + propertyCache + ", xmlPath=" + xmlPath
				+ ", cacheModel=" + cacheModel + ", errorInfo=" + errorInfo + "]";
	}
}
