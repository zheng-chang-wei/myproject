package com.hirain.phm.synapsis.result;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import com.hirain.phm.synapsis.result.domain.ResultSegmentContainer;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "root")
public class XMLRoot {

	@XmlElementRef(name = "container")
	private List<ResultSegmentContainer> containers;

	/**
	 * @return the containers
	 */
	public List<ResultSegmentContainer> getContainers() {
		return containers;
	}

	/**
	 * @param containers
	 *            the containers to set
	 */
	public void setContainers(List<ResultSegmentContainer> containers) {
		this.containers = containers;
	}
}