package com.charlie.transaction.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.charlie.resource.commons.GenericIBatisDao;
import com.charlie.transaction.model.RandomDinnerModel;

@Repository
public class RandomDinnerDao extends GenericIBatisDao<RandomDinnerModel, Long> {

	public RandomDinnerDao() {
		super.setNamespace("dinnerMain");
	}
	
	/** 修改隨機次數累加 */
	public int updateRandomTimes(String sysid, int randomtimes) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sysid", sysid);
		map.put("randomTimes", randomtimes);
		return getSqlMapClientTemplate().update(namespace + ".updateRandomTimes", map);
	}
	
}
