package com.charlie.transaction.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class StockTransService {

	protected Logger log = Logger.getLogger(this.getClass());
	
	public String getOneStockInfomation(String stockNumber) throws Exception {
		return getCsvDataFromUrl("http://mis.twse.com.tw/data/" + stockNumber +".csv");
	}
	
	private String getCsvDataFromUrl(String requestUrl) throws MalformedURLException, IOException {
		log.debug("requestUrl is " + requestUrl);
		
		System.setProperty("java.net.useSystemProxies", "true"); 
		InputStream fis = new URL(requestUrl).openStream();
		
//		FileInputStream fis = new FileInputStream(requestUrl);
		BufferedReader bReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
        StringBuffer sbfFileContents = new StringBuffer();
        String line = null, strContent = null;
       
        //read file line by line
        while( (line = bReader.readLine()) != null){
                sbfFileContents.append(line);
        }
       
        //finally convert StringBuffer object to String!
        strContent = sbfFileContents.toString();
        
        return StringUtils.split(strContent, ",")[0];
	}

}
