package com.hirain.phm.synapsis.mvb.model.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.hirain.phm.synapsis.mvb.model.ITrain;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 下午2:42:50
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "manufacturer", "description" })
public class Train implements ITrain {

	private static final long serialVersionUID = -1904581907388317863L;

	@XmlAttribute(name = "id", required = true)
	private String id;

	@XmlAttribute(name = "name", required = true)
	private String name;

	@XmlAttribute(name = "description", required = true)
	private String description;

	@XmlAttribute(name = "manufacturer", required = true)
	private String manufacturer;

	public Train() {
	}

	public Train(final String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	@Override
	public void setId(final String id) {
		this.id = id;
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

	/**
	 * @return the manufacturer
	 */
	@Override
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer
	 *            the manufacturer to set
	 */
	@Override
	public void setManufacturer(final String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Override
	public String toString() {
		return "Train [id=" + id + ", name=" + name + ", description=" + description + ", manufacturer=" + manufacturer + "]";
	}

}
