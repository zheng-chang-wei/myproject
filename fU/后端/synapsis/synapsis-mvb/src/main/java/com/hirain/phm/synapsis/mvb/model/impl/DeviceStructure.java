package com.hirain.phm.synapsis.mvb.model.impl;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.hirain.phm.synapsis.mvb.model.IDevice;
import com.hirain.phm.synapsis.mvb.model.IDeviceStructure;
import com.hirain.phm.synapsis.mvb.model.IPortStructure;
import com.hirain.phm.synapsis.mvb.model.IRoot;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 下午2:39:53
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
@XmlType(propOrder = { "address", "car", "system", "description", "children" })
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceStructure implements IDeviceStructure {

	private static final long serialVersionUID = -2577369404804742623L;

	@XmlAttribute(name = "description", required = true)
	private String description;

	@XmlAttribute(name = "deviceAddress", required = true)
	private short address;

	@XmlAttribute(name = "car", required = true)
	private String car;

	@XmlAttribute(name = "system", required = true)
	private String system;

	@XmlElement(name = "PortStructure", required = true, type = PortStructure.class)
	private final List<IPortStructure> children = new ArrayList<>();

	@XmlTransient
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	@XmlTransient
	private IDevice device;

	@XmlTransient
	private IRoot parent;

	@XmlTransient
	private final List<IPortStructure> portStructureSource = new ArrayList<>();

	@XmlTransient
	private final List<IPortStructure> portStructureSink = new ArrayList<>();

	public DeviceStructure() {
	}

	/**
	 * �Զ��趨structure�󶨵ĵ�ַ
	 * 
	 * @param device
	 */
	public DeviceStructure(final IDevice device) {
		setDevice(device);
	}

	/**
	 * @see com.hirain.mct.model.standard.IStandardModel#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * @see com.hirain.mct.model.standard.IStandardModel#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDeviceStructure#getAddress()
	 */
	@Override
	public short getAddress() {
		return address;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDeviceStructure#setAddress(short)
	 */
	@Override
	public void setAddress(final short address) {
		this.address = address;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDeviceStructure#getCar()
	 */
	@Override
	public String getCar() {
		return car;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDeviceStructure#setCar(java.lang.String)
	 */
	@Override
	public void setCar(final String car) {
		this.car = car;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPropertyChangeSupport#getPropertyChangeSupport()
	 */
	@Override
	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDeviceStructure#getChildren()
	 */
	@Override
	public List<IPortStructure> getChildren() {
		return children;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDeviceStructure#getDevice()
	 */
	@Override
	public IDevice getDevice() {
		return device;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDeviceStructure#setDevice(com.hirain.mct.model.standard.IDevice)
	 */
	@Override
	public void setDevice(final IDevice device) {
		this.device = device;
		if (device == null) {
			return;
		}
		setAddress(device.getAddress());
	}

	/**
	 * @see com.hirain.mct.model.standard.IDeviceStructure#getPortSource()
	 */
	@Override
	public List<IPortStructure> getPortStructureSource() {
		return portStructureSource;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDeviceStructure#getPortSink()
	 */
	@Override
	public List<IPortStructure> getPortStructureSink() {
		return portStructureSink;
	}

	/**
	 * @see com.hirain.mct.model.standard.IStructure#getRoot()
	 */
	@Override
	public IRoot getRoot() {
		return getParent();
	}

	/**
	 * @see com.hirain.mct.model.standard.IDeviceStructure#getSystem()
	 */
	@Override
	public String getSystem() {
		return system;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDeviceStructure#setSystem(java.lang.String)
	 */
	@Override
	public void setSystem(final String system) {
		this.system = system;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDeviceStructure#getParent()
	 */
	@Override
	public IRoot getParent() {
		return parent;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDeviceStructure#setParent(com.hirain.mct.model.standard.IRoot)
	 */
	@Override
	public void setParent(final IRoot parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "DeviceStructure [description=" + description + ", address=" + address + ", car=" + car + ", system=" + system + ", children="
				+ children + ", device=" + device + ", parent=" + parent + ", portStructureSource=" + portStructureSource + ", portStructureSink="
				+ portStructureSink + "]";
	}

}
