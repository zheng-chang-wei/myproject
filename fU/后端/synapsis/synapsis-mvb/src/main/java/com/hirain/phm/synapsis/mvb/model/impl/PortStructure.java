package com.hirain.phm.synapsis.mvb.model.impl;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.hirain.phm.synapsis.mvb.model.IDatagram;
import com.hirain.phm.synapsis.mvb.model.IDeviceStructure;
import com.hirain.phm.synapsis.mvb.model.IPort;
import com.hirain.phm.synapsis.mvb.model.IPortStructure;
import com.hirain.phm.synapsis.mvb.model.IRoot;
import com.hirain.phm.synapsis.mvb.model.ISignalStructure;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 下午2:41:21
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
@XmlType(propOrder = { "address", "deviceAddrSource", "deviceAddrSinks", "datagramId", "enable", "description" })
@XmlAccessorType(XmlAccessType.FIELD)
public class PortStructure implements IPortStructure {

	private static final long serialVersionUID = 5456153983807675556L;

	@XmlAttribute(name = "description", required = true)
	private String description;

	@XmlAttribute(name = "portAddress", required = true)
	private short address;

	@XmlAttribute(name = "deviceAddressSource", required = true)
	private short deviceAddrSource;

	@XmlAttribute(name = "deviceAddressSinks", required = true)
	private String deviceAddrSinks;

	@XmlAttribute(name = "datagramId", required = true)
	private String datagramId;

	@XmlAttribute(name = "enable", required = true)
	private boolean enable;

	@XmlTransient
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	@XmlTransient
	private boolean source = true;

	@XmlTransient
	private IPort port;

	@XmlTransient
	private IDeviceStructure parent;

	@XmlTransient
	private final List<ISignalStructure> children = new ArrayList<ISignalStructure>();

	@XmlTransient
	private IDatagram datagram;

	public PortStructure() {
	}

	// /**
	// * 自动绑定PortStructure的地址
	// *
	// * @param port
	// */
	// public PortStructure(final IPort port) {
	// setPort(port);
	// }

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
	 * @see com.hirain.mct.model.standard.IPortStructure#getAddress()
	 */
	@Override
	public short getAddress() {
		return address;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#setAddress(short)
	 */
	@Override
	public void setAddress(final short address) {
		this.address = address;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#getDeviceAddrSource()
	 */
	@Override
	public short getDeviceAddrSource() {
		return deviceAddrSource;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#setDeviceAddrSource(short)
	 */
	@Override
	public void setDeviceAddrSource(final short deviceAddrSource) {
		this.deviceAddrSource = deviceAddrSource;
		if (parent != null) {
			setSource(parent.getAddress() == getDeviceAddrSource());
		}
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#getDeviceAddrSink()
	 */
	@Override
	public String getDeviceAddrSinks() {
		return deviceAddrSinks;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#setDeviceAddrSink(short)
	 */
	@Override
	public void setDeviceAddrSinks(final String deviceAddrSinks) {
		this.deviceAddrSinks = deviceAddrSinks;
		if (parent != null) {
			final String addressParent = parent.getAddress() + "";
			final String[] split = deviceAddrSinks.split(",");
			final List<String> asList = Arrays.asList(split);
			final boolean contains = asList.contains(addressParent);
			setSource(!contains);
		}
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#getDatagramId()
	 */
	@Override
	public String getDatagramId() {
		return datagramId;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#setDatagramId(java.lang.String)
	 */
	@Override
	public void setDatagramId(final String datagramId) {
		this.datagramId = datagramId;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#isEnable()
	 */
	@Override
	public boolean isEnable() {
		return enable;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#setEnable(boolean)
	 */
	@Override
	public void setEnable(final boolean enable) {
		this.enable = enable;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPropertyChangeSupport#getPropertyChangeSupport()
	 */
	@Override
	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#isSource()
	 */
	@Override
	public boolean isSource() {
		return source;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#setSource(boolean)
	 */
	@Override
	public void setSource(final boolean source) {
		this.source = source;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#getPort()
	 */
	@Override
	public IPort getPort() {
		return port;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#setPort(com.hirain.mct.model.standard.IPort)
	 */
	@Override
	public void setPort(final IPort port) {
		this.port = port;
		if (port == null) {
			return;
		}
		setAddress(port.getAddress());
		// IDatagram iDatagram = getRoot().getCacheModel().getDatagramByPortAddr().get(address);
		// if (iDatagram == null) {
		// iDatagram = new Datagram();
		// iDatagram.setId(UUID.randomUUID().toString());
		// getRoot().getDatagrams().add(iDatagram);
		// getRoot().getCacheModel().getDatagramsKeyId().put(iDatagram.getId(), iDatagram);
		// getRoot().getCacheModel().getDatagramByPortAddr().put(address, iDatagram);
		// }
		// setDatagramId(iDatagram.getId());
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#setDatagram(com.hirain.mct.model.standard.IDatagram)
	 */
	@Override
	public void setDatagram(final IDatagram datagram) {
		this.datagram = datagram;
		if (datagram != null) {
			setDatagramId(datagram.getId());
		} else {
			setDatagramId(null);
		}
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#getChildren()
	 */
	@Override
	public List<ISignalStructure> getChildren() {
		return children;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#setParent(com.hirain.mct.model.standard.IDeviceStructure)
	 */
	@Override
	public void setParent(final IDeviceStructure parent) {
		this.parent = parent;
		if (parent == null) {
			return;
		}
		setSource(parent.getAddress() == getDeviceAddrSource());
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#getParent()
	 */
	@Override
	public IDeviceStructure getParent() {
		return parent;
	}

	/**
	 * @see com.hirain.mct.model.standard.IStructure#getRoot()
	 */
	@Override
	public IRoot getRoot() {
		return parent.getRoot();
	}

	/**
	 * @see com.hirain.mct.model.standard.IPortStructure#getDatagram()
	 */
	@Override
	public IDatagram getDatagram() {
		return datagram;
	}

	@Override
	public String toString() {
		return "PortStructure [description=" + description + ", address=" + address + ", deviceAddrSource=" + deviceAddrSource + ", deviceAddrSinks="
				+ deviceAddrSinks + ", enable=" + enable + ", source=" + source + ", port=" + port + ", parent=" + parent + ", children=" + children
				+ ", datagram=" + datagram + "]";
	}

}
