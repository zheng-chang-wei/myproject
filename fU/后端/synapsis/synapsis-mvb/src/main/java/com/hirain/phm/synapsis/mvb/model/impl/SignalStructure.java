package com.hirain.phm.synapsis.mvb.model.impl;

import java.beans.PropertyChangeSupport;

import com.hirain.phm.synapsis.mvb.model.IPortStructure;
import com.hirain.phm.synapsis.mvb.model.IRoot;
import com.hirain.phm.synapsis.mvb.model.ISignal;
import com.hirain.phm.synapsis.mvb.model.ISignalStructure;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 下午2:42:31
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
public class SignalStructure implements ISignalStructure {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8483015581198090298L;

	private PropertyChangeSupport propertyChangeSupport;

	private IPortStructure parent;

	private String description;

	private ISignal signal;

	public SignalStructure() {
	}

	public SignalStructure(final ISignal signal) {
		setSignal(signal);
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
	 * @see com.hirain.mct.model.standard.IStructure#getRoot()
	 */
	@Override
	public IRoot getRoot() {
		return parent.getRoot();
	}

	/**
	 * @see com.hirain.mct.model.standard.IPropertyChangeSupport#getPropertyChangeSupport()
	 */
	@Override
	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignalStructure#getSignal()
	 */
	@Override
	public ISignal getSignal() {
		return signal;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignalStructure#setSignal(com.hirain.mct.model.standard.ISignal)
	 */
	@Override
	public void setSignal(final ISignal signal) {
		this.signal = signal;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignalStructure#getParent()
	 */
	@Override
	public IPortStructure getParent() {
		return parent;
	}

	/**
	 * @see com.hirain.mct.model.standard.ISignalStructure#setParent(com.hirain.mct.model.standard.IPortStructure)
	 */
	@Override
	public void setParent(final IPortStructure parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "SignalStructure [parent=" + parent + ", description=" + description + ", signal=" + signal + "]";
	}

}
