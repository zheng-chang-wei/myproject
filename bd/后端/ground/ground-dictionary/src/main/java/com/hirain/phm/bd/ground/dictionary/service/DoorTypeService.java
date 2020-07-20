/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.dictionary.domain.DoorType;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 9, 2020 5:47:22 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface DoorTypeService extends IService<DoorType> {

	/**
	 * @param doorType
	 */
	boolean checkDuplicate(DoorType doorType);

	/**
	 * @return
	 */
	List<DoorType> selectAllActive();

	/**
	 * @param doorType
	 */
	void deactive(DoorType doorType);

	/**
	 * @param doorType
	 * @param file
	 * @throws IOException
	 */
	void batchImport(DoorType doorType, MultipartFile file) throws IOException;

}
