/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.db;

import java.util.List;

import com.hirain.phm.synapsis.setting.Subsystem;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月24日 上午9:42:35
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月24日 changwei.zheng@hirain.com 1.0 create file
 */
public interface SubsystemService {

	List<Subsystem> selectAllSubsystems();

	List<Subsystem> selectByLikeName(String name);

	boolean isRepeat(Subsystem subsystem);

	void save(Subsystem subsystem);

	void update(Subsystem subsystem);

	void deleteById(Integer[] ids);

	void save(List<Subsystem> subsystems);

}
