package com.charlie.resource.commons;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-service.xml",
		"classpath:**/spring-*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@Transactional("transactionManager")
public class GenericTest extends AbstractJUnit4SpringContextTests {

	protected final Logger log = Logger.getLogger(this.getClass());

}
