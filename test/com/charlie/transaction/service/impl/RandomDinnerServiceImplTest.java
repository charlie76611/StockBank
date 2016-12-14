package com.charlie.transaction.service.impl;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.charlie.resource.commons.GenericTest;
import com.charlie.resource.commons.QueryConditionModel;
import com.charlie.transaction.model.RandomDinnerModel;
import com.charlie.transaction.service.RandomDinnerService;

public class RandomDinnerServiceImplTest extends GenericTest {

	@Autowired
	private RandomDinnerService service;
	
	@Test
	public void testGetRandomDinner() {
		QueryConditionModel<RandomDinnerModel> qcModel = new QueryConditionModel<RandomDinnerModel>();
		
		try {
			RandomDinnerModel model = service.getRandomDinner(qcModel);
			System.out.println(model.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Test
	public void testGetAll() {
		fail("Not yet implemented");
	}

//	@Test
	public void testGetByQueryCondition() {
		fail("Not yet implemented");
	}

//	@Test
	public void testGetById() {
		fail("Not yet implemented");
	}

}
