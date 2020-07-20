package com.hirain.phm.bd.ground.life.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.life.domain.LifeDoorItem;
import com.hirain.phm.bd.ground.life.param.LifeDoorItemAVG;

public interface LifeDoorItemMapper extends CommonMapper<LifeDoorItem> {

	/**
	 * 通过列车ID、车厢号、车门地址查找对应的寿命项点信息的集合
	 */
	@Select("select * from t_life_dooritem where train_id=#{trainId} and car_no=#{carNo} and door_addr=#{doorAddr}")
	List<LifeDoorItem> findDoorItemLifes(Integer trainId, Integer carNo, Integer doorAddr);

	/**
	 * 通过列车ID、车厢号、车门地址、寿命项点ID查找对应的寿命项点信息的集合
	 */
	@Select("select * from t_life_dooritem where train_id=#{trainId} and car_no=#{carNo} and door_addr=#{doorAddr} and lifeitem_id=#{lifeitemId}")
	LifeDoorItem findDoorItemLife(Integer trainId, Integer carNo, Integer doorAddr, Integer lifeitemId);

	/**
	 * 更新指定数据
	 */
	@UpdateProvider(type = DoorItemLifeMapperProvider.class, method = "updateDoorItemLifeDataByParams")
	void updateDoorItemLifeData(LifeDoorItem doorItemLife);

	class DoorItemLifeMapperProvider {

		public String updateDoorItemLifeDataByParams(LifeDoorItem doorItemLife) {
			String sql = "update  t_life_dooritem set t_life_dooritem.value=#{value}" + "  where true";

			sql += " and t_life_dooritem.lifeitem_id=#{lifeitemId}";
			sql += " and t_life_dooritem.train_id = #{trainId}";
			sql += " and t_life_dooritem.car_no = #{carNo}";
			return sql;
		}

	}

	@Select("SELECT item_name, used_life, reference_value FROM t_life_traininfo tlt RIGHT JOIN ( SELECT id, item_name, used_life FROM t_life_item tli RIGHT JOIN ( SELECT train_id, lifeitem_id, AVG(VALUE) AS used_life FROM t_life_dooritem GROUP BY lifeitem_id ) tld ON tld.lifeitem_id = tli.id ) tl ON tlt.lifeitem_id = tl.id WHERE tlt.train_id  = #{trainId}")
	List<LifeDoorItemAVG> findDoorItemAVGValue(Integer trainId);
}