package com.hirain.phm.synapsis.mvb.model.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.hirain.phm.synapsis.mvb.model.ISignal;
import com.hirain.phm.synapsis.setting.BusDataType;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 下午2:42:13
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "path", "variableId", "signalName", "dataType", "byteOffset", "bitOffset", "length", "range", "fomula", "factor",
		"offset", "unit", "locked", "keyWord", "universal", "description" })
public class Signal implements ISignal {

	private static final long serialVersionUID = 1908929407973314350L;

	@XmlAttribute(name = "varId", required = true)
	private String variableId;

	@XmlAttribute(name = "signalName", required = true)
	private String signalName;

	@XmlAttribute(name = "dataType", required = true)
	private BusDataType dataType;

	@XmlAttribute(name = "length", required = true)
	private int length;

	@XmlAttribute(name = "range", required = true)
	private String range;

	@XmlAttribute(name = "fomula", required = true)
	private boolean fomula;

	@XmlAttribute(name = "factor", required = true)
	private double factor = 1.0;

	@XmlAttribute(name = "offset", required = true)
	private double offset;

	@XmlAttribute(name = "unit", required = true)
	private String unit;

	@XmlAttribute(name = "keyWord", required = true)
	private String keyWord;

	@XmlAttribute(name = "universal", required = true)
	private String universal;

	@XmlAttribute(name = "description", required = true)
	private String description;

	@XmlTransient
	private Object value;

	@XmlAttribute(name = "id", required = true)
	private String id;

	@XmlAttribute(name = "path", required = true)
	private String path;

	@XmlAttribute(name = "byteOffset", required = true)
	private int byteOffset;

	@XmlAttribute(name = "bitOffset", required = true)
	private int bitOffset;

	@XmlAttribute(name = "locked", required = true)
	private boolean locked;

	public Signal() {
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#getVariableId()
	 */
	@Override
	public String getVariableId() {
		return variableId;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#setVariableId(java.lang.String)
	 */
	@Override
	public void setVariableId(final String variableId) {
		this.variableId = variableId;
	}

	/**
	 * @return the signalName
	 */
	@Override
	public String getSignalName() {
		return signalName;
	}

	/**
	 * @param signalName
	 *            the signalName to set
	 */
	@Override
	public void setSignalName(final String signalName) {
		this.signalName = signalName;
	}

	/**
	 * @return the dataType
	 */
	@Override
	public BusDataType getDataType() {
		return dataType;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#setDataType(com.hirain.mct.model.standard.BusDataType)
	 */
	@Override
	public void setDataType(final BusDataType dataType) {
		this.dataType = dataType;
		switch (dataType) {
		case BITS:
			break;
		default:
			setLength(dataType.getSize());
			break;
		}
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#getLength()
	 */
	@Override
	public int getLength() {
		return length;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#setLength(int)
	 */
	@Override
	public void setLength(final int length) {
		this.length = length;
	}

	/**
	 * @return the range
	 */
	@Override
	public String getRange() {
		return range;
	}

	/**
	 * @param range
	 *            the range to set
	 */
	@Override
	public void setRange(final String range) {
		this.range = range;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#isFomula()
	 */
	@Override
	public boolean isFomula() {
		return fomula;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#setFomula(boolean)
	 */
	@Override
	public void setFomula(final boolean fomula) {
		this.fomula = fomula;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#getFactor()
	 */
	@Override
	public double getFactor() {
		return factor;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#setFactor(double)
	 */
	@Override
	public void setFactor(final double factor) {
		this.factor = factor;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#getOffset()
	 */
	@Override
	public double getOffset() {
		return offset;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#setOffset(double)
	 */
	@Override
	public void setOffset(final double offset) {
		this.offset = offset;
	}

	/**
	 * @return the unit
	 */
	@Override
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit
	 *            the unit to set
	 */
	@Override
	public void setUnit(final String unit) {
		this.unit = unit;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#getKeyWord()
	 */
	@Override
	public String getKeyWord() {
		return keyWord;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#setKeyWord(java.lang.String)
	 */
	@Override
	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#getUniversal()
	 */
	@Override
	public String getUniversal() {
		return universal;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#setUniversal(java.lang.String)
	 */
	@Override
	public void setUniversal(final String universal) {
		this.universal = universal;
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
	 * @see com.hirain.mct.model.standard.ISignal#getValue()
	 */
	@Override
	public Object getValue() {
		return value;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(final Object value) {
		this.value = value;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#getValueString()
	 */
	@Override
	public String getValueString() {
		return dataType.toString(value);
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#setId(java.lang.String)
	 */
	@Override
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#getPath()
	 */
	@Override
	public String getPath() {
		return path;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#setPath(java.lang.String)
	 */
	@Override
	public void setPath(final String path) {
		this.path = path;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#getByteOffset()
	 */
	@Override
	public int getByteOffset() {
		return byteOffset;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#setByteOffset(int)
	 */
	@Override
	public void setByteOffset(final int byteOffset) {
		this.byteOffset = byteOffset;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#getBitOffset()
	 */
	@Override
	public int getBitOffset() {
		return bitOffset;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#setBitOffset(int)
	 */
	@Override
	public void setBitOffset(final int bitOffset) {
		this.bitOffset = bitOffset;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#isLocked()
	 */
	@Override
	public boolean isLocked() {
		return locked;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignal#setLocked(boolean)
	 */
	@Override
	public void setLocked(final boolean locked) {
		this.locked = locked;
	}

	@Override
	public String toString() {
		return "Signal [variableId=" + variableId + ", signalName=" + signalName + ", dataType=" + dataType + ", length=" + length + ", range="
				+ range + ", fomula=" + fomula + ", factor=" + factor + ", offset=" + offset + ", unit=" + unit + ", keyWord=" + keyWord
				+ ", universal=" + universal + ", description=" + description + ", value=" + value + ", path=" + path + ", byteOffset=" + byteOffset
				+ ", bitOffset=" + bitOffset + ", locked=" + locked + "]";
	}

}
