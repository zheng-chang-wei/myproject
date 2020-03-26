package com.hirain.phm.synapsis.mvb.model.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.hirain.phm.synapsis.mvb.model.IDevice;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 下午2:39:39
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "address", "name", "description" })
public class Device implements IDevice {

	private static final long serialVersionUID = -1003487972322194347L;

	@XmlAttribute(name = "address", required = true)
	private short address;

	@XmlAttribute(name = "name", required = true)
	private String name;

	@XmlAttribute(name = "description", required = true)
	private String description;

	public Device() {
	}

	public Device(final short address) {
		this.address = address;
	}

	/**
	 * @return the address
	 */
	@Override
	public short getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	@Override
	public void setAddress(final short address) {
		this.address = address;
	}

	/**
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	@Override
	public void setName(final String name) {
		this.name = name;
	}

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

	@Override
	public String toString() {
		return "Device [address=" + address + ", name=" + name + ", description=" + description + "]";
	}

}
