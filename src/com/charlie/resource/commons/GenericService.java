package com.charlie.resource.commons;

import com.charlie.transaction.model.RandomDinnerModel;

public interface GenericService<T> {
	
	public QueryConditionModel<RandomDinnerModel> getAll() throws Exception;

	public QueryConditionModel<T> getByQueryCondition(QueryConditionModel<T> qcModel) throws Exception;
	
	public T getById(Long sysid) ;
	
	public T insert(T model, String account) throws Exception ;
	
	public T update(T model, String account) throws Exception ;
	
	public void delete(T model, String account) throws Exception ;
	
	public void delete(Long sysid, String account) throws Exception ;
	
	public void delete(Long[] sysids, String account) throws Exception ;
	
}
