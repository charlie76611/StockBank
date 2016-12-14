package com.charlie.resource.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;

public class AutowiredSqlMapClientDaoSupport extends SqlMapClientDaoSupport {
	@Autowired
	public void setSqlMapClientForAutowire(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}
}
