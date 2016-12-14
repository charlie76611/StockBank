package com.charlie.transaction.web;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import com.charlie.resource.commons.BaseActionBean;
import com.charlie.transaction.service.StockTransService;

@UrlBinding("/trans/stockTrans.action")
public class StockTransActionBean extends BaseActionBean {

	private String stockNumber;
	private String stockPrice;
	
	@SpringBean
	private StockTransService service;
	
	@DefaultHandler
	public Resolution getStockInfo() {
		
		try {
			stockPrice = service.getOneStockInfomation(stockNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ForwardResolution("/defaultMain.tiles");
	}

	public String getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(String stockNumber) {
		this.stockNumber = stockNumber;
	}

	public String getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(String stockPrice) {
		this.stockPrice = stockPrice;
	}
}
