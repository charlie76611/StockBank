package com.charlie.resource.commons;


/**
 * EXCEL Validate interface
 * Use guide：
 * 1.Prepare an entity and new a Service to extends this 
 * 2.Overwrite abstract method setEntity(String[] oneRowCellsArray);
 * 3.Call readExcel methods so that return a List<T>
 * 
 *
 *
 * @author Arda Liao
 *
 */
public interface ExcelValidateInterface<T> {

	public String validateModel(Integer rowLine, T record) throws Exception;
}
