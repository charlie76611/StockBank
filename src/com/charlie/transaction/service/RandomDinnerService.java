package com.charlie.transaction.service;

import com.charlie.resource.commons.GenericService;
import com.charlie.resource.commons.QueryConditionModel;
import com.charlie.transaction.model.RandomDinnerModel;

public interface RandomDinnerService extends GenericService<RandomDinnerModel> {

	public RandomDinnerModel getRandomDinner(QueryConditionModel<RandomDinnerModel> qcModel) throws Exception;
}
