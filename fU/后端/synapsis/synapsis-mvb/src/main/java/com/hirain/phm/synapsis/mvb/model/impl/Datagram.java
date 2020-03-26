package com.hirain.phm.synapsis.mvb.model.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.hirain.phm.synapsis.mvb.model.ByteOrder;
import com.hirain.phm.synapsis.mvb.model.IDatagram;
import com.hirain.phm.synapsis.mvb.model.ISignal;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 下午2:37:55
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "byteOrder", "idleValue", "sheetName", "description", "signals" })
public class Datagram implements IDatagram {

	private static final long serialVersionUID = -3295112065474031619L;

	@XmlAttribute(name = "description", required = true)
	private String description;

	@XmlAttribute(name = "id", required = true)
	private String id;

	@XmlAttribute(name = "name", required = true)
	private String name;

	@XmlAttribute(name = "byteOrder", required = true)
	private ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;

	@XmlAttribute(name = "idleValue", required = true)
	private int idleValue;

	@XmlElement(name = "signal", required = true, type = Signal.class)
	private final List<ISignal> signals = new ArrayList<ISignal>();

	@XmlAttribute(name = "sheetName", required = true)
	private String sheetName;

	public Datagram() {
	}

	public Datagram(final String diagramId) {
		id = diagramId;
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
	 * @see com.hirain.mct.model.standard.IDatagram#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDatagram#setId(java.lang.String)
	 */
	@Override
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDatagram#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDatagram#setName(java.lang.String)
	 */
	@Override
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDatagram#getByteOrder()
	 */
	@Override
	public ByteOrder getByteOrder() {
		return byteOrder;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDatagram#setByteOrder(com.hirain.mct.model.standard.ByteOrder)
	 */
	@Override
	public void setByteOrder(final ByteOrder byteOrder) {
		this.byteOrder = byteOrder;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDatagram#getIdleValue()
	 */
	@Override
	public int getIdleValue() {
		return idleValue;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDatagram#setIdleValue(int)
	 */
	@Override
	public void setIdleValue(final int idleValue) {
		this.idleValue = idleValue;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDatagram#getSignals()
	 */
	@Override
	public List<ISignal> getSignals() {
		return signals;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDatagram#getSheetName()
	 */
	@Override
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDatagram#setSheetName(java.lang.String)
	 */
	@Override
	public void setSheetName(final String sheetName) {
		this.sheetName = sheetName;
	}

	@Override
	public String toString() {
		return "Datagram [description=" + description + ", name=" + name + ", byteOrder=" + byteOrder + ", idleValue=" + idleValue + ", signals="
				+ signals + ", sheetName=" + sheetName + "]";
	}

}
