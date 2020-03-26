package com.hirain.qsy.shaft.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class AxleExceptionStateData implements Comparable<AxleExceptionStateData>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String isException;

	private String axleName;

	public AxleExceptionStateData(String axlename, String isexception) {
		this.axleName = axlename;
		this.isException = isexception;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof AxleExceptionStateData) {
			if (this.getAxleName() == ((AxleExceptionStateData) obj).getAxleName())
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Integer.parseInt(getAxleName());
	}

	@Override
	public int compareTo(AxleExceptionStateData o) {
		int num = Integer.parseInt(this.getAxleName()) - Integer.parseInt(o.getAxleName());

		return num;
	}

}
