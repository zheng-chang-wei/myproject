package com.hirain.phm.synapsis.mvb.model.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.hirain.phm.synapsis.mvb.model.IDataType;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 下午2:38:23
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "baseType", "name", "length", "range", "description" })
public class DataType implements IDataType {

	private static final long serialVersionUID = 4371842922882925519L;

	@XmlAttribute(name = "baseType", required = true)
	private String baseType;

	@XmlAttribute(name = "name", required = true)
	private String name;

	@XmlAttribute(name = "range", required = true)
	private String range;

	@XmlAttribute(name = "length", required = true)
	private int length;

	@XmlAttribute(name = "description", required = true)
	private String description;

	/**
	 * @return the baseType
	 */
	@Override
	public String getBaseType() {
		return baseType;
	}

	/**
	 * @param baseType
	 *            the baseType to set
	 */
	@Override
	public void setBaseType(final String baseType) {
		this.baseType = baseType;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDataType#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDataType#setName(java.lang.String)
	 */
	@Override
	public void setName(final String name) {
		this.name = name;
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
	 * @see com.hirain.mct.model.standard.IDataType#getLength()
	 */
	@Override
	public int getLength() {
		return length;
	}

	/**
	 * @see com.hirain.mct.model.standard.IDataType#setLength(int)
	 */
	@Override
	public void setLength(final int length) {
		this.length = length;
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

	@Override
	public String toString() {
		return "DataType [baseType=" + baseType + ", name=" + name + ", range=" + range + ", length=" + length + ", description=" + description + "]";
	}

}
