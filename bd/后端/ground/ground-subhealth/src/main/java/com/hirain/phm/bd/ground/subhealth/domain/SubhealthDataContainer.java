/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 27, 2019 3:19:35 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 27, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Table(name = "t_subhealth_data")
@Data
public class SubhealthDataContainer {

	@Id
	private Integer id;

	private Integer state;

	@Column(name = "key_json")
	private String keys;

	private String datas;
}
