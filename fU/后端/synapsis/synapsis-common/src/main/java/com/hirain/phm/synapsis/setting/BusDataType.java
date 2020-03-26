package com.hirain.phm.synapsis.setting;

import java.util.Arrays;

import lombok.Getter;

public enum BusDataType {
	CHAR8(8, true, 1),
	//
	BOOLEAN(1, false, 1),
	//
	UINT8(8, true, 1),
	//
	INT8(8, true, 1),
	//
	UINT16(16, true, 2),
	//
	INT16(16, true, 2),
	//
	UINT32(32, true, 4),
	//
	INT32(32, true, 4),
	//
	FLOAT(32, true, 4),
	//
	DOUBLE(64, true, 8),
	//
	LONG(64, true, 8),
	//
	BITS(-1, false, 1);

	@Getter
	private final int size;

	@Getter
	private final boolean number;

	@Getter
	private int byteLen;

	private BusDataType(final int size, final boolean number, int byteLen) {
		this.size = size;
		this.number = number;
		this.byteLen = byteLen;
	}

	public String toString(final Object value) {
		String str;
		if (value instanceof byte[]) {
			str = Arrays.toString((byte[]) value);
		} else {
			str = value.toString();
		}
		return str;
	}
}