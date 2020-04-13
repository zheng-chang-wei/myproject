package com.hirain.ptu.common.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class QueryRequest implements Serializable {

	private static final long serialVersionUID = -4869594085374385813L;

	private Integer pageSize;

	private Integer pageNum;

}
