package com.hirain.phm.synapsis.mvb.model.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.hirain.phm.synapsis.mvb.model.IPort;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 下午2:40:09
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "address", "name", "size", "circle", "timeSlot", "description" })
public class Port implements IPort {

	private static final long serialVersionUID = 3177562866020153392L;

	@XmlAttribute(name = "address", required = true)
	private short address;

	@XmlAttribute(name = "size", required = true)
	private int size;

	@XmlTransient
	private int fCode;

	@XmlAttribute(name = "circle", required = true)
	private double circle;

	@XmlAttribute(name = "timeSlot", required = true)
	private int timeSlot;

	@XmlAttribute(name = "description", required = true)
	private String description;

	@XmlAttribute(name = "name", required = true)
	private String name;

	public Port() {
	}

	public Port(final short address, final int size, final double circle) {
		this.address = address;
		this.size = size;
		this.circle = circle;
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
	 * @see com.hirain.mct.model.standard.IPort#getSize()
	 */
	@Override
	public int getSize() {
		return size;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPort#setSize(int)
	 */
	@Override
	public void setSize(final int size) {
		this.size = size;
	}

	/**
	 * @return the circle
	 */
	@Override
	public double getCircle() {
		return circle;
	}

	/**
	 * @param circle
	 *            the circle to set
	 */
	@Override
	public void setCircle(final double circle) {
		this.circle = circle;
	}

	/**
	 * @return the timeSlot
	 */
	@Override
	public int getTimeSlot() {
		return timeSlot;
	}

	/**
	 * @param timeSlot
	 *            the timeSlot to set
	 */
	@Override
	public void setTimeSlot(final int timeSlot) {
		this.timeSlot = timeSlot;
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

	/**
	 * @see com.hirain.mct.model.standard.IPort#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @see com.hirain.mct.model.standard.IPort#setName(java.lang.String)
	 */
	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Port [address=" + address + ", size=" + size + ", circle=" + circle + ", timeSlot=" + timeSlot + ", description=" + description
				+ ", name=" + name + "]";
	}

	@Override
	public int getFCode() {
		return fCode;
	}

	@Override
	public void setFCode(int fCode) {
		this.fCode = fCode;
	}

}
