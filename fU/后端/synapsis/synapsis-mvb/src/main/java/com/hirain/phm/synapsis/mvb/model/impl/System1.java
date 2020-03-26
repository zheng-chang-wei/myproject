package com.hirain.phm.synapsis.mvb.model.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.hirain.phm.synapsis.mvb.model.ISystem;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 下午2:43:09
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "description" })
public class System1 implements ISystem {

	private static final long serialVersionUID = 1193870455371509024L;

	@XmlAttribute(name = "id", required = true)
	private String id;

	@XmlAttribute(name = "name", required = true)
	private String name;

	@XmlAttribute(name = "description", required = true)
	private String description;

	public System1() {
	}

	public System1(final String id) {
		super();
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

	@Override
	public String toString() {
		return "System1 [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

}
